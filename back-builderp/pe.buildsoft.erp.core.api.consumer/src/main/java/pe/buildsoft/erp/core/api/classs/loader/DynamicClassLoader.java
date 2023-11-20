package pe.buildsoft.erp.core.api.classs.loader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

//https://stackoverflow.com/questions/68380968/java-11-issue-with-adding-dependency-jars-at-runtime
public class DynamicClassLoader extends URLClassLoader {

	public DynamicClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public void addURL(URL url) {
		super.addURL(url);
	}

	public DynamicClassLoader(String name, ClassLoader parent) {
		super(name, new URL[0], parent);
	}

	/*
	 * Required when this classloader is used as the system classloader
	 */
	public DynamicClassLoader(ClassLoader parent) {
		this("classpath", parent);
	}

	public DynamicClassLoader() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public static DynamicClassLoader findAncestor(ClassLoader cl) {
		do {

			if (cl instanceof DynamicClassLoader)
				return (DynamicClassLoader) cl;

			cl = cl.getParent();
		} while (cl != null);

		return null;
	}

	/*
	 * Required for Java Agents when this classloader is used as the system
	 * classloader
	 */
	@SuppressWarnings("unused")
	private void appendToClassPathForInstrumentation(String jarfile) throws IOException {
		addURL(Paths.get(jarfile).toRealPath().toUri().toURL());
	}
}