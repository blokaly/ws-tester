package com.blokaly.juno;

import spark.Service;

public class Server {

    public static void main(String[] args) {

        Service service = Service.ignite().port(4567);
        service.webSocket("/juno", WebSocketHandler.class);
        service.get("/juno", (req, res) -> {
            res.status(200);
            return null;
        });
        CorsFilter.apply(service);
        service.awaitInitialization();
    }
}
