package pe.buildsoft.erp.core.domain.entidades.escalafon.vo;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class ConfiguracionEntityManagerUtil.
 * <ul>
 * <li>Copyright 2014 CONTA-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author natan.
 * @version 1.0, Fri Apr 25 18:56:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ConceptoCaluldadosVO implements Serializable {


	private static final long serialVersionUID = 1L;
	private String idConceptoPDT;
	private String idConceptosTipoPlanilla;//formula actual
	private String variable;
	private String formula;
	private BigDecimal monto; 
	
	
	public ConceptoCaluldadosVO() {
		super();
	}

}
