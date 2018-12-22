package depends.addons.dv8.matrix.comp.data.output;

import java.util.ArrayList;

public class PairDiff{
	public String key;
	public ArrayList<WeightDiff> weightDiffs = new ArrayList<>();

	public PairDiff(String pairKey) {
		this.key = pairKey;
	}

	public void addWeightDiff(String type, double leftWeight, double rightWeight) {
		weightDiffs.add(new WeightDiff(type,leftWeight,rightWeight));
	}
}