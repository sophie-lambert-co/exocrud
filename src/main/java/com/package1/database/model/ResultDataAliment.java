package com.package1.database.model;

// Classe utilisée pour encapsuler les données d'un aliment dans un objet résultat
public class ResultDataAliment {
    private Aliment aliment; // Aliment encapsulé dans cet objet

    // Constructeur prenant un aliment en paramètre
    public ResultDataAliment(Aliment aliment) {
        this.aliment = aliment;
    }

    // Méthode pour récupérer l'aliment encapsulé
    public Aliment getAliment() {
        return aliment;
    }

    // Méthode pour définir l'aliment encapsulé
    public void setAliment(Aliment aliment) {
        this.aliment = aliment;
    }
}
