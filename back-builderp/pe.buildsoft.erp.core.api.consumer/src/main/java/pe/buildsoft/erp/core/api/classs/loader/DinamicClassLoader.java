package pe.buildsoft.erp.core.api.classs.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.AnnotatedType;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.inject.spi.InjectionTarget;
import jakarta.enterprise.util.AnnotationLiteral;

//https://stackoverflow.com/questions/17453260/discovery-beans-from-dynamically-loaded-jar-in-jboss-as-7-1
public class DinamicClassLoader implements Extension {
	
	void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
		System.out.println("afterBeanDiscovery");
		for (final var c : getBeanClasses()) {

			// use this to read annotations of the class
			AnnotatedType at = bm.createAnnotatedType(c);

			// use this to create the class and inject dependencies
			final InjectionTarget it = null;// bm.createInjectionPoint(at)

			abd.addBean(new Bean() {
				@Override
				public Class<?> getBeanClass() {
					return c;
				}

				@Override
				public Set<InjectionPoint> getInjectionPoints() {
					return it.getInjectionPoints();
				}

				@Override
				public String getName() {
					String s = c.getSimpleName();
					s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
					return s;
				}

				@Override
				public Set<Annotation> getQualifiers() {
					Set<Annotation> qualifiers = new HashSet<>();
					qualifiers.add(new AnnotationLiteral<>() {
					});
					qualifiers.add(new AnnotationLiteral<>() {
					});
					return qualifiers;
				}

				@Override
				public Class<? extends Annotation> getScope() {
					return Dependent.class;
				}

				@Override
				public Set<Class<? extends Annotation>> getStereotypes() {
					return Collections.emptySet();
				}

				@Override
				public Set<Type> getTypes() {
					Set<Type> types = new HashSet<>();
					types.add(c);
					types.add(Object.class);
					return types;
				}

				@Override
				public boolean isAlternative() {
					return false;
				}

				@Override
				public boolean isNullable() {
					return false;
				}

				@Override
				public Object create(CreationalContext ctx) {
					Object instance = it.produce(ctx);
					it.inject(instance, ctx);
					it.postConstruct(instance);
					return instance;
				}

				@Override
				public void destroy(Object instance, CreationalContext ctx) {
					it.preDestroy(instance);
					it.dispose(instance);
					ctx.release();
				}

			});
		}
	}

	private Class[] getBeanClasses() {
		// TODO Auto-generated method stub
		return null;
	}
}
