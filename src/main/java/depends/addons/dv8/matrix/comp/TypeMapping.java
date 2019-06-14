package depends.addons.dv8.matrix.comp;

import java.util.HashMap;

public class TypeMapping {
	HashMap<String,String> mapPairs = new HashMap<>();
	public TypeMapping() {
		
	}
	public void load(String[] mappings){
		for (String mapping:mappings) {
			String[] pair = mapping.split(":");
			if (pair.length<2) {
				System.err.println("Warning: wrong mapping format "+mapping);
				continue;
			}
			mapPairs.put(pair[0],pair[1]);
		}
	}

	public String map(String type) {
		String r = mapPairs.get(type);
		if (r==null)
			r = type;
		return r;
	}
}
