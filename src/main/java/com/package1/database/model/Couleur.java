package com.package1.database.model;

// Classe représentant la table "Couleur"
public class Couleur extends TableData {
    private String hexadecimalRVB;

        // Constructeur spécifique à la table "Couleur"
        public Couleur(int id, String nom, String hexadecimalRVB) {
            super(id, nom);
            this.hexadecimalRVB = hexadecimalRVB;
        }

         

    // Méthodes spécifiques à la table "Couleur"

    public Couleur() {
        }



    public String getHexadecimalRVB() {
        return this.hexadecimalRVB;
    }

    public void setHexadecimalRVB(String hexadecimalRVB) {
        this.hexadecimalRVB = hexadecimalRVB;
    }
  
}

