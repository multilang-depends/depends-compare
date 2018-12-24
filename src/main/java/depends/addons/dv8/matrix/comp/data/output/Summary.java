package depends.addons.dv8.matrix.comp.data.output;

public class Summary {
	int commonVariablesCount = 0;
	int variablesLeftCount = 0;
	int variablesRightCount = 0;
	int commonDependencyPairsCount = 0;
	int deletedDependencyPairsCount = 0;
	int addedDependencyPairsCount = 0;
	int commonDependencyTypesCount = 0;
	int deletedTypesCount  = 0;
	int addedTypesCount = 0;
	int weightDiffOnly = 0;
	
	public void setCounts(int commonVariablesCount, int variablesLeftCount, int variablesRightCount,
			int commonDependencyPairsCount, int deletedDependencyPairsCount, int addedDependencyPairsCount) {
		
		this.commonDependencyPairsCount = commonDependencyPairsCount;
		this.variablesLeftCount = variablesLeftCount;
		this.variablesRightCount = variablesRightCount;

		this.commonVariablesCount = commonVariablesCount;
		this.deletedDependencyPairsCount = deletedDependencyPairsCount;
		this.addedDependencyPairsCount = addedDependencyPairsCount;
	}

	public void incrCommonDependencyTypesCount(int commonTypesCount) {
		this.commonDependencyTypesCount += commonTypesCount;
	}

	public void incrDeletedDependencyTypesCount(int deletedTypesCount) {
		this.deletedTypesCount += deletedTypesCount;
	}

	public void incrAddedDependencyTypesCount(int addedTypesCount) {
		this.addedTypesCount += addedTypesCount;
	}

	public void incrWeightDiffCount() {
		this.weightDiffOnly  ++;
	}

	public int getCommonVariablesCount() {
		return commonVariablesCount;
	}

	public int getVariablesLeftCount() {
		return variablesLeftCount;
	}

	public int getVariablesRightCount() {
		return variablesRightCount;
	}

	public int getCommonDependencyPairsCount() {
		return commonDependencyPairsCount;
	}

	public int getDeletedDependencyPairsCount() {
		return deletedDependencyPairsCount;
	}

	public int getAddedDependencyPairsCount() {
		return addedDependencyPairsCount;
	}

	public int getCommonDependencyTypesCount() {
		return commonDependencyTypesCount;
	}

	public int getDeletedTypesCount() {
		return deletedTypesCount;
	}

	public int getAddedTypesCount() {
		return addedTypesCount;
	}

	public int getWeightDiffOnly() {
		return weightDiffOnly;
	}
}
