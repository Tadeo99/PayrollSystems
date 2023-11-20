package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ExcelComboDataVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 13/04/2016
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ExcelComboDataVO implements Serializable {

    /** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String nombreCampo;
	private Integer posicionCampo;
    private List<String> listaExcelComboData = new ArrayList<>();
    
	public ExcelComboDataVO() {
		super();
	}
	
}