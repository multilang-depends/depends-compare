package depends.addons.dv8.matrix.comp;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import depends.addons.dv8.matrix.comp.data.output.CompareResult;
import depends.addons.dv8.matrix.comp.data.output.XlsxDumper;
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
		TypeMapping mapping = new TypeMapping();
		mapping.load(app.getMapping());
		Comparator comparator = new Comparator(app.getControl(),app.getIgnore(),mapping);
		CompareResult result = comparator.compare(app.getLeft(), app.getRight());
		if (app.getFormat().contains("excel")) {
			XlsxDumper xlsxDumper = new XlsxDumper();
			xlsxDumper.output(app.getOutput()+".xlsx", result);
		}
		if (app.getFormat().contains("json")) {
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

}
