package pe.buildsoft.erp.core.domain.servicios.aas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.security.ConfiguracionMenu;
import pe.buildsoft.erp.core.domain.entidades.security.Entidad;
import pe.buildsoft.erp.core.domain.entidades.security.Menu;
import pe.buildsoft.erp.core.domain.entidades.security.Privilegio;
import pe.buildsoft.erp.core.domain.entidades.security.Properties;
import pe.buildsoft.erp.core.domain.entidades.security.PropertiesLenguaje;
import pe.buildsoft.erp.core.domain.entidades.security.Usuario;
import pe.buildsoft.erp.core.domain.entidades.security.UsuarioEntidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.ConfiguracionMenuDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.EntidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.MenuDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PrivilegioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PropertiesDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.PropertiesLenguajeDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.UsuarioDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.security.UsuarioEntidadDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.aas.AasServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.cryto.EncriptarUtil;

/**
 * La Class AasServiceImpl.
 * <ul>
 * <li>Copyright 2019 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, 08/01/2019
 * @since SIAA-CORE 2.1
 */
@Stateless
public class AasServiceImpl implements AasServiceLocal {

	/** El servicio persona dao impl. */
	@Inject
	private UsuarioDaoLocal usuarioDaoImpl;

	/** El servicio privilegio dao impl. */
	@Inject
	private PrivilegioDaoLocal privilegioDaoImpl;

	/** El servicio menu dao impl. */
	@Inject
	private MenuDaoLocal menuDaoImpl;

	/** El servicio usuario entidad dao impl. */
	@Inject
	private UsuarioEntidadDaoLocal usuarioEntidadDaoImpl;

	/** El servicio configuracion menu dao impl. */
	@Inject
	private ConfiguracionMenuDaoLocal configuracionMenuDaoImpl;

	/** El servicio properties lenguaje dao impl. */
	@Inject
	private PropertiesLenguajeDaoLocal propertiesLenguajeDaoImpl;

	/** El servicio properties dao impl. */
	@Inject
	private PropertiesDaoLocal propertiesDaoImpl;

	@Inject
	private EntidadDaoLocal entidadDaoImpl;

	@Override
	public Entidad finByIdEntidad(String idEntidad) {
		return entidadDaoImpl.find(Entidad.class, idEntidad);
	}

	@Override
	public Usuario validarLogin(String userName, String userPassword) {
		return usuarioDaoImpl.validarLogin(userName, EncriptarUtil.encriptar(userPassword));
	}

	@Override
	public List<ConfiguracionMenu> obtenerConfiguracionMenu(Long idMenu) {
		return configuracionMenuDaoImpl.obtenerConfiguracionMenu(idMenu);
	}

	@Override
	public List<PropertiesLenguaje> obtenerPropertiesLenguajeAllMap() {
		return propertiesLenguajeDaoImpl.obtenerPropertiesLenguajeAllMap();
	}

	@Override
	public List<Properties> obtenerPropertiesLenguaje(BaseSearch filtro) {
		return listarProperties(filtro);
	}

	public List<Properties> listarProperties(BaseSearch filtro) {
		return this.propertiesDaoImpl.listar(filtro);
	}

	@Override
	public Map<String, Boolean> obtenerPrivilegiosUsuario(String idUsuario) {
		Map<String, Boolean> resultado = new HashMap<>();
		List<Privilegio> listaPrivilegio = privilegioDaoImpl.obtenerPrivilegioByUsuario(idUsuario);
		if (listaPrivilegio != null) {
			for (var privilegio : listaPrivilegio) {
				resultado.put(privilegio.getAcronimo(), true);
			}
		}
		return resultado;
	}

	@Override
	public List<Menu> obtenerMenuUsuario(String idUsuario) {
		return menuDaoImpl.obtenerMenuUsuario(idUsuario);
	}

	@Override
	public List<UsuarioEntidad> listarUsuarioEntidad(BaseSearch filtro) {
		return usuarioEntidadDaoImpl.listar(filtro);
	}

}