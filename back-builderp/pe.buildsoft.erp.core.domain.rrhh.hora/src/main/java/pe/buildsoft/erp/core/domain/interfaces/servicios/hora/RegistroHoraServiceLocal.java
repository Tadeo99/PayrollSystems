package pe.buildsoft.erp.core.domain.interfaces.servicios.hora;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.hora.Periodo;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHora;
import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHoraDet;
import pe.buildsoft.erp.core.domain.entidades.hora.Requerimiento;
import pe.buildsoft.erp.core.domain.entidades.hora.RequerimientoPersonal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class CamoteMantenedorServiceLocal.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Mon Sep 06 16:48:51 COT 2021
 * @since BuildErp 1.0
 */
@Local
public interface RegistroHoraServiceLocal {

	RegistroHora registrarHora(RegistroHora obj, AccionType accionType);

	List<RegistroHora> listarRegistroHora(BaseSearch filtro);

	int contarListarRegistroHora(BaseSearch filtro);

	String generarIdRegistroHora();

	List<RegistroHoraDet> listarRegistroHoraDet(BaseSearch filtro);

	Periodo controladorAccionPeriodo(Periodo obj, AccionType accionType);

	List<Periodo> listarPeriodo(BaseSearch filtro);

	int contarListarPeriodo(BaseSearch filtro);

	String generarIdPeriodo();

	List<Requerimiento> listarRequerimiento(BaseSearch filtro);

	Requerimiento controladorAccionRequerimiento(Requerimiento obj, AccionType accionType);

	int contarListarRequerimiento(BaseSearch filtro);

	RequerimientoPersonal controladorAccionRequerimientoPersonal(RequerimientoPersonal obj, AccionType accionType);

	List<RequerimientoPersonal> listarRequerimientoPersonal(BaseSearch filtro);

	int contarListarRequerimientoPersonal(BaseSearch filtro);

}