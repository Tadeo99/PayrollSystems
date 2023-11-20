package pe.buildsoft.erp.core.domain.servicios.pago.proceso;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.JsonObject;
import pe.buildsoft.erp.core.domain.entidades.pago.vo.SunatDatosVO;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class Utilidades.
 *
 * @author ndavilal.
 * @version 1.0 , 18/03/2012
 * @since SIAA 2.0
 */
public class Utilidades {

	public static List<SunatDatosVO> generaraccionComprobante(SunatDatosVO sfs, String targetURL) {
		List<SunatDatosVO> listaResul = new ArrayList<>();
		URL url;
		HttpURLConnection connection = null;
		String urlParameters = "{\"num_ruc\":\"" + sfs.getNum_ruc() + "\",\"tip_docu\":\"" + sfs.getTip_docu()
				+ "\",\"num_docu\":\"" + sfs.getNum_docu() + "\"}";

		System.out.println("JSN:: " + urlParameters);
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("messageGenerarCom= " + response.toString());
			List<String> listadoStrin = new ArrayList<>();
			listadoStrin.add(response.toString());
			SunatDatosVO obj = new SunatDatosVO();
			obj.setListaResultado(listadoStrin);
			listaResul.add(obj);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
		return listaResul;
	}

	public static List<SunatDatosVO> eliminarBandeja() {
		List<SunatDatosVO> listaResul = new ArrayList<>();
		URL url;
		HttpURLConnection connection = null;
		String targetURL = "http://localhost:9000/api/EliminarBandeja.htm";// here is my local server url
		String urlParameters = "{\"rutaCertificado\":\"\"}";

		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
		return listaResul;
	}

	public static List<SunatDatosVO> actualizarPantalla(JsonObject sfs) {
		List<SunatDatosVO> listaResul = new ArrayList<>();
		URL url;
		HttpURLConnection connection = null;
		String targetURL = "http://localhost:9000/api/ActualizarPantalla.htm";// here is my local server url
		String urlParameters = "{\"txtSecuencia\":\"000\"}";

		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			// System.out.println("messageActualizar= "+response.toString());
			List<String> listadoStrin = new ArrayList<>();
			listadoStrin.add(response.toString());
			SunatDatosVO obj = new SunatDatosVO();
			obj.setListaResultado(listadoStrin);
			listaResul.add(obj);

		} catch (Exception e) {
			String mensaje2 = "Sin Conexion al Servicio de Sunat";
			String mensaje = "Sin Conexion al Servicio de Sunat";
			if (mensaje.equals(mensaje2)) {
				System.out.println(mensaje);
				mensaje2 = "";
			}

			// e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return listaResul;
	}

	public static void cargarPantalla() {
		URL url;
		HttpURLConnection connection = null;
		String targetURL = "http://localhost:9000/api/CargarPantalla.htm";// here is my local server url
		String urlParameters = "{\"txtSecuencia\":\"000\"}";

		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

		} catch (Exception e) {
			String mensaje2 = "Sin Conexion al Servicio de Sunat";
			String mensaje = "Sin Conexion al Servicio de Sunat";
			if (mensaje.equals(mensaje2)) {
				System.out.println(mensaje);
				mensaje2 = "";
			}
			// e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}