package pe.buildsoft.erp.core.domain.servicios.hora;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.hora.Periodo;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHora;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHoraDet;
import pe.buildsoft.erp.core.domain.entidades.hora.Requerimiento;
import pe.buildsoft.erp.core.domain.entidades.hora.RequerimientoPersonal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.PeriodoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RegistroHoraDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RegistroHoraDetDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RequerimientoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.RequerimientoPersonalDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.hora.RegistroHoraServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;

/**
 * La Class RegistroHorasMantenedorServiceImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:42 COT 2022
 * @since BuildErp 1.0
 */
@Stateless
public class RegistroHoraServiceImpl implements RegistroHoraServiceLocal {

	private Logger log = LoggerFactory.getLogger(RegistroHoraServiceImpl.class);

	/** El servicio registry cabecera dao impl. */
	@Inject
	private RegistroHoraDaoLocal registroHoraDaoImpl;

	/** El servicio detalle registro cabecera dao impl. */
	@Inject
	private RegistroHoraDetDaoLocal registroHoraDetDaoImpl;

	@Inject
	private PeriodoDaoLocal periodoDaoImpl;

	@Inject
	private RequerimientoDaoLocal requerimientoDaoImpl;

	/** El servicio requerimiento personal dao impl. */
	@Inject
	private RequerimientoPersonalDaoLocal requerimientoPersonalDaoImpl;

	@Override
	public RegistroHora registrarHora(RegistroHora obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdRegistroHora(this.registroHoraDaoImpl.generarId());
			this.registroHoraDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.registroHoraDaoImpl.update(obj);
			break;

		default:
			break;
		}
		registarDetRegistroHora(obj);
		return obj;
	}

	@Override
	public List<RegistroHora> listarRegistroHora(BaseSearch filtro) {
		return this.registroHoraDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarRegistroHora(BaseSearch filtro) {
		return this.registroHoraDaoImpl.contar(filtro);
	}

	@Override
	public String generarIdRegistroHora() {
		return this.registroHoraDaoImpl.generarId();
	}

	private void registarDetRegistroHora(RegistroHora objRegistroHora) {
		Long numSemana = null;
		for (var registroHoraDet : objRegistroHora.getRegistroHoraDetRegistroHoraList()) {
			numSemana = registroHoraDet.getNumSemana();
		}
		this.registroHoraDetDaoImpl.eliminar(objRegistroHora.getIdRegistroHora(), numSemana);
		for (var registroHoraDet : objRegistroHora.getRegistroHoraDetRegistroHoraList()) {
			registroHoraDet.setRegistroHora(objRegistroHora);
			registroHoraDet.setIdRegistroHoraDet(this.registroHoraDetDaoImpl.generarId());
			this.registroHoraDetDaoImpl.save(registroHoraDet);
		}
	}

	@Override
	public List<RegistroHoraDet> listarRegistroHoraDet(BaseSearch filtro) {
		return this.registroHoraDetDaoImpl.listar(filtro);
	}

	@Override
	public Periodo controladorAccionPeriodo(Periodo obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdPeriodo(this.periodoDaoImpl.generarId());
			clonarRequerimientoPersona(obj);
			break;
		case MODIFICAR:
			this.periodoDaoImpl.update(obj);
			break;
		case ELIMINAR:
			obj = this.periodoDaoImpl.find(Periodo.class, obj.getIdPeriodo());
			this.periodoDaoImpl.delete(obj);
			break;
		case FIND_BY_ID:
			obj = this.periodoDaoImpl.find(Periodo.class, obj.getIdPeriodo());
			break;
		default:
			break;
		}
		return obj;
	}

	private void clonarRequerimientoPersona(Periodo objPeriodo) {
		BaseSearch objFiltroReq = new BaseSearch();
		List<Periodo> listaPeriodo = periodoDaoImpl.listarPeriodoMax(objPeriodo);
		for (var periodo : listaPeriodo) {
			objFiltroReq.setIdPeriodo(periodo.getIdPeriodo());
		}
		objFiltroReq.setEstado("C");
		List<RequerimientoPersonal> listaReq = requerimientoPersonalDaoImpl.listar(objFiltroReq);
		this.periodoDaoImpl.save(objPeriodo);
		if (listaReq != null && !listaReq.isEmpty()) {
			for (var obj : listaReq) {
				RequerimientoPersonal temp;
				try {
					temp = (RequerimientoPersonal) BeanUtils.cloneBean(obj);
					temp.setPeriodo(new Periodo());
					temp.setIdRequerimientoPersonal(null);
					temp.setPeriodo(objPeriodo);
					temp.setIdRequerimientoPersonal(this.requerimientoPersonalDaoImpl.generarId());
					this.requerimientoPersonalDaoImpl.save(temp);
				} catch (Exception e) {
					log.error("clonarRequerimientoPersona",e);
				}
			}
		}
	}

	@Override
	public List<Periodo> listarPeriodo(BaseSearch filtro) {
		return this.periodoDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarPeriodo(BaseSearch filtro) {
		return this.periodoDaoImpl.contar(filtro);
	}

	@Override
	public String generarIdPeriodo() {
		return this.periodoDaoImpl.generarId();
	}

	public List<Requerimiento> listarRequerimiento(BaseSearch filtro) {
		return this.requerimientoDaoImpl.listar(filtro);
	}

	public Requerimiento controladorAccionRequerimiento(Requerimiento obj, AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdRequerimiento(this.requerimientoDaoImpl.generarId());
			obj.setFechaRegistra(FechaUtil.obtenerFechaActual());
			this.requerimientoDaoImpl.save(obj);
			break;
		case MODIFICAR:
			Requerimiento requerimientoTmp = this.requerimientoDaoImpl.find(Requerimiento.class,
					obj.getIdRequerimiento());
			obj.setFechaRegistra(requerimientoTmp.getFechaRegistra());
			this.requerimientoDaoImpl.update(obj);
			break;
		case ELIMINAR:
			obj = this.requerimientoDaoImpl.find(Requerimiento.class, obj.getIdRequerimiento());
			this.requerimientoDaoImpl.delete(obj);
			break;
		case FIND_BY_ID:
			obj = this.requerimientoDaoImpl.find(Requerimiento.class, obj.getIdRequerimiento());
			break;

		default:
			break;
		}
		return obj;
	}

	@Override
	public int contarListarRequerimiento(BaseSearch filtro) {
		return this.requerimientoDaoImpl.contar(filtro);
	}

	@Override
	public RequerimientoPersonal controladorAccionRequerimientoPersonal(RequerimientoPersonal obj,
			AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdRequerimientoPersonal(this.requerimientoPersonalDaoImpl.generarId());
			this.requerimientoPersonalDaoImpl.save(obj);
			break;

		case MODIFICAR:
			this.requerimientoPersonalDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.requerimientoPersonalDaoImpl.find(RequerimientoPersonal.class, obj.getIdRequerimientoPersonal());
			this.requerimientoPersonalDaoImpl.delete(obj);

			break;

		case FIND_BY_ID:
			obj = this.requerimientoPersonalDaoImpl.find(RequerimientoPersonal.class, obj.getIdRequerimientoPersonal());
			break;

		default:
			break;
		}
		return obj;
	}

	@Override
	public List<RequerimientoPersonal> listarRequerimientoPersonal(BaseSearch filtro) {
		return this.requerimientoPersonalDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarRequerimientoPersonal(BaseSearch filtro) {
		return this.requerimientoPersonalDaoImpl.contar(filtro);
	}

}