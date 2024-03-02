package com.package1.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Router extends HttpServlet {

    // Méthode service qui gère les requêtes HTTP
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupère le chemin de la requête à partir de l'URI
        String path = request.getRequestURI().substring(request.getContextPath().length());
        
        // Utilise un switch pour router la requête vers le bon servlet en fonction du chemin
        switch (path) {
            case "/couleur":
                // Redirige la requête vers le servlet CouleurServlet
                request.getRequestDispatcher("/couleurServlet").forward(request, response);
                break;
            case "/aliments":
                // Redirige la requête vers le servlet AlimentsServlet
                request.getRequestDispatcher("/alimentsServlet").forward(request, response);
                break;
            default:
                // Si le chemin n'est pas trouvé, envoie une erreur 404
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

