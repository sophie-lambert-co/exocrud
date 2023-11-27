package com.package1.database.model;

public class AlimentPrinter {

    public static void printAliment(Aliment aliment) {
        // Logique d'affichage
        System.out.println("Aliment : ");
        System.out.println(" - ID: " + aliment.getId());
        System.out.println(" - Nom: " + aliment.getNom());
        System.out.println(" - Poids Moyen: " + aliment.getPoidsMoyen());
        System.out.println(" - Calories: " + aliment.getCalories());
        System.out.println(" - Vitamines C: " + aliment.getVitaminesC());
        System.out.println(" - Type ID: " + aliment.getTypeId());
        System.out.println(" - Couleur ID: " + aliment.getCouleurId());
    }
}
