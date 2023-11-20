package pe.buildsoft.erp.core.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * @author ndavilal --
 */

@ApplicationPath("/api")
@OpenAPIDefinition(info = @Info(
        title = "common service", 
        version = "1.0.0", 
        contact = @Contact(
                name = "mdavilal", 
                email = "ndavilal88@gmail.com",
                url = "http://buildsoft.com.pe")
        ),
        servers = {
            @Server(url = "/common",description = "Servicio common")
        }
)
public class ApplicationConfig extends Application {

	public ApplicationConfig() {

	}

}
