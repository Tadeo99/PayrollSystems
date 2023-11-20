package pe.buildsoft.erp.core.application.servicios.rrhh.hora.reporte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ProcesarPlantillaUtil;

/**
 * <ul>
 * <li>Copyright 2017 ndavilal. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class GeneradorSwaggerPostman.
 *
 * @author ndavilal
 * @version 1.0 , 07/04/2015
 * @since SIAA-CORE 2.1
 */

public class GeneradorSwaggerPostman {

	private static final String SOLICITUD_RESPONSE = "Res";
	private static final String SOLICITUD_REQUEST = "Req";

	private Map<String, Map<String, String>> schemaMap = new HashMap<>();

	/**
	 * El metodo principal.
	 *
	 * @param args los argumentos
	 */
	private String getPath(JSONArray path) {
		StringBuilder resultado = new StringBuilder();
		Iterator<String> iterator = path.iterator();
		while (iterator.hasNext()) {
			resultado.append(iterator.next());
			resultado.append("/");
		}
		return resultado.toString();
	}

	public static void main(String[] args) {
		try {
			GeneradorSwaggerPostman gs = new GeneradorSwaggerPostman();
			boolean autoGenerarpath = true;
			File directory = new File("D:\\swagger\\postman");
			for (var file : directory.listFiles()) {
				try {
					if (file.isFile()) {
						JSONObject postman = (JSONObject) gs.readJsonFile("D:\\swagger\\postman\\" + file.getName());
						JSONArray item = (JSONArray) postman.get("item");
						JSONObject info = (JSONObject) postman.get("info");
						String nombre = info.containsKey("description") ? (String) info.get("description")
								: (String) info.get("name");
						List<Tags> listaTags = gs.generarTags(nombre);
						Map<String, Verbo> verboMap = new HashMap<>();
						List<Path> listPath = new ArrayList<>();
						List<String> listaPath = new ArrayList<>();
						Component componente = gs.generarComponent();
						Iterator<JSONObject> iterator = item.iterator();

						while (iterator.hasNext()) {
							JSONObject json = iterator.next();
							JSONObject request = (JSONObject) json.get("request");
							String name = (String) json.get("name");
							JSONObject body = (JSONObject) request.get("body");
							JSONObject url = (JSONObject) request.get("url");
							String path = name;
							List<String> pathGen = new ArrayList<>();
							JSONArray objPathGen = (JSONArray) url.get("path");
							Iterator<String> iteratorPath = objPathGen.iterator();
							while (iteratorPath.hasNext()) {
								pathGen.add(iteratorPath.next());
							}
							if (autoGenerarpath) {
								path = gs.getPath((JSONArray) url.get("path"));
							}
							if (!listaPath.contains(path)) {
								listaPath.add(path);
							}
							String resName = "";
							if (pathGen.size() > 0) {
								resName = pathGen.get(0);
								if (pathGen.size() >= 2) {
									resName = pathGen.get(pathGen.size() - 2) + pathGen.get(pathGen.size() - 1);
								}
							}
							JSONArray response = (JSONArray) json.get("response");
							String bodyRes = "";
							if (response != null && response.size() > 0) {
								JSONObject responseData = (JSONObject) response.get(0);
								bodyRes = (String) responseData.get("body");
							}
							// listaPath.add(path);
							System.out.println("resName " + resName);
							JSONObject jsonInput = (JSONObject) gs.readJsonText(body);
							System.out.println("jsonInput" + jsonInput);

							JSONObject jsonResponse = (JSONObject) gs.readJsonText(bodyRes);
							System.out.println("jsonResponse" + jsonResponse);

							verboMap.putAll(gs.generarVerbo(request.get("method").toString().toLowerCase(), resName,
									false, path, nombre, resName + SOLICITUD_REQUEST, resName + SOLICITUD_RESPONSE));

							gs.generarcomponent(componente, jsonInput, jsonResponse, resName + SOLICITUD_REQUEST,
									resName + SOLICITUD_RESPONSE);
						}
						listPath = gs.generarPath(verboMap, listaPath.toArray(String[]::new));

						Map<String, Object> dataModel = new HashMap<String, Object>();
						dataModel.put("tags", listaTags);
						dataModel.put("paths", listPath);
						dataModel.put("component", componente);

						StringBuilder data = new StringBuilder(ProcesarPlantillaUtil.procesarPlantillaByFreemarkerDo(
								dataModel, "D:\\swagger\\template", "demoswagger.json.ftl"));

						crearArchivo("swagger_" + file.getName().replace(".postman_collection.json", ""), data);
						System.out.println("fin commo.item.sql " + FechaUtil.obtenerFechaActual());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component generarComponent() {
		Component resultado = new Component();
		resultado.setSchemas(new ArrayList<>());
		return resultado;
	}

	private Component generarcomponent(Component resultado, JSONObject jsonInput, JSONObject jsonRes,
			String schemaInput, String schemaInputRest) {
		resultado.getSchemas().addAll(generarSchema(jsonInput, schemaInput));
		resultado.getSchemas().addAll(generarSchema(jsonRes, schemaInputRest));
		return resultado;
	}

	private List<Schema> generarSchema(JSONObject jsonObject, String schemaInput) {
		List<Schema> resultado = new ArrayList<>();
		Schema schema = new Schema();
		schema.setType("object");
		schema.setName(schemaInput);
		schema.setProperties(new ArrayList<>());
		for (var obj : jsonObject.entrySet()) {
			Map.Entry dataMap = (Map.Entry) obj;
			Properties properties = new Properties();
			String key = dataMap.getKey().toString();
			properties.setName(key);
			properties.setType(getType(dataMap));
			properties.setExample(dataMap.getValue() + "");
			properties.setDescription("");
			properties.setSchemaRef("");
			properties.setFormat("");
			if (jsonObject.get(key) instanceof JSONObject jsonObj) {
				properties.setType("");
				properties.setExample("");
				properties.setSchemaRef(key);
				resultado.addAll(generarSchema(jsonObj, key));
			} else if (jsonObject.get(key) instanceof JSONArray jsonObj) {
				if (jsonObj.size() > 0) {
					Object jsonData = jsonObj.get(0);
					properties.setType("array");
					ItemsArray itemArray = new ItemsArray();
					itemArray.setType(getType(dataMap));
					if (jsonData instanceof JSONObject) {
						properties.setType("");
						properties.setExample("");
						itemArray.setSchemaRef(key);
					}
					itemArray.setXml(new Xml());
					itemArray.getXml().setName(key + "s");
					itemArray.getXml().setWrapped("true");
					properties.setItems(itemArray);
					if (jsonData instanceof JSONObject) {
						resultado.addAll(generarSchema((JSONObject) jsonObj.get(0), key));
					}
				}
			}
			schema.getProperties().add(properties);
		}
		schema.setXml(new Xml());
		schema.getXml().setName(schemaInput);
		if (!schemaMap.containsKey(schemaInput)) {
			resultado.add(schema);
			schemaMap.put(schemaInput, null);
		}
		return resultado;
	}

	private String getType(Map.Entry dataMap) {
		String resultado = "string";
		if (dataMap.getValue() instanceof String) {
			resultado = "string";
		} else if (dataMap.getValue() instanceof Number) {
			resultado = "number";
		} else if (dataMap.getValue() instanceof Date) {
			resultado = "date";
		} else if (dataMap.getValue() == null) {
			resultado = "string";
		}
		return resultado;
	}

	private Map<String, Verbo> generarVerbo(String verboName, String operacionId, boolean paramQuery, String path,
			String tagsName, String schemaRefInput, String schemaRefRes) {
		Map<String, Verbo> resultado = new HashMap<>();
		Verbo verbo = new Verbo();
		verbo.setName(verboName);
		verbo.setTags(new ArrayList<>());
		Tags tags = new Tags();
		tags.setName(tagsName);
		verbo.getTags().add(tags);
		verbo.setSummary("Update an existing pet");
		verbo.setDescription("Update an existing pet by Id");
		verbo.setOperationId(operacionId + verboName);

		if (!paramQuery) {
			RequestBody requestBody = new RequestBody();
			requestBody.setDescription("Update an existent pet in the store");
			requestBody.setContent(new ArrayList<>());

			Content content = new Content();
			content.setType("application/json");
			content.setSchemaRef(schemaRefInput);
			requestBody.getContent().add(content);
			requestBody.setRequired("true");
			verbo.setRequestBodyParam(true);
			verbo.setRequestBody(requestBody);
		} else {
			verbo.setParameters(new ArrayList<>());
			Parameters param = new Parameters();
			param.setName("status");
			param.setIn("query");
			param.setDescription("Status values that need to be considered for filter");
			param.setRequired("false");
			param.setExplode("true");
			SchemaRef schemaType = new SchemaRef();
			schemaType.setType("string");
			schemaType.setDefaults("available");
			schemaType.setEnums(new ArrayList<>());
			schemaType.getEnums().add("available");
			schemaType.getEnums().add("pending");
			schemaType.getEnums().add("sold");
			param.setSchema(schemaType);
			verbo.setParametersParam(true);
			verbo.getParameters().add(param);
		}
		ResponsesStatus responsesStatus = new ResponsesStatus();
		responsesStatus.setCode("200");
		responsesStatus.setDescription("Successful operation");
		responsesStatus.setContent(new ArrayList<>());

		Content contentRes = new Content();
		contentRes.setType("application/json");
		contentRes.setSchemaRef(schemaRefRes);
		responsesStatus.getContent().add(contentRes);
		verbo.setResponses(new ArrayList<>());

		verbo.getResponses().add(responsesStatus);
		resultado.put(verboName + path, verbo);
		return resultado;
	}

	private List<Path> generarPath(Map<String, Verbo> verboMap, String... paths) {
		List<Path> resultado = new ArrayList<>();
		List<String> verbosName = new ArrayList<>();
		verbosName.add("post");
		verbosName.add("put");
		verbosName.add("get");
		verbosName.add("delete");
		for (var name : paths) {
			Path obj = new Path();
			obj.setName(name);
			obj.setVerbos(new ArrayList<>());
			for (var keyVerbo : verbosName) {
				String keyVerbos = keyVerbo + name;
				if (verboMap.containsKey(keyVerbos)) {
					obj.getVerbos().add(verboMap.get(keyVerbos));
				}
			}

			resultado.add(obj);
		}

		return resultado;
	}

	private List<Tags> generarTags(String... tags) {
		List<Tags> resultado = new ArrayList<>();
		for (var keyTags : tags) {
			Tags obj = new Tags();
			obj.setExternalDocs(new ExternalDocs());
			obj.setName(keyTags);
			obj.setDescription("Everything about your Pets");
			obj.getExternalDocs().setDescription("Find out more");
			obj.getExternalDocs().setUrl("http://swagger.io");
			resultado.add(obj);
		}
		return resultado;
	}

	public Object readJsonText(JSONObject body) {
		String json = "";
		try {
			json = body.get("raw") + "";
			return readJsonText(json);
		} catch (Exception e) {
			System.out.println("error al parecer json " + json);
		}
		return new JSONObject();

	}

	public Object readJsonText(String json) {
		try {
			JSONParser jsonParser = new JSONParser();
			return jsonParser.parse(json);
		} catch (Exception e) {
			System.out.println("error al parecer json " + json);
		}
		return new JSONObject();

	}

	public Object readJsonFile(String filename) throws Exception {
		FileReader reader = new FileReader(filename);
		JSONParser jsonParser = new JSONParser();
		return jsonParser.parse(reader);
	}

	private static void crearArchivo(String nombre, StringBuilder data) {
		try {
			String ruta = "D:\\swagger\\" + nombre + ".json";
			File archivo = new File(ruta);
			BufferedWriter bw;
			archivo.delete();
			if (archivo.exists()) {
				archivo.delete();
			}
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(data.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Getter
	@Setter
	public class Tags {
		private String name;
		private String description;
		private ExternalDocs externalDocs;
	}

	@Getter
	@Setter
	public class ExternalDocs {
		private String description;
		private String url;
	}

	@Getter
	@Setter
	public class Path {
		private String name;
		private List<Verbo> verbos;
	}

	@Getter
	@Setter
	public class Verbo {
		private String name;
		private List<Tags> tags;
		private String summary;
		private String description;
		private String operationId;
		private boolean parametersParam;
		private List<Parameters> parameters;
		private RequestBody requestBody;
		private boolean requestBodyParam;
		private List<ResponsesStatus> responses;
		private Security security;
	}

	@Getter
	@Setter
	public class Parameters {
		private String name;
		private String in;
		private String description;
		private String required;
		private String explode;
		private SchemaRef schema;

	}

	@Getter
	@Setter
	public class SchemaRef {
		private String type;
		private String defaults;
		private List<String> enums;
	}

	@Getter
	@Setter
	public class RequestBody {
		private String description;
		private List<Content> content;
		private String required;
		private String operationId;
	}

	@Getter
	@Setter
	public class Content {
		private String type;
		private String schemaRef;
		private ItemsArray items;
	}

	@Getter
	@Setter
	public class ResponsesStatus {
		private String code;
		private String description;
		private List<Content> content;
	}

	@Getter
	@Setter
	public class Security {
		private List<SecurityItem> securityItem;
	}

	@Getter
	@Setter
	public class SecurityItem {
		private String name;
		private List<String> item;
	}

	@Getter
	@Setter
	public class Component {
		private List<Schema> schemas;
		private RequestBodies requestBodies;
		private SecuritySchemes securitySchemes;
	}

	@Getter
	@Setter
	public class SecuritySchemes {
		private String name;
		private SecuritySchemesItem securitySchemesItem;
	}

	@Getter
	@Setter
	public class SecuritySchemesItem {
		private String type;
		private String name;
		private String in;
		private Flows flows;
	}

	@Getter
	@Setter
	public class Flows {
		private String authorizationUrl;
		private List<String> scopes;
	}

	@Getter
	@Setter
	public class RequestBodies {
		private String name;
		private RequestBodiesItem requestBodiesItem;
	}

	@Getter
	@Setter
	public class RequestBodiesItem {
		private String description;
		private List<Content> content;
	}

	@Getter
	@Setter
	public class Schema {
		private String name;
		private String type;
		private List<Properties> properties;
		private List<String> required;
		private Xml xml;
	}

	@Getter
	@Setter
	public class Properties {
		private String name;
		private String type;
		private String format;
		private String example;
		private String description;
		private String schemaRef;
		private List<String> enums;
		private ItemsArray items;
	}

	@Getter
	@Setter
	public class Xml {
		private String name;
		private String wrapped;
	}

	@Getter
	@Setter
	public class ItemsArray {
		private String type;
		private Xml xml;
		private String schemaRef;
	}

}
