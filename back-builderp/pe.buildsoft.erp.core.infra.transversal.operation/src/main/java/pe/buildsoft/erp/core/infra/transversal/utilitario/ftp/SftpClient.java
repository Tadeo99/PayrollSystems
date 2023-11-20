package pe.buildsoft.erp.core.infra.transversal.utilitario.ftp;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.net.ftp.FTPFile;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;

public class SftpClient {

	private static Logger logger = LoggerFactory.getLogger(SftpClient.class);
	private String server;
	private int port;
	private String login;
	private String password;

	private JSch jsch = null;
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	public SftpClient() {

	}

	public SftpClient(String server, int port, String login, String password) {
		this.server = server;
		this.port = port;
		this.login = login;
		this.password = password;
	}

	public String changeWorkingDirectory(String rutaFTP) {
		String resultado = "";
		try {
			channelSftp.cd(rutaFTP);
		} catch (Exception e) {
			logger.error("changeWorkingDirectory", e);
			resultado = e.getMessage();
		}
		return resultado;
	}

	public String makeDirectory(String rutaFTP) {
		String resultado = "";
		try {
			channelSftp.mkdir(rutaFTP);
		} catch (Exception e) {
			logger.error("makeDirectory", e);
			resultado = e.getMessage();
		}
		return resultado;
	}

	/**
	 * Connects to the server and does some commands.
	 */
	public String connect() {
		String resultad = "";
		try {
			logger.debug("Initializing jsch");
			jsch = new JSch();
			session = jsch.getSession(login, server, port);
			session.setPassword(password.getBytes(Charset.forName("ISO-8859-1")));
			logger.debug("Jsch set to StrictHostKeyChecking=no");
			Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			logger.info("Connecting to " + server + ":" + port);
			// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
			int timeout = 600 * 1000;// 10 min
			if (ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
				timeout = ConfiguracionCacheUtil.getInstance()
						.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
			}
			session.setTimeout(timeout);
			// Fin BuildSoft Mejora Orquestador flujo 11/09/2019
			session.connect();
			logger.info("Connected !");

			// Initializing a channel
			logger.debug("Opening a channel ...");
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			logger.debug("Channel sftp opened");

		} catch (JSchException e) {
			logger.error("", e);
			resultad = e.getMessage();
		}
		return resultad;
	}

	/**
	 * Uploads a file to the sftp server
	 * 
	 * @param sourceFile      String path to sourceFile
	 * @param destinationFile String path on the remote server
	 * @throws InfinItException if connection and channel are not available or if an
	 *                          error occurs during upload.
	 */
	public String uploadFile(String sourceFile, String destinationFile) throws InfinItException {
		String resultado = "";
		if (channelSftp == null || session == null || !session.isConnected() || !channelSftp.isConnected()) {
			resultado = ("Connection to server is closed. Open it first.");
		}

		try {
			logger.debug("Uploading file to server");
			channelSftp.put(sourceFile, destinationFile);
			logger.info("Upload successfull.");
		} catch (Exception e) {
			logger.error("uploadFile", e);
			resultado = e.getMessage();
		}

		return resultado;
	}

	/**
	 * Retrieves a file from the sftp server
	 * 
	 * @param destinationFile String path to the remote file on the server
	 * @param sourceFile      String path on the local fileSystem
	 * @throws InfinItException if connection and channel are not available or if an
	 *                          error occurs during download.
	 */
	public String retrieveFile(String sourceFile, String destinationFile) {
		String resultado = "";
		if (channelSftp == null || session == null || !session.isConnected() || !channelSftp.isConnected()) {
			resultado = ("Connection to server is closed. Open it first.");
		}

		try {
			logger.debug("Downloading file to server");
			OutputStream outputStream1 = new FileOutputStream(destinationFile);
			channelSftp.get(sourceFile, destinationFile);
			outputStream1.close();
			logger.info("Download successfull.");
		} catch (Exception e) {
			logger.error("retrieveFile", e);
			resultado = e.getMessage();
		}
		return resultado;
	}

	public String deleteFile(String remoteFile1) {
		String resultado = "";
		boolean deletedflag = false;
		if (channelSftp == null || session == null || !session.isConnected() || !channelSftp.isConnected()) {
			resultado = ("Connection to server is closed. Open it first.");
		}
		try {
			channelSftp.rm(remoteFile1);
			deletedflag = true;
			if (deletedflag) {
				logger.info("File deleted successfully.");
				resultado = "Archivo eliminado sastisfactoriamente";
			}
			logger.info("Archivo eliminado.");
		} catch (SftpException e) {
			logger.error("deleteFile", e);
			resultado = e.getMessage();
		}
		return resultado;// TODO Auto-generated method stub

	}

	public void disconnect() {
		if (channelSftp != null) {
			logger.debug("Disconnecting sftp channel");
			channelSftp.disconnect();
		}
		if (channel != null) {
			logger.debug("Disconnecting channel");
			channel.disconnect();
		}
		if (session != null) {
			logger.debug("Disconnecting session");
			session.disconnect();
		}
	}

	public FTPFile[] listFiles() {
		FTPFile[] ftpFileArray = new FTPFile[10];
		try {
			Vector ls = channelSftp.ls("*");
			logger.error(ls.size() + "");
			ftpFileArray = new FTPFile[ls.size()];
			for (int i = 0; i < ls.size(); i++) {
				LsEntry lsEntry = (LsEntry) ls.get(i);
				FTPFile ftpFile = new FTPFile();
				ftpFile.setName(lsEntry.getFilename());
				ftpFileArray[i] = ftpFile;
			}
		} catch (Exception e) {
			Log.error(e);
		}
		return ftpFileArray;
	}

	public static void main(String[] args) {
		SftpClient client = new SftpClient();
		client.setServer("localhost");
		client.setPort(22);
		client.setLogin("test");
		client.setPassword("testPassword");

		client.connect();

		try {
			client.uploadFile("src/main/resources/upload.txt", "/uploaded.txt");
			client.retrieveFile("/uploaded.txt", "target/downloaded.txt");
		} catch (InfinItException e) {
			logger.error("", e);
		} finally {
			client.disconnect();
		}
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}