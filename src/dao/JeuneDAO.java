package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import classe.Jeune;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JeuneDAO {
	public static boolean inscrire(String nom, String prenom, String identifiant, String mot_de_passe, String email, String telephone, String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "INSERT INTO jeune(jeune_nom, jeune_prenom, jeune_identifiant, jeune_mot_de_passe, jeune_email, jeune_telephone, jeune_adresse, jeune_ville, jeune_code_postal, "
							+ "jeune_derniere_connexion, jeune_date_ajout) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
		boolean retour = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, nom);
		prepared_statement.setString(2, prenom);
		prepared_statement.setString(3, identifiant);
		prepared_statement.setString(4, mot_de_passe);
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
        	retour = true;
        } else { 
        	retour = false;
        	}
        
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static boolean modifier(int id, String nom, String prenom, String identifiant, String email, String telephone, String adresse, String ville, String code_postal) throws SQLException, ClassNotFoundException {
        Connection connexion =  Connect.getInstance().getConnection();
        String requete = "UPDATE jeune SET jeune_nom = ?, jeune_prenom = ?, jeune_identifiant = ?, jeune_email = ?, jeune_telephone = ?, jeune_adresse = ?, jeune_ville = ?, jeune_code_postal = ? WHERE jeune_id = ?";
        
        boolean retour = false;
        
        PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requete);
        prepared_statement.setString(1, nom);
        prepared_statement.setString(2, prenom);
        prepared_statement.setString(3, identifiant);
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
        	retour = true;
        } else {
        	retour = false;
        	}
        
        prepared_statement.close();
        connexion.close();
        return retour;
     }
     
	public static boolean supprimer(int  id) throws SQLException, ClassNotFoundException {
        Connection connexion = Connect.getInstance().getConnection();
        String requeteSQL = "DELETE FROM jeune WHERE jeune_id = ?";
        
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
 
     public static ObservableList<Jeune> recherche_filtre(String filtre) throws ClassNotFoundException, SQLException {     
    	Connection connexion = Connect.getInstance().getConnection();
    	String requete = "SELECT jeune_id, jeune_nom, jeune_prenom, jeune_identifiant, jeune_email, jeune_telephone, jeune_adresse,"
    			+ "jeune_ville, jeune_code_postal, jeune_derniere_connexion, jeune_date_ajout FROM jeune "
	 					+ "WHERE jeune_nom LIKE ? ESCAPE '!' "
	 					+ "OR jeune_prenom LIKE ? ESCAPE '!' "
	 					+ "OR jeune_identifiant LIKE ? ESCAPE '!' "
	 					+ "OR jeune_email LIKE ? ESCAPE '!'"
	 					+ "OR jeune_telephone LIKE ? ESCAPE '!'";
    	
    	filtre = filtre
	 			.replace("!", "!!")
	 			.replace("%", "!%")
	 			.replace("_", "!_")
	 			.replace("[", "![");
    	
    	ObservableList<Jeune> retour = FXCollections.observableArrayList();
    	
    	PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 	prepared_statement.setString(1, "%" + filtre +  "%");
	 	prepared_statement.setString(2, "%" + filtre +  "%");
	 	prepared_statement.setString(3, "%" + filtre +  "%");
	 	prepared_statement.setString(4, "%" + filtre +  "%");
	 	prepared_statement.setString(5, "%" + filtre +  "%");
	 	
	 	int 	id;
	 	String 	nom;
	 	String 	prenom;
	 	String 	identifiant;
	 	String 	email;
	 	String 	telephone;
	 	String	adresse;
	 	String 	ville;
	 	String 	code_postal;
	 	String 	derniere_connexion;
	 	String 	date_ajout;
	     
	    ResultSet resultat = prepared_statement.executeQuery();
     
	    while(resultat.next()) {
	    	id					= resultat.getInt("jeune_id");
   		 	nom 		 		= resultat.getString("jeune_nom");
   		 	prenom 	 			= resultat.getString("jeune_prenom");
   		 	identifiant 		= resultat.getString("jeune_identifiant");
   		 	email 		 		= resultat.getString("jeune_email");
   		 	telephone 		 	= resultat.getString("jeune_telephone");
   		 	adresse				= resultat.getString("jeune_adresse");
   		 	ville				= resultat.getString("jeune_ville");
   		 	code_postal			= resultat.getString("jeune_code_postal");
   		 	derniere_connexion	= resultat.getString("jeune_derniere_connexion");
   		 	date_ajout	 		= resultat.getString("jeune_date_ajout");
		 
   		 	Jeune jeune = new Jeune();
   		 	
   		 	jeune.setJeune_id(id);
   		 	jeune.setJeune_nom(nom);
   		 	jeune.setJeune_prenom(prenom);
	   		jeune.setJeune_email(email);
	   		jeune.setJeune_telephone(telephone);
	   		jeune.setJeune_adresse(adresse);
	   		jeune.setJeune_ville(ville);
	   		jeune.setJeune_code_postal(code_postal);
	   		jeune.setJeune_derniere_connexion(derniere_connexion);
	   		jeune.setJeune_date_ajout(date_ajout);
	   		
	   		retour.add(jeune);
	    	System.out.println(jeune);
	    }
  
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
     	return retour;
     }
 
     public static ObservableList<Jeune> recherche() throws ClassNotFoundException, SQLException {
    	 Connection connexion = Connect.getInstance().getConnection();
    	 String requete = "SELECT jeune_id, jeune_nom, jeune_prenom, jeune_identifiant, jeune_email, jeune_telephone, jeune_adresse,"
    	 		+ "jeune_ville, jeune_code_postal, jeune_derniere_connexion, jeune_date_ajout FROM jeune";
    	 
    	 ObservableList<Jeune> retour = FXCollections.observableArrayList();
    	 
    	 int 	id;
    	 String nom;
    	 String prenom;
    	 String identifiant;
    	 String email;
    	 String telephone;
    	 String	adresse;
    	 String ville;
    	 String code_postal;
    	 String derniere_connexion;
    	 String date_ajout;
	 
    	 PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 
    	 ResultSet resultat = prepared_statement.executeQuery();
    	 
    	 while(resultat.next()) {
    		 id					= resultat.getInt("jeune_id");
    		 nom 					= resultat.getString("jeune_nom");
    		 prenom 				= resultat.getString("jeune_prenom");
    		 identifiant 			= resultat.getString("jeune_identifiant");
    		 email 					= resultat.getString("jeune_email");
    		 telephone 				= resultat.getString("jeune_telephone");
    		 adresse				= resultat.getString("jeune_adresse");
    		 ville					= resultat.getString("jeune_ville");
    		 code_postal			= resultat.getString("jeune_code_postal");
    		 derniere_connexion 	= resultat.getString("jeune_derniere_connexion");
    		 date_ajout 			= resultat.getString("jeune_date_ajout");
		 
    		 Jeune jeune = new Jeune();
    		 
    		 jeune.setJeune_id(id);
    		 jeune.setJeune_nom(nom);
    		 jeune.setJeune_prenom(prenom);
    		 jeune.setJeune_email(email);
    		 jeune.setJeune_telephone(telephone);
    		 jeune.setJeune_adresse(adresse);
    		 jeune.setJeune_ville(ville);
    		 jeune.setJeune_code_postal(code_postal);
    		 jeune.setJeune_derniere_connexion(derniere_connexion);
    		 jeune.setJeune_date_ajout(date_ajout);
		 
    		 retour.add(jeune);
    		 System.out.println(jeune);
    	 }
    	 
    	 resultat.close();
    	 prepared_statement.close();
	 	 connexion.close();
	 	 return retour;
     }
}
