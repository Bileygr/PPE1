package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classe.Partenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartenaireDAO {
	public static boolean inscrire(int siret, String nom, String hash, String email, String telephone, String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "INSERT INTO partenaire(partenaire_siret, partenaire_nom, partenaire_mot_de_passe_hash, partenaire_email, "
							+ "partenaire_telephone, partenaire_adresse, partenaire_ville, partenaire_code_postal, "
							+ "partenaire_derniere_connexion, partenaire_date_ajout) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
		boolean retour = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setInt(1, siret);
		prepared_statement.setString(2, nom);
		prepared_statement.setString(3, hash);
		prepared_statement.setString(4, email);
		prepared_statement.setString(5, telephone);
		prepared_statement.setString(6, adresse);
		prepared_statement.setString(7, ville);
		prepared_statement.setString(8, code_postal);
		
		int nblignes = 0;
        
        try {
        	nblignes = prepared_statement.executeUpdate();
        } catch(SQLException ex) {
        	System.out.println(ex.getMessage());
        	}
		
        if(nblignes == 1) { 
        	retour = true;
        } else { 
        	retour = false;
        	}
        
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static boolean modifier(int id, int siret, String nom, String email, String telephone, String adresse, String ville, String code_postal) throws SQLException {
        Connection connexion =  Connect.getInstance().getConnection();
        String requete = "UPDATE partenaire SET partenaire_siret = ?, partenaire_nom = ?, partenaire_email = ?, "
        		+ "partenaire_telephone = ?, partenaire_adresse = ?, partenaire_ville = ?, partenaire_code_postal = ? "
        		+ "WHERE partenaire_id = ?";
        
        boolean retour = false;
        
        PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requete);
        prepared_statement.setInt(1, siret);
        prepared_statement.setString(2, nom);
        prepared_statement.setString(3, email);
        prepared_statement.setString(4, telephone);
        prepared_statement.setString(5, adresse);
        prepared_statement.setString(6, ville);
        prepared_statement.setString(7, code_postal);
        prepared_statement.setInt(8, id);
        
        int nblignes = 0;
        
        try {
            nblignes = prepared_statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
          	}
        
        if(nblignes == 1) { 
        	retour = true;
        } else { 
        	retour = false;
        	}
        
        prepared_statement.close();
        connexion.close();
        return retour;
	}
	
	public static boolean supprimer(int id) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "DELETE FROM partenaire WHERE partenaire_id = ?";
		
		boolean retour = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setInt(1, id);
		
		int nblignes = 0;
		
		if(nblignes == 1) { 
        	retour = true;
        } else { 
        	retour = false;
        	}
		
		try {
			nblignes = prepared_statement.executeUpdate();
		} catch(SQLException ex) {
			System.out.println(ex.getMessage());
			}
		
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static ObservableList<Partenaire> recherche() throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT partenaire_id, partenaire_siret, partenaire_nom, partenaire_email, partenaire_telephone, "
				+ "partenaire_adresse, partenaire_ville, partenaire_code_postal, partenaire_derniere_connexion, partenaire_date_ajout FROM partenaire";
		
		ObservableList<Partenaire> retour = FXCollections.observableArrayList();
		
		int 	id;
		int 	siret;
		String 	nom;
		String 	email;
		String 	telephone;
		String  adresse;
		String 	ville;
		String 	code_postal;
		String 	derniere_connexion;
		String 	creation;
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		
		ResultSet resultat = prepared_statement.executeQuery();
		
		while(resultat.next()) {
			id 					= resultat.getInt("partenaire_id");
			siret 				= resultat.getInt("partenaire_siret");
			nom 				= resultat.getString("partenaire_nom");
			email 				= resultat.getString("partenaire_email");
			telephone 			= resultat.getString("partenaire_telephone");
			adresse				= resultat.getString("partenaire_adresse");
			ville 				= resultat.getString("partenaire_ville");
			code_postal 		= resultat.getString("partenaire_code_postal");
			derniere_connexion 	= resultat.getString("partenaire_derniere_connexion");
			creation			= resultat.getString("partenaire_date_ajout");
			
			Partenaire partenaire = new Partenaire();
			
			partenaire.setPartenaire_id(id);
			partenaire.setPartenaire_siret(siret);
			partenaire.setPartenaire_nom(nom);
			partenaire.setPartenaire_email(email);
			partenaire.setPartenaire_telephone(telephone);
			partenaire.setPartenaire_adresse(adresse);
			partenaire.setPartenaire_ville(ville);
			partenaire.setPartenaire_code_postal(code_postal);
			partenaire.setPartenaire_derniere_connexion(derniere_connexion);
			partenaire.setPartenaire_date_ajout(creation);
			
			retour.add(partenaire);
			System.out.println(partenaire);
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static ObservableList<Partenaire> recherche_filtre(String filtre) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT partenaire_id, partenaire_siret, partenaire_nom, partenaire_email, partenaire_telephone, "
						+ "partenaire_adresse, partenaire_ville, partenaire_code_postal, "
						+ "partenaire_derniere_connexion, partenaire_date_ajout FROM partenaire WHERE  partenaire_siret LIKE ? ESCAPE '!' "
																						   	     + "OR partenaire_nom   LIKE ? ESCAPE '!' "
																						   	   + "OR partenaire_email   LIKE ? ESCAPE '!'"
																						   	 + "OR partenaire_telephone LIKE ? ESCAPE '!'";
	 	
		ObservableList<Partenaire> retour = FXCollections.observableArrayList();
		
		filtre = filtre
	 			.replace("!", "!!")
	 			.replace("%", "!%")
	 			.replace("_", "!_")
	 			.replace("[", "![");
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, "%" + filtre +  "%");
		prepared_statement.setString(2, "%" + filtre +  "%");
		prepared_statement.setString(3, "%" + filtre +  "%");
		prepared_statement.setString(4, "%" + filtre +  "%");
	 	
	 	int 	id;
	 	int 	siret;
	 	String 	nom;
	 	String 	email;
	 	String 	telephone;
		String  adresse;
		String 	ville;
		String 	code_postal;
	 	String 	derniere_connexion;
	 	String 	creation;
	     
	    ResultSet resultat = prepared_statement.executeQuery();
     
	    while(resultat.next()){
	    	id 					= resultat.getInt("partenaire_id");
			siret 				= resultat.getInt("partenaire_siret");
			nom 				= resultat.getString("partenaire_nom");
			email 				= resultat.getString("partenaire_email");
			telephone 			= resultat.getString("partenaire_telephone");
			adresse				= resultat.getString("partenaire_adresse");
			ville 				= resultat.getString("partenaire_ville");
			code_postal 		= resultat.getString("partenaire_code_postal");
			derniere_connexion 	= resultat.getString("partenaire_derniere_connexion");
			creation 			= resultat.getString("partenaire_date_ajout");
			
			Partenaire partenaire = new Partenaire();
			
			partenaire.setPartenaire_id(id);
			partenaire.setPartenaire_siret(siret);
			partenaire.setPartenaire_nom(nom);
			partenaire.setPartenaire_email(email);
			partenaire.setPartenaire_telephone(telephone);
			partenaire.setPartenaire_adresse(adresse);
			partenaire.setPartenaire_ville(ville);
			partenaire.setPartenaire_code_postal(code_postal);
			partenaire.setPartenaire_derniere_connexion(derniere_connexion);
			partenaire.setPartenaire_date_ajout(creation);
    	 
	    	retour.add(partenaire);
	    	System.out.println(partenaire);
	    }
	    
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
     	return retour;
	}
}
