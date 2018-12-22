package depends.addons.dv8.matrix.comp.data.input;

public class DependencyValue {
	private double weight;
	private String type;

	public DependencyValue(String type) {
		this.type = type;
		this.weight = 0;
	}

	public DependencyValue(String type, double weight) {
		this.type = type;
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public String getType() {
		return type;
	}
}