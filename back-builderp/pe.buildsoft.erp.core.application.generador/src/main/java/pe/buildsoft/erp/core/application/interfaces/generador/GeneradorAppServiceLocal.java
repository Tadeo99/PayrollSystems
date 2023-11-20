package pe.buildsoft.erp.core.application.interfaces.generador;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.application.entidades.generador.ConfigParametroDTO;
import pe.buildsoft.erp.core.application.entidades.generador.ModeloDTO;
import pe.buildsoft.erp.core.application.entidades.generador.AtributoDTO;
import pe.buildsoft.erp.core.application.entidades.generador.EquivalenciaTypeDTO;
import pe.buildsoft.erp.core.application.entidades.generador.GrupoNegocioDTO;
import pe.buildsoft.erp.core.application.entidades.generador.PlantillaDTO;
import pe.buildsoft.erp.core.application.entidades.generador.ProyectoDTO;
import pe.buildsoft.erp.core.application.entidades.generador.TecnologiaDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class GeneradorAppServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 12:14:56 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface GeneradorAppServiceLocal {
	
	List<SelectItemVO> listarSelectItem(String groupName,BaseSearch filtro);
	int contarSelectItem(String groupName,BaseSearch filtro);
	
	/**
	 * Controlador accion config tabla class det.
	 *
	 * @param obj        el config tabla class det
	 * @param accionType el accion type
	 * @return the config tabla class det
	 * @throws Exception the exception
	 */
	AtributoDTO controladorAccionAtributo(AtributoDTO obj, AccionType accionType);

	/**
	 * Listar config tabla class det.
	 *
	 * @param filtro el config tabla class det
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AtributoDTO> listarAtributo(BaseSearch filtro);

	/**
	 * contar lista config tabla class det.
	 *
	 * @param filtro el config tabla class det
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAtributo(BaseSearch filtro);

	/**
	 * Controlador accion proyecto.
	 *
	 * @param obj        el proyecto
	 * @param accionType el accion type
	 * @return the proyecto
	 * @throws Exception the exception
	 */
	ProyectoDTO controladorAccionProyecto(ProyectoDTO obj, AccionType accionType);

	/**
	 * Listar proyecto.
	 *
	 * @param filtro el proyecto
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ProyectoDTO> listarProyecto(BaseSearch filtro);

	/**
	 * contar lista proyecto.
	 *
	 * @param filtro el proyecto
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarProyecto(BaseSearch filtro);

	/**
	 * Controlador accion tecnologia.
	 *
	 * @param obj        el tecnologia
	 * @param accionType el accion type
	 * @return the tecnologia
	 * @throws Exception the exception
	 */
	TecnologiaDTO controladorAccionTecnologia(TecnologiaDTO obj, AccionType accionType);

	/**
	 * Listar tecnologia.
	 *
	 * @param filtro el tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	List<TecnologiaDTO> listarTecnologia(BaseSearch filtro);

	/**
	 * contar lista tecnologia.
	 *
	 * @param filtro el tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarTecnologia(BaseSearch filtro);

	/**
	 * Controlador accion config type equivalencia.
	 *
	 * @param obj        el config type equivalencia
	 * @param accionType el accion type
	 * @return the config type equivalencia
	 * @throws Exception the exception
	 */
	EquivalenciaTypeDTO controladorAccionEquivalenciaType(EquivalenciaTypeDTO obj,
			AccionType accionType);

	/**
	 * Listar config type equivalencia.
	 *
	 * @param filtro el config type equivalencia
	 * @return the list
	 * @throws Exception the exception
	 */
	List<EquivalenciaTypeDTO> listarEquivalenciaType(BaseSearch filtro);

	/**
	 * contar lista config type equivalencia.
	 *
	 * @param filtro el config type equivalencia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarEquivalenciaType(BaseSearch filtro);

	/**
	 * Controlador accion config grupo servicio.
	 *
	 * @param obj        el config grupo servicio
	 * @param accionType el accion type
	 * @return the config grupo servicio
	 * @throws Exception the exception
	 */
	GrupoNegocioDTO controladorAccionGrupoNegocio(GrupoNegocioDTO obj, AccionType accionType);

	/**
	 * Listar config grupo servicio.
	 *
	 * @param filtro el config grupo servicio
	 * @return the list
	 * @throws Exception the exception
	 */
	List<GrupoNegocioDTO> listarGrupoNegocio(BaseSearch filtro);

	/**
	 * contar lista config grupo servicio.
	 *
	 * @param filtro el config grupo servicio
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarGrupoNegocio(BaseSearch filtro);

	/**
	 * Controlador accion config arquetipo.
	 *
	 * @param obj        el config arquetipo
	 * @param accionType el accion type
	 * @return the config arquetipo
	 * @throws Exception the exception
	 */
	ConfigParametroDTO controladorAccionConfigParametro(ConfigParametroDTO obj, AccionType accionType);

	/**
	 * Listar config arquetipo.
	 *
	 * @param filtro el config arquetipo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfigParametroDTO> listarConfigParametro(BaseSearch filtro);

	/**
	 * contar lista config arquetipo.
	 *
	 * @param filtro el config arquetipo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConfigParametro(BaseSearch filtro);

	/**
	 * Controlador accion config tabla class.
	 *
	 * @param obj        el config tabla class
	 * @param accionType el accion type
	 * @return the config tabla class
	 * @throws Exception the exception
	 */
	ModeloDTO controladorAccionModelo(ModeloDTO obj, AccionType accionType);

	/**
	 * Listar config tabla class.
	 *
	 * @param filtro el config tabla class
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ModeloDTO> listarModelo(BaseSearch filtro);

	/**
	 * contar lista config tabla class.
	 *
	 * @param filtro el config tabla class
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarModelo(BaseSearch filtro);

	/**
	 * Controlador accion config tecnologia.
	 *
	 * @param obj        el config tecnologia
	 * @param accionType el accion type
	 * @return the config tecnologia
	 * @throws Exception the exception
	 */
	PlantillaDTO controladorAccionPlantilla(PlantillaDTO obj, AccionType accionType);

	/**
	 * Listar config tecnologia.
	 *
	 * @param filtro el config tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PlantillaDTO> listarPlantilla(BaseSearch filtro);

	/**
	 * contar lista config tecnologia.
	 *
	 * @param filtro el config tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPlantilla(BaseSearch filtro);

}