package com.package1.database.model;

// Classe représentant la table "Aliment"
public class Aliment extends TableData {
    private double poidsMoyen;
    private int calories;
    private float vitaminesC;
    private int typeId; // Supposons que le type soit un identifiant vers une autre table
    private int couleurId; // Supposons que la couleur soit un identifiant vers la table "Couleur"


    public Aliment() {
        super(); // Appel du constructeur de la classe mère
    }


    // Constructeur spécifique à la table "Aliment"
    public Aliment(int id, String nom, double poidsMoyen2, int calories, float vitaminesC, int typeId, int couleurId) {
        super(id, nom);
        this.poidsMoyen = poidsMoyen2;
        this.calories = calories;
        this.vitaminesC = vitaminesC;
        this.typeId = typeId;
        this.couleurId = couleurId;
    }

   

    // Méthodes spécifiques à la table "Aliment"
    public double getPoidsMoyen() {
        return poidsMoyen;
    }

    public void setPoidsMoyen(double poidsMoyen) {
        this.poidsMoyen = poidsMoyen;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getVitaminesC() {
        return vitaminesC;
    }

    public void setVitaminesC(float vitaminesC) {
        this.vitaminesC = vitaminesC;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCouleurId() {
        return couleurId;
    }

    public void setCouleurId(int couleurId) {
        this.couleurId = couleurId;
    }
   
}


