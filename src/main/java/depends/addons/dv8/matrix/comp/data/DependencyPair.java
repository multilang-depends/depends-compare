package depends.addons.dv8.matrix.comp.data;

import java.util.Collection;
import java.util.HashMap;


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
}
