package ro.ase.acs.classes;

@FunctionalInterface
public interface DataSeriesOperation {
	public Double doOperation(Integer[] values);
	
	default String transformArray(Integer[] values) {
		StringBuilder values_as_string = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			values_as_string.append(values[i]);
			if(i < values.length - 1) {
				values_as_string.append(", ");
			}
		}
		return values_as_string.toString();
	}
}
