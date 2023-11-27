package com.package1.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;

// Importation  de la bibliothèque Java Util
//  bibliothèque standard de Java 
import java.util.List;

// Importation de l'implémentation spécifique de l'interface ICrudDAO pour la gestion des couleurs
import com.package1.database.dao.impl.CrudCouleurImpl;
import com.package1.database.dao.impl.JsonUtil;
import com.package1.database.dao.impl.CrudAlimentImpl;
import com.package1.database.model.Aliment;
import com.package1.database.model.AlimentPrinter;
import com.package1.database.handler.CouleurHandler;
import com.package1.database.handler.AlimentsHandler;

// Importation de la classe Couleur du modèle de données
// Cette classe représente un objet Couleur qui peut être manipulé dans le code.
import com.package1.database.model.Couleur;
import com.package1.database.model.CouleurPrinter;

/**
 * En Java, DAO signifie généralement "Data Access Object". C'est un modèle de
 * conception (pattern) qui fournit une interface abstraite pour certains types
 * d'opérations sur une source de données, telle qu'une base de données. Le but
 * d'un DAO est de séparer la logique d'accès aux données de la logique métier
 * de l'application.
 **/

// Classe principale du programme
public class Main {

    private static ConnectDatabase connectDatabase;
    private static CrudCouleurImpl crudCouleurImpl;
    private static CrudAlimentImpl crudAlimentImpl;
    private ObjectMapper objectMapper = new ObjectMapper();

    public String getAllColorsAsJson() throws JsonProcessingException {
        List<Couleur> couleurs = crudCouleurImpl.getAll(0);
        return objectMapper.writeValueAsString(couleurs);
    }

    public String getAllAlimentsAsJson() throws JsonProcessingException {
        List<Aliment> aliments = crudAlimentImpl.getAll(0);
        return objectMapper.writeValueAsString(aliments);
    }

    public boolean updateAlimentFromJson(String json) {
        try {
            Aliment aliment = JsonUtil.fromJson(json, Aliment.class);
            return modifierAliment(aliment.getId(), aliment);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Aliment createAlimentFromJson(String json) {
        try {
            Aliment nouvelAliment = objectMapper.readValue(json, Aliment.class);
            if (nouvelAliment != null) {
                if (nouvelAliment.getPoidsMoyen() == 0.0) {
                    System.out.println("Erreur : Le champ 'poidsMoyen' est obligatoire.");
                    return null;
                }
    
                if (crudAlimentImpl.ajouter(nouvelAliment)) {
                    System.out.println("Aliment créé avec succès!");
                    // Utilisez l'id pour obtenir et imprimer les détails de l'aliment
                    AlimentPrinter.printAliment(crudAlimentImpl.getById(nouvelAliment.getId()));
                    return nouvelAliment;
                } else {
                    System.out.println("Erreur lors de la création de l'aliment.");
                }
            } else {
                System.out.println("Erreur : Impossible de mapper le JSON à un objet Aliment.");
            }
        } catch (JsonProcessingException e) {
            System.out.println("Erreur de traitement du JSON : " + e.getMessage());
        }
        return null;
    }

    // Méthode pour mettre à jour une couleur à partir de données JSON
    public boolean updateCouleurFromJson(int id, String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Couleur updatedCouleur = objectMapper.readValue(json, Couleur.class);
            return modifierCouleur(id, updatedCouleur);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createCouleurFromJson(String json) {
        try {
            Couleur nouvelleCouleur = JsonUtil.fromJson(json, Couleur.class);
            return createCouleur(nouvelleCouleur.getId(), nouvelleCouleur.getNom(),
                    nouvelleCouleur.getHexadecimalRVB());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendResponse(HttpExchange exchange, String response) {
        // implémentez la logique pour envoyer la réponse HTTP
    }

    public Main() {
        connectDatabase = new ConnectDatabase("jdbc:mysql://localhost:/alimentations", "root", "root");
        crudCouleurImpl = new CrudCouleurImpl(connectDatabase.getConnection());
        crudAlimentImpl = new CrudAlimentImpl(connectDatabase.getConnection());
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/couleur", new CouleurHandler(main));
        server.createContext("/aliments", new AlimentsHandler(main));
        server.setExecutor(null);
        server.start();
        main.createCouleur(78, "Rouge", "#FF0000");
        main.getAllColor();
        main.getOneColor(1, "Rouge", "#FF0000");
        main.modifierCouleur(12, new Couleur(0, null, null));
        main.supprimerCouleur(36);
        main.createAliment(44, "Pomme", 0.1, 50, 0, 1, 1);
        main.getAllAliments();
        main.getOneAliment(1);
        main.modifierAliment(46, new Aliment(46, "NouvellePomme", 0.1, 60, 0, 2, 2));
        main.disconnect();
    }

    // Méthode pour mettre à jour un aliment à partir de données JSON
    public boolean updateAlimentFromJson(int id, String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Aliment updatedAliment = objectMapper.readValue(json, Aliment.class);
            return modifierAliment(id, updatedAliment);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean createCouleur(int id, String nom, String hexadecimalRVB) {
        Couleur nouvelleCouleur = new Couleur(id, hexadecimalRVB, hexadecimalRVB);
        if (crudCouleurImpl.ajouter(nouvelleCouleur)) {
            System.out.println("Couleur créée avec succès!");
            CouleurPrinter.printCouleur(crudCouleurImpl.getById(id));
            return true;
        } else {
            System.out.println("Erreur lors de la création de la couleur.");
            return false;
        }
    }

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

    public Couleur getOneColor(int id, String nom, String hexadecimal_id) {
        Couleur couleur = crudCouleurImpl.getById(id);
        if (couleur != null) {
            System.out.println("Couleur recuperée avec succès!");
            CouleurPrinter.printCouleur(couleur);
        } else {
            System.out.println("Erreur lors de la recupération de la couleur.");
        }
        return couleur;
    }

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

    public List<Couleur> getAllColor() {
        List<Couleur> couleurs = crudCouleurImpl.getAll(0);
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

    public List<Aliment> getAllAliments() {
        List<Aliment> aliments = crudAlimentImpl.getAll(0);
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

    public boolean modifierCouleur(int id, Couleur couleur) {
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

    public boolean modifierAliment(int id, Aliment aliment) {
        if (crudAlimentImpl.modifier(id, aliment)) {
            System.out.println("Aliment modifié avec succès!");
            AlimentPrinter.printAliment(crudAlimentImpl.getById(id));
            return true;
        } else {
            System.out.println("Erreur lors de la modification de l'aliment.");
            return false;
        }
    }

    public boolean supprimerCouleur(int id) {
        if (crudCouleurImpl.supprimer(id)) {
            System.out.println("Couleur supprimée avec succès!");
            CouleurPrinter.printCouleur(crudCouleurImpl.getById(id));
            return true;
        } else {
            System.out.println("Erreur lors de la suppression de la couleur.");
            return false;
        }
    }

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

    // ... (autres méthodes de votre classe)

    public void disconnect() {
        connectDatabase.disconnectDatabase();
    }
}
