package com.blokaly.juno;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebSocket
public class WebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final Set<Session> sessions = new HashSet<>();

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        LOGGER.info("WS session connected: {}", session.getRemoteAddress());
        es.submit(() -> {
            addSession(session);
        });
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        LOGGER.info("WS session disconnected: {}", session.getRemoteAddress());
        es.submit(() -> {
            removeSession(session);
        });
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String json) {
        es.submit(() -> {
            broadcast(json);
        });
    }

    private void addSession(Session session) {
        sessions.add(session);
    }

    private void removeSession(Session session) {
        sessions.remove(session);
    }

    private void broadcast(String message) {
        sessions.forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (IOException ex) {
                LOGGER.error("Failed to broadcast message to session " + session.getRemoteAddress(), ex);
            }
        });
    }
}
