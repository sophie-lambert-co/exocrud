package com.package1.database.handler;

import com.package1.database.Main;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CouleurHandler implements HttpHandler {
    private final Main main;

    public CouleurHandler(Main main) {
        this.main = main;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
       // String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                handleGet(exchange);
                break;
            case "POST":
                handlePost(exchange);
                break;
            case "PUT":
                handlePut(exchange);
                break;
            case "DELETE":
                handleDelete(exchange);
                break;
            default:
                exchange.sendResponseHeaders(405, -1); // Méthode non autorisée
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        // Traitement de la requête GET pour /couleur
        String response = main.getAllColorsAsJson();

        // Envoi de la réponse au client
        sendResponse(exchange, 200, response);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        // Traitement de la requête POST pour /couleur
        String requestBody = getRequestBody(exchange);
        boolean success = main.createCouleurFromJson(requestBody);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 201, "Couleur créée avec succès");
        } else {
            sendResponse(exchange, 500, "Erreur lors de la création de la couleur");
        }
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        // Traitement de la requête PUT pour /couleur/{id}
        String requestBody = getRequestBody(exchange);
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.updateCouleurFromJson(id, requestBody);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Couleur modifiée avec succès");
        } else {
            sendResponse(exchange, 500, "Erreur lors de la modification de la couleur");
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        // Traitement de la requête DELETE pour /couleur/{id}
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.supprimerCouleur(id);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Couleur supprimée avec succès");
        } else {
            sendResponse(exchange, 500, "Erreur lors de la suppression de la couleur");
        }
    }

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

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
