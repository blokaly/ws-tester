package com.blokaly.juno;

import com.google.common.collect.Sets;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebSocket
public class WebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final Set<Session> sessions = Sets.newConcurrentHashSet();

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        LOGGER.info("WS session connected: {}", session);
        sessions.add(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        LOGGER.info("WS session disconnected: {}", session);
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String json) {
        es.submit(()->{
            sessions.forEach(sess->{
                try {
                    sess.getRemote().sendString(json);
                } catch (IOException ex) {
                    LOGGER.error("Failed to broadcast message to session " + sess, ex);
                }
            });
        });
    }
}
