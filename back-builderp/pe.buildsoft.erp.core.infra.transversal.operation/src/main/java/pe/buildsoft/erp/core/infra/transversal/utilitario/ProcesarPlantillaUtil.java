package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * <ul>
 * <li>Copyright 2012 UNIVERSIDAD PRIVADA DE PUCALLPA - UPP. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ProcesarPlantillaUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 27/07/2013
 * @since SIAA 2.0
 */
public class ProcesarPlantillaUtil {
	
	/** La Constante LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ProcesarPlantillaUtil.class);
    
	
	/**
	 * Procesar plantilla by freemarker do.
	 *
	 * @param dataModel el data model
	 * @param basePath el base path
	 * @param template el template
	 * @return the string
	 */
	public static String procesarPlantillaByFreemarkerDo(Map<String,Object> dataModel,String basePath,String template) {
		
	    return procesarPlantillaByFreemarkerDo(dataModel, basePath, template,"");

	  }
	public static String procesarPlantillaByFreemarkerDo(Map<String,Object> dataModel,String basePath,String template,String rutaArchivo) {
		String resultado = "";
	    // Configuration
	    Configuration cfg = new Configuration();

	    try {
	      // Set Directory for templates
	      cfg.setDirectoryForTemplateLoading(new File(basePath));
	      // load template
	      Template tpl = cfg.getTemplate(template);
	      
	      // data-model = dataModel
	      
	     StringWriter sw = new StringWriter();

		 tpl.process(dataModel, sw);
		 
		 resultado = sw.toString();
		 if (!StringUtil.isNullOrEmpty(rutaArchivo)) {
			 Writer file = new OutputStreamWriter(new FileOutputStream(new File(rutaArchivo)), "UTF-8");
			 tpl.process(dataModel, file);
			 file.flush();
			 file.close();
		 }
         
	    } catch (Exception e) {
	    	LOG.error(e.getMessage());

	    } 
	    
	    return resultado;

	  }
	
	
}
