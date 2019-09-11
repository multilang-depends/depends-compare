package depends.addons;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import depends.addons.dv8.matrix.comp.DependencyMatrixJsonLoader;
import depends.addons.dv8.matrix.comp.TypeMapping;
import depends.addons.dv8.matrix.comp.data.input.DependenciesRelation;

public class DependencyMatrixJsonLoaderTest {
	@Test
	public void test() throws Exception {
		 DependencyMatrixJsonLoader loader = new  DependencyMatrixJsonLoader();
		 DependenciesRelation dependencies = loader.loadDependencyMatrix(Paths.get("./src/test/resources/1.json"),new ArrayList<>(),new TypeMapping(),0);
		 assertEquals(5,dependencies.getVariables().length);
		 assertEquals(4,dependencies.getPairs().size());
	}
}
