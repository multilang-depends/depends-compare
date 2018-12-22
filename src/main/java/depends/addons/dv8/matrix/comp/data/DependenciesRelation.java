package depends.addons.dv8.matrix.comp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DependenciesRelation {

	private String[] variables;
	private HashMap<String,DependencyPair> pairs;
	
	public DependenciesRelation() {
		pairs = new HashMap<>();
	}
	public void addDependencies(String type, int src, int dest, double weight) {
		if (!pairs.containsKey(DependencyPair.key(src, dest))) {
			pairs.put(DependencyPair.key(src, dest), new DependencyPair(src, dest));
		}
		DependencyPair pair = pairs.get(DependencyPair.key(src, dest));
		pair.setDependency(type,weight);
	}

	public void addVariables(String[] variables) {
		this.variables = variables;
	}
	
	public String[] getVariables() {
		return variables;
	}
	public HashMap<String, DependencyPair> getPairs() {
		return pairs;
	}

}
