package com.package1.database.dao;

import java.util.List;

public interface ICrudDAO<T> {

    // Obtenir une couleur ou un Aliment par son ID
    T getById(int id);

    // Obtenir toutes les couleurs ou Aliments
    List<T> getAll(int id);

    boolean ajouter(T objet);

    // Supprimer une couleur ou un aliment par son ID
    boolean supprimer(int id);

    boolean modifier(int id, T objet);

    default String getAllAsJson(int id) {
        return "[]"; // ou tout autre JSON par défaut que vous souhaitez renvoyer
    }



    // Ajouter une couleur
    // boolean ajouter();

    // // Ajouter un aliment
    // boolean ajouterAliment(int id, String nom, double poidsMoyen, int calories,
    // int vitaminesC, int typeId,
    // int couleurId);

    // // Modifier le nom d'une couleur ou d'un Aliment
    // boolean modifier(int id, String nouveauNom);

    // // Modifier les détails d'un aliment
    // boolean modifierAliment(int id, String nouveauNom, double nouveauPoidsMoyen,
    // int nouvellesCalories,
    // int nouvellesVitaminesC, int nouveauTypeId, int nouvelleCouleurId);

}
