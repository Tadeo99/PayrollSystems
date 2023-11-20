package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * <ul>
 * <li>Co pyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class CabeceraReporteVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class CabeceraReporteVO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El usuario. */
	@Transient
	private String usuario;

	/** El usuario creacion. */
	@Transient
	private OffsetDateTime fechaGeneracion;

	/** El titulo. */
	@Transient
	private String titulo;

	/** El piePagina. */
	@Transient
	private String piePagina;

	@Transient
	private String rangoFechaTitle;

	@Transient
	private boolean isOnline = true;

	@Transient
	private String idSolicitudReporte;

	/** El usuario. */
	@Transient
	private String idUsuario;

	@Transient
	private String correoCorporativo;

	@Transient
	private String nombreCompleto;

	/** El opcion. */
	@Transient
	private Long menu;

	@Transient
	private Long codigoCola;

	@Transient
	private List<Map<String, Object>> listaDetalle = new ArrayList<>();

	@Transient
	private boolean batch;

	@Transient
	private String archivoName;

	@Transient
	private String classDinamic;

	@Transient
	private String tipoOpcion;

	@Transient
	private String KeyWebSockect;

	@Transient
	private String authToken;

}