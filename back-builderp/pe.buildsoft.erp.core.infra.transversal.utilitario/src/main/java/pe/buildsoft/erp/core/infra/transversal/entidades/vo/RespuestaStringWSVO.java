package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class RespuestaWSVO.
 *
 * @author BuildSoft
 * @version 1.0 , 27/08/2015
 * @param <T> el tipo generico
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RespuestaStringWSVO implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La lista resultado. */
	private List<String> listaResultado;
	
	/** La objeto resultado. */
	private String objetoResultado;
	
	/** La codigo error. */
	private String codigoError;
	
	/** La mensaje error. */
	private String mensajeError;
	
	/** La is error. */
	private boolean isError;
	
	
	
	/**
	 * Instancia un nuevo resultado wsvo.
	 */
	public RespuestaStringWSVO() {
		super();
	}


	/**
	 * Instancia un nuevo resultado wsvo.
	 *
	 * @param listaResultado el lista resultado
	 * @param objetoResultado el objeto resultado
	 * @param codigoError el codigo error
	 * @param mensajeError el mensaje error
	 * @param isError el is error
	 */
	public RespuestaStringWSVO(List<String> listaResultado, String objetoResultado,
			String codigoError, String mensajeError, boolean isError) {
		super();
		this.listaResultado = listaResultado;
		this.objetoResultado = objetoResultado;
		this.codigoError = codigoError;
		this.mensajeError = mensajeError;
		this.isError = isError;
	}

	public String getObjetoResultado() {
		if (listaResultado != null && listaResultado.size() > 0) {
			objetoResultado = listaResultado.get(0);
		} 
		return objetoResultado;
	}

}