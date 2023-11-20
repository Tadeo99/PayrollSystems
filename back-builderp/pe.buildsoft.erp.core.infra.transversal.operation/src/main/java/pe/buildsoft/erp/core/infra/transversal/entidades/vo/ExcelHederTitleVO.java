package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ExcelHederTitleVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 05/04/20156
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ExcelHederTitleVO implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El nameHeader. */
	private String nameHeader;

	/** El nameAtribute. */
	private HorizontalAlignment aling;

	/** La vertical alignment. */
	private VerticalAlignment verticalAlignment;

	/** La posicion celda. */
	private Integer posicionCelda = -1;

	/** La posicion row. */
	private Integer posicionRow = -1;

	/** La cantidad agrupar. */
	private Integer cantidadAgrupar = 0;

	/** La cantidad agrupar horizontal. */
	private Integer cantidadAgruparHorizontal = 0;

	/** La font height in points. */
	private short fontHeightInPoints = 9;

	/** La column index. */
	private int columnIndex = -1;
	
	/** La width. */
	private int width = -1;
	
	/** La valor. */
	private String valor;

    /** The wrap text. */
    private boolean wrapText = false;
    
    /** The rotacion. */
    private int rotacion = 0;
    
    /** The es pie pagina. */
    private boolean esPiePagina = false;	
	
	/**
	 * Instancia un nuevo excel heder title vo.
	 */
	public ExcelHederTitleVO() {
		super();
	}

	/**
	 * Instancia un nuevo excel heder title vo.
	 *
	 * @param nameHeader el name header
	 * @param aling el aling
	 */
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
	}

	// get y set

	/**
	 * Instancia un nuevo excel heder title vo.
	 *
	 * @param nameHeader el name header
	 * @param aling el aling
	 * @param posicionCelda el posicion celda
	 */
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,Integer posicionCelda) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.posicionCelda = posicionCelda;
	}

	/**
	 * Instancia un nuevo excel heder title vo.
	 *
	 * @param nameHeader el name header
	 * @param aling el aling
	 * @param posicionCelda el posicion celda
	 * @param cantidadAgrupar el cantidad agrupar
	 */
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,Integer posicionCelda, Integer cantidadAgrupar) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.posicionCelda = posicionCelda;
		this.cantidadAgrupar = cantidadAgrupar;
	}

	/**
	 * Instancia un nuevo excel heder title vo.
	 *
	 * @param nameHeader el name header
	 * @param aling el aling
	 * @param verticalAlignment el vertical alignment
	 * @param posicionCelda el posicion celda
	 * @param posicionRow el posicion row
	 * @param cantidadAgrupar el cantidad agrupar
	 * @param cantidadAgruparHorizontal el cantidad agrupar horizontal
	 * @param columnIndex el column index
	 * @param width el width
	 */
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment, Integer posicionCelda,Integer posicionRow, Integer cantidadAgrupar,Integer cantidadAgruparHorizontal, Integer columnIndex,Integer width) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.columnIndex = columnIndex;
		this.width = width;
	}

	/**
	 * Instancia un nuevo excel heder title vo.
	 *
	 * @param nameHeader el name header
	 * @param aling el aling
	 * @param verticalAlignment el vertical alignment
	 * @param posicionCelda el posicion celda
	 * @param posicionRow el posicion row
	 * @param cantidadAgrupar el cantidad agrupar
	 * @param cantidadAgruparHorizontal el cantidad agrupar horizontal
	 */
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment, Integer posicionCelda,Integer posicionRow, Integer cantidadAgrupar,Integer cantidadAgruparHorizontal) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
	}
	
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment, Integer posicionCelda,Integer posicionRow, Integer cantidadAgrupar,Integer cantidadAgruparHorizontal, String valor) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.valor = valor;
	}
	

	/**
	 * Instancia un nuevo excel heder title vo.
	 *
	 * @param nameHeader el name header
	 * @param aling el aling
	 * @param verticalAlignment el vertical alignment
	 * @param posicionCelda el posicion celda
	 * @param posicionRow el posicion row
	 * @param cantidadAgrupar el cantidad agrupar
	 * @param cantidadAgruparHorizontal el cantidad agrupar horizontal
	 * @param fontHeightInPoints el font height in points
	 * @param columnIndex el column index
	 * @param width el width
	 */
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment, Integer posicionCelda,Integer posicionRow, Integer cantidadAgrupar,Integer cantidadAgruparHorizontal, short fontHeightInPoints,Integer columnIndex, Integer width) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.fontHeightInPoints = fontHeightInPoints;
		this.columnIndex = columnIndex;
		this.width = width;
	}

	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment,
			Integer posicionCelda, Integer posicionRow, Integer cantidadAgrupar, Integer cantidadAgruparHorizontal, Integer columnIndex, Integer width,boolean wrapText,int rotacion,short fontHeightInPoints ) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.columnIndex = columnIndex;
		this.width = width;
		this.wrapText = wrapText;
		this.rotacion = rotacion;
		this.fontHeightInPoints = fontHeightInPoints ;
	}
	
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment,
			Integer posicionCelda, Integer posicionRow, Integer cantidadAgrupar, Integer cantidadAgruparHorizontal, Integer columnIndex, Integer width,boolean wrapText ) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.columnIndex = columnIndex;
		this.width = width;
		this.wrapText = wrapText;
	}
	
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment,
			Integer posicionCelda, Integer posicionRow, Integer cantidadAgrupar, Integer cantidadAgruparHorizontal, Integer columnIndex, Integer width,boolean wrapText, boolean esPiePagina ) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.columnIndex = columnIndex;
		this.width = width;
		this.wrapText = wrapText;
		this.esPiePagina = esPiePagina;
	}
	
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment,
			Integer posicionCelda, Integer posicionRow, Integer cantidadAgrupar, Integer cantidadAgruparHorizontal,boolean wrapText) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.wrapText = wrapText;
	}
	
	
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment,
			Integer posicionCelda, Integer posicionRow, Integer cantidadAgrupar, Integer cantidadAgruparHorizontal, short fontHeightInPoints,boolean wrapText) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.fontHeightInPoints = fontHeightInPoints;
		this.wrapText = wrapText;
	}	
	
	/**
	 * Instancia un nuevo excel heder title vo.
	 *
	 * @param nameHeader el name header
	 * @param aling el aling
	 * @param verticalAlignment el vertical alignment
	 * @param posicionCelda el posicion celda
	 * @param posicionRow el posicion row
	 * @param cantidadAgrupar el cantidad agrupar
	 * @param cantidadAgruparHorizontal el cantidad agrupar horizontal
	 * @param fontHeightInPoints el font height in points
	 */
	public ExcelHederTitleVO(String nameHeader, HorizontalAlignment aling,VerticalAlignment verticalAlignment, Integer posicionCelda,Integer posicionRow, Integer cantidadAgrupar,Integer cantidadAgruparHorizontal, short fontHeightInPoints) {
		super();
		this.nameHeader = nameHeader;
		this.aling = aling;
		this.verticalAlignment = verticalAlignment;
		this.posicionCelda = posicionCelda;
		this.posicionRow = posicionRow;
		this.cantidadAgrupar = cantidadAgrupar;
		this.cantidadAgruparHorizontal = cantidadAgruparHorizontal;
		this.fontHeightInPoints = fontHeightInPoints;
	}
	
}