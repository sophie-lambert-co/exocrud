package com.package1.database.model;


// Classe abstraite représentant les attributs communs à toutes les tables
public abstract class TableData {
   int id;
   String nom;

    // Constructeur commun
    public TableData(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public TableData() {
    }

    // Méthode commune
    public int getId() {
        return id;
    }

     // Méthode commune
    public void  setId(int id) {
         this.id = id;
    }

    public String getNom() {
        return nom;
    }

    // Méthodes setter
    public void setNom(String nom) {
        this.nom = nom;
    }
}



