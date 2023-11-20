package pe.buildsoft.erp.core.application.servicios.generador;

import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.generador.ConfigParametroDTO;
import pe.buildsoft.erp.core.application.entidades.generador.ModeloDTO;
import pe.buildsoft.erp.core.application.entidades.generador.AtributoDTO;
import pe.buildsoft.erp.core.application.entidades.generador.EquivalenciaTypeDTO;
import pe.buildsoft.erp.core.application.entidades.generador.GrupoNegocioDTO;
import pe.buildsoft.erp.core.application.entidades.generador.PlantillaDTO;
import pe.buildsoft.erp.core.application.entidades.generador.ProyectoDTO;
import pe.buildsoft.erp.core.application.entidades.generador.TecnologiaDTO;
import pe.buildsoft.erp.core.application.interfaces.generador.GeneradorAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.generador.ConfigParametro;
import pe.buildsoft.erp.core.domain.entidades.generador.Modelo;
import pe.buildsoft.erp.core.domain.entidades.generador.Atributo;
import pe.buildsoft.erp.core.domain.entidades.generador.EquivalenciaType;
import pe.buildsoft.erp.core.domain.entidades.generador.GrupoNegocio;
import pe.buildsoft.erp.core.domain.entidades.generador.Plantilla;
import pe.buildsoft.erp.core.domain.entidades.generador.Proyecto;
import pe.buildsoft.erp.core.domain.entidades.generador.Tecnologia;
import pe.buildsoft.erp.core.domain.interfaces.servicios.generador.GeneradorServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

/**
 * La Class GeneradorAppServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 12:14:56 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class GeneradorAppServiceImpl extends BaseTransfer implements GeneradorAppServiceLocal {

	@Inject
	private GeneradorServiceLocal servicio;

	public GeneradorAppServiceImpl() {
		AtributosEntityCacheUtil.getInstance().sincronizarAtributos("pe.buildsoft.erp.core.domain.entidades.generador");
	}

	@Override
	public List<SelectItemVO> listarSelectItem(String groupName, BaseSearch filtro) {
		List<SelectItemVO> resultado = new ArrayList<>();
		var filtroPer = new BaseSearch();
		switch (groupName) {
		case "tecnologia":
			resultado = listarTecnologiaSelectItem(filtro);
			break;

		case "grupoNegocio":
			filtroPer.setId(filtro.getId());
			filtroPer.setSortFields("nombre");
			filtroPer.setSortDirections("asc");
			resultado = listarGrupoNegocioSelectItem(filtroPer);
			break;

		case "modelo":
			filtroPer.setId(filtro.getId());
			filtroPer.setSortFields("nombreTabla");
			filtroPer.setSortDirections("asc");
			resultado = listarModeloSelectItem(filtroPer);
			break;

		default:
			break;
		}
		return resultado;
	}

	@Override
	public int contarSelectItem(String groupName, BaseSearch filtro) {
		int resultado = 0;
		var filtroPer = new BaseSearch();
		switch (groupName) {
		case "tecnologia":
			resultado = servicio.contarListarTecnologia(filtro);
			break;

		case "grupoNegocio":
			filtroPer.setId(filtro.getId());
			resultado = servicio.contarListarGrupoNegocio(filtroPer);
			break;

		case "modelo":
			filtroPer.setId(filtro.getId());
			resultado = servicio.contarListarModelo(filtroPer);
			break;

		default:
			break;
		}
		return resultado;
	}

	private List<SelectItemVO> listarTecnologiaSelectItem(BaseSearch filtro) {
		var resultado = new ArrayList<SelectItemVO>();
		var resulTmp = servicio.listarTecnologia(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdTecnologia(), obj.getNombre(), obj.getTipo()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarGrupoNegocioSelectItem(BaseSearch filtro) {
		var resultado = new ArrayList<SelectItemVO>();
		var resulTmp = servicio.listarGrupoNegocio(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdGrupoNegocio(), obj.getNombre()));
		}
		return resultado;
	}

	private List<SelectItemVO> listarModeloSelectItem(BaseSearch filtro) {
		var resultado = new ArrayList<SelectItemVO>();
		var resulTmp = servicio.listarModelo(filtro);
		for (var obj : resulTmp) {
			resultado.add(new SelectItemVO(obj.getIdModelo(), obj.getNombreTabla()));
		}
		return resultado;
	}

	@Override
	public AtributoDTO controladorAccionAtributo(AtributoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionAtributo(to(obj, Atributo.class), accionType), AtributoDTO.class);
	}

	@Override
	public List<AtributoDTO> listarAtributo(BaseSearch filtro) {
		return toList(this.servicio.listarAtributo(filtro), AtributoDTO.class);
	}

	@Override
	public int contarListarAtributo(BaseSearch filtro) {
		return this.servicio.contarListarAtributo(filtro);
	}

	@Override
	public ProyectoDTO controladorAccionProyecto(ProyectoDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionProyecto(to(obj, Proyecto.class), accionType), ProyectoDTO.class);
	}

	@Override
	public List<ProyectoDTO> listarProyecto(BaseSearch filtro) {
		return toList(this.servicio.listarProyecto(filtro), ProyectoDTO.class, "tecnologiaByBaseDatos",
				"tecnologiaByBackEnd", "tecnologiaByFrontEnd");
	}

	@Override
	public int contarListarProyecto(BaseSearch filtro) {
		return this.servicio.contarListarProyecto(filtro);
	}

	@Override
	public TecnologiaDTO controladorAccionTecnologia(TecnologiaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionTecnologia(to(obj, Tecnologia.class), accionType), TecnologiaDTO.class);
	}

	@Override
	public List<TecnologiaDTO> listarTecnologia(BaseSearch filtro) {
		return toList(this.servicio.listarTecnologia(filtro), TecnologiaDTO.class);
	}

	@Override
	public int contarListarTecnologia(BaseSearch filtro) {
		return this.servicio.contarListarTecnologia(filtro);
	}

	@Override
	public EquivalenciaTypeDTO controladorAccionEquivalenciaType(EquivalenciaTypeDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionEquivalenciaType(to(obj, EquivalenciaType.class), accionType),
				EquivalenciaTypeDTO.class);
	}

	@Override
	public List<EquivalenciaTypeDTO> listarEquivalenciaType(BaseSearch filtro) {
		return toList(this.servicio.listarEquivalenciaType(filtro), EquivalenciaTypeDTO.class, "tecnologiaEquivalente");
	}

	@Override
	public int contarListarEquivalenciaType(BaseSearch filtro) {
		return this.servicio.contarListarEquivalenciaType(filtro);
	}

	@Override
	public GrupoNegocioDTO controladorAccionGrupoNegocio(GrupoNegocioDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionGrupoNegocio(to(obj, GrupoNegocio.class), accionType),
				GrupoNegocioDTO.class);
	}

	@Override
	public List<GrupoNegocioDTO> listarGrupoNegocio(BaseSearch filtro) {
		return toList(this.servicio.listarGrupoNegocio(filtro), GrupoNegocioDTO.class);
	}

	@Override
	public int contarListarGrupoNegocio(BaseSearch filtro) {
		return this.servicio.contarListarGrupoNegocio(filtro);
	}

	@Override
	public ConfigParametroDTO controladorAccionConfigParametro(ConfigParametroDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionConfigParametro(to(obj, ConfigParametro.class), accionType),
				ConfigParametroDTO.class);
	}

	@Override
	public List<ConfigParametroDTO> listarConfigParametro(BaseSearch filtro) {
		return toList(this.servicio.listarConfigParametro(filtro), ConfigParametroDTO.class);
	}

	@Override
	public int contarListarConfigParametro(BaseSearch filtro) {
		return this.servicio.contarListarConfigParametro(filtro);
	}

	@Override
	public ModeloDTO controladorAccionModelo(ModeloDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionModelo(to(obj, Modelo.class), accionType), ModeloDTO.class);
	}

	@Override
	public List<ModeloDTO> listarModelo(BaseSearch filtro) {
		return toList(this.servicio.listarModelo(filtro), ModeloDTO.class, "grupoNegocio");
	}

	@Override
	public int contarListarModelo(BaseSearch filtro) {
		return this.servicio.contarListarModelo(filtro);
	}

	@Override
	public PlantillaDTO controladorAccionPlantilla(PlantillaDTO obj, AccionType accionType) {
		return toDTO(servicio.controladorAccionPlantilla(to(obj, Plantilla.class), accionType), PlantillaDTO.class);
	}

	@Override
	public List<PlantillaDTO> listarPlantilla(BaseSearch filtro) {
		return toList(this.servicio.listarPlantilla(filtro), PlantillaDTO.class);
	}

	@Override
	public int contarListarPlantilla(BaseSearch filtro) {
		return this.servicio.contarListarPlantilla(filtro);
	}
}