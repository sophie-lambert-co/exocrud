package com.package1.database.model;

public class CouleurPrinter {

    // Méthode pour afficher les détails d'une couleur
    public static void printCouleur(Couleur couleur) {
        // Vérifie si la couleur est null
        if (couleur != null) {
            // Affiche les détails de la couleur
            System.out.println("Couleur : ");
            System.out.println(" - ID: " + couleur.getId());
            System.out.println(" - Nom: " + couleur.getNom());
            System.out.println(" - Hexadecimal RVB: " + couleur.getHexadecimalRVB());
        } else {
            // Affiche un message si la couleur est null
            System.out.println("La couleur est null.");
        }
    }
}

