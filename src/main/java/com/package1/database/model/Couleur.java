package com.package1.database.model;

// Classe représentant la table "Couleur"
public class Couleur extends TableData {
    // Attribut spécifique à la table "Couleur"
    private String hexadecimalRVB;

    // Constructeur spécifique à la table "Couleur"
    public Couleur(int id, String nom, String hexadecimalRVB) {
        super(id, nom); // Appel du constructeur de la classe mère avec l'ID et le nom
        this.hexadecimalRVB = hexadecimalRVB; // Initialisation de l'attribut hexadecimalRVB
    }

    // Constructeur par défaut
    public Couleur() {
    }

    // Méthodes spécifiques à la table "Couleur"
    
    // Getter pour l'attribut hexadecimalRVB
    public String getHexadecimalRVB() {
        return this.hexadecimalRVB;
    }

    // Setter pour l'attribut hexadecimalRVB
    public void setHexadecimalRVB(String hexadecimalRVB) {
        this.hexadecimalRVB = hexadecimalRVB;
    }
}
