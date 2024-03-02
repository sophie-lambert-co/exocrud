package com.package1.database.handler;

import com.package1.database.Main;
import com.package1.database.dao.impl.JsonUtil;
import com.package1.database.model.Couleur;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;


/**
 * Cette classe gère les requêtes HTTP liées aux couleurs.
 */
public class CouleurHandler implements HttpHandler {
    private final Main main;


      /**
     * Constructeur de la classe CouleurHandler.
     *
     * @param main L'instance principale de l'application.
     */
    public CouleurHandler(Main main) {
        this.main = main;
    }
  /**
     * Méthode principale qui traite les requêtes entrantes.
     *
     * @param exchange L'échange HTTP entrant.
     * @throws IOException En cas d'erreur d'entrée/sortie lors du traitement de la requête.
     */
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
                    handleGet(exchange, path);// Traitement des requêtes
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
                    return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            int errorCode;
            String errorMessage;

           // Détermination du code d'erreur et du message en fonction de la méthode
            switch (method) {
                case "GET":
                    errorCode = 500;
                    errorMessage = "Erreur serveur lors de la récupération des données";
                    break;
                case "POST":
                    errorCode = 500;
                    errorMessage = "Erreur serveur lors de la création des données";
                    break;
                case "PUT":
                    errorCode = 500;
                    errorMessage = "Erreur serveur lors de la modification des données";
                    break;
                case "DELETE":
                    errorCode = 500;
                    errorMessage = "Erreur serveur lors de la suppression des données";
                    break;
                default:
                    errorCode = 500;
                    errorMessage = "Erreur serveur lors de l'opération";
            }
    
            sendResponse(exchange, errorCode, errorMessage, "");
        }
    }
    

     /**
     * Méthode pour gérer les requêtes GET concernant les couleurs.
     *
     * @param exchange L'échange HTTP entrant.
     * @param path     Le chemin de la requête.
     * @throws IOException  En cas d'erreur d'entrée/sortie lors du traitement de la requête.
     * @throws SQLException En cas d'erreur SQL lors de l'accès à la base de données.
     */
    private void handleGet(HttpExchange exchange, String path) throws IOException, SQLException {
        // Traitement de la requête GET pour /couleur
         // Récupération de toutes les couleurs depuis la base de données sous forme JSON
        String response = main.getAllColorsAsJson();

        // Envoi de la réponse au client
        sendResponse(exchange, 200, response, response);
    }


       /**
     * Méthode pour gérer les requêtes POST concernant les couleurs.
     *
     * @param exchange L'échange HTTP entrant.
     * @param path     Le chemin de la requête.
     * @throws IOException  En cas d'erreur d'entrée/sortie lors du traitement de la requête.
     * @throws SQLException En cas d'erreur SQL lors de l'accès à la base de données.
     */
    private void handlePost(HttpExchange exchange, String path) throws IOException, SQLException {
        // Traitement de la requête POST pour /couleur
        String requestBody = getRequestBody(exchange);
        Couleur couleur = JsonUtil.jsonToObject(requestBody, Couleur.class);

        // Envoi de la réponse au client
        if (couleur != null) {
            boolean success = main.createCouleurFromJson(requestBody);
            if (success) {
                sendResponse(exchange, 201, "Couleur créée avec succès", JsonUtil.objectToJson(couleur));
            } else {
                sendResponse(exchange, 500, "Erreur lors de la création de la couleur", JsonUtil.objectToJson(couleur));
            }
        } else {
            sendResponse(exchange, 400, "Erreur lors de la conversion du JSON en objet Couleur", requestBody);
        }
    }

    private void handlePut(HttpExchange exchange, String path) throws IOException, SQLException {
        // Traitement de la requête PUT pour /couleur/{id}
        String requestBody = getRequestBody(exchange);
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.updateCouleurFromJson(id, requestBody);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Couleur modifiée avec succès", path);
        } else {
            sendResponse(exchange, 500, "Erreur lors de la modification de la couleur", path);
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException, SQLException {
        // Traitement de la requête DELETE pour /couleur/{id}
        String[] pathParts = path.split("/");
        int id = Integer.parseInt(pathParts[pathParts.length - 1]);

        boolean success = main.supprimer(id);

        // Envoi de la réponse au client
        if (success) {
            sendResponse(exchange, 200, "Couleur supprimée avec succès", path);
        } else {
            sendResponse(exchange, 500, "Erreur lors de la suppression de la couleur", path);
        }
    }


       // Les méthodes handlePut et handleDelete sont similaires à handlePost, elles gèrent les requêtes PUT et DELETE respectivement.

    /**
     * Méthode pour récupérer le corps de la requête HTTP.
     *
     * @param exchange L'échange HTTP entrant.
     * @return Le corps de la requête sous forme de chaîne de caractères.
     * @throws IOException En cas d'erreur d'entrée/sortie lors de la récupération du corps de la requête.
     */
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


       /**
     * Méthode pour envoyer une réponse HTTP.
     *
     * @param exchange   L'échange HTTP entrant.
     * @param statusCode Le code de statut HTTP.
     * @param response   Le corps de la réponse.
     * @param path       Le chemin de la requête.
     * @throws IOException En cas d'erreur d'entrée/sortie lors de l'envoi de la réponse.
     */
    private void sendResponse(HttpExchange exchange, int statusCode, String response, String path) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
