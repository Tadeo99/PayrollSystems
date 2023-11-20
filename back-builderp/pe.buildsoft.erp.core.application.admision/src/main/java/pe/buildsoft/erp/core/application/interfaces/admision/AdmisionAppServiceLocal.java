package pe.buildsoft.erp.core.application.interfaces.admision;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.application.entidades.admision.ApoderadoDTO;
import pe.buildsoft.erp.core.application.entidades.admision.AsignaPostulanteDTO;
import pe.buildsoft.erp.core.application.entidades.admision.GradoDTO;
import pe.buildsoft.erp.core.application.entidades.admision.PostulanteDTO;
import pe.buildsoft.erp.core.application.entidades.admision.SeccionDTO;
import pe.buildsoft.erp.core.application.entidades.admision.SedeDTO;
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
public interface AdmisionAppServiceLocal {
	
	List<SelectItemVO> listarSelectItem(String groupName);

	/**
	 * Controlador accion seccion.
	 *
	 * @param obj        el seccion
	 * @param accionType el accion type
	 * @return the seccion
	 * @throws Exception the exception
	 */
	SeccionDTO controladorAccionSeccion(SeccionDTO obj, AccionType accionType);

	/**
	 * Listar seccion.
	 *
	 * @param filtro el seccion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<SeccionDTO> listarSeccion(BaseSearch filtro);

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
	ApoderadoDTO controladorAccionApoderado(ApoderadoDTO obj, AccionType accionType) throws IOException;

	/**
	 * Listar apoderado.
	 *
	 * @param filtro el apoderado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ApoderadoDTO> listarApoderado(BaseSearchVO filtro);

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
	List<AsignaPostulanteDTO> listarAsignaPostulante(BaseSearchVO filtro);

	/**
	 * contar lista asigna postulante.
	 *
	 * @param filtro el asigna postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAsignaPostulante(BaseSearchVO filtro);

	/**
	 * Controlador accion grado.
	 *
	 * @param obj        el grado
	 * @param accionType el accion type
	 * @return the grado
	 * @throws Exception the exception
	 */
	GradoDTO controladorAccionGrado(GradoDTO obj, AccionType accionType);

	/**
	 * Listar grado.
	 *
	 * @param filtro el grado
	 * @return the list
	 * @throws Exception the exception
	 */
	List<GradoDTO> listarGrado(BaseSearch filtro);

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
	PostulanteDTO controladorAccionPostulante(PostulanteDTO obj, AccionType accionType);

	/**
	 * Listar postulante.
	 *
	 * @param filtro el postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PostulanteDTO> listarPostulante(BaseSearchVO filtro);

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
	SedeDTO controladorAccionSede(SedeDTO obj, AccionType accionType);

	List<SedeDTO> vacantesDisponibles(BaseSearch filtro);

	/**
	 * Listar sede.
	 *
	 * @param filtro el sede
	 * @return the list
	 * @throws Exception the exception
	 */
	List<SedeDTO> listarSede(BaseSearch filtro);

	/**
	 * contar lista sede.
	 *
	 * @param filtro el sede
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarSede(BaseSearch filtro);

}