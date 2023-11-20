package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class AtributoEntityVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class AtributoEntityVO {

	private String tableName;
	private String schema;

	/** La nombre atributo. */
	private String nombreAtributo;

	/** La nombre columna. */
	private String nombreColumna;

	/** La classs atributo. */
	private Object classsAtributo;

	/** La classs atributo. */
	private Class<?> classsAtributoType;

	/** La posicion. */
	private int posicion;

	private boolean pKCompuesta = false;
	private boolean column = false;

	private List<AtributoEntityVO> listaAtributoEntityVOPK = new ArrayList<>();

	private boolean isTransient = false;
	private boolean esPK = false;

	/**
	 * Instancia un nuevo atributo entity vo.
	 */
	public AtributoEntityVO() {
		super();
	}

	/**
	 * Instancia un nuevo atributo entity vo.
	 *
	 * @param nombreAtributo el nombre atributo
	 * @param classsAtributo el classs atributo
	 */
	public AtributoEntityVO(String nombreAtributo, Object classsAtributo) {
		super();
		this.nombreAtributo = nombreAtributo;
		this.classsAtributo = classsAtributo;
	}

	/**
	 * Instancia un nuevo atributo entity vo.
	 *
	 * @param nombreAtributo el nombre atributo
	 * @param nombreColumna  el nombre columna
	 * @param classsAtributo el classs atributo
	 * @param posicion       el posicion
	 */
	public AtributoEntityVO(String nombreAtributo, String nombreColumna, Object classsAtributo, int posicion) {
		super();
		this.nombreAtributo = nombreAtributo;
		this.nombreColumna = nombreColumna;
		this.classsAtributo = classsAtributo;
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		return "AtributoEntityVO [tableName=" + tableName + ", schema=" + schema + ", nombreAtributo=" + nombreAtributo
				+ ", nombreColumna=" + nombreColumna + ", classsAtributo=" + classsAtributo + ", classsAtributoType="
				+ classsAtributoType + ", posicion=" + posicion + ", pKCompuesta=" + pKCompuesta + ", column=" + column
				+ ", listaAtributoEntityVOPK=" + listaAtributoEntityVOPK + "]";
	}

}