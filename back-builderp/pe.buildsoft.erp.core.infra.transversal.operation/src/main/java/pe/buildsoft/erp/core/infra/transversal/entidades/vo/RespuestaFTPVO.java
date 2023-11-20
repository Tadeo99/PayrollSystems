package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaFTPVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Map<String,Map<String,String>>> listaGrupoArchivoMap = new ArrayList<>();
	private Map<String,String> nombreArchivoFTPMap = new HashMap<>();
	private Map<String,String> nombreArchivoMetaDataFTPMap = new HashMap<>();
	
	/** La codigo error. */
	private String codigoError;
	
	/** La mensaje error. */
	private String mensajeError;
	
	/** La is error. */
	private boolean isError;
	
	public RespuestaFTPVO() {
		super();
	}
	
}
