package util.dimensions;

public class DimensionUtil {
	public String getDimensionName(int dimension_id) {
		switch(dimension_id) {
		case -1:
			return "nether";
		case 0:
			return "overworld";
		case 1:
			return "end";
		default:
			return "unknown";
		}
	}

	public int getDimensionId(String dimension_name) {
		switch(dimension_name) {
		case "nether":
			return -1;
		case "overworld":
			return 0;
		case "end":
			return 1;
		default:
			return 42;
		}
	}
}
