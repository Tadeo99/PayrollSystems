package pe.buildsoft.erp.core.domain.interfaces.servicios.generador;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.generador.ConfigParametro;
import pe.buildsoft.erp.core.domain.entidades.generador.Modelo;
import pe.buildsoft.erp.core.domain.entidades.generador.Atributo;
import pe.buildsoft.erp.core.domain.entidades.generador.EquivalenciaType;
import pe.buildsoft.erp.core.domain.entidades.generador.GrupoNegocio;
import pe.buildsoft.erp.core.domain.entidades.generador.Plantilla;
import pe.buildsoft.erp.core.domain.entidades.generador.Proyecto;
import pe.buildsoft.erp.core.domain.entidades.generador.Tecnologia;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class GeneradorServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface GeneradorServiceLocal {
	/**
	 * Controlador accion config tabla class det.
	 *
	 * @param configTablaClassDet el config tabla class det
	 * @param accionType          el accion type
	 * @return the config tabla class det
	 * @throws Exception the exception
	 */
	Atributo controladorAccionAtributo(Atributo obj, AccionType accionType);

	/**
	 * Listar config tabla class det.
	 *
	 * @param configTablaClassDet el config tabla class det
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Atributo> listarAtributo(BaseSearch filtro);

	/**
	 * contar lista config tabla class det.
	 *
	 * @param configTablaClassDet el config tabla class det
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAtributo(BaseSearch filtro);

	/**
	 * Controlador accion proyecto.
	 *
	 * @param proyecto   el proyecto
	 * @param accionType el accion type
	 * @return the proyecto
	 * @throws Exception the exception
	 */
	Proyecto controladorAccionProyecto(Proyecto obj, AccionType accionType);

	/**
	 * Listar proyecto.
	 *
	 * @param proyecto el proyecto
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Proyecto> listarProyecto(BaseSearch filtro);

	/**
	 * contar lista proyecto.
	 *
	 * @param proyecto el proyecto
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarProyecto(BaseSearch filtro);


	/**
	 * Controlador accion tecnologia.
	 *
	 * @param tecnologia el tecnologia
	 * @param accionType el accion type
	 * @return the tecnologia
	 * @throws Exception the exception
	 */
	Tecnologia controladorAccionTecnologia(Tecnologia obj, AccionType accionType);

	/**
	 * Listar tecnologia.
	 *
	 * @param tecnologia el tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Tecnologia> listarTecnologia(BaseSearch filtro);

	/**
	 * contar lista tecnologia.
	 *
	 * @param tecnologia el tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarTecnologia(BaseSearch filtro);

	/**
	 * Controlador accion config type equivalencia.
	 *
	 * @param configTypeEquivalencia el config type equivalencia
	 * @param accionType             el accion type
	 * @return the config type equivalencia
	 * @throws Exception the exception
	 */
	EquivalenciaType controladorAccionEquivalenciaType(EquivalenciaType obj, AccionType accionType);

	/**
	 * Listar config type equivalencia.
	 *
	 * @param configTypeEquivalencia el config type equivalencia
	 * @return the list
	 * @throws Exception the exception
	 */
	List<EquivalenciaType> listarEquivalenciaType(BaseSearch filtro);

	/**
	 * contar lista config type equivalencia.
	 *
	 * @param configTypeEquivalencia el config type equivalencia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarEquivalenciaType(BaseSearch filtro);

	/**
	 * Controlador accion config grupo servicio.
	 *
	 * @param configGrupoServicio el config grupo servicio
	 * @param accionType          el accion type
	 * @return the config grupo servicio
	 * @throws Exception the exception
	 */
	GrupoNegocio controladorAccionGrupoNegocio(GrupoNegocio obj, AccionType accionType);

	/**
	 * Listar config grupo servicio.
	 *
	 * @param configGrupoServicio el config grupo servicio
	 * @return the list
	 * @throws Exception the exception
	 */
	List<GrupoNegocio> listarGrupoNegocio(BaseSearch filtro);

	/**
	 * contar lista config grupo servicio.
	 *
	 * @param configGrupoServicio el config grupo servicio
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarGrupoNegocio(BaseSearch filtro);

	/**
	 * Controlador accion config arquetipo.
	 *
	 * @param configParametro el config arquetipo
	 * @param accionType      el accion type
	 * @return the config arquetipo
	 * @throws Exception the exception
	 */
	ConfigParametro controladorAccionConfigParametro(ConfigParametro obj, AccionType accionType);

	/**
	 * Listar config arquetipo.
	 *
	 * @param configParametro el config arquetipo
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfigParametro> listarConfigParametro(BaseSearch filtro);

	/**
	 * contar lista config arquetipo.
	 *
	 * @param configParametro el config arquetipo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConfigParametro(BaseSearch filtro);


	/**
	 * Controlador accion config tabla class.
	 *
	 * @param configTablaClass el config tabla class
	 * @param accionType       el accion type
	 * @return the config tabla class
	 * @throws Exception the exception
	 */
	Modelo controladorAccionModelo(Modelo obj, AccionType accionType);

	/**
	 * Listar config tabla class.
	 *
	 * @param configTablaClass el config tabla class
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Modelo> listarModelo(BaseSearch filtro);

	/**
	 * contar lista config tabla class.
	 *
	 * @param configTablaClass el config tabla class
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarModelo(BaseSearch filtro);

	/**
	 * Controlador accion config tecnologia.
	 *
	 * @param configTecnologia el config tecnologia
	 * @param accionType       el accion type
	 * @return the config tecnologia
	 * @throws Exception the exception
	 */
	Plantilla controladorAccionPlantilla(Plantilla obj, AccionType accionType);

	/**
	 * Listar config tecnologia.
	 *
	 * @param configTecnologia el config tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Plantilla> listarPlantilla(BaseSearch filtro);

	/**
	 * contar lista config tecnologia.
	 *
	 * @param configTecnologia el config tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarPlantilla(BaseSearch filtro);

}