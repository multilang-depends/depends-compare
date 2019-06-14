package depends.addons.dv8.matrix.comp;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "depends-compare")
public class DependsCompareCommand {
	@Parameters(index = "0", description = "The 1st json file to be compared")
    private String left;
	@Parameters(index = "1", description = "The 2nd json file to be compared")
    private String right;
	@Parameters(index = "2",  description = "The result file to be outputed")
	private String output;
    @Option(names = {"-h","--help"}, usageHelp = true, description = "display this help and exit")
    boolean help;
    @Option(names = {"--ignore"},split=",",  description = "ignored types")
    private String[] ignore=new String[]{};  
    @Option(names = {"--control"},  description = "d(deleted only)|a(added only)|ad(both)")
    private String control="ad";
    
	public boolean isHelp() {
		return help;
	}
	public String getLeft() {
		return left;
	}
	public String getRight() {
		return right;
	}
	public String getOutput() {
		return output;
	}
	public String[] getIgnore() {
		return ignore;
	}
	public String getControl() {
		return control;
	}
	
}
