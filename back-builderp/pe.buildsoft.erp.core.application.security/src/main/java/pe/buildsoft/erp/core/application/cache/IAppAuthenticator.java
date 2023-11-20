package pe.buildsoft.erp.core.application.cache;

import java.security.GeneralSecurityException;
import java.util.Map;

import jakarta.ejb.Local;
import javax.security.auth.login.LoginException;

import pe.buildsoft.erp.core.application.entidades.security.UsuarioDTO;


@Local
public  interface IAppAuthenticator {

    Map<String,Object> login( String serviceKey, String userName, String userPassword ) throws LoginException ;
    
    boolean isAuthTokenValid( String serviceKey, String authToken ) ;

    /**
     * This method checks is the service key is valid
     *
     * @param serviceKey
     * @return TRUE if service key matches the pre-generated ones in service key
     * storage. FALSE for otherwise.
     */
    boolean isServiceKeyValid( String serviceKey );

    void logout( String serviceKey, String authToken ) throws GeneralSecurityException ;
    
    UsuarioDTO getUsuario( String serviceKey, String authToken);
   
    boolean isSessionActiva( String serviceKey, String authToken) ;
    
    String getUserName( String authToken) ;
    
    UsuarioDTO getByUserName( String userName) ;
}