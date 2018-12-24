package depends.addons.dv8.matrix.comp.data.output;

import java.util.Collection;
import java.util.HashMap;

public class CompareResult {


	public Collection<String> deletedVariables;
	public Collection<String> addedVariables;
	public Collection<String> deletedDependencyPairs;
	public Collection<String> addedDependencyPairs;
	public HashMap<String,PairDiff> pairDiffs = new HashMap<>();;
	public Summary summary = new Summary();
	
	public void addPairWeightDiff(String pairKey, String type, double leftWeight, double rightWeight) {
		if (!pairDiffs.containsKey(pairKey)){
			pairDiffs.put(pairKey, new PairDiff(pairKey));
		}
		PairDiff diff = pairDiffs.get(pairKey);
		diff.addWeightDiff(type,leftWeight,rightWeight);
	}

	public Collection<String> getDeletedVariables() {
		return deletedVariables;
	}

	public Collection<String> getAddedVariables() {
		return addedVariables;
	}


	public Collection<String> getDeletedDependencyPairs() {
		return deletedDependencyPairs;
	}

	public Collection<String> getAddedDependencyPairs() {
		return addedDependencyPairs;
	}
	public HashMap<String, PairDiff> getPairDiffs() {
		return pairDiffs;
	}

	public Summary getSummary() {
		return this.summary;
	}

	
	
}
