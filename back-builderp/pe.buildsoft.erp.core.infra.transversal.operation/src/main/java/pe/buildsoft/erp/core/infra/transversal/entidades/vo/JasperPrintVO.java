package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class JaspertPrintVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 04/08/2016
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class JasperPrintVO implements Serializable {
	
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 8226758990925789085L;

	/** La jasper print. */
	private JasperPrint jasperPrint;
	
	/** La virtualizer. */
	private JRAbstractLRUVirtualizer virtualizer;
	
	/**
	 * Instancia un nuevo jaspert print vo.
	 */
	public JasperPrintVO() {
		super();
	}
	
	/**
	 * Instancia un nuevo jaspert print vo.
	 *
	 * @param jasperPrint el jasper print
	 * @param virtualizer el virtualizer
	 */
	public JasperPrintVO(JasperPrint jasperPrint, JRAbstractLRUVirtualizer virtualizer) {
		super();
		this.jasperPrint = jasperPrint;
		this.virtualizer = virtualizer;
	}

}
