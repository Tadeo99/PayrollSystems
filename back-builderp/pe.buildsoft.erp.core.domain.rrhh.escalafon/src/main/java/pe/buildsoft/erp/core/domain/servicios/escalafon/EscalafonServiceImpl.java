package pe.buildsoft.erp.core.domain.servicios.escalafon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.escalafon.AsociarCentroCosto;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Beneficiarios;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Carrera;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CentroCosto;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Contrato;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CuentaBancariaPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DetalleContrado;
import pe.buildsoft.erp.core.domain.entidades.escalafon.DireccionPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.HistorialBasico;
import pe.buildsoft.erp.core.domain.entidades.escalafon.HistorialCargoArea;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Institucion;
import pe.buildsoft.erp.core.domain.entidades.escalafon.PeriodoLaboraPersonal;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.AsociarCentroCostoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.BeneficiariosDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.CarreraDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.CentroCostoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.ContratoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.CuentaBancariaPersonalDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.DetalleContradoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.DireccionPersonalDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.HistorialBasicoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.HistorialCargoAreaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.InstitucionDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.PeriodoLaboraPersonalDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon.PersonalDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.escalafon.EscalafonServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.security.SecurityServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.security.vo.TipoUsuarioVO;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.security.vo.UsuarioVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class EscalafonServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class EscalafonServiceImpl implements EscalafonServiceLocal {

	private static final long TIPO_USUARIO_EMPLEADO = 2L;

	private static final String GRUPO_USUARIO_EMPLEADO = "2";

	/** El servicio historial cargo area dao impl. */
	@Inject
	private HistorialCargoAreaDaoLocal historialCargoAreaDao;

	/** El servicio centro costo dao impl. */
	@Inject
	private CentroCostoDaoLocal centroCostoDao;

	/** El servicio personal dao impl. */
	@Inject
	private PersonalDaoLocal personalDao;

	/** El servicio detalle contrado dao impl. */
	@Inject
	private DetalleContradoDaoLocal detalleContradoDao;

	/** El servicio contrato dao impl. */
	@Inject
	private ContratoDaoLocal contratoDao;

	/** El servicio cuenta bancaria personal dao impl. */
	@Inject
	private CuentaBancariaPersonalDaoLocal cuentaBancariaPersonalDao;

	/** El servicio historial basico dao impl. */
	@Inject
	private HistorialBasicoDaoLocal historialBasicoDao;

	/** El servicio asociar centro costo dao impl. */
	@Inject
	private AsociarCentroCostoDaoLocal asociarCentroCostoDao;

	/** El servicio carrera dao impl. */
	@Inject
	private CarreraDaoLocal carreraDao;

	/** El servicio institucion dao impl. */
	@Inject
	private InstitucionDaoLocal institucionDao;

	/** El servicio direccion personal dao impl. */
	@Inject
	private DireccionPersonalDaoLocal direccionPersonalDao;

	/** El servicio beneficiarios dao impl. */
	@Inject
	private BeneficiariosDaoLocal beneficiariosDao;

	/** El servicio periodo labora personal dao impl. */
	@Inject
	private PeriodoLaboraPersonalDaoLocal periodoLaboraPersonalDao;

	@Inject
	private SecurityServiceClient securityClient;

	@Override
	public HistorialCargoArea controladorAccionHistorialCargoArea(HistorialCargoArea obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdHistorialCargoArea(this.historialCargoAreaDao.generarId());
				this.historialCargoAreaDao.save(obj);
				break;
			case MODIFICAR:
				this.historialCargoAreaDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.historialCargoAreaDao.find(HistorialCargoArea.class, obj.getIdHistorialCargoArea());
				this.historialCargoAreaDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.historialCargoAreaDao.find(HistorialCargoArea.class, obj.getIdHistorialCargoArea());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<HistorialCargoArea> listarHistorialCargoArea(BaseSearch filtro) {
		return this.historialCargoAreaDao.listar(filtro);
	}

	@Override
	public int contarListarHistorialCargoArea(BaseSearch filtro) {
		return this.historialCargoAreaDao.contar(filtro);
	}

	@Override
	public CentroCosto controladorAccionCentroCosto(CentroCosto obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdCentroCosto(this.centroCostoDao.generarId());
				this.centroCostoDao.save(obj);
				break;
			case MODIFICAR:
				this.centroCostoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.centroCostoDao.find(CentroCosto.class, obj.getIdCentroCosto());
				this.centroCostoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.centroCostoDao.find(CentroCosto.class, obj.getIdCentroCosto());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<CentroCosto> listarCentroCosto(BaseSearch filtro) {
		return this.centroCostoDao.listar(filtro);
	}

	@Override
	public int contarListarCentroCosto(BaseSearch filtro) {
		return this.centroCostoDao.contar(filtro);
	}

	@Override
	public CentroCosto findCentroCosto(CentroCosto filtro) {
		return centroCostoDao.findCentroCosto(filtro);
	}

	@Override
	public Personal controladorAccionPersonal(Personal obj, AccionType accionType) throws IOException {
		switch (accionType) {
			case CREAR:
				obj.setIdPersonal(this.personalDao.generarId());
				this.personalDao.save(obj);
				integrarUsuario(obj, accionType);
				break;
			case MODIFICAR:
				this.personalDao.update(obj);
				break;
			case ELIMINAR:
				obj = this.personalDao.find(Personal.class, obj.getIdPersonal());
				this.personalDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.personalDao.find(obj.getIdPersonal());
				break;

			default:
				break;
		}

		return obj;
	}

	private void integrarUsuario(Personal personal, AccionType accionType) throws IOException {
		var obj = new UsuarioVO();
		obj.setNombre(personal.getNombres());
		obj.setApellidoPaterno(personal.getApellidoPaterno());
		obj.setApellidoMaterno(personal.getApellidoMaterno());
		obj.setEmail(personal.getEmail());
		obj.setTelefono(personal.getTelefono1());
		obj.setCelular(personal.getCelular());
		obj.setUserName(personal.getNroDoc());
		obj.setUsuarioSession(personal.getUsuarioSession());
		obj.setUserPassword(personal.getNroDoc());
		obj.setTipoUsuario(new TipoUsuarioVO());
		obj.getTipoUsuario().setIdTipoUsuario(TIPO_USUARIO_EMPLEADO);
		obj.setCodigoExterno(personal.getIdPersonal());
		obj.setIdEntidadSelect(personal.getIdEntidad());
		obj.setId(GRUPO_USUARIO_EMPLEADO);
		obj.setEstado(EstadoGeneralState.ACTIVO.getKey());
		securityClient.integracionUsuario(obj, accionType);
	}

	@Override
	public List<Personal> listarPersonal(BaseSearch filtro) {
		return this.personalDao.listar(filtro);
	}

	@Override
	public List<String> listarPersonalIds(BaseSearch filtro) {
		return this.personalDao.listarIds(filtro);
	}
	
	@Override
	public int contarListarPersonal(BaseSearch filtro) {
		return this.personalDao.contar(filtro);
	}

	@Override
	public Personal findPersonal(Personal filtro) {
		return personalDao.findPersonal(filtro);
	}

	@Override
	public DetalleContrado controladorAccionDetalleContrado(DetalleContrado obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdDetalleContrado(this.detalleContradoDao.generarId());
				this.detalleContradoDao.save(obj);
				break;
			case MODIFICAR:
				this.detalleContradoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.detalleContradoDao.find(DetalleContrado.class, obj.getIdDetalleContrado());
				this.detalleContradoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.detalleContradoDao.find(DetalleContrado.class, obj.getIdDetalleContrado());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<DetalleContrado> listarDetalleContrado(BaseSearch filtro) {
		return this.detalleContradoDao.listar(filtro);
	}

	@Override
	public int contarListarDetalleContrado(BaseSearch detalleContrado) {
		return this.detalleContradoDao.contar(detalleContrado);
	}

	@Override
	public Contrato controladorAccionContrato(Contrato obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdContrato(this.contratoDao.generarId());
				this.contratoDao.save(obj);
				break;
			case MODIFICAR:
				this.contratoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.contratoDao.find(Contrato.class, obj.getIdContrato());
				this.contratoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.contratoDao.find(Contrato.class, obj.getIdContrato());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<Contrato> listarContrato(BaseSearch filtro) {
		return this.contratoDao.listar(filtro);
	}

	@Override
	public int contarListarContrato(BaseSearch filtro) {
		return this.contratoDao.contar(filtro);
	}

	@Override
	public CuentaBancariaPersonal controladorAccionCuentaBancariaPersonal(CuentaBancariaPersonal obj,
			AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdCuentaBancariaPersonal(this.cuentaBancariaPersonalDao.generarId());
				this.cuentaBancariaPersonalDao.save(obj);
				break;
			case MODIFICAR:
				this.cuentaBancariaPersonalDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.cuentaBancariaPersonalDao.find(CuentaBancariaPersonal.class,
						obj.getIdCuentaBancariaPersonal());
				this.cuentaBancariaPersonalDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.cuentaBancariaPersonalDao.find(CuentaBancariaPersonal.class,
						obj.getIdCuentaBancariaPersonal());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<CuentaBancariaPersonal> listarCuentaBancariaPersonal(BaseSearch filtro) {
		return this.cuentaBancariaPersonalDao.listar(filtro);
	}

	@Override
	public int contarListarCuentaBancariaPersonal(BaseSearch filtro) {
		return this.cuentaBancariaPersonalDao.contar(filtro);
	}

	@Override
	public HistorialBasico controladorAccionHistorialBasico(HistorialBasico obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdHistorialBasico(this.historialBasicoDao.generarId());
				this.historialBasicoDao.save(obj);
				break;
			case MODIFICAR:
				this.historialBasicoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.historialBasicoDao.find(HistorialBasico.class, obj.getIdHistorialBasico());
				this.historialBasicoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.historialBasicoDao.find(HistorialBasico.class, obj.getIdHistorialBasico());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<HistorialBasico> listarHistorialBasico(BaseSearch filtro) {
		return this.historialBasicoDao.listar(filtro);
	}

	@Override
	public int contarListarHistorialBasico(BaseSearch filtro) {
		return this.historialBasicoDao.contar(filtro);
	}

	@Override
	public AsociarCentroCosto controladorAccionAsociarCentroCosto(AsociarCentroCosto obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdAsociarCentroCosto(this.asociarCentroCostoDao.generarId());
				this.asociarCentroCostoDao.save(obj);
				break;

			case MODIFICAR:
				this.asociarCentroCostoDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.asociarCentroCostoDao.find(AsociarCentroCosto.class, obj.getIdAsociarCentroCosto());
				this.asociarCentroCostoDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.asociarCentroCostoDao.find(AsociarCentroCosto.class, obj.getIdAsociarCentroCosto());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<AsociarCentroCosto> listarAsociarCentroCosto(BaseSearch filtro) {
		return this.asociarCentroCostoDao.listar(filtro);
	}

	@Override
	public int contarListarAsociarCentroCosto(BaseSearch filtro) {
		return this.asociarCentroCostoDao.contar(filtro);
	}

	@Override
	public Carrera controladorAccionCarrera(Carrera obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdCarrera(this.carreraDao.generarId());
				this.carreraDao.save(obj);
				break;
			case MODIFICAR:
				this.carreraDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.carreraDao.find(Carrera.class, obj.getIdCarrera());
				this.carreraDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.carreraDao.find(Carrera.class, obj.getIdCarrera());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<Carrera> listarCarrera(BaseSearch filtro) {
		return this.carreraDao.listar(filtro);
	}

	@Override
	public int contarListarCarrera(BaseSearch filtro) {
		return this.carreraDao.contar(filtro);
	}

	@Override
	public Carrera findCarrera(Carrera filtro) {
		return carreraDao.findCarrera(filtro);
	}

	@Override
	public Institucion controladorAccionInstitucion(Institucion obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdInstitucion(this.institucionDao.generarId());
				this.institucionDao.save(obj);
				break;
			case MODIFICAR:
				this.institucionDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.institucionDao.find(Institucion.class, obj.getIdInstitucion());
				this.institucionDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.institucionDao.find(Institucion.class, obj.getIdInstitucion());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<Institucion> listarInstitucion(BaseSearch filtro) {
		return this.institucionDao.listar(filtro);
	}

	@Override
	public int contarListarInstitucion(BaseSearch filtro) {
		return this.institucionDao.contar(filtro);
	}

	@Override
	public DireccionPersonal controladorAccionDireccionPersonal(DireccionPersonal obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdDireccionPersonal(this.direccionPersonalDao.generarId());
				this.direccionPersonalDao.save(obj);
				break;
			case MODIFICAR:
				this.direccionPersonalDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.direccionPersonalDao.find(DireccionPersonal.class, obj.getIdDireccionPersonal());
				this.direccionPersonalDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.direccionPersonalDao.find(DireccionPersonal.class, obj.getIdDireccionPersonal());
				break;

			default:
				break;
		}
		return obj;
	}

	@Override
	public List<DireccionPersonal> listarDireccionPersonal(BaseSearch filtro) {
		return this.direccionPersonalDao.listar(filtro);
	}

	@Override
	public int contarListarDireccionPersonal(BaseSearch filtro) {
		return this.direccionPersonalDao.contar(filtro);
	}

	@Override
	public Beneficiarios controladorAccionBeneficiarios(Beneficiarios obj, AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdBeneficiario(this.beneficiariosDao.generarId());
				this.beneficiariosDao.save(obj);
				break;
			case MODIFICAR:
				this.beneficiariosDao.update(obj);
				break;
			case ELIMINAR:
				obj = this.beneficiariosDao.find(Beneficiarios.class, obj.getIdBeneficiario());
				this.beneficiariosDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.beneficiariosDao.find(Beneficiarios.class, obj.getIdBeneficiario());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<Beneficiarios> listarBeneficiarios(BaseSearch filtro) {
		return this.beneficiariosDao.listar(filtro);
	}

	@Override
	public int contarListarBeneficiarios(BaseSearch filtro) {
		return this.beneficiariosDao.contar(filtro);
	}

	@Override
	public PeriodoLaboraPersonal controladorAccionPeriodoLaboraPersonal(PeriodoLaboraPersonal obj,
			AccionType accionType) {
		switch (accionType) {
			case CREAR:
				obj.setIdPeriodoLaboraPersonal(this.periodoLaboraPersonalDao.generarId());
				this.periodoLaboraPersonalDao.save(obj);
				break;
			case MODIFICAR:
				this.periodoLaboraPersonalDao.update(obj);
				break;

			case ELIMINAR:
				obj = this.periodoLaboraPersonalDao.find(PeriodoLaboraPersonal.class, obj.getIdPeriodoLaboraPersonal());
				this.periodoLaboraPersonalDao.delete(obj);
				break;

			case FIND_BY_ID:
				obj = this.periodoLaboraPersonalDao.find(PeriodoLaboraPersonal.class, obj.getIdPeriodoLaboraPersonal());
				break;

			default:
				break;
		}

		return obj;
	}

	@Override
	public List<PeriodoLaboraPersonal> listarPeriodoLaboraPersonal(BaseSearch filtro) {
		return this.periodoLaboraPersonalDao.listar(filtro);
	}

	@Override
	public int contarListarPeriodoLaboraPersonal(BaseSearch filtro) {
		return this.periodoLaboraPersonalDao.contar(filtro);
	}

	@Override
	public Map<String, BigDecimal> obtenerBasicoPersonalMap(Long idCategoriaTrabajador) {
		return this.historialBasicoDao.obtenerBasicoPersonalMap(idCategoriaTrabajador);
	}

}