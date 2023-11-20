package pe.buildsoft.erp.core.application.entidades.aas;


import pe.buildsoft.erp.core.application.entidades.security.UsuarioDTO;

public class UserPrincipal /*implements UserDetails */{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuarioDTO usuario;
 
    public UserPrincipal(UsuarioDTO usuario) {
        this.usuario = usuario;	
    }

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
    
}