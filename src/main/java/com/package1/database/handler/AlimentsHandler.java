package com.package1.database.handler;

import com.package1.database.Main;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class AlimentsHandler implements HttpHandler {
    private final Main main;

    // Constructeur prenant l'instance principale de Main
    public AlimentsHandler(Main main) {
        this.main = main;
    }

    // Méthode principale pour gérer les requêtes HTTP
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
    
        // Retirez le slash final du chemin, s'il y en a un
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
    
        try {
            switch (method) {
                case "GET":
                    handleGet(exchange, path);
                    break;
                case "POST":
                    handlePost(exchange, path);
                    break;
                case "PUT":
                    handlePut(exchange, path);
                    break;
                case "DELETE":
                    handleDelete(exchange, path);
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1); // Méthode non autorisée
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Erreur serveur lors de l'opération", "");
        }
    }
    
    // Méthode pour gérer les requêtes GET
    private void handleGet(HttpExchange exchange, String path) throws IOException {
        // Traitement de la requête GET pour /aliments
        String response = main.getAllAlimentsAsJson();

        // Envoi de la réponse au client
        sendResponse(exchange, 200, response, response);
    }

    // Méthode pour gérer les requêtes POST
    private void handlePost(HttpExchange exchange, String path) throws IOException {
        // Traitement de la requête POST pour /aliments
        String requestBody = getRequestBody(exchange);
        boolean success = main.createAlimentFromJson(requestBody) != null;

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 201, "Aliment créé avec succès", requestBody);
        } else {
            sendResponse(exchange, 500, "Erreur lors de la création de l'aliment", requestBody);
        }
    }

    // Méthode pour gérer les requêtes PUT
    private void handlePut(HttpExchange exchange, String path) throws IOException, SQLException {
        // Traitement de la requête PUT pour /aliments/{id}
        String requestBody = getRequestBody(exchange);
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.updateAlimentFromJson(id, requestBody);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Aliment modifié avec succès", path);
        } else {
            sendResponse(exchange, 500, "Erreur lors de la modification de l'aliment", path);
        }
    }

    // Méthode pour gérer les requêtes DELETE
    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        // Traitement de la requête DELETE pour /aliments/{id}
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.supprimerAliment(id);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Aliment supprimé avec succès", path);
        } else {
            sendResponse(exchange, 500, "Erreur lors de la suppression de l'aliment", path);
        }
    }

    // Méthode pour récupérer le corps de la requête HTTP
    private String getRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            return requestBody.toString();
        }
    }

    // Méthode pour envoyer une réponse HTTP
    private void sendResponse(HttpExchange exchange, int statusCode, String response, String path) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
