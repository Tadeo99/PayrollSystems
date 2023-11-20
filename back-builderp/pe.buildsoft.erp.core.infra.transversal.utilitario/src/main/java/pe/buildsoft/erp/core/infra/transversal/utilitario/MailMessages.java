package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.IOException;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;

// TODO: Auto-generated Javadoc
/**
 * La Class MailMessages.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Apr 25 18:56:23 COT 2014
 * @since Rep v1..0
 */
public class MailMessages {	
        /**
         * Obtiene parametro.
         *
         * @param key el key
         * @return parametro
         * @throws IOException Se�ales de que una excepci�n de E / S se ha producido.
         */
        public static String getParametro(String key) throws IOException{
		return ConfiguracionCacheUtil.getInstance().getMailServer(key);
	}

}
