package pe.buildsoft.erp.core.application.servicios.admision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.admision.ApoderadoDTO;
import pe.buildsoft.erp.core.application.entidades.admision.AsignaPostulanteDTO;
import pe.buildsoft.erp.core.application.entidades.admision.DetSedeDTO;
import pe.buildsoft.erp.core.application.entidades.admision.GradoDTO;
import pe.buildsoft.erp.core.application.entidades.admision.PostulanteDTO;
import pe.buildsoft.erp.core.application.entidades.admision.SeccionDTO;
import pe.buildsoft.erp.core.application.entidades.admision.SedeDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.application.interfaces.admision.AdmisionAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.admision.Apoderado;
import pe.buildsoft.erp.core.domain.entidades.admision.AsignaPostulante;
import pe.buildsoft.erp.core.domain.entidades.admision.DetSede;
import pe.buildsoft.erp.core.domain.entidades.admision.Grado;
import pe.buildsoft.erp.core.domain.entidades.admision.Postulante;
import pe.buildsoft.erp.core.domain.entidades.admision.Seccion;
import pe.buildsoft.erp.core.domain.entidades.admision.Sede;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.domain.interfaces.servicios.admision.AdmisionServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
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
public class AdmisionAppServiceImpl extends BaseTransfer implements AdmisionAppServiceLocal {

	private static final String ITEM_BY_NACIONALIDAD = "itemByNacionalidad";

	private static final String LUGAR_NACIMIENTO = "lugarNacimiento";

	private static final String ITEM_BY_DOC_IDENTIDAD = "itemByDocIdentidad";

	private static final String CACHE_GRADO = "grado";
	private static final String CACHE_ITEM = "item";
	private static final String CACHE_UBIGEO = "ubigeo";

	/** El servicio admision impl. */
	@Inject
	private AdmisionServiceLocal servicio;

	@Inject
	private ICache cacheUtil;

	public AdmisionAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.admision");
	}

	@Override
	public List<SelectItemVO> listarSelectItem(String groupName) {
		List<SelectItemVO> resultado = new ArrayList<>();
		switch (groupName) {
			case "grado":
				resultado = listarGradoSelectItem();
				break;
			case "ubigeoSede":
				resultado = listarUbigeoSedeSelectItem();
				break;
			default:
				break;
		}
		return resultado;
	}

	private List<SelectItemVO> listarUbigeoSedeSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idUbigeo");
		filtro.setSortDirections("asc");
		filtro.setEstado(EstadoGeneralState.ACTIVO.getKey());
		List<SelectItemVO> resultado = new ArrayList<>();
		List<Sede> resulTmp = servicio.listarSede(filtro);
		Map<String, String> ubigeoMap = new HashMap<String, String>();
		for (var obj : resulTmp) {
			if (!ubigeoMap.containsKey(obj.getIdUbigeo())) {
				resultado.add(new SelectItemVO(obj.getIdUbigeo(), obj.getIdUbigeo()));
				ubigeoMap.put(obj.getIdUbigeo(),"");
			}
		}
		return resultado;
	}

	private List<SelectItemVO> listarGradoSelectItem() {
		var filtro = new BaseSearch();
		filtro.setSortFields("idGrupo");
		filtro.setSortDirections("asc");
		List<SelectItemVO> resultado = new ArrayList<>();
		List<Grado> resulTmp = servicio.listarGrado(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdGrado(), obj.getNombre(), obj.getIdItemByNivel() + ""));
		}
		return resultado;
	}

	@Override
	public SeccionDTO controladorAccionSeccion(SeccionDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionSeccion(to(obj, Seccion.class), accionType), SeccionDTO.class);
	}

	@Override
	public List<SeccionDTO> listarSeccion(BaseSearch filtro) {
		List<SeccionDTO> resultado = toList(this.servicio.listarSeccion(filtro), SeccionDTO.class, "grado");

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idItemByNivel", "itemByNivel");

		for (var objData : resultado) {
			cacheUtil.completarData(objData.getGrado(), completeMap, CACHE_ITEM);
			generarDescripcionViewGrado(objData.getGrado());
			generarDescripcionViewSeccion(objData);
		}
		return resultado;
	}

	private SeccionDTO generarDescripcionViewSeccion(SeccionDTO obj) {
		obj.setDescripcionView(obj.getGrado().getDescripcionView() + " - " + obj.getNombre());
		return obj;
	}

	@Override
	public int contarListarSeccion(BaseSearch filtro) {
		return this.servicio.contarListarSeccion(filtro);
	}

	@Override
	public ApoderadoDTO controladorAccionApoderado(ApoderadoDTO obj, AccionType accionType) throws IOException {
		return toDTO(servicio.controladorAccionApoderado(to(obj, Apoderado.class), accionType), ApoderadoDTO.class);

	}

	@Override
	public List<ApoderadoDTO> listarApoderado(BaseSearchVO filtro) {
		List<ApoderadoDTO> resultado = toList(this.servicio.listarApoderado(filtro), ApoderadoDTO.class);

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idItemByDocIdentidad", "itemByDocIdentidad");
		completeMap.put("idItemByEstadoCivil", "itemByEstadoCivil");
		completeMap.put("idItemByNacionalidad", "itemByNacionalidad");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);

		completeMap = new HashMap<>();
		completeMap.put("idLugarNacimiento", "lugarNacimiento");
		cacheUtil.completarData(resultado, completeMap, CACHE_UBIGEO);

		for (var obj : resultado) {
			generarDescripcionViewItem(obj.getItemByDocIdentidad());
			generarDescripcionViewItem(obj.getItemByEstadoCivil());
			generarDescripcionViewItem(obj.getItemByNacionalidad());
			generarDescripcionViewUbigeo(obj.getLugarNacimiento());
		}
		return resultado;
	}

	@Override
	public int contarListarApoderado(BaseSearchVO filtro) {
		return this.servicio.contarListarApoderado(filtro);
	}

	@Override
	public List<AsignaPostulanteDTO> listarAsignaPostulante(BaseSearchVO filtro) {
		List<AsignaPostulanteDTO> resultado = new ArrayList<>();
		List<AsignaPostulante> resultadoTmp = this.servicio.listarAsignaPostulante(filtro);

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idItemByDocIdentidad", "itemByDocIdentidad");
		completeMap.put("idItemByNacionalidad", "itemByNacionalidad");

		for (var objTmp : resultadoTmp) {
			AsignaPostulanteDTO obj = toDTO(objTmp, AsignaPostulanteDTO.class);
			obj.setPostulante(toDTO(objTmp.getPostulante(), PostulanteDTO.class));

			cacheUtil.completarData(obj.getPostulante(), completeMap, CACHE_ITEM);

			completeMap = new HashMap<>();
			completeMap.put("idLugarNacimiento", LUGAR_NACIMIENTO);
			cacheUtil.completarData(obj.getPostulante(), completeMap, CACHE_UBIGEO);

			generarDescripcionViewItem(obj.getPostulante().getItemByDocIdentidad());
			generarDescripcionViewItem(obj.getPostulante().getItemByNacionalidad());
			generarDescripcionViewUbigeo(obj.getPostulante().getLugarNacimiento());
			resultado.add(obj);
		}
		return resultado;
	}

	@Override
	public int contarListarAsignaPostulante(BaseSearchVO filtro) {
		return this.servicio.contarListarAsignaPostulante(filtro);
	}

	private List<DetSedeDTO> listarDetSede(BaseSearch filtro) {
		return toList(this.servicio.listarDetSede(filtro), DetSedeDTO.class, "grado");
	}

	private GradoDTO generarDescripcionViewGrado(GradoDTO obj) {
		if (obj.getItemByNivel() != null) {
			obj.setDescripcionView(obj.getItemByNivel().getNombre() + " " + obj.getNombre());
		}
		return obj;
	}

	@Override
	public GradoDTO controladorAccionGrado(GradoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionGrado(to(obj, Grado.class), accionType), GradoDTO.class);
	}

	@Override
	public List<GradoDTO> listarGrado(BaseSearch filtro) {
		List<GradoDTO> resultado = toList(this.servicio.listarGrado(filtro), GradoDTO.class);
		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idItemByNivel", "itemByNivel");
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);

		for (var obj : resultado) {
			generarDescripcionViewGrado(obj);
		}
		return resultado;
	}

	@Override
	public Map<Long, List<SelectItemVO>> listarGradoMap(BaseSearch filtro) {
		return servicio.listarGradoMap(filtro);
	}

	@Override
	public int contarListarGrado(BaseSearch filtro) {
		return this.servicio.contarListarGrado(filtro);
	}

	@Override
	public PostulanteDTO controladorAccionPostulante(PostulanteDTO obj, AccionType accionType) {
		return toDTO(
				servicio.controladorAccionPostulante(to(obj, Postulante.class, "asignaPostulante:{grado}"), accionType),
				PostulanteDTO.class);

	}

	@Override
	public List<PostulanteDTO> listarPostulante(BaseSearchVO filtro) {
		List<PostulanteDTO> resultado = toList(this.servicio.listarPostulante(filtro), PostulanteDTO.class);

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idItemByDocIdentidad", ITEM_BY_DOC_IDENTIDAD);
		completeMap.put("idItemByNacionalidad", ITEM_BY_NACIONALIDAD);
		cacheUtil.completarData(resultado, completeMap, CACHE_ITEM);

		completeMap = new HashMap<>();
		completeMap.put("idLugarNacimiento", LUGAR_NACIMIENTO);
		cacheUtil.completarData(resultado, completeMap, CACHE_UBIGEO);

		return resultado;
	}

	private UbigeoDTO generarDescripcionViewUbigeo(UbigeoDTO ubigeo) {
		if (ubigeo != null && StringUtil.isNotNullOrBlank(ubigeo.getIdUbigeo())) {
			ubigeo.setDescripcionView(ubigeo.getIdUbigeo() + " " + ubigeo.getDescripcion());
		}
		return ubigeo;
	}

	private ItemDTO generarDescripcionViewItem(ItemDTO item) {
		if (item != null && StringUtil.isNotNullOrBlank(item.getCodigoExterno())) {
			item.setDescripcionView(item.getCodigoExterno() + " " + item.getNombre());
		}
		return item;
	}

	@Override
	public int contarListarPostulante(BaseSearchVO filtro) {
		return this.servicio.contarListarPostulante(filtro);
	}

	@Override
	public SedeDTO controladorAccionSede(SedeDTO obj, AccionType accionType) {
		SedeDTO resultado = new SedeDTO();
		Sede objE = to(obj, Sede.class);
		objE.setSedeDetSedeList(toListEntity(obj.getSedeDetSedeList(), DetSede.class));
		resultado = toDTO(servicio.controladorAccionSede(objE, accionType), SedeDTO.class);
		if (AccionType.FIND_BY_ID.equals(accionType)) {
			var filtro = new BaseSearch();
			filtro.setId(obj.getIdSede());
			resultado.setSedeDetSedeList(this.listarDetSede(filtro));
		}
		return resultado;
	}

	@Override
	public List<SedeDTO> vacantesDisponibles(BaseSearch filtro) {
		List<SedeDTO> resultado = new ArrayList<>();
		List<Sede> resultadoTmp = servicio.vacantesDisponibles(filtro);
		for (var objTmp : resultadoTmp) {
			SedeDTO obj = toDTO(objTmp, SedeDTO.class);
			obj.setSedeDetSedeList(toList(objTmp.getSedeDetSedeList(), DetSedeDTO.class, "grado"));
			resultado.add(obj);
		}
		return resultado;
	}

	@Override
	public List<SedeDTO> listarSede(BaseSearch filtro) {
		List<SedeDTO> resultado = toList(this.servicio.listarSede(filtro), SedeDTO.class);

		Map<String, String> completeMap = new HashMap<>();
		completeMap.put("idUbigeo", "ubigeo");
		cacheUtil.completarData(resultado, completeMap, CACHE_UBIGEO);

		for (var obj : resultado) {
			generarDescripcionViewUbigeo(obj.getUbigeo());
		}
		return resultado;
	}

	@Override
	public int contarListarSede(BaseSearch filtro) {
		return this.servicio.contarListarSede(filtro);
	}

}