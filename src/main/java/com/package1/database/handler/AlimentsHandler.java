// AlimentsHandler
package com.package1.database.handler;

import com.package1.database.Main;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AlimentsHandler implements HttpHandler {
    private final Main main;

    public AlimentsHandler(Main main) {
        this.main = main;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // String path = exchange.getRequestURI().getPath(); // Cette ligne a été commentée pour éviter l'erreur
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
        // Traitement de la requête GET pour /aliments
        String response = main.getAllAlimentsAsJson();

        // Envoi de la réponse au client
        sendResponse(exchange, 200, response);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        // Traitement de la requête POST pour /aliments
        String requestBody = getRequestBody(exchange);
        boolean success = main.createAlimentFromJson(requestBody) != null;

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 201, "Aliment créé avec succès");
        } else {
            sendResponse(exchange, 500, "Erreur lors de la création de l'aliment");
        }
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        // Traitement de la requête PUT pour /aliments/{id}
        String requestBody = getRequestBody(exchange);
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.updateAlimentFromJson(id, requestBody);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Aliment modifié avec succès");
        } else {
            sendResponse(exchange, 500, "Erreur lors de la modification de l'aliment");
        }
    }

    

    private void handleDelete(HttpExchange exchange) throws IOException {
        // Traitement de la requête DELETE pour /aliments/{id}
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.supprimerAliment(id);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Aliment supprimé avec succès");
        } else {
            sendResponse(exchange, 500, "Erreur lors de la suppression de l'aliment");
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
