package com.package1.database.model;

// Classe utilisée pour encapsuler les données d'une couleur dans un objet résultat
public class ResultDataCouleur {
    private Couleur couleur; // Couleur encapsulée dans cet objet

    // Constructeur prenant une couleur en paramètre
    public ResultDataCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    // Méthode pour récupérer la couleur encapsulée
    public Couleur getCouleur() {
        return couleur;
    }

    // Méthode pour définir la couleur encapsulée
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }
}
