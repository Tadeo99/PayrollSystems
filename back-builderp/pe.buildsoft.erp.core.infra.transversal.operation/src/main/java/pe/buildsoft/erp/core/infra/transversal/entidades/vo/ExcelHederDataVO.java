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
 * La Class ExcelHederDataVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ExcelHederDataVO implements Serializable {

    /** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El nameHeader. */
    private String nameHeader;
    
    /** El nameAtribute. */
    private String nameAtribute;
    
    /** El nameAtribute. */
    private String typeCell; // S , I , D

    /** La posicion celda. */
    private Integer posicionCelda = -1;

    /** La lista excel heder data vo. */
    private List<ExcelHederDataVO> listaExcelHederDataVO = new ArrayList<ExcelHederDataVO>();
    
	/** La cantidad agrupar. */
	private Integer cantidadAgrupar = 0;
    
	/**
	 * Instancia un nuevo excel heder data vo.
	 */
	public ExcelHederDataVO() {
		super();
	}

	/**
	 * Instancia un nuevo excel heder data vo.
	 *
	 * @param nameHeader el name header
	 * @param nameAtribute el name atribute
	 */
	public ExcelHederDataVO(String nameHeader, String nameAtribute) {
		super();
		this.nameHeader = nameHeader;
		this.nameAtribute = nameAtribute;
	}
	
	/**
	 * Instancia un nuevo excel heder data vo.
	 *
	 * @param nameHeader el name header
	 * @param nameAtribute el name atribute
	 */
	public ExcelHederDataVO(String nameHeader, Integer posicionCelda) {
		super();
		this.nameHeader = nameHeader;
		this.posicionCelda = posicionCelda;
	}

	public ExcelHederDataVO(String nameHeader, String nameAtribute, String typeCell) {
		this.nameHeader = nameHeader;
		this.nameAtribute = nameAtribute;
		this.typeCell = typeCell;
	}

	/**
	 * Instancia un nuevo excel heder data vo.
	 *
	 * @param nameHeader el name header
	 * @param nameAtribute el name atribute
	 * @param posicionCelda el posicion celda
	 */
	public ExcelHederDataVO(String nameHeader, String nameAtribute,
			Integer posicionCelda) {
		super();
		this.nameHeader = nameHeader;
		this.nameAtribute = nameAtribute;
		this.posicionCelda = posicionCelda;
	}
}