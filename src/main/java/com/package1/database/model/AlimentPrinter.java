package com.package1.database.model;

// Classe utilitaire pour l'impression des détails d'un aliment
public class AlimentPrinter {

    // Méthode statique pour imprimer les détails d'un aliment
    public static void printAliment(Aliment aliment) {
        // Affichage des détails de l'aliment
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

