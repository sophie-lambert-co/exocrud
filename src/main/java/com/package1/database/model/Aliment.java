package com.package1.database.model;

// Classe représentant la table "Aliment"
public class Aliment extends TableData {
    // Attributs spécifiques à la table "Aliment"
    private double poidsMoyen;
    private int calories;
    private float vitaminesC;
    private int typeId; // Supposons que le type soit un identifiant vers une autre table
    private int couleurId; // Supposons que la couleur soit un identifiant vers la table "Couleur"

    // Constructeur par défaut
    public Aliment() {
        super(); // Appel du constructeur de la classe mère
    }

    // Constructeur spécifique à la table "Aliment"
    public Aliment(String nom, double poidsMoyen, int calories, float vitaminesC, int typeId, int couleurId) {
        super(nom); // Appel du constructeur de la classe mère avec le nom de l'aliment
        this.poidsMoyen = poidsMoyen;
        this.calories = calories;
        this.vitaminesC = vitaminesC;
        this.typeId = typeId;
        this.couleurId = couleurId;
    }

    // Constructeur spécifique à la table "Aliment"
    public Aliment(int id, String nom, double poidsMoyen, int calories, float vitaminesC, int typeId, int couleurId) {
        super(id, nom); // Appel du constructeur de la classe mère avec l'ID et le nom de l'aliment
        this.poidsMoyen = poidsMoyen;
        this.calories = calories;
        this.vitaminesC = vitaminesC;
        this.typeId = typeId;
        this.couleurId = couleurId;
    }

    // Méthodes getters et setters spécifiques à la table "Aliment"
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
