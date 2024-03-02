package com.package1.servlets;

import com.package1.database.Main;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CouleurServlets extends HttpServlet {

    // Instance de la classe Main pour accéder aux fonctionnalités de la base de données
    private final Main main = new Main();

    // Méthode appelée lorsqu'une requête GET est reçue
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String colorsJson = ""; // Initialisez la variable en cas d'erreur

        try {
            // Récupère toutes les couleurs sous forme de JSON à partir de la classe Main
            colorsJson = main.getAllColorsAsJson();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception ici (par exemple, loggez l'erreur)
        }

        // Définit le type de contenu de la réponse comme JSON
        response.setContentType("application/json");
        
        // Écrit le JSON des couleurs dans le flux de sortie de la réponse HTTP
        response.getWriter().write(colorsJson);
    }
}

