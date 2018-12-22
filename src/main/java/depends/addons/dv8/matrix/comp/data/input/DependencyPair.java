package depends.addons.dv8.matrix.comp.data.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


public class DependencyPair {
	private Integer from;
	private Integer to;
	HashMap<String, DependencyValue> dependencies;
	public DependencyPair(Integer from, Integer to) {
		this.from = from;
		this.to= to;
		dependencies = new HashMap<>();
	}
	public static String key(Integer from, Integer to) {
		return ""+from+"-->"+to;
	}
	public Integer getFrom() {
		return from;
	}
	public Integer getTo() {
		return to;
	}
	public Collection<DependencyValue> getDependencies() {
		return dependencies.values();
	}
	public void setDependency(String type, double weight) {
		dependencies.put(type, new DependencyValue(type,weight));
	}
	public Set<String> getDependencyTypes() {
		return dependencies.keySet();
	}
	public DependencyValue getDependencyOfType(String type) {
		if (dependencies.containsKey(type)) return dependencies.get(type);
		return new DependencyValue(type);
	}
}
