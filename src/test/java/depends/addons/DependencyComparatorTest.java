package depends.addons;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import depends.addons.dv8.matrix.comp.Comparator;
import depends.addons.dv8.matrix.comp.data.output.CompareResult;

public class DependencyComparatorTest {
	@Test
	public void test() throws Exception {
		String f_1 = "./src/test/resources/1.json";
		String f_2 = "./src/test/resources/2.json";
		Comparator comparator = new Comparator("ad",new String[] {});
		CompareResult result = comparator.compare(f_1, f_2);
		assertEquals(1, result.getDeletedVariables().size());
		assertEquals(1, result.getAddedVariables().size());
		assertEquals(1, result.getAddedDependencyPairs().size());
		assertEquals(1, result.getDeletedDependencyPairs().size());
	}
}
