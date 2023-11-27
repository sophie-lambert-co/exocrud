package com.package1.database;

// Importe la classe Connection de java.sql pour gérer les connexions à la base de données
import java.sql.Connection;
// Importe la classe DriverManager de java.sql pour gérer les pilotes de bases de données JDBC
import java.sql.DriverManager;
// Importe la classe SQLException de java.sql pour gérer les exceptions SQL
import java.sql.SQLException;

/**
 * La classe ConnectDatabase gère la connexion à la base de données.
* Elle utilise JDBC (Java Database Connectivity) pour interagir avec une base de données MySQL.
 */
public class ConnectDatabase {
    private Connection connexion;
    private String url;
    private String utilisateur;
    private String motDePasse;

    // Constructeur pour initialiser la connexion à la base de données
    public ConnectDatabase(String url, String utilisateur, String motDePasse) {
        this.url = url;
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;

        // Appel de la méthode de connexion à la base de données dans le constructeur
        connectDatabase();
    }

    // Méthode pour établir la connexion à la base de données
    private void connectDatabase() {
        try {
            // DriverManager.getConnection() établit la connexion à la base de données
            // en utilisant les informations fournies (URL, nom d'utilisateur, mot de passe).
            this.connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            // Vous pourriez également afficher un message de confirmation ici si nécessaire.
            System.out.println("Connexion à la base de données réussie.");
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'exécution de la requête SQL.");
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer la connexion
    public Connection getConnection() {
        return this.connexion;
    }

    // Méthode pour fermer la connexion à la base de données
    public void disconnectDatabase() {
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
                System.out.println("Déconnexion de la base de données réussie.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de la déconnexion de la base de données.");
            e.printStackTrace();
        }
    }
}