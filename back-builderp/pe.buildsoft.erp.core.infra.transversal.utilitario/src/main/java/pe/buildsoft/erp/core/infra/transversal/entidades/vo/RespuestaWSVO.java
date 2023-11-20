package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BasePagination;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class RespuestaWSVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 27/08/2015
 * @param <T> el tipo generico
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RespuestaWSVO<T> implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La lista resultado. */
	private List<T> listaResultado;

	/** La objeto resultado. */
	private T objetoResultado;

	/** La codigo error. */
	private String codigoError;

	/** La mensaje error. */
	private String mensajeError;

	/** La is error. */
	private boolean isError;
	private int contador;
	private String accessToken;

	private BasePagination pagination;

	/**
	 * Instancia un nuevo resultado wsvo.
	 */
	public RespuestaWSVO() {
		super();
	}

	public boolean isData() {
		return contador > 0;
	}

	/**
	 * Instancia un nuevo resultado wsvo.
	 *
	 * @param listaResultado  el lista resultado
	 * @param objetoResultado el objeto resultado
	 * @param codigoError     el codigo error
	 * @param mensajeError    el mensaje error
	 * @param isError         el is error
	 */
	public RespuestaWSVO(List<T> listaResultado, T objetoResultado, String codigoError, String mensajeError,
			boolean isError) {
		super();
		this.listaResultado = listaResultado;
		this.objetoResultado = objetoResultado;
		this.codigoError = codigoError;
		this.mensajeError = mensajeError;
		this.isError = isError;
	}

	public T getObjetoResultado() {
		return objetoResultado;
	}

}