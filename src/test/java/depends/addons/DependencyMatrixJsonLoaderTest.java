package depends.addons;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Test;

import depends.addons.dv8.matrix.comp.DependencyMatrixJsonLoader;
import depends.addons.dv8.matrix.comp.data.DependenciesRelation;

public class DependencyMatrixJsonLoaderTest {
	@Test
	public void test() throws Exception {
		 DependencyMatrixJsonLoader loader = new  DependencyMatrixJsonLoader();
		 DependenciesRelation dependencies = loader.loadDependencyMatrix(Paths.get("./src/test/resources/1.json"));
		 assertEquals(96,dependencies.getVariables().length);
		 assertEquals(302,dependencies.getPairs().size());
	}
}
