package pe.buildsoft.erp.core.domain.servicios.generador;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.generador.Atributo;
import pe.buildsoft.erp.core.domain.entidades.generador.ConfigParametro;
import pe.buildsoft.erp.core.domain.entidades.generador.EquivalenciaType;
import pe.buildsoft.erp.core.domain.entidades.generador.GrupoNegocio;
import pe.buildsoft.erp.core.domain.entidades.generador.Modelo;
import pe.buildsoft.erp.core.domain.entidades.generador.Plantilla;
import pe.buildsoft.erp.core.domain.entidades.generador.Proyecto;
import pe.buildsoft.erp.core.domain.entidades.generador.Tecnologia;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.ConfigParametroDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.ModeloDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.AtributoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.EquivalenciaTypeDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.GrupoNegocioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.PlantillaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.ProyectoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.generador.TecnologiaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.generador.GeneradorServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class GeneradorServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class GeneradorServiceImpl implements GeneradorServiceLocal {
	/** El servicio config tabla class det dao impl. */
	@Inject
	private AtributoDaoLocal atributoDao;

	/** El servicio proyecto dao impl. */
	@Inject
	private ProyectoDaoLocal proyectoDao;

	/** El servicio tecnologia dao impl. */
	@Inject
	private TecnologiaDaoLocal tecnologiaDao;

	/** El servicio config type equivalencia dao impl. */
	@Inject
	private EquivalenciaTypeDaoLocal equivalenciaTypeDao;

	/** El servicio config grupo servicio dao impl. */
	@Inject
	private GrupoNegocioDaoLocal grupoNegocioDao;

	/** El servicio config arquetipo dao impl. */
	@Inject
	private ConfigParametroDaoLocal configParametroDao;

	/** El servicio config tabla class dao impl. */
	@Inject
	private ModeloDaoLocal modeloDao;

	/** El servicio config tecnologia dao impl. */
	@Inject
	private PlantillaDaoLocal plantillaDao;

	@Override
	public Atributo controladorAccionAtributo(Atributo obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdAtributo(this.atributoDao.generarId());
			this.atributoDao.save(obj);
			break;
		case MODIFICAR:
			this.atributoDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.atributoDao.find(Atributo.class, obj.getIdAtributo());
			this.atributoDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.atributoDao.find(Atributo.class, obj.getIdAtributo());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Atributo> listarAtributo(BaseSearch filtro) {
		return this.atributoDao.listar(filtro);
	}

	@Override
	public int contarListarAtributo(BaseSearch filtro) {
		return this.atributoDao.contar(filtro);
	}

	@Override
	public Proyecto controladorAccionProyecto(Proyecto obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdProyecto(this.proyectoDao.generarId());
			this.proyectoDao.save(obj);
			break;
		case MODIFICAR:
			this.proyectoDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.proyectoDao.find(Proyecto.class, obj.getIdProyecto());
			this.proyectoDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.proyectoDao.find(Proyecto.class, obj.getIdProyecto());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Proyecto> listarProyecto(BaseSearch filtro) {
		return this.proyectoDao.listar(filtro);
	}

	@Override
	public int contarListarProyecto(BaseSearch filtro) {
		return this.proyectoDao.contar(filtro);
	}

	@Override
	public Tecnologia controladorAccionTecnologia(Tecnologia obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdTecnologia(this.tecnologiaDao.generarId());
			this.tecnologiaDao.save(obj);
			break;
		case MODIFICAR:
			this.tecnologiaDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.tecnologiaDao.find(Tecnologia.class, obj.getIdTecnologia());
			this.tecnologiaDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.tecnologiaDao.find(Tecnologia.class, obj.getIdTecnologia());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Tecnologia> listarTecnologia(BaseSearch filtro) {
		return this.tecnologiaDao.listar(filtro);
	}

	@Override
	public int contarListarTecnologia(BaseSearch filtro) {
		return this.tecnologiaDao.contar(filtro);
	}

	@Override
	public EquivalenciaType controladorAccionEquivalenciaType(EquivalenciaType obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdEquivalenciaType(this.equivalenciaTypeDao.generarId());
			this.equivalenciaTypeDao.save(obj);
			break;
		case MODIFICAR:
			this.equivalenciaTypeDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.equivalenciaTypeDao.find(EquivalenciaType.class, obj.getIdEquivalenciaType());
			this.equivalenciaTypeDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.equivalenciaTypeDao.find(EquivalenciaType.class, obj.getIdEquivalenciaType());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<EquivalenciaType> listarEquivalenciaType(BaseSearch filtro) {
		return this.equivalenciaTypeDao.listar(filtro);
	}

	@Override
	public int contarListarEquivalenciaType(BaseSearch filtro) {
		return this.equivalenciaTypeDao.contar(filtro);
	}

	@Override
	public GrupoNegocio controladorAccionGrupoNegocio(GrupoNegocio obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdGrupoNegocio(this.grupoNegocioDao.generarId());
			this.grupoNegocioDao.save(obj);
			break;
		case MODIFICAR:
			this.grupoNegocioDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.grupoNegocioDao.find(GrupoNegocio.class, obj.getIdGrupoNegocio());
			this.grupoNegocioDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.grupoNegocioDao.find(GrupoNegocio.class, obj.getIdGrupoNegocio());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<GrupoNegocio> listarGrupoNegocio(BaseSearch filtro) {
		return this.grupoNegocioDao.listar(filtro);
	}

	@Override
	public int contarListarGrupoNegocio(BaseSearch filtro) {
		return this.grupoNegocioDao.contar(filtro);
	}

	@Override
	public ConfigParametro controladorAccionConfigParametro(ConfigParametro obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdConfigParametro(this.configParametroDao.generarId());
			this.configParametroDao.save(obj);
			break;
		case MODIFICAR:
			this.configParametroDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.configParametroDao.find(ConfigParametro.class, obj.getIdConfigParametro());
			this.configParametroDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.configParametroDao.find(ConfigParametro.class, obj.getIdConfigParametro());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ConfigParametro> listarConfigParametro(BaseSearch filtro) {
		return this.configParametroDao.listar(filtro);
	}

	@Override
	public int contarListarConfigParametro(BaseSearch filtro) {
		return this.configParametroDao.contar(filtro);
	}

	@Override
	public Modelo controladorAccionModelo(Modelo obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdModelo(this.modeloDao.generarId());
			this.modeloDao.save(obj);
			break;
		case MODIFICAR:
			this.modeloDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.modeloDao.find(Modelo.class, obj.getIdModelo());
			this.modeloDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.modeloDao.find(Modelo.class, obj.getIdModelo());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Modelo> listarModelo(BaseSearch filtro) {
		return this.modeloDao.listar(filtro);
	}

	@Override
	public int contarListarModelo(BaseSearch filtro) {
		return this.modeloDao.contar(filtro);
	}

	@Override
	public Plantilla controladorAccionPlantilla(Plantilla obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPlantilla(this.plantillaDao.generarId());
			this.plantillaDao.save(obj);
			break;
		case MODIFICAR:
			this.plantillaDao.update(obj);
			break;

		case ELIMINAR:
			obj = this.plantillaDao.find(Plantilla.class, obj.getIdPlantilla());
			this.plantillaDao.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.plantillaDao.find(Plantilla.class, obj.getIdPlantilla());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Plantilla> listarPlantilla(BaseSearch filtro) {
		return this.plantillaDao.listar(filtro);
	}

	@Override
	public int contarListarPlantilla(BaseSearch filtro) {
		return this.plantillaDao.contar(filtro);
	}
}