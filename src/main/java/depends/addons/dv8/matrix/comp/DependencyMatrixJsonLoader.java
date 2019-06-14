package depends.addons.dv8.matrix.comp;

import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import depends.addons.dv8.matrix.comp.data.input.DependenciesRelation;

public class DependencyMatrixJsonLoader {
	public DependencyMatrixJsonLoader() {
	}

	public DependenciesRelation loadDependencyMatrix(Path path, List<String> ignoredTypes, TypeMapping typeMapping) throws Exception {
		DependenciesRelation m = new DependenciesRelation();
		ObjectMapper mapper = new ObjectMapper();
		try (InputStream input = Files.newInputStream(path, READ)) {
			JsonParser parser = new JsonFactory().createParser(input);
			JsonNode matrixNode = mapper.readTree(parser);
			m.addVariables(mapper.treeToValue(matrixNode.get("variables"), String[].class));
			for (JsonNode cell : matrixNode.get("cells")) {
				JsonNode types = cell.get("values");
				for (Iterator<String> iter = types.fieldNames(); iter.hasNext();) {
					String dependencyName = iter.next();
					double weight = types.get(dependencyName).asDouble();
					int src = cell.get("src").asInt();
					int dest = cell.get("dest").asInt();
					if (!ignoredTypes.contains(dependencyName)) {
						m.addDependencies(typeMapping.map(dependencyName),src,dest,weight);
					}
					
				}
			}
		} catch (IOException | NullPointerException ex) {
			throw new Exception("File not found " + path.toString());
		}
		return m;
	}
}