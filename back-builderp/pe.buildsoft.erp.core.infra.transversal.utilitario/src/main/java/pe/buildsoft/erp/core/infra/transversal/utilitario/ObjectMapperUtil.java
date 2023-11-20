package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	
	private ObjectMapperUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> T to(String json,Class<T> classs) {
		try {
			return new ObjectMapper().readValue(json, classs);
		} catch (IOException e) {
			e.printStackTrace();
			return  null;
		}
	}
	
}
