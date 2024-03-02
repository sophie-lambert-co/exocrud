package com.package1.database.dao.impl;

// Import de la classe d'exception spécifique à Jackson pour les erreurs de traitement JSON
import com.fasterxml.jackson.core.JsonProcessingException;

// Import de la classe principale de Jackson utilisée pour la conversion entre objets Java et JSON
import com.fasterxml.jackson.databind.ObjectMapper;

// Classe utilitaire pour la conversion entre objets Java et chaînes JSON
public class JsonUtil {

    // ObjectMapper est une classe de Jackson, une bibliothèque Java pour traiter JSON
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Méthode pour convertir un objet en format JSON
    public static String objectToJson(Object object) {
        try {
            // Utilisez l'ObjectMapper pour convertir l'objet en une chaîne JSON
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // En cas d'erreur lors de la conversion, gère l'exception
            handleJsonProcessingException(e);
            return null;
        }
    }

    
    // Méthode pour convertir une chaîne JSON en un objet d'un type spécifié
    public static <T> T jsonToObject(String json, Class<T> valueType) {
        try {
            // Utilisez l'ObjectMapper pour convertir la chaîne JSON en un objet du type spécifié
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            // En cas d'erreur lors de la conversion, gère l'exception
            handleJsonProcessingException(e);
            return null;
        }
    }

    // Méthode privée pour gérer les exceptions liées à la conversion JSON
    private static void handleJsonProcessingException(JsonProcessingException e) {
        // Affiche un message d'erreur décrivant la nature de l'erreur
        System.err.println("Erreur lors de la conversion entre objet et JSON : " + e.getMessage());
        // Affiche la trace complète de l'erreur
        e.printStackTrace();
        // Vous pouvez également logger l'erreur ici si vous utilisez un framework de logging
    }
}
