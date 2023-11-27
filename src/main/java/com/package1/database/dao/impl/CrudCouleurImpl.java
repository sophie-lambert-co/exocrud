package com.package1.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Statement;
import com.fasterxml.jackson.core.JsonProcessingException;

// Importation de la classe ResultSet de la bibliothèque SQL
// Cette classe fournit des méthodes pour parcourir et manipuler les données retournées par la base de données.
// Elle représente l'ensemble des résultats d'une requête SQL.
import java.sql.ResultSet;

// Importation de la classe SQLException de la bibliothèque SQL
// Cette classe gère les erreurs spécifiques à SQL qui peuvent survenir lors de l'interaction avec la base de données.
import java.sql.SQLException;

// Importation de la classe ArrayList de la bibliothèque Java Util
// Cette classe fait partie de la bibliothèque standard de Java et est utilisée pour créer une liste dynamique.
import java.util.ArrayList;
import java.util.List;

import com.package1.database.dao.ICrudDAO;
import com.package1.database.model.Couleur;

// Cette classe implémente l'interface CouleurDAO, ce qui signifie qu'elle doit fournir une implémentation pour toutes les méthodes définies dans cette interface.
public class CrudCouleurImpl implements ICrudDAO<Couleur> {

    private Connection connexion; // La connexion à la base de données
    private ObjectMapper objectMapper;

    // Le constructeur de la classe prend une connexion en paramètre.
    // Cela signifie que lorsqu'on crée une instance de CouleurDAOImpl, on doit lui
    // fournir une connexion à la base de données.
    public CrudCouleurImpl(Connection connexion) {
        this.connexion = connexion;
        this.objectMapper = new ObjectMapper();
    }

    PreparedStatement statement = null;
    @Override
    public boolean ajouter(Couleur couleur) {
        // Cette méthode est responsable de l'ajout d'une nouvelle couleur dans la base
        // de données.

        // Vérifier si l'objet Couleur est nul
        if (couleur == null || couleur.getNom() == null || couleur.getNom().isEmpty()) {
            System.err.println("Erreur : Tentative d'ajout d'une couleur avec un nom nul ou vide.");
            return false;
        }

        // À compléter avec le code d'ajout de couleur

        // La requête SQL préparée est utilisée pour préparer la requête d'insertion
        // avec un paramètre (le nom de la couleur).
        // Cela est fait pour éviter les attaques par injection SQL et pour rendre la
        // requête réutilisable.
        String query = "INSERT INTO couleur (nom, hexadecimal_RVB) VALUES (?, ?)";
        // Déclarez le PreparedStatement en dehors du bloc try pour qu'il soit accessible dans le bloc finally
      

        try (PreparedStatement statement = connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Ne remplacez pas le paramètre pour l'ID si la base de données le génère
            // automatiquement
            // Remplacement du paramètre dans la requête par la valeur fournie
            statement.setString(1, couleur.getNom());
            statement.setString(2, couleur.getHexadecimalRVB());

            // Exécution de la requête d'insertion
            int rowsAffected = statement.executeUpdate();

            // Vérification si des lignes ont été affectées dans la base de données
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête SQL d'ajout de couleur : " + e.getMessage());
            // En cas d'erreur SQL, imprimer la trace de l'erreur.
            e.printStackTrace();
            return false;
        } finally {
            // Fermeture du PreparedStatement dans le bloc finally
            if (statement!= null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // L'annotation @Override est utilisée pour indiquer au compilateur que la
    // méthode suivante est censée remplacer une méthode héritée de l'interface.
    @Override
    public Couleur getById(int id) {
        // Cette méthode est responsable de la récupération d'une couleur par son ID
        // depuis la base de données.

        // À compléter avec le code de récupération d'une couleur par ID
        String query = "SELECT id, nom, hexadecimal_rvb FROM couleur WHERE id = ?";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            // Remplacement du paramètre dans la requête par la valeur fournie
            statement.setInt(1, id);

            // Exécution de la requête de sélection
            ResultSet resultSet = statement.executeQuery();

            // Traitement du résultat de la requête
            if (resultSet.next()) {
                // Construction de l'objet Couleur à partir des données de la base de données
                Couleur couleur = new Couleur(id, resultSet.getString("nom"), resultSet.getString("hexadecimal_rvb"));


                couleur.setNom(resultSet.getString("nom"));
                couleur.setHexadecimalRVB(resultSet.getString("hexadecimal_rvb"));
    
                return couleur;
            } else {
                return null; // Retourne null si aucune couleur n'est trouvée
            }

        } catch (SQLException e) {
            // En cas d'erreur SQL, imprimer la trace de l'erreur.
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Couleur> getAll(int id) {
        // Cette méthode est responsable de la récupération de toutes les couleurs
        // depuis la base de données.

        // À compléter avec le code de récupération de toutes les couleurs
        String query = "SELECT * FROM couleur";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            // Exécution de la requête de sélection
            ResultSet resultSet = statement.executeQuery();

             // Construction de la liste des couleurs à partir des données de la base de
        // données
        List<Couleur> couleurs = new ArrayList<>();
        while (resultSet.next()) {
            Couleur couleur = new Couleur(id, query, query);
            couleur.setId(resultSet.getInt("id"));  // Correction ici
            couleur.setNom(resultSet.getString("nom"));
            couleur.setHexadecimalRVB(resultSet.getString("hexadecimal_RVB"));

            couleurs.add(couleur);  // Ajout de la couleur à la liste
        }
            return couleurs;

        } catch (SQLException e) {
            // En cas d'erreur SQL, imprimer la trace de l'erreur.
            e.printStackTrace();
            return new ArrayList<>(); // Retourne une liste vide en cas d'erreur
        }
    }

    
    @Override
    public boolean modifier(int id, Couleur couleur) {
        // Cette méthode est responsable de la modification du nom d'une couleur dans la
        // base de données.

        // À compléter avec le code de modification du nom d'une couleur
        String query = "UPDATE couleur SET nom = ?, hexadecimal_RVB = ? WHERE id = ?";


        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            // Remplacement des paramètres dans la requête par les valeurs fournies
            statement.setInt(1, id);
            statement.setString(2, couleur.getNom());
            statement.setString(3, couleur.getHexadecimalRVB());
           

            // Exécution de la requête de mise à jour
            int rowsAffected = statement.executeUpdate();

            // Vérification si des lignes ont été affectées dans la base de données
            return rowsAffected > 0;

        } catch (SQLException e) {
            // En cas d'erreur SQL, imprimer la trace de l'erreur.
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean supprimer(int id) {
        // Cette méthode est responsable de la suppression d'une couleur par son ID de
        // la base de données.

        // À compléter avec le code de suppression d'une couleur par ID
        String query = "DELETE FROM couleur WHERE id = ?";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            // Remplacement du paramètre dans la requête par la valeur fournie
            statement.setInt(1, id);

            // Exécution de la requête de suppression
            int rowsAffected = statement.executeUpdate();

            // Vérification si des lignes ont été affectées dans la base de données
            return rowsAffected > 0;

        } catch (SQLException e) {
            // En cas d'erreur SQL, imprimer la trace de l'erreur.
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getAllAsJson(int id) {
        String query = "SELECT * FROM couleur";
    
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
    
            List<Couleur> couleurs = new ArrayList<>();
            while (resultSet.next()) {
                Couleur couleur = new Couleur(); // Utilisation du constructeur par défaut
                couleur.setId(resultSet.getInt("id")); // Assurez-vous que "id" est le nom correct de la colonne
                couleur.setNom(resultSet.getString("nom"));
                couleur.setHexadecimalRVB(resultSet.getString("hexadecimal_RVB"));
                couleurs.add(couleur);
            }
    
            // Convertir la liste en JSON
            return objectMapper.writeValueAsString(couleurs);
    
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return "[]"; // Retourne un tableau JSON vide en cas d'erreur
        }
    }
}
