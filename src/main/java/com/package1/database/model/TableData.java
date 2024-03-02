package com.package1.database.model;

// Classe abstraite représentant les attributs communs à toutes les tables
public abstract class TableData {
    int id; // Identifiant de la table
    String nom; // Nom de la table

    // Constructeur avec ID et nom
    public TableData(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Constructeur avec nom uniquement
    public TableData(String nom) {
        this.nom = nom;
    }

    // Constructeur par défaut
    public TableData() {
    }

    // Méthode pour obtenir l'ID de la table
    public int getId() {
        return id;
    }

    // Méthode pour définir l'ID de la table
    public void setId(int id) {
        this.id = id;
    }

    // Méthode pour obtenir le nom de la table
    public String getNom() {
        return nom;
    }

    // Méthode pour définir le nom de la table
    public void setNom(String nom) {
        this.nom = nom;
    }
}
