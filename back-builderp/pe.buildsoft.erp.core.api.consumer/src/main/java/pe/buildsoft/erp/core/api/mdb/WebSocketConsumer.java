package pe.buildsoft.erp.core.api.mdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
@ServerEndpoint("/notificacionSolicitud/{key}")
public class WebSocketConsumer {

	private static Logger LOGGER = LoggerFactory.getLogger(WebSocketConsumer.class);
	private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();

	@OnMessage
	public void onMessage(String message, @PathParam("key") String key, Session session) {
		LOGGER.info(
				"onMessage.consumer session ID: " + session.getId() + ", message: " + message + ", key: " + key + "");
		sessions.get(key).parallelStream().forEach(session2 -> {
			if (session == session2) {
				return;
			}
			session2.getAsyncRemote().sendText(message);
		});
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("key") String key) {
		LOGGER.info("Session.consumer onOpen ID: " + session.getId() + " key: " + key + "");
		if (!sessions.containsKey(key)) {
			sessions.put(key, new ArrayList<>());
		}
		sessions.get(key).add(session);
	}

	@OnClose
	public void onClose(Session session, @PathParam("key") String key) {
		LOGGER.info("Session.consumer closed ID: " + session.getId() + ", key: " + key + "");
		sessions.get(key).remove(session);
	}

	@OnError
	public void onError(Session session, @PathParam("key") String key, Throwable throwable) {
		LOGGER.error("Session error consumer. Removing session: "
				+ (Arrays.toString(new Object[] { session.getId(), key }) + ""), throwable);
		sessions.get(key).remove(session);
		try {
			session.close();
		} catch (IOException ex) {
			LOGGER.warn("Error closing session ID: {}", session.getId());
		}
	}

	public void send(String message, String key) {
		if (!sessions.containsKey(key)) {
			LOGGER.warn("Key consumer '" + key + "' not registered, can't send message '" + message + "'");
			return;
		}
		sessions.get(key).parallelStream().forEach(session -> {
			session.getAsyncRemote().sendText(message);
		});
	}

	public List<String> getKeys() {
		return new ArrayList<>(sessions.keySet());
	}
}
