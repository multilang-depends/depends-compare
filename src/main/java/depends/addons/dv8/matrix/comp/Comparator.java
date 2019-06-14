package depends.addons.dv8.matrix.comp;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import depends.addons.dv8.matrix.comp.data.input.DependenciesRelation;
import depends.addons.dv8.matrix.comp.data.input.DependencyPair;
import depends.addons.dv8.matrix.comp.data.input.DependencyValue;
import depends.addons.dv8.matrix.comp.data.output.CompareResult;
import depends.addons.dv8.matrix.comp.data.output.PairDiff;

public class Comparator {
	CompareResult result;
	DependenciesRelation left;
	DependenciesRelation right;
	private String outputControl;
	private List<String> ignoredTypes;
	public Comparator(String outputControl, String[] ignoredTypes) {
		result = new CompareResult();
		this.outputControl = outputControl;
		this.ignoredTypes = Arrays.asList(ignoredTypes);
	} 
	public CompareResult compare(String leftFile, String rightFile) {
		try {
			DependencyMatrixJsonLoader loader = new DependencyMatrixJsonLoader();
			left = loader.loadDependencyMatrix(Paths.get(leftFile),this.ignoredTypes);
			right = loader.loadDependencyMatrix(Paths.get(rightFile),this.ignoredTypes);
			List<String> variablesLeft = Arrays.asList(left.getVariables());
			List<String> variablesRight = Arrays.asList(right.getVariables());
			Collection<String> commonVariables = CollectionUtils.intersection(variablesLeft, variablesRight);
			result.deletedVariables = CollectionUtils.subtract(variablesLeft,commonVariables);
			result.addedVariables = CollectionUtils.subtract(variablesRight,commonVariables);
			Set<String> relationKeysLeft = left.getPairs().keySet();
			Set<String> relationKeysRight = right.getPairs().keySet();
			Collection<String> commonDependencyPairs = CollectionUtils.intersection(relationKeysLeft, relationKeysRight);
			result.deletedDependencyPairs =  CollectionUtils.subtract(relationKeysLeft,commonDependencyPairs);
			result.addedDependencyPairs =  CollectionUtils.subtract(relationKeysRight,commonDependencyPairs);
			
			result.deletedDependencyPairs = result.deletedDependencyPairs.stream().filter(new Predicate<String>() {
				@Override
				public boolean test(String pairKey) {
					return !right.isDueToVariableRemoval(pairKey,result.deletedVariables);
				}
			}).collect(Collectors.toSet());
			result.addedDependencyPairs.stream().filter(new Predicate<String>() {
				@Override
				public boolean test(String pairKey) {
					return !left.isDueToVariableAdded(pairKey,result.addedVariables);
				}
			}).collect(Collectors.toSet());
			
			
			for (String commonPair:commonDependencyPairs) {
				DependencyPair leftValue = left.getPairs().get(commonPair);
				DependencyPair rightValue = right.getPairs().get(commonPair);
				Set<String> leftTypes = leftValue.getDependencyTypes();
				Set<String> rightTypes = rightValue.getDependencyTypes();
				Collection<String> commonTypes = CollectionUtils.intersection(leftTypes, rightTypes);
				result.getSummary().incrCommonDependencyTypesCount(commonTypes.size());
				Collection<String> deletedTypes = CollectionUtils.subtract(leftTypes,commonTypes);
				Collection<String> addedTypes = CollectionUtils.subtract(rightTypes,commonTypes);
				result.getSummary().incrDeletedDependencyTypesCount(deletedTypes.size());
				result.getSummary().incrAddedDependencyTypesCount(addedTypes.size());
				
				for (String deleteType:deletedTypes) {
					addPairWeightDiff(commonPair,deleteType,leftValue.getDependencyOfType(deleteType).getWeight(),0.0);
				}
				for (String addedType:addedTypes) {
					addPairWeightDiff(commonPair,addedType,0.0,rightValue.getDependencyOfType(addedType).getWeight());
				}
				
				for (String commonType:commonTypes) {
					DependencyValue leftTypeValue = leftValue.getDependencyOfType(commonType);
					DependencyValue rightTypeValue = rightValue.getDependencyOfType(commonType);
					if (isDiff(leftTypeValue.getWeight(), rightTypeValue.getWeight())) {
						addPairWeightDiff(commonPair,commonType,leftTypeValue.getWeight(),rightTypeValue.getWeight());
						result.getSummary().incrWeightDiffCount();
					}
				}
			}
			if (!this.outputControl.contains("a")) {
				result.addedDependencyPairs = new ArrayList<>();
				result.addedVariables = new ArrayList<>();
			}
			if (!this.outputControl.contains("d")) {
				result.deletedDependencyPairs = new ArrayList<>();
				result.deletedVariables = new ArrayList<>();
			}
			result.getSummary().setCounts(commonVariables.size(),result.deletedVariables.size(),result.addedVariables.size(),
					commonDependencyPairs.size(),result.deletedDependencyPairs.size(),result.addedDependencyPairs.size());
		
		} catch (Exception e) {
			e.printStackTrace();
		}


		return result;
	}
	private boolean isDiff(double value1, double value2) {
		return Math.abs(value1-value2)>1e-3;
	}

	private void addPairWeightDiff(String pairKey, String type, double leftWeight, double rightWeight) {
		if (!((this.outputControl.contains("a") && leftWeight<rightWeight)||
				(this.outputControl.contains("d") && leftWeight>rightWeight))) {
			return;
		}
		if (!result.pairDiffs.containsKey(pairKey)){
			result.pairDiffs.put(pairKey, new PairDiff(pairKey));
		}
		PairDiff diff = result.pairDiffs.get(pairKey);
		diff.addWeightDiff(type,leftWeight,rightWeight);
	}
}
