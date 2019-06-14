package depends.addons.dv8.matrix.comp;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import depends.addons.dv8.matrix.comp.data.output.CompareResult;
import picocli.CommandLine;

public class DependsCompareMain {

	public static void main(String[] args) {
		try {
			DependsCompareCommand app = CommandLine.populateCommand(new DependsCompareCommand(), args);
			if (app.help) {
				CommandLine.usage(new DependsCompareCommand(), System.out);
				System.exit(0);
			}
			executeCommand(app);
		} catch (Exception e) {
			System.err.println("Exception encountered");
			CommandLine.usage(new DependsCompareCommand(), System.out);
			System.exit(0);
		}
		
	}

	@SuppressWarnings("deprecation")
	private static void executeCommand(DependsCompareCommand app) {
		Comparator comparator = new Comparator(app.getControl(),app.getIgnore());
		CompareResult result = comparator.compare(app.getLeft(), app.getRight());
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		om.configure(SerializationFeature.INDENT_OUTPUT, true);
		om.setSerializationInclusion(Include.NON_NULL);

		try {
			om.writerWithDefaultPrettyPrinter().writeValue(new File(app.getOutput()), result);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
