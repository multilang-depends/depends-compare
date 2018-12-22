package depends.addons.dv8.matrix.comp.data.output;
public class WeightDiff{
	private String type;
	private double left;
	private double right;

	public WeightDiff(String type, double leftWeight, double rightWeight){
		this.type = type;
		this.left = leftWeight;
		this.right = rightWeight;
	}

	public String getType() {
		return type;
	}

	public double getLeft() {
		return left;
	}

	public double getRight() {
		return right;
	}
}