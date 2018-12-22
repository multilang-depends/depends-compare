package depends.addons.dv8.matrix.comp;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
	public Comparator() {
		result = new CompareResult();
	} 
	public CompareResult compare(String leftFile, String rightFile) {
		try {
			DependencyMatrixJsonLoader loader = new DependencyMatrixJsonLoader();
			left = loader.loadDependencyMatrix(Paths.get(leftFile));
			right = loader.loadDependencyMatrix(Paths.get(rightFile));
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
			
			for (String commonPair:commonDependencyPairs) {
				DependencyPair leftValue = left.getPairs().get(commonPair);
				DependencyPair rightValue = right.getPairs().get(commonPair);
				Set<String> leftTypes = leftValue.getDependencyTypes();
				Set<String> rightTypes = rightValue.getDependencyTypes();
				Collection<String> commonTypes = CollectionUtils.intersection(leftTypes, rightTypes);
				Collection<String> deletedTypes = CollectionUtils.subtract(leftTypes,commonTypes);
				Collection<String> addedTypes = CollectionUtils.subtract(rightTypes,commonTypes);
				for (String deleteType:deletedTypes) {
					addPairWeightDiff(commonPair,deleteType,leftValue.getDependencyOfType(deleteType).getWeight(),0.0);
				}
				for (String addedType:addedTypes) {
					addPairWeightDiff(commonPair,addedType,0.0,rightValue.getDependencyOfType(addedType).getWeight());
				}
				
				for (String commonType:commonTypes) {
					DependencyValue leftTypeValue = leftValue.getDependencyOfType(commonType);
					DependencyValue rightTypeValue = rightValue.getDependencyOfType(commonType);
					if (leftTypeValue.getWeight()-rightTypeValue.getWeight()>1e-3) {
						addPairWeightDiff(commonPair,commonType,leftTypeValue.getWeight(),rightTypeValue.getWeight());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void addPairWeightDiff(String pairKey, String type, double leftWeight, double rightWeight) {
		if (!result.pairDiffs.containsKey(pairKey)){
			result.pairDiffs.put(pairKey, new PairDiff(pairKey));
		}
		PairDiff diff = result.pairDiffs.get(pairKey);
		diff.addWeightDiff(type,leftWeight,rightWeight);
	}
}
