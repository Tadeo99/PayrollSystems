package pe.buildsoft.erp.core.domain.servicios.cola;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroCola;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ColaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ColaNocturaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ConfiguracionFiltroColaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ConfiguracionFiltroReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ConfiguracionColaServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ValorConfiguracionFiltroReporteDaoLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class ConfiguracionColaServiceImpl.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ConfiguracionColaServiceImpl implements ConfiguracionColaServiceLocal {

	private static Logger log = LoggerFactory.getLogger(ConfiguracionColaServiceImpl.class);

	/** El servicio configuracion filtro reporte dao impl. */
	@Inject
	private ConfiguracionFiltroReporteDaoLocal configuracionFiltroReporteDaoImpl;

	/** El servicio configuracion filtro cola dao impl. */
	@Inject
	private ConfiguracionFiltroColaDaoLocal configuracionFiltroColaDaoImpl;

	/** El servicio valor configuracion filtro reporte dao impl. */
	@Inject
	private ValorConfiguracionFiltroReporteDaoLocal valorConfiguracionFiltroReporteDaoImpl;

	/** El servicio cola noctura dao impl. */
	@Inject
	private ColaNocturaDaoLocal colaNocturaDaoImpl;

	/** El servicio cola dao impl. */
	@Inject
	private ColaDaoLocal colaDaoImpl;

	@Override
	public String actualizarEstadoColaNoctura(String idColaNocturna,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate) {
		return colaNocturaDaoImpl.actualizarEstadoColaNoctura(idColaNocturna, estadoSolicitudEjecucionEstate);
	}

	@Override
	public ConfiguracionFiltroReporte controladorAccionConfiguracionFiltroReporte(ConfiguracionFiltroReporte obj,
			AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCodigoFiltro(this.configuracionFiltroReporteDaoImpl.generarId());
			this.configuracionFiltroReporteDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.configuracionFiltroReporteDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.configuracionFiltroReporteDaoImpl.find(ConfiguracionFiltroReporte.class,
					obj.getIdCodigoFiltro());
			this.configuracionFiltroReporteDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.configuracionFiltroReporteDaoImpl.find(ConfiguracionFiltroReporte.class,
					obj.getIdCodigoFiltro());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ConfiguracionFiltroReporte> listarConfiguracionFiltroReporte(BaseSearch filtro) {
		return this.configuracionFiltroReporteDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarConfiguracionFiltroReporte(BaseSearch filtro) {
		return this.configuracionFiltroReporteDaoImpl.contar(filtro);
	}

	@Override
	public ConfiguracionFiltroCola controladorAccionConfiguracionFiltroCola(ConfiguracionFiltroCola obj,
			AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdJuegoFiltro(this.configuracionFiltroColaDaoImpl.generarId());
			this.configuracionFiltroColaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.configuracionFiltroColaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.configuracionFiltroColaDaoImpl.find(ConfiguracionFiltroCola.class, obj.getIdJuegoFiltro());
			this.configuracionFiltroColaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.configuracionFiltroColaDaoImpl.find(ConfiguracionFiltroCola.class, obj.getIdJuegoFiltro());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public TreeMap<Long, List<ConfiguracionFiltroCola>> listarConfiguracionFiltroColaMap(BaseSearch filtro) {
		TreeMap<Long, List<ConfiguracionFiltroCola>> respuesta = new TreeMap<>();
		List<ConfiguracionFiltroCola> resultado = this.configuracionFiltroColaDaoImpl.listar(filtro);
		for (var objConfFiltroCola : resultado) {
			Long key = objConfFiltroCola.getCodigoJuego();
			if (!respuesta.containsKey(key)) {
				List<ConfiguracionFiltroCola> configuracionFiltroList = new ArrayList<>();
				configuracionFiltroList.add(objConfFiltroCola);
				respuesta.put(key, configuracionFiltroList);
			} else {
				List<ConfiguracionFiltroCola> configuracionFiltroList = respuesta.get(key);
				configuracionFiltroList.add(objConfFiltroCola);
			}
		}
		return respuesta;
	}

	@Override
	public int contarListarConfiguracionFiltroCola(BaseSearch filtro) {
		return this.configuracionFiltroColaDaoImpl.contar(filtro);
	}

	@Override
	public ValorConfiguracionFiltroReporte controladorAccionValorConfiguracionFiltroReporte(
			ValorConfiguracionFiltroReporte obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdValorFiltro(this.valorConfiguracionFiltroReporteDaoImpl.generarId());
			this.valorConfiguracionFiltroReporteDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.valorConfiguracionFiltroReporteDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.valorConfiguracionFiltroReporteDaoImpl.find(ValorConfiguracionFiltroReporte.class,
					obj.getIdValorFiltro());
			this.valorConfiguracionFiltroReporteDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.valorConfiguracionFiltroReporteDaoImpl.find(ValorConfiguracionFiltroReporte.class,
					obj.getIdValorFiltro());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ValorConfiguracionFiltroReporte> listarValorConfiguracionFiltroReporte(BaseSearch filtro) {
		return this.valorConfiguracionFiltroReporteDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarValorConfiguracionFiltroReporte(BaseSearch filtro) {
		return this.valorConfiguracionFiltroReporteDaoImpl.contar(filtro);
	}

	@Override
	public ColaNoctura controladorAccionColaNoctura(ColaNoctura obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdColaNocturna(this.colaNocturaDaoImpl.generarId());
			this.colaNocturaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.colaNocturaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.colaNocturaDaoImpl.find(ColaNoctura.class, obj.getIdColaNocturna());
			this.colaNocturaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.colaNocturaDaoImpl.find(ColaNoctura.class, obj.getIdColaNocturna());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ColaNoctura> listarColaNoctura(BaseSearch filtro) {
		return this.colaNocturaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarColaNoctura(BaseSearch filtro) {
		return this.colaNocturaDaoImpl.contar(filtro);
	}

	@Override
	public Cola controladorAccionCola(Cola obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdCola(this.colaDaoImpl.generarId());
			this.colaDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.colaDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.colaDaoImpl.find(Cola.class, obj.getIdCola());
			this.colaDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.colaDaoImpl.find(Cola.class, obj.getIdCola());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<Cola> listarCola(BaseSearch filtro) {
		return this.colaDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarCola(BaseSearch filtro) {
		return this.colaDaoImpl.contar(filtro);
	}
}