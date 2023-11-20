package pe.buildsoft.erp.core.api.mdb;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */
//https://www.baeldung.com/java-websockets
//https://es.quarkus.io/guides/websockets
//https://blogs.oracle.com/javamagazine/post/how-to-build-applications-with-the-websocket-api-for-java-ee-and-jakarta-ee
//https://eclipse-ee4j.github.io/tyrus-project.github.io/documentation/latest/index/websocket-api.html
//https://jakarta.ee/specifications/websocket/2.1/jakarta-websocket-spec-2.1.html#receiving-messages
@ClientEndpoint
public class WebsocketClientEndpoint {

	private static Logger LOGGER = LoggerFactory.getLogger(WebsocketClientEndpoint.class);

	private Session userSession = null;
	private String keyWebSocket = "";
	private MessageHandler messageHandler;
	private Map<String, Session> sessions = new ConcurrentHashMap<>();

	public WebsocketClientEndpoint(String key) {
		try {
			this.keyWebSocket = key;
			URI endpointURI = new URI("ws://192.168.0.19:8080/consumer/notificacionSolicitud/" + key);
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, endpointURI);
		} catch (Exception e) {
			LOGGER.error("WebsocketClientEndpoint",e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Callback hook for Connection open events.
	 *
	 * @param userSession the userSession which is opened.
	 */
	@OnOpen
	public void onOpen(Session userSession) {
		LOGGER.info("opening websocket client" + this.keyWebSocket);
		this.userSession = userSession;
		sessions.put(userSession.getId(), userSession);
	}

	/**
	 * Callback hook for Connection close events.
	 *
	 * @param userSession the userSession which is getting closed.
	 * @param reason      the reason for connection close
	 */
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		LOGGER.info("closing websocket client" + this.keyWebSocket);
		this.userSession = null;
	}

	/**
	 * Callback hook for Message Events. This method will be invoked when a client
	 * send a message.
	 *
	 * @param message The text message
	 */
	@OnMessage
	public void onMessage(String message) {
		LOGGER.info("message client " + this.keyWebSocket + " " + message);
		if (this.messageHandler != null) {
			this.messageHandler.handleMessage(message);
		}
	}

	@OnMessage
	public void onMessage(ByteBuffer bytes) {
		LOGGER.info("Handle byte buffer client" + this.keyWebSocket);
	}

	/**
	 * register message handler
	 *
	 * @param msgHandler
	 */
	public void addMessageHandler(MessageHandler msgHandler) {
		this.messageHandler = msgHandler;
	}

	/**
	 * Send a message.
	 *
	 * @param message
	 */
	public void sendMessage(String message) {
		LOGGER.info(" sendMessage client " + this.keyWebSocket + " " + message);
		this.userSession.getAsyncRemote().sendText(message);
	}

	/**
	 * Message handler.
	 *
	 * @author Jiji_Sasidharan
	 */
	public static interface MessageHandler {

		public void handleMessage(String message);
	}
}