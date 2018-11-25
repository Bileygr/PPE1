package models.dao;

import models.base.Administrateur;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdministrateurDAO {
	
	public static String obtenir_le_hache_correspondant_a_l_email_de_connexion(String email) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_mot_de_passe_hash "+
						 "FROM administrateur "+
						 "WHERE administrateur_email = ?";
		
		String resultatDeLaRequete = "";
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			resultatDeLaRequete = resultat.getString("administrateur_mot_de_passe_hash");
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
	
	public static boolean mise_a_jour_de_la_date_de_derniere_connexion(String email, String hash) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "UPDATE administrateur "+
						 "SET administrateur_derniere_connexion =  NOW() "+
						 "WHERE administrateur_email = ? AND administrateur_mot_de_passe_hash = ?";
		
		boolean resultatDeLaRequete = false;
		
		PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requete);
        prepared_statement.setString(1, email);
        prepared_statement.setString(2, hash);
        int nblignes = 0;
        
        try {
        	nblignes = prepared_statement.executeUpdate();
        } catch (SQLException ex) {
        	System.out.println(ex.getMessage());
        	}
        
        if(nblignes == 1) {
        	resultatDeLaRequete = true;
        } else {
        	resultatDeLaRequete = false;
        	}
        
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
	
	public static boolean obtenir_le_status_super_administrateur(String email, String hash) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT * "+
						 "FROM administrateur "+
						 "WHERE administrateur_email = ? AND administrateur_mot_de_passe_hash = ? AND administrateur_super = 1";
		
		boolean resultatDeLaRequete = false;
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		prepared_statement.setString(2, hash);
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			resultatDeLaRequete = true;
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
	
	public static String obtenir_le_nom_de_la_personne_connecte(String identifiant, String hash) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_nom, administrateur_prenom "+ 
						 "FROM administrateur "+
						 "WHERE administrateur_email = ? AND administrateur_mot_de_passe_hash = ?";
		
		String resultatDeLaRequete = "";
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, identifiant);
		prepared_statement.setString(2, hash);
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			resultatDeLaRequete = (resultat.getString("administrateur_prenom") + " " +resultat.getString("administrateur_nom"));
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
	
	public static boolean ajouter_un_administrateur(int administrateur_super, String nom, String prenom, String hash, String email, String telephone,
			String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "INSERT INTO administrateur(administrateur_super, administrateur_nom, administrateur_prenom, administrateur_mot_de_passe_hash, "+
				 		 "administrateur_email, administrateur_telephone, administrateur_adresse, administrateur_ville, administrateur_code_postal,"+
				         "administrateur_derniere_connexion, administrateur_creation) "+
				 		 "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
		boolean resultatDeLaRequete = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setInt(1, administrateur_super);
		prepared_statement.setString(2, nom);
		prepared_statement.setString(3, prenom);
		prepared_statement.setString(4, hash);
		prepared_statement.setString(5, email);
		prepared_statement.setString(6, telephone);
		prepared_statement.setString(7, adresse);
		prepared_statement.setString(8, ville);
		prepared_statement.setString(9, code_postal);
		int nblignes = 0;
		
		try {
        	nblignes = prepared_statement.executeUpdate();
        } catch(SQLException ex) {
        	System.out.println(ex.getMessage());
        	}
		
        if(nblignes == 1) { 
        	resultatDeLaRequete = true;
        } else { 
        	resultatDeLaRequete = false;
        	}
        
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
	
	public static boolean modifier_les_informations_d_un_administrateur(int id, int administrateur_super, String nom, String prenom, String email, String telephone,
		String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "UPDATE administrateur "+
						 "SET administrateur_super = ?, administrateur_nom = ?, administrateur_prenom = ?, administrateur_email = ?, "+
				 		 "administrateur_telephone = ?, administrateur_adresse = ?, administrateur_ville = ?, administrateur_code_postal = ? "+
				 		 "WHERE administrateur_id = ?";
		
		boolean resultatDeLaRequete = false;
		
		PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requete);
        prepared_statement.setInt(1, administrateur_super);
        prepared_statement.setString(2, nom);
        prepared_statement.setString(3, prenom);
        prepared_statement.setString(4, email);
        prepared_statement.setString(5, telephone);
        prepared_statement.setString(6, adresse);
        prepared_statement.setString(7, ville);
        prepared_statement.setString(8, code_postal);
        prepared_statement.setInt(9, id);
        int nblignes = 0;
        
        try {
        	nblignes = prepared_statement.executeUpdate();
        } catch (SQLException ex) {
        	System.out.println(ex.getMessage());
        	}
        
        if(nblignes == 1) {
        	resultatDeLaRequete = true;
        } else {
        	resultatDeLaRequete = false;
        	}
        
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
	
	public static boolean supprimer_un_administrateur(int  id) throws SQLException, ClassNotFoundException {
        Connection connexion = Connect.getInstance().getConnection();
        String requeteSQL = "DELETE FROM administrateur WHERE administrateur_id = ?";
        boolean reponse  = false;
        PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requeteSQL);
        prepared_statement.setInt(1, id);
        int nblignes = 0;
        
        try {
        	nblignes = prepared_statement.executeUpdate();
        } catch (SQLException ex) 
          {
            System.out.println(ex.getMessage());
          }
        
        if(nblignes == 1) {
        	reponse = true;
        } else {
        	reponse = false;
        	}
        
        prepared_statement.close();
        connexion.close();
        return reponse;
     }
	
	public static ObservableList<Administrateur> lister_tout_les_administrateurs() throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_id, administrateur_super, administrateur_nom, administrateur_prenom, administrateur_email, administrateur_telephone,"+
				 		 "administrateur_adresse, administrateur_ville, administrateur_code_postal, administrateur_derniere_connexion, administrateur_creation "+
				 		 "FROM administrateur";
		
		ObservableList<Administrateur> retour = FXCollections.observableArrayList();
		
		int id;
		int administrateur_super;
		String administrateur_super_str = "";
		String nom;
		String prenom;
		String email;
		String telephone;
		String adresse;
		String ville;
		String code_postal;
		String derniere_connexion;
		String creation;
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		ResultSet resultat = prepared_statement.executeQuery();
		
		while(resultat.next()) {
			id = resultat.getInt("administrateur_id");
			administrateur_super = resultat.getInt("administrateur_super");
			nom = resultat.getString("administrateur_nom");
			prenom = resultat.getString("administrateur_prenom");
			email = resultat.getString("administrateur_email");
			telephone = resultat.getString("administrateur_telephone");
			adresse = resultat.getString("administrateur_adresse");
			ville = resultat.getString("administrateur_ville");
			code_postal = resultat.getString("administrateur_code_postal");
			derniere_connexion = resultat.getString("administrateur_derniere_connexion");
			creation = resultat.getString("administrateur_creation");
			
			if(administrateur_super == 1) {
				administrateur_super_str = "Oui";
			}else if(administrateur_super == 0){
				administrateur_super_str = "Non";
			}
			
			Administrateur instance = new Administrateur();
			
			instance.setAdministrateur_id(id);
			instance.setAdministrateur_super(administrateur_super_str);
			instance.setAdministrateur_nom(nom);
			instance.setAdministrateur_prenom(prenom);
			instance.setAdministrateur_email(email);
			instance.setAdministrateur_telephone(telephone);
			instance.setAdministrateur_adresse(adresse);
			instance.setAdministrateur_ville(ville);
			instance.setAdministrateur_code_postal(code_postal);
			instance.setAdministrateur_derniere_connexion(derniere_connexion);
			instance.setAdministrateur_creation(creation);
   		 	
			retour.add(instance);
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static ObservableList<Administrateur> recherche_filtre(String filtre) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_id, administrateur_super, administrateur_nom, administrateur_prenom, "
							+ "administrateur_email, administrateur_telephone, administrateur_adresse, administrateur_ville, "
							+ "administrateur_code_postal, administrateur_derniere_connexion, administrateur_creation "
							+ "FROM administrateur WHERE administrateur_nom LIKE ?"
							+ "OR administrateur_prenom LIKE ?"
							+ "OR administrateur_telephone LIKE ?"
							+ "OR administrateur_email LIKE ?"
							+ "OR administrateur_derniere_connexion LIKE ?"
							+ "OR administrateur_creation LIKE ?";
		
		ObservableList<Administrateur> resultatDeLaRequete  = FXCollections.observableArrayList();
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, "%" + filtre +  "%");
		prepared_statement.setString(2, "%" + filtre +  "%");
		prepared_statement.setString(3, "%" + filtre +  "%");
		prepared_statement.setString(4, "%" + filtre +  "%");
		prepared_statement.setString(5, "%" + filtre +  "%");
		prepared_statement.setString(6, "%" + filtre +  "%");
		
		int id;
		int administrateur_super;
		String administrateur_super_str = "";
		String nom;
		String prenom;
		String email;
		String telephone;
		String adresse;
		String ville;
		String code_postal;
		String derniere_connexion;
		String creation;
		
		ResultSet resultat = prepared_statement.executeQuery();
    
		while(resultat.next()) {
			id = resultat.getInt("administrateur_id");
			administrateur_super = resultat.getInt("administrateur_super");
			nom = resultat.getString("administrateur_nom");
			prenom = resultat.getString("administrateur_prenom");
			email = resultat.getString("administrateur_email");
			telephone = resultat.getString("administrateur_telephone");
			adresse = resultat.getString("administrateur_adresse");
			ville = resultat.getString("administrateur_ville");
			code_postal = resultat.getString("administrateur_code_postal");
			derniere_connexion = resultat.getString("administrateur_derniere_connexion");
			creation = resultat.getString("administrateur_creation");
			
			if(administrateur_super == 1) {
				administrateur_super_str = "Oui";
			}else if(administrateur_super == 0){
				administrateur_super_str = "Non";
			}
			
			Administrateur administrateur = new Administrateur();
			
			administrateur.setAdministrateur_id(id);
			administrateur.setAdministrateur_super(administrateur_super_str);
			administrateur.setAdministrateur_nom(nom);
			administrateur.setAdministrateur_prenom(prenom);
			administrateur.setAdministrateur_email(email);
			administrateur.setAdministrateur_telephone(telephone);
			administrateur.setAdministrateur_adresse(adresse);
			administrateur.setAdministrateur_ville(ville);
			administrateur.setAdministrateur_code_postal(code_postal);
			administrateur.setAdministrateur_derniere_connexion(derniere_connexion);
			administrateur.setAdministrateur_creation(creation);
   		 	
			resultatDeLaRequete.add(administrateur);
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
}