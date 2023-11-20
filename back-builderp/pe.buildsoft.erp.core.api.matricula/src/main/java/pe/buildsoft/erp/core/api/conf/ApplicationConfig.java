package pe.buildsoft.erp.core.api.conf;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * @author ndavila --
 */

//@ApplicationPath("/rs")
@ApplicationPath("/api")
public class ApplicationConfig extends Application {

	// ======================================
	// = Business methods =
	// ======================================

	/*
	 * @Override public Set<Class<?>> getClasses() { Set<Class<?>> classes = new
	 * HashSet<Class<?>>(); classes.add(BibliaServiceRestImpl.class); return
	 * classes; }
	 */

	public ApplicationConfig() {

	}

}
