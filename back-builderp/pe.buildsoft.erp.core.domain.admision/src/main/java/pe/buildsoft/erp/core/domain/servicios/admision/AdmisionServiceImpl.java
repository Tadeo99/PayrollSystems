package pe.buildsoft.erp.core.domain.servicios.admision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.admision.Apoderado;
import pe.buildsoft.erp.core.domain.entidades.admision.AsignaPostulante;
import pe.buildsoft.erp.core.domain.entidades.admision.DetSede;
import pe.buildsoft.erp.core.domain.entidades.admision.Grado;
import pe.buildsoft.erp.core.domain.entidades.admision.Postulante;
import pe.buildsoft.erp.core.domain.entidades.admision.Seccion;
import pe.buildsoft.erp.core.domain.entidades.admision.Sede;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.ApoderadoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.AsignaPostulanteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.DetSedeDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.GradoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.PostulanteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.SeccionDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.admision.SedeDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.admision.AdmisionServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.security.SecurityServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.security.vo.TipoUsuarioVO;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.security.vo.UsuarioVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class AdmisionServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AdmisionServiceImpl implements AdmisionServiceLocal {

	private static final long TIPO_USUARIO_APODERADO = 2L;

	/** El servicio seccion dao impl. */
	@Inject
	private SeccionDaoLocal seccionDaoImpl;

	/** El servicio apoderado dao impl. */
	@Inject
	private ApoderadoDaoLocal apoderadoDaoImpl;

	/** El servicio asigna postulante dao impl. */
	@Inject
	private AsignaPostulanteDaoLocal asignaPostulanteDaoImpl;

	/** El servicio det sede dao impl. */
	@Inject
	private DetSedeDaoLocal detSedeDaoImpl;

	/** El servicio grado dao impl. */
	@Inject
	private GradoDaoLocal gradoDaoImpl;

	/** El servicio postulante dao impl. */
	@Inject
	private PostulanteDaoLocal postulanteDaoImpl;

	/** El servicio sede dao impl. */
	@Inject
	private SedeDaoLocal sedeDaoImpl;

	@Inject
	private SecurityServiceClient securityClient;

	@Override
	public Seccion controladorAccionSeccion(Seccion obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdSeccion(this.seccionDaoImpl.generarId());
			this.seccionDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.seccionDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.seccionDaoImpl.find(Seccion.class, obj.getIdSeccion());
			this.seccionDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.seccionDaoImpl.find(Seccion.class, obj.getIdSeccion());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Seccion> listarSeccion(BaseSearch filtro) {
		return this.seccionDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarSeccion(BaseSearch filtro) {
		return this.seccionDaoImpl.contar(filtro);
	}

	@Override
	public Apoderado controladorAccionApoderado(Apoderado obj, AccionType accionType) throws IOException {
		switch (accionType) {
		case CREAR:
			obj.setEstado(EstadoGeneralState.ACTIVO.getKey());
			obj.setIdApoderado(this.apoderadoDaoImpl.generarId());
			this.apoderadoDaoImpl.save(obj);
			integrarUsuario(obj, accionType);
			break;
		case MODIFICAR:
			this.apoderadoDaoImpl.update(obj);
			integrarUsuario(obj, accionType);
			break;

		case ELIMINAR:
			obj = this.apoderadoDaoImpl.find(Apoderado.class, obj.getIdApoderado());
			this.apoderadoDaoImpl.delete(obj);
			integrarUsuario(obj, accionType);
			break;

		case FIND_BY_ID:
			obj = this.apoderadoDaoImpl.find(Apoderado.class, obj.getIdApoderado());
			break;

		default:
			break;
		}
		return obj;
	}

	private void integrarUsuario(Apoderado objA, AccionType accionType) throws IOException {
		UsuarioVO obj = new UsuarioVO();
		obj.setNombre(objA.getNombres());
		obj.setApellidoPaterno(objA.getApellidoPaterno());
		obj.setApellidoMaterno(objA.getApellidoMaterno());
		obj.setEmail(objA.getEmail());
		obj.setTelefono(objA.getTelefono1());
		obj.setCelular(objA.getCelular());
		obj.setUserName(objA.getNroDoc());
		obj.setUsuarioSession(objA.getUsuarioSession());
		obj.setUserPassword(objA.getClave());
		obj.setTipoUsuario(new TipoUsuarioVO());
		obj.getTipoUsuario().setIdTipoUsuario(TIPO_USUARIO_APODERADO);
		obj.setCodigoExterno(objA.getIdApoderado());
		obj.setIdEntidadSelect(objA.getIdEntidadSelect());
		obj.setId(TIPO_USUARIO_APODERADO);
		obj.setEstado(objA.getEstado());
		obj.setAccion(accionType.getKey());
		obj.setAuthToken(objA.getAuthToken());
		securityClient.integracionUsuario(obj, accionType);
	}

	@Override
	public List<Apoderado> listarApoderado(BaseSearchVO filtro) {
		return this.apoderadoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarApoderado(BaseSearchVO filtro) {
		return this.apoderadoDaoImpl.contar(filtro);
	}

	private void registrarAsignaPostulante(Postulante obj) {
		AsignaPostulante resultadoEntity = obj.getAsignaPostulante();
		resultadoEntity.setIdAsignaPostulante(this.asignaPostulanteDaoImpl.generarId());
		resultadoEntity.setPostulante(obj);
		resultadoEntity.setEstado(EstadoGeneralState.ACTIVO.getKey());
		this.asignaPostulanteDaoImpl.save(resultadoEntity);
	}

	@Override
	public List<AsignaPostulante> listarAsignaPostulante(BaseSearchVO filtro) {
		return this.asignaPostulanteDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarAsignaPostulante(BaseSearchVO filtro) {
		return this.asignaPostulanteDaoImpl.contarListar(filtro);
	}

	public void registrarDetSede(List<DetSede> listaDetSede, Sede obj) {
		this.detSedeDaoImpl.eliminarBySede(obj.getIdSede());
		for (var detSede : listaDetSede) {
			detSede.setIdDetSede(this.detSedeDaoImpl.generarId());
			detSede.setSede(obj);
			this.detSedeDaoImpl.save(detSede);
		}
	}

	@Override
	public List<DetSede> listarDetSede(BaseSearch filtro) {
		return this.detSedeDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarDetSede(BaseSearch filtro) {
		return this.detSedeDaoImpl.contar(filtro);
	}

	@Override
	public Grado controladorAccionGrado(Grado obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdGrado(this.gradoDaoImpl.generarId());
			this.gradoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.gradoDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.gradoDaoImpl.find(Grado.class, obj.getIdGrado());
			this.gradoDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.gradoDaoImpl.find(Grado.class, obj.getIdGrado());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Grado> listarGrado(BaseSearch grado) {
		return this.gradoDaoImpl.listar(grado);
	}

	@Override
	public Map<Long, List<SelectItemVO>> listarGradoMap(BaseSearch grado) {
		Map<Long, List<SelectItemVO>> resultado = new HashMap<>();
		List<Grado> resul = listarGrado(grado);
		for (var obj : resul) {
			// Long key = obj.getItemByNivel().getIdItem();
			Long key = obj.getIdItemByNivel();
			if (!resultado.containsKey(key)) {
				List<SelectItemVO> value = new ArrayList<>();
				value.add(new SelectItemVO(obj.getIdGrado(), obj.getNombre(), obj.getNombre()));
				resultado.put(key, value);
			} else {
				List<SelectItemVO> value = resultado.get(key);
				value.add(new SelectItemVO(obj.getIdGrado(), obj.getNombre(), obj.getNombre()));
			}
		}
		return resultado;
	}

	@Override
	public int contarListarGrado(BaseSearch grado) {
		return this.gradoDaoImpl.contar(grado);
	}

	@Override
	public Postulante controladorAccionPostulante(Postulante obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPostulante(this.postulanteDaoImpl.generarId());
			this.postulanteDaoImpl.save(obj);
			if (StringUtil.isNotNullOrBlank(obj.getAsignaPostulante())
					&& StringUtil.isNotNullOrBlank(obj.getAsignaPostulante().getApoderado())) {
				registrarAsignaPostulante(obj);
			}
			break;
		case MODIFICAR:
			this.postulanteDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.postulanteDaoImpl.find(Postulante.class, obj.getIdPostulante());
			this.postulanteDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.postulanteDaoImpl.find(Postulante.class, obj.getIdPostulante());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Postulante> listarPostulante(BaseSearchVO filtro) {
		return this.postulanteDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPostulante(BaseSearchVO filtro) {
		return this.postulanteDaoImpl.contar(filtro);
	}

	@Override
	public Sede controladorAccionSede(Sede obj, AccionType accionType) {
		boolean resitrarDetalle = false;
		switch (accionType) {
		case CREAR:
			obj.setIdSede(this.sedeDaoImpl.generarId());
			this.sedeDaoImpl.save(obj);
			resitrarDetalle = true;
			break;
		case MODIFICAR:
			this.sedeDaoImpl.update(obj);
			resitrarDetalle = true;
			break;

		case ELIMINAR:
			obj = this.sedeDaoImpl.find(Sede.class, obj.getIdSede());
			this.sedeDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.sedeDaoImpl.find(Sede.class, obj.getIdSede());
			break;

		default:
			break;
		}
		if (resitrarDetalle) {
			registrarDetSede(obj.getSedeDetSedeList(), obj);
		}
		return obj;
	}

	@Override
	public List<Sede> vacantesDisponibles(BaseSearch filtro) {
		List<Sede> resultado = listarSede(filtro);
		List<String> listaIdSede = new ArrayList<>();
		for (var obj : resultado) {
			listaIdSede.add(obj.getIdSede());
		}
		Map<String, List<DetSede>> listaDetSedeMap = this.detSedeDaoImpl.listarDetSedeMap(listaIdSede);
		Map<String, Long> listaVacantesAsignadosMap = this.asignaPostulanteDaoImpl
				.listarAsignaPostulanteMap(listaIdSede);
		for (var obj : resultado) {
			String key = obj.getIdSede();
			if (listaDetSedeMap.containsKey(key)) {
				obj.setSedeDetSedeList(listaDetSedeMap.get(key));
				for (var objDet : obj.getSedeDetSedeList()) {
					objDet.setChecked(false);
					String keyDet = StringUtil.generarKey(obj.getIdSede(), objDet.getGrado().getIdGrado());
					Long cantidadPostulante = 0L;
					if (listaVacantesAsignadosMap.containsKey(keyDet)) {
						cantidadPostulante = listaVacantesAsignadosMap.get(keyDet);
					}
					if (!objDet.getNroVacante().equals(cantidadPostulante)) {
						objDet.setChecked(true);// vacante disponible
					}

				}
			}
		}
		return resultado;
	}

	@Override
	public List<Sede> listarSede(BaseSearch filtro) {
		return this.sedeDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarSede(BaseSearch filtro) {
		return this.sedeDaoImpl.contar(filtro);
	}
}