package com.package1.database.dao.impl;

// Importe la classe JsonProcessingException du package com.fasterxml.jackson.core
//Jackson, qui est utilisée pour travailler avec des objets JSON en Java. JsonProcessingException est une exception qui peut être levée lors du traitement JSON.
import com.fasterxml.jackson.core.JsonProcessingException;

// Importe la classe ObjectMapper du package com.fasterxml.jackson.databind
//ObjectMapper est utilisé pour mapper (convertir) des objets Java en format JSON et vice versa.
import com.fasterxml.jackson.databind.ObjectMapper;

// Importe la classe Connection du package java.sql
//Cette classe fait partie du package Java standard (java.sql) et est utilisée pour représenter une connexion à une base de données.
import java.sql.Connection;

// Importe la classe PreparedStatement du package java.sql
//Une classe du package Java standard (java.sql) qui représente une instruction SQL précompilée. Elle est utilisée pour exécuter des requêtes SQL paramétrées.
import java.sql.PreparedStatement;

// Importe la classe ResultSet du package java.sql
//classe du package Java standard (java.sql) utilisée pour représenter l'ensemble de résultats d'une requête SQL.
import java.sql.ResultSet;

// Importe la classe SQLException du package java.sql
//Une classe du package Java standard (java.sql) qui représente une exception liée à des erreurs SQL.
import java.sql.SQLException;

// Importe la classe ArrayList du package java.util
//Cette classe fait partie du package Java standard (java.util) et est une implémentation de la classe List. Elle est utilisée ici pour stocker des objets Couleur dans la méthode getAll().
import java.util.ArrayList;

// Importe la classe List du package java.util
// Une interface du package Java standard (java.util) représentant une liste ordonnée.
import java.util.List;

// Importe la classe Statement du package java.sql
//Une interface du package Java standard (java.sql) utilisée pour exécuter des instructions SQL simples.
import java.sql.Statement;

// Importe l'interface ICrudDAO du package com.package1.database.dao
import com.package1.database.dao.ICrudDAO;

// Importe la classe Couleur du package com.package1.database.model
import com.package1.database.model.Couleur;

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

    // méthode pour ajouter une nouvelle couleur à la base de données
    @Override
    public boolean ajouter(Couleur couleur) throws SQLException {

        // Vérifier si l'objet Couleur est nul
        if (couleur == null || couleur.getNom() == null || couleur.getNom().isEmpty()) {
            // Si l'objet Couleur est nul ou son nom est nul ou vide, lever une exception
            throw new IllegalArgumentException("Erreur : Tentative d'ajout d'une couleur avec un nom nul ou vide.");
        }

        // La requête SQL préparée est utilisée pour préparer la requête d'insertion
        // avec un paramètre (le nom de la couleur).
        // Cela est fait pour éviter les attaques par injection SQL et pour rendre la
        // requête réutilisable.
        String query = "INSERT INTO couleur (nom, hexadecimal_RVB) VALUES (?, ?)";

        try (PreparedStatement statement = connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Ne remplacez pas le paramètre pour l'ID si la base de données le génère
            // automatiquement
            // Remplacement du paramètre dans la requête par la valeur fournie
            statement.setString(1, couleur.getNom());
            statement.setString(2, couleur.getHexadecimalRVB());

            // Exécution de la requête d'insertion
            int rowsAffected = statement.executeUpdate();

            // Vérification si des lignes ont été affectées dans la base de données
            if (rowsAffected > 0) {
                // Si au moins une ligne a été affectée, le nouvel enregistrement a été ajouté
                // avec succès
                return true;
            } else {
                // Sinon, aucune ligne n'a été affectée, ce qui indique un problème lors de
                // l'ajout
                throw new SQLException(
                        "Erreur lors de l'exécution de la requête SQL d'ajout de couleur. Aucune ligne affectée.");
            }
        } catch (SQLException e) {
            // En cas d'erreur SQL, lever une exception avec un message explicatif
            throw new SQLException("Erreur lors de l'exécution de la requête SQL d'ajout de couleur.", e);
        }
    }

    // Méthode pour récupérer une couleur par son ID depuis la base de données
    @Override
    public Couleur getById(int id) throws SQLException {

        // À compléter avec le code de récupération d'une couleur par ID
        String query = "SELECT id, nom, hexadecimal_rvb FROM couleur WHERE id = ?";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            // Remplacement du paramètre dans la requête par la valeur fournie
            statement.setInt(1, id);

            // Exécution de la requête de sélection
            try (ResultSet resultSet = statement.executeQuery()) {

                // Traitement du résultat de la requête
                if (resultSet.next()) {
                    // Si la requête retourne un résultat, crée une nouvelle instance de Couleur
                    // avec les données de la base de données
                    return new Couleur(id, resultSet.getString("nom"), resultSet.getString("hexadecimal_rvb"));
                } else {
                    // Si la requête ne retourne aucun résultat, renvoie null
                    return null;
                }
            }
        } catch (SQLException e) {
            // En cas d'erreur SQL, imprimer la trace de l'erreur.
            throw new SQLException("Erreur lors de la récupération de la couleur par ID.", e);
        }
    }

    // Méthode pour récupérer toutes les couleurs depuis la base de données
    @Override
    public List<Couleur> getAll() throws SQLException {

        // À compléter avec le code de récupération de toutes les couleurs
        String query = "SELECT * FROM couleur";

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            // Cette ligne utilise une structure try-with-resources pour créer une
            // PreparedStatement.
            // Cela garantit que la ressource (dans ce cas, statement) est correctement
            // fermée,
            // même en cas d'exception, à la fin du bloc try.
            try (ResultSet resultSet = statement.executeQuery()) {
                // À l'intérieur du premier bloc try, une nouvelle ressource ResultSet est créée
                // en exécutant la requête SQL avec executeQuery().

                // Une instance de ResultSet représente l'ensemble des résultats de la requête
                // SQL.

                List<Couleur> couleurs = new ArrayList<>();
                // Crée une liste qui stockera les objets Couleur récupérés de la base de
                // données.

                // Parcourir les résultats de la requête à l'aide de la boucle while
                while (resultSet.next()) {
                    // Pour chaque ligne de résultat, crée une nouvelle instance de Couleur
                    Couleur couleur = new Couleur();
                    // Initialise les propriétés de la couleur avec les valeurs de la ligne actuelle
                    // du résultat
                    couleur.setId(resultSet.getInt("id"));
                    couleur.setNom(resultSet.getString("nom"));
                    couleur.setHexadecimalRVB(resultSet.getString("hexadecimal_RVB"));
                    // Ajoute la couleur à la liste
                    couleurs.add(couleur);
                }
                // Retourne la liste de toutes les couleurs
                return couleurs;
            }
        } catch (SQLException e) {
            // En cas d'erreur SQL, imprimer la trace de l'erreur.
            throw new SQLException("Erreur lors de la récupération de toutes les couleurs.", e);
        }
    }

   // Méthode pour modifier une couleur dans la base de données
@Override
public boolean modifier(int id, Couleur couleur) throws SQLException {
    // Cette méthode est responsable de la modification du nom d'une couleur dans la
    // base de données.
    if (couleur == null || couleur.getNom() == null || couleur.getHexadecimalRVB() == null) {
        // Vérifie que la couleur fournie est valide
        throw new IllegalArgumentException("Erreur : Tentative de modification avec une couleur invalide.");
    }

    if (!existeCouleur(id)) {
        // Vérifie que l'ID fourni existe dans la base de données
        throw new SQLException("Erreur : L'ID fourni n'existe pas dans la base de données.");
    }

    // À compléter avec le code de modification du nom d'une couleur
    String query = "UPDATE couleur SET nom = ?, hexadecimal_RVB = ? WHERE id = ?";

    try (PreparedStatement statement = connexion.prepareStatement(query)) {
        // Remplace les paramètres dans la requête par les valeurs fournies
        statement.setString(1, couleur.getNom());
        statement.setString(2, couleur.getHexadecimalRVB());
        statement.setInt(3, id);

        // Exécution de la requête de mise à jour
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            return true; // Retourne true si la modification a réussi
        } else {
            throw new SQLException("Erreur lors de la modification de la couleur. Aucune ligne affectée.");
        }
    } catch (SQLException e) {
        // En cas d'erreur SQL, imprimer la trace de l'erreur.
        throw new SQLException("Erreur lors de l'exécution de la requête SQL de modification de couleur.", e);
    }
}

// Méthode pour supprimer une couleur de la base de données par son ID
@Override
public boolean supprimer(int id) throws SQLException {
    // Cette méthode est responsable de la suppression d'une couleur par son ID de
    // la base de données.
    if (id <= 0) {
        // Vérifie que l'ID fourni pour la suppression est valide
        throw new IllegalArgumentException("Erreur : L'ID fourni pour la suppression est invalide.");
    }

    // Ajoutez la validation que l'ID existe dans la base de données
    if (!existeCouleur(id)) {
        // Vérifie que l'ID fourni existe dans la base de données
        throw new SQLException("Erreur : L'ID fourni n'existe pas dans la base de données.");
    }

    // À compléter avec le code de suppression d'une couleur par ID
    String query = "DELETE FROM couleur WHERE id = ?";

    try (PreparedStatement statement = connexion.prepareStatement(query)) {
        // Remplace le paramètre dans la requête par la valeur fournie
        statement.setInt(1, id);

        // Exécution de la requête de suppression
        int rowsAffected = statement.executeUpdate();

        // Vérification si des lignes ont été affectées dans la base de données
        if (rowsAffected > 0) {
            return true; // Retourne true si la suppression a réussi
        } else {
            throw new SQLException("Erreur lors de la suppression de la couleur. Aucune ligne affectée.");
        }
    } catch (SQLException e) {
        // En cas d'erreur SQL, imprimer la trace de l'erreur.
        throw new SQLException("Erreur SQL lors de la suppression de la couleur.", e);
    }
}

// Méthode pour vérifier si une couleur avec l'ID donné existe dans la base de
// données
private boolean existeCouleur(int id) {
    String query = "SELECT id FROM couleur WHERE id = ?";
    try (PreparedStatement statement = connexion.prepareStatement(query)) {
        // Remplace le paramètre dans la requête par la valeur fournie
        statement.setInt(1, id);

        // Exécution de la requête de sélection
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next(); // Retourne true si l'ID existe
    } catch (SQLException e) {
        // En cas d'erreur SQL, imprimer la trace de l'erreur.
        System.err.println("Erreur lors de la vérification de l'existence de la couleur : " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

// Méthode pour récupérer toutes les couleurs sous forme de JSON depuis la base de données
@Override
public String getAllAsJson() {
    String query = "SELECT * FROM couleur";

    try (PreparedStatement statement = connexion.prepareStatement(query)) {
        // Exécute la requête de sélection
        ResultSet resultSet = statement.executeQuery();

        List<Couleur> couleurs = new ArrayList<>();
        // Crée une liste pour stocker les objets Couleur récupérés de la base de données

        while (resultSet.next()) {
            // Pour chaque ligne de résultat, crée une nouvelle instance de Couleur
            Couleur couleur = new Couleur(); // Utilisation du constructeur par défaut
            couleur.setId(resultSet.getInt("id")); // Assurez-vous que "id" est le nom correct de la colonne
            couleur.setNom(resultSet.getString("nom"));
            couleur.setHexadecimalRVB(resultSet.getString("hexadecimal_RVB"));
            couleurs.add(couleur);
        }

        // Convertir la liste en JSON
        return objectMapper.writeValueAsString(couleurs);

    } catch (SQLException | JsonProcessingException e) {
        // En cas d'erreur SQL ou d'erreur lors de la conversion en JSON,
        // imprimer la trace de l'erreur et retourner un tableau JSON vide.
        e.printStackTrace();
        return "[]";
    }
}}
