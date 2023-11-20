package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La Class CollectionUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Lu Eno 25 01:25:41 COT 2016
 * @since BuildErp 1.0
 */
public class CollectionUtil implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(CollectionUtil.class);

	/**
	 * Ordenador.
	 *
	 * @param descending    el descending
	 * @param listaGeneral  el lista general
	 * @param nombreColumna el nombre columna
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void ordenador(boolean descending, List listaGeneral, String nombreColumna) {
		try {
			BeanComparator actorComparator = null;
			if (descending) {
				actorComparator = new BeanComparator(nombreColumna, Collections.reverseOrder());
			} else {
				actorComparator = new BeanComparator(nombreColumna);
			}
			Collections.sort(listaGeneral, actorComparator);
		} catch (Exception e) {
			log.error("ordenador", e);
		}
	}

	public static void ordenadorGroup(boolean descending, List listaGeneral, String... nombreColumna) {
		try {
			List<BeanComparator> listaFiled = new ArrayList<>();
			ComparatorChain chain = null;
			if (descending) {
				for (var columna : nombreColumna) {
					listaFiled.add(new BeanComparator(columna, Collections.reverseOrder()));
				}
			} else {
				for (var columna : nombreColumna) {
					listaFiled.add(new BeanComparator(columna));
				}
			}
			chain = new ComparatorChain(listaFiled);
			Collections.sort(listaGeneral, chain);
		} catch (Exception e) {
			log.error("ordenadorGroup", e);
		}
	}

	public static boolean isEmpty(List<?> list) {
		boolean respuesta = false;
		if (list == null || list.isEmpty()) {
			respuesta = true;
		}
		return respuesta;
	}

	public static boolean isEmpty(Map<?, ?> listMap) {
		boolean respuesta = false;
		if (listMap == null || listMap.isEmpty()) {
			respuesta = true;
		}
		return respuesta;
	}
}