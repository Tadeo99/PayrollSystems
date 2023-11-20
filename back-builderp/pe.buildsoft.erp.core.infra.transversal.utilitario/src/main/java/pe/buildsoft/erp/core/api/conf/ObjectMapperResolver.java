package pe.buildsoft.erp.core.api.conf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

//https://www.cloudhadoop.com/java8-serialize-datetime/
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

	//	2013-08-20T08:16:26.937
	//https://stackoverflow.com/questions/30234594/whats-the-difference-between-zoneddatetime-and-offsetdatetime
	//https://mkyong.com/java8/java-convert-OffsetDateTime-to-localdatetime/
	//https://docs.oracle.com/javase/tutorial/datetime/iso/datetime.html
	//private static String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	//private static String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ";
	
	private ObjectMapper mapper;

	public ObjectMapperResolver() {
		mapper = new ObjectMapper().registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//mapper.setDateFormat(new SimpleDateFormat(  DateTimeFormatter.ISO_OffsetDateTime.toString()));
	}

	@Override
	public ObjectMapper getContext(Class<?> cls) {
		return mapper;
	}
}