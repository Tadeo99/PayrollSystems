package pe.buildsoft.erp.core.infra.transversal.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSearch {
	/** La offset. */
	@Transient
	private int offSet;

	/** La start row. */
	@Transient
	private int startRow;

	/** La offset. */
	@Transient
	private Object id;

	@Transient
	private String search;

	@Transient
	private String sortFields;

	@Transient
	private String sortDirections;

	@Transient
	private String codigoSearch;

	@Transient
	private transient Object idPadreView;

	@Transient
	private String idNivel;

	@Transient
	private String usuarioSession;

	@Transient
	private String idEntidadSelect;

	@Transient
	private String authToken;

	@Transient
	private String serviceKey;

	@Transient
	private String keyWebSockect;

	@Transient
	private String estado;

	@Transient
	private List<Long> listaIdMenu = new ArrayList<>();

	@Transient
	private String idTupla;

	@Transient
	private String tipo;

	@Transient
	private Long idItem;

	@Transient
	private String codigo;

	@Transient
	private List<String> listaIdPersonal = new ArrayList<String>();

	@Transient
	private List<String> listaEstado = new ArrayList<String>();

	@Transient
	private Long idAnhio;
	@Transient
	private String idAlumno;
	@Transient
	private String idPeriodo;
	@Transient
	private String idUbigeo;

	@Transient
	private Long idItemByMes;
	@Transient
	private Long idItemByCategoriaOcupacional;
	@Transient
	private Long idItemByPeriocidad;
	@Transient
	private String idPersonal;

	@Transient
	private String idPeriodoPlanilla;
	@Transient
	private String idTipoPlanilla;

	@Transient
	private Long idItemByCategoriaTrabajador;

	@Transient
	private Long idItemByPeriodoMes;

	@Transient
	private String estadoProceso;

	@Transient
	private Long idSolicitudReporte;
	@Transient
	private Long idOpcionMenu;

	@Transient
	private Long idItemByMesByDevengue;

	@Transient
	private Long idItemByTipoTrabajador;

	@Transient
	private Long idItemByRegimenPensionario;

	@Transient
	private Long numSemana;

	@Transient
	private String idRequerimiento;

	@Transient
	private String codigoReporte;

	@Transient
	private String idEntidad;
	@Transient
	private String idPersonalByDocentePrincipal;
	@Transient
	private String idCargaAcademica;
	@Transient
	private String tipoPeriodo;
	@Transient
	private Long idSeccion;
	@Transient
	private Long idGrado;

	@Transient
	private String idDetMallaCurricular;
	@Transient
	private String idMallaCurricular;

	@Transient
	private Long idItemByTurno;

	@Transient
	private Long idAula;

	@Transient
	private String idDetalleCargaAcademica;

	@Transient
	private Long idSistema;

	@Transient
	private BigDecimal nota;

	@Transient
	private Long idItemByEps;
}
