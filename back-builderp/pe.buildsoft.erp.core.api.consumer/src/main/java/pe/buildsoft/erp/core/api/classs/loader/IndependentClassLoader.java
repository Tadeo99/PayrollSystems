package pe.buildsoft.erp.core.api.classs.loader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public final class IndependentClassLoader extends URLClassLoader {

	private static final ClassLoader INSTANCE = new IndependentClassLoader();

	/**
	 * @return instance
	 */
	public static ClassLoader getInstance() {

		return INSTANCE;
	}

	private IndependentClassLoader() {

		super(getAppClassLoaderUrls(), null);
	}

	private static URL[] getAppClassLoaderUrls() {

		return getURLs(IndependentClassLoader.class.getClassLoader());
	}

	private static URL[] getURLs(ClassLoader classLoader) {

		Class<?> clazz = classLoader.getClass();

		try {
			Field field = null;
			field = clazz.getDeclaredField("ucp");
			field.setAccessible(true);

			Object urlClassPath = field.get(classLoader);

			Method method = urlClassPath.getClass().getDeclaredMethod("getURLs", new Class[] {});
			method.setAccessible(true);
			URL[] urls = (URL[]) method.invoke(urlClassPath, new Object[] {});

			return urls;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}