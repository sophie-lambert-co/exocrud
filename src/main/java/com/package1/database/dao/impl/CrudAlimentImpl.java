package com.package1.database.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import com.package1.database.dao.ICrudDAO;
import com.package1.database.model.Aliment;


public class CrudAlimentImpl implements ICrudDAO<Aliment> {

    private Connection connexion;
    private ObjectMapper objectMapper;

    public CrudAlimentImpl(Connection connexion) {
        this.connexion = connexion;
        this.objectMapper = new ObjectMapper();
    }


    

    @Override
    public boolean ajouter(Aliment aliment) {
        String query = "INSERT INTO aliments (nom, poids_moyen, calories, vitamines_C, type_ID, couleur_ID) VALUES (?, ?, ?, ?, ?, ?)";


        try (PreparedStatement statement = connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Remplacement des paramètres dans la requête par les valeurs fournies
            statement.setString(1, aliment.getNom());
            statement.setFloat(2, (float) aliment.getPoidsMoyen());
            statement.setInt(3, aliment.getCalories());
            statement.setFloat(4, aliment.getVitaminesC());
            statement.setInt(5, aliment.getTypeId());
            statement.setInt(6, aliment.getCouleurId());

            // Exécution de la requête d'insertion
            int rowsAffected = statement.executeUpdate();

            // Vérification si des lignes ont été affectées dans la base de données
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête SQL d'ajout d'aliment : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Aliment getById(int id) {
        String query = "SELECT * FROM aliments WHERE id = ?";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Aliment aliment = new Aliment(id, "nom", 0.0, 0, 0, 0, 0); // Remplacez les valeurs par les bonnes
                aliment.setNom(resultSet.getString("nom"));
                aliment.setPoidsMoyen(resultSet.getDouble("poids_moyen"));
                aliment.setCalories(resultSet.getInt("calories"));
                aliment.setVitaminesC(resultSet.getFloat("vitamines_C"));
                aliment.setTypeId(resultSet.getInt("type_id"));
                aliment.setId(resultSet.getInt("couleur_id"));

                return aliment;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Aliment> getAll(int id) {
        String query = "SELECT * FROM aliments";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            List<Aliment> aliments = new ArrayList<>();
            while (resultSet.next()) {
                Aliment aliment = new Aliment(id, "nom", 0.0, 0, 0, 0, 0); // Remplacez les valeurs par les bonnes
                aliment.setNom(resultSet.getString("nom"));
                aliment.setPoidsMoyen(resultSet.getDouble("poids_moyen"));
                aliment.setCalories(resultSet.getInt("calories"));
                aliment.setVitaminesC(resultSet.getFloat("vitamines_C"));
                aliment.setTypeId(resultSet.getInt("type_id"));
                aliment.setId(resultSet.getInt("couleur_id"));

                aliments.add(aliment);
            }

            return aliments;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean modifier(int id, Aliment aliment) {
        String query = "UPDATE aliments SET nom = ?, poids_moyen = ?, calories = ?, vitamines_C = ?, type_id = ?, couleur_id = ? WHERE id = ?";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, aliment.getNom());
            statement.setDouble(3, aliment.getPoidsMoyen());
            statement.setInt(4, aliment.getCalories());
            statement.setFloat(5, aliment.getVitaminesC());
            statement.setInt(6, aliment.getTypeId());
            statement.setInt(7, aliment.getCouleurId());
            

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean supprimer(int id) {
        String query = "DELETE FROM aliments WHERE id = ?";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


public String getAllAsJson(int id) {
    String query = "SELECT * FROM aliments";

    try (PreparedStatement statement = connexion.prepareStatement(query)) {
        ResultSet resultSet = statement.executeQuery();

        List<Aliment> aliments = new ArrayList<>();
        while (resultSet.next()) {
            Aliment aliment = new Aliment(); // Utilisation du constructeur par défaut
            aliment.setId(resultSet.getInt("id")); // Assurez-vous que "id" est le nom correct de la colonne
            aliment.setNom(resultSet.getString("nom"));
            aliment.setPoidsMoyen(resultSet.getDouble("poids_moyen"));
            aliment.setCalories(resultSet.getInt("calories"));
            aliment.setVitaminesC(resultSet.getFloat("vitamines_C"));
            aliment.setTypeId(resultSet.getInt("type_id"));
            aliment.setCouleurId(resultSet.getInt("couleur_id"));

            aliments.add(aliment);
        }

        // Convertir la liste en JSON
        return objectMapper.writeValueAsString(aliments);

    } catch (SQLException | JsonProcessingException e) {
        e.printStackTrace();
        return "[]"; // Retourne un tableau JSON vide en cas d'erreur
    }
}
}


