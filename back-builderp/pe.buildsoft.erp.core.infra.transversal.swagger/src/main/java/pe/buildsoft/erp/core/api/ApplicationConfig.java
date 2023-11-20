package pe.buildsoft.erp.core.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * @author ndavilal
 * 
 */
//https://piotrminkowski.com/2020/12/14/microprofile-java-microservices-on-wildfly/
//https://www.ibm.com/docs/es/was-liberty/base?topic=management-liberty-features
//https://github.com/eclipse/microprofile-config
//https://craftsmen.nl/an-introduction-to-the-eclipse-microprofile-specification-part-2/
//https://blog.payara.fish/microservices-for-java-ee-developers2
//https://microprofile.io/2021/09/01/videotutoriales-recorriendo-microprofile-en-espanol/
//https://github.com/eclipse/microprofile/releases/tag/5.0

//tremendo:
//https://www.eclipse.org/community/eclipse_newsletter/2019/september/microprofile.php
//https://github.com/phillip-kruger/microprofile-demo

//http://localhost:8080/swagger/api/openapi.json
//https://support.intershop.com/kb/index.php/Display/2914L4

//https://www.phillip-kruger.com/post/microprofile_openapi_swaggerui/

@ApplicationPath("/api")
@OpenAPIDefinition(info = @Info(
        title = "Swagger application", 
        version = "1.0.0", 
        contact = @Contact(
                name = "ndavilal", 
                email = "ndavilal88@gmail.com",
                url = "http://buildsoft.com.pe")
        ),
        servers = {
            @Server(url = "/swagger",description = "Servicio Swagger"),
        }
)
public class ApplicationConfig extends Application {

	public ApplicationConfig() {
		super();
		
	}

}
