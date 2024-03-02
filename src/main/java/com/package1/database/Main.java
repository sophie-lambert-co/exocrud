package com.package1.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.List;
import com.package1.database.dao.impl.CrudCouleurImpl;
import com.package1.database.dao.impl.JsonUtil;
import com.package1.database.dao.impl.CrudAlimentImpl;
import com.package1.database.model.Aliment;
import com.package1.database.model.AlimentPrinter;
import com.package1.database.handler.CouleurHandler;
import com.package1.database.handler.AlimentsHandler;
import com.package1.database.model.Couleur;
import com.package1.database.model.CouleurPrinter;

public class Main {

    private static ConnectDatabase connectDatabase;
    private static CrudCouleurImpl crudCouleurImpl;
    private static CrudAlimentImpl crudAlimentImpl;
    private ObjectMapper objectMapper = new ObjectMapper();

    // Constructeur pour initialiser les instances de connectDatabase, crudCouleurImpl et crudAlimentImpl
    public Main() {
        connectDatabase = new ConnectDatabase("jdbc:mysql://localhost:8889/alimentations", "root", "root");
        crudCouleurImpl = new CrudCouleurImpl(connectDatabase.getConnection());
        crudAlimentImpl = new CrudAlimentImpl(connectDatabase.getConnection());
    }

    // Méthode pour récupérer toutes les couleurs sous forme de JSON
    public String getAllColorsAsJson() throws JsonProcessingException, SQLException {
        List<Couleur> couleurs = crudCouleurImpl.getAll();
        return objectMapper.writeValueAsString(couleurs);
    }

    // Méthode pour récupérer tous les aliments sous forme de JSON
    public String getAllAlimentsAsJson() throws JsonProcessingException {
        List<Aliment> aliments = crudAlimentImpl.getAll();
        return objectMapper.writeValueAsString(aliments);
    }

    // Méthode pour mettre à jour un aliment à partir d'un JSON
    public boolean updateAlimentFromJson(String json) {
        try {
            Aliment aliment = JsonUtil.jsonToObject(json, Aliment.class);
            if (aliment != null) {
                return modifier(aliment.getId(), aliment);
            } else {
                System.err.println("Erreur lors de la conversion du JSON en objet Aliment.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'aliment à partir du JSON : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour créer un aliment à partir d'un JSON
    public Aliment createAlimentFromJson(String json) {
        try {
            Aliment nouvelAliment = objectMapper.readValue(json, Aliment.class);
            if (nouvelAliment != null) {
                if (nouvelAliment.getPoidsMoyen() == 0.0) {
                    System.out.println("Erreur : Le champ 'poidsMoyen' est obligatoire.");
                    return null;
                } else {
                    if (crudAlimentImpl.ajouter(nouvelAliment)) {
                        System.out.println("Aliment créé avec succès!");
                        AlimentPrinter.printAliment(crudAlimentImpl.getById(nouvelAliment.getId()));
                        return nouvelAliment;
                    } else {
                        System.out.println("Erreur lors de la création de l'aliment.");
                    }
                }
            } else {
                System.out.println("Erreur : Impossible de mapper le JSON à un objet Aliment.");
            }
        } catch (JsonProcessingException e) {
            System.out.println("Erreur de traitement du JSON : " + e.getMessage());
        }
        return null;
    }

    // Méthode pour mettre à jour une couleur à partir d'un JSON
    public boolean updateCouleurFromJson(int id, String json) throws SQLException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Couleur updatedCouleur = objectMapper.readValue(json, Couleur.class);
            return modifierCouleur(id, updatedCouleur);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour démarrer le serveur HTTP et les différents contextes
    public static void main(String[] args) throws IOException, SQLException {
        Main main = new Main();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/couleur", new CouleurHandler(main));
        server.createContext("/aliments", new AlimentsHandler(main));
        server.createContext("/", (exchange -> {
            // Gestionnaire d'erreurs global
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                exchange.close();
            }
        }));
        server.setExecutor(null);
        server.start();
    }

    // Méthode pour créer une couleur à partir de paramètres
    public boolean createCouleur(int id, String nom, String hexadecimalRVB) {
        try {
            Couleur nouvelleCouleur = new Couleur(id, nom, hexadecimalRVB);

            System.out.println("Création de couleur - ID: " + id + ", Nom: " + nom + ", RVB: " + hexadecimalRVB);

            if (crudCouleurImpl.ajouter(nouvelleCouleur)) {
                System.out.println("Couleur créée avec succès!");
                CouleurPrinter.printCouleur(crudCouleurImpl.getById(id));
                return true;
            } else {
                System.out.println("Erreur lors de la création de la couleur.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erreur inattendue lors de la création de la couleur");
            e.printStackTrace(); // Affichez également la trace complète de l'erreur
            return false;
        }
    }

    // Méthode pour créer un aliment à partir de paramètres
    public boolean createAliment(int id, String nom, double poidsMoyen, int calories, int vitaminesC, int typeId,
            int couleurId) {
        Aliment nouvelAliment = new Aliment(id, nom, poidsMoyen, calories, vitaminesC, typeId, couleurId);
        if (crudAlimentImpl.ajouter(nouvelAliment)) {
            System.out.println("Aliment créé avec succès!");
            AlimentPrinter.printAliment(crudAlimentImpl.getById(id));
            return true;
        } else {
            System.out.println("Erreur lors de la création de l'aliment.");
            return false;
        }
    }

    // Méthode pour mettre à jour un aliment à partir d'un JSON et d'un ID
    public boolean updateAlimentFromJson(int id, String json) throws SQLException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Aliment updatedAliment = objectMapper.readValue(json, Aliment.class);
            return modifier(id, updatedAliment);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour créer une couleur à partir d'un JSON
    public boolean createCouleurFromJson(String json) throws SQLException {
        try {
            Couleur nouvelleCouleur = objectMapper.readValue(json, Couleur.class);
            return createCouleur(nouvelleCouleur.getId(), nouvelleCouleur.getNom(), nouvelleCouleur.getHexadecimalRVB());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour récupérer une couleur par ID
    public Couleur getOneColor(int id, String nom, String hexadecimal_id) throws SQLException {
        Couleur couleur = crudCouleurImpl.getById(id);
        if (couleur != null) {
            System.out.println("Couleur récupérée avec succès!");
            CouleurPrinter.printCouleur(couleur);
        } else {
            System.out.println("Erreur lors de la récupération de la couleur.");
        }
        return couleur;
    }

    // Méthode pour récupérer un aliment par ID
    public Aliment getOneAliment(int id) {
        Aliment aliment = crudAlimentImpl.getById(id);
        if (aliment != null) {
            System.out.println("Aliment récupéré avec succès!");
            AlimentPrinter.printAliment(aliment);
        } else {
            System.out.println("Erreur lors de la récupération de l'aliment.");
        }
        return aliment;
    }

    // Méthode pour récupérer toutes les couleurs
    public List<Couleur> getAllColor() throws SQLException {
        List<Couleur> couleurs = crudCouleurImpl.getAll();
        if (!couleurs.isEmpty()) {
            System.out.println("Toutes les couleurs ont été récupérées avec succès!");
            for (Couleur couleur : couleurs) {
                CouleurPrinter.printCouleur(couleur);
            }
        } else {
            System.out.println("Erreur lors de la récupération des couleurs.");
        }
        return couleurs;
    }

    // Méthode pour récupérer tous les aliments
    public List<Aliment> getAllAliments() {
        List<Aliment> aliments = crudAlimentImpl.getAll();
        if (!aliments.isEmpty()) {
            System.out.println("Tous les aliments ont été récupérés avec succès!");
            for (Aliment aliment : aliments) {
                AlimentPrinter.printAliment(aliment);
            }
        } else {
            System.out.println("Erreur lors de la récupération des aliments.");
        }
        return aliments;
    }

    // Méthode pour mettre à jour une couleur
    public boolean modifierCouleur(int id, Couleur couleur) throws SQLException {
        if (crudCouleurImpl.modifier(id, couleur)) {
            System.out.println("Couleur modifiée avec succès!");
            int idNouvelleCouleur = couleur.getId();
            CouleurPrinter.printCouleur(crudCouleurImpl.getById(idNouvelleCouleur));
            return true;
        } else {
            System.out.println("Erreur lors de la modification de la couleur.");
            return false;
        }
    }

    // Méthode pour mettre à jour un aliment
    public boolean modifier(int id, Aliment aliment) {
        if (crudAlimentImpl.modifier(id, aliment)) {
            System.out.println("Aliment modifié avec succès!");
            AlimentPrinter.printAliment(crudAlimentImpl.getById(id));
            return true;
        } else {
            System.out.println("Erreur lors de la modification de l'aliment.");
            return false;
        }
    }

    // Méthode pour supprimer une couleur
    public boolean supprimer(int id) throws SQLException {
        if (crudCouleurImpl.supprimer(id)) {
            System.out.println("Couleur supprimée avec succès!");
            CouleurPrinter.printCouleur(crudCouleurImpl.getById(id));
            return true;
        } else {
            System.out.println("Erreur lors de la suppression de la couleur.");
            return false;
        }
    }

    // Méthode pour supprimer un aliment
    public boolean supprimerAliment(int id) {
        if (crudAlimentImpl.supprimer(id)) {
            System.out.println("Aliment supprimé avec succès!");
            AlimentPrinter.printAliment(crudAlimentImpl.getById(id));
            return true;
        } else {
            System.out.println("Erreur lors de la suppression de l'aliment.");
            return false;
        }
    }

    // Méthode pour déconnecter la base de données
    public void disconnect() {
        connectDatabase.disconnectDatabase();
    }
}
