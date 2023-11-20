package pe.buildsoft.erp.core.domain.interfaces.servicios.admision;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.admision.Apoderado;
import pe.buildsoft.erp.core.domain.entidades.admision.AsignaPostulante;
import pe.buildsoft.erp.core.domain.entidades.admision.DetSede;
import pe.buildsoft.erp.core.domain.entidades.admision.Grado;
import pe.buildsoft.erp.core.domain.entidades.admision.Postulante;
import pe.buildsoft.erp.core.domain.entidades.admision.Seccion;
import pe.buildsoft.erp.core.domain.entidades.admision.Sede;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class AdmisionServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface AdmisionServiceLocal {
	/**
	 * Controlador accion seccion.
	 *
	 * @param obj        el seccion
	 * @param accionType el accion type
	 * @return the seccion
	 * @throws Exception the exception
	 */
	Seccion controladorAccionSeccion(Seccion obj, AccionType accionType);

	/**
	 * Listar seccion.
	 *
	 * @param filtro el seccion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Seccion> listarSeccion(BaseSearch filtro);

	/**
	 * contar lista seccion.
	 *
	 * @param filtro el seccion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarSeccion(BaseSearch filtro);

	/**
	 * Controlador accion apoderado.
	 *
	 * @param obj        el apoderado
	 * @param accionType el accion type
	 * @return the apoderado
	 * @throws Exception the exception
	 */
	Apoderado controladorAccionApoderado(Apoderado obj, AccionType accionType) throws IOException;

	/**
	 * Listar apoderado.
	 *
	 * @param filtro el apoderado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Apoderado> listarApoderado(BaseSearchVO filtro);

	/**
	 * contar lista apoderado.
	 *
	 * @param filtro el apoderado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarApoderado(BaseSearchVO filtro);

	/**
	 * Listar asigna postulante.
	 *
	 * @param filtro el asigna postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AsignaPostulante> listarAsignaPostulante(BaseSearchVO filtro);

	/**
	 * contar lista asigna postulante.
	 *
	 * @param filtro el asigna postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAsignaPostulante(BaseSearchVO filtro);

	/**
	 * Listar det sede.
	 *
	 * @param filtro el det sede
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetSede> listarDetSede(BaseSearch filtro);

	/**
	 * contar lista det sede.
	 *
	 * @param filtro el det sede
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarDetSede(BaseSearch filtro);

	/**
	 * Controlador accion grado.
	 *
	 * @param obj        el grado
	 * @param accionType el accion type
	 * @return the grado
	 * @throws Exception the exception
	 */
	Grado controladorAccionGrado(Grado obj, AccionType accionType);

	/**
	 * Listar grado.
	 *
	 * @param filtro el grado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Grado> listarGrado(BaseSearch filtro);

	Map<Long, List<SelectItemVO>> listarGradoMap(BaseSearch filtro);

	/**
	 * contar lista grado.
	 *
	 * @param filtro el grado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarGrado(BaseSearch filtro);

	/**
	 * Controlador accion postulante.
	 *
	 * @param obj        el postulante
	 * @param accionType el accion type
	 * @return the postulante
	 * @throws Exception the exception
	 */
	Postulante controladorAccionPostulante(Postulante obj, AccionType accionType);

	/**
	 * Listar postulante.
	 *
	 * @param filtro el postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Postulante> listarPostulante(BaseSearchVO filtro);

	/**
	 * contar lista postulante.
	 *
	 * @param filtro el postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPostulante(BaseSearchVO filtro);

	/**
	 * Controlador accion sede.
	 *
	 * @param obj        el sede
	 * @param accionType el accion type
	 * @return the sede
	 * @throws Exception the exception
	 */
	Sede controladorAccionSede(Sede obj, AccionType accionType);

	List<Sede> vacantesDisponibles(BaseSearch filtro);

	/**
	 * Listar sede.
	 *
	 * @param filtro el sede
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Sede> listarSede(BaseSearch filtro);

	/**
	 * contar lista sede.
	 *
	 * @param filtro el sede
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarSede(BaseSearch filtro);

}