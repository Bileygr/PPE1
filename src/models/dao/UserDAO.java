package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.base.User;

public class UserDAO {
	public static String obtenir_le_hache_correspondant_a_l_email_de_connexion(String email) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT password FROM user WHERE email = ?";
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		ResultSet resultat = prepared_statement.executeQuery();
		
		String motDePasse = "";
		
		if(resultat.next()) {
			motDePasse = resultat.getString("password");
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return motDePasse;
	}
	
	public static String obtenir_le_role(String email) throws SQLException 
	{
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT roles FROM user WHERE email = ?";
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		ResultSet resultat = prepared_statement.executeQuery();
		
		String  roles = "";
		
		if(resultat.next())
		{
			roles = resultat.getString("roles");
		}
		
		return roles;
	}
	
	public static String obtenir_le_nom_de_la_personne_connecte(String email, String hash) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT nom, prenom FROM user WHERE email = ? AND password = ?";
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		prepared_statement.setString(2, hash);
		ResultSet resultat = prepared_statement.executeQuery();
		
		String nom = "";
		
		if(resultat.next()) {
			nom = (resultat.getString("prenom") + " " +resultat.getString("nom"));
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return nom;
	}
	
	public static boolean supprimer(int  id) throws SQLException, ClassNotFoundException {
        Connection connexion = Connect.getInstance().getConnection();
        String requeteSQL = "DELETE FROM user WHERE id = ?";
        
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
	
	public static boolean inscrire(User user) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "INSERT INTO user(siret, roles, nom, prenom, username, password, email, telephone, adresse, ville, codepostal, dateajout) "
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
		
		//String [] roles = user.getRoles();
		String role = user.getRoles();
		boolean retour = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, Integer.toString(user.getSiret()));
		prepared_statement.setString(2, role);
		prepared_statement.setString(3, user.getNom());
		prepared_statement.setString(4, user.getPrenom());
		prepared_statement.setString(5, user.getUsername());
		prepared_statement.setString(6, user.getPassword());
		prepared_statement.setString(7, user.getEmail());
		prepared_statement.setString(8, user.getTelephone());
		prepared_statement.setString(9, user.getAdresse());
		prepared_statement.setString(10, user.getVille());
		prepared_statement.setString(11, user.getCodepostal());
		
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
	
	public static boolean modifier(User user) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "UPDATE user SET siret = ?, roles = ?, nom = ?, prenom = ?, username = ?, email = ?, telephone = ?, adresse = ?, ville = ?, codepostal = ?"
						+ "WHERE id = ?";
		
		//String [] roles = user.getRoles();
		String role = user.getRoles();
		boolean retour = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, Integer.toString(user.getSiret()));
		prepared_statement.setString(2, role);
		prepared_statement.setString(3, user.getNom());
		prepared_statement.setString(4, user.getPrenom());
		prepared_statement.setString(5, user.getUsername());
		prepared_statement.setString(6, user.getEmail());
		prepared_statement.setString(7, user.getTelephone());
		prepared_statement.setString(8, user.getAdresse());
		prepared_statement.setString(9, user.getVille());
		prepared_statement.setString(10, user.getCodepostal());
		prepared_statement.setInt(11, user.getId());
		
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
	
	public static ObservableList<User> obtenir_la_liste_de_tout_les_utilisateur_d_un_certain_role(String [] roles) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT id, roles, siret, nom, prenom, username, email, telephone, adresse, ville, codepostal, dateajout FROM user WHERE roles LIKE ?";
		
		ObservableList<User> users = FXCollections.observableArrayList();
   	 
   	 	int id;
   	 	String role;
   	 	String siret;
   	 	String nom;
   	 	String prenom;
   	 	String email;
   	 	String telephone;
   	 	String adresse;
   	 	String ville;
   	 	String code_postal;
   	 	String creation;
	 
   	 	PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 	prepared_statement.setString(1, "%" + roles[0] +  "%");
	 
   	 	ResultSet resultat = prepared_statement.executeQuery();
   	 
   	 	while(resultat.next()) 
   	 	{
   	 		id = resultat.getInt("id");
   	 		role = resultat.getString("roles");
   	 		siret = resultat.getString("siret");
   	 		nom = resultat.getString("nom");
   	 		prenom = resultat.getString("prenom");
   	 		email = resultat.getString("email");
   	 		telephone = resultat.getString("telephone");
   	 		adresse = resultat.getString("adresse");
   	 		ville = resultat.getString("ville");
   	 		code_postal = resultat.getString("codepostal");
   	 		creation = resultat.getString("dateajout");
		 
   	 		User user = new User();
   		 
   	 		user.setId(id);
   	 		user.setRoles(role);
   	 		if(siret != null) {
   	 			user.setSiret(Integer.parseInt(siret));
   	 		}
   	 		user.setNom(nom);
   	 		user.setPrenom(prenom);
   	 		user.setUsername();
   	 		user.setEmail(email);
   			user.setTelephone(telephone);
   			user.setAdresse(adresse);
   			user.setVille(ville);
   			user.setCodepostal(code_postal);
   			user.setDateajout(creation);
		 
   	 		users.add(user);
   	 	}
   	 
   	 	resultat.close();
   	 	prepared_statement.close();
	 	connexion.close();
	 	return users;
	}
	
	public static ObservableList<User> obtenir_la_liste_filtre(String filtre, String [] roles) throws ClassNotFoundException, SQLException {     
    	Connection connexion = Connect.getInstance().getConnection();
    	String requete = "SELECT id, siret, roles, nom, prenom, email, telephone, adresse, ville, codepostal, dateajout FROM user "
	 					+ "WHERE roles LIKE ?"
	 					+ "AND siret LIKE  ?"
	 					+ "OR nom LIKE ?"
	 					+ "OR prenom LIKE ?"
	 					+ "OR username LIKE ?"
	 					+ "OR email LIKE ?"
	 					+ "OR telephone LIKE ?";
    	
    	ObservableList<User> users = FXCollections.observableArrayList();
    	
    	PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 	prepared_statement.setString(1, "%" + roles[0] +  "%");
	 	prepared_statement.setString(2, "%" + filtre +  "%");
	 	prepared_statement.setString(3, "%" + filtre +  "%");
	 	prepared_statement.setString(4, "%" + filtre +  "%");
	 	prepared_statement.setString(5, "%" + filtre +  "%");
	 	prepared_statement.setString(6, "%" + filtre +  "%");
	 	prepared_statement.setString(7, "%" + filtre +  "%");
	 	
	 	int id;
	 	String role;
	 	String siret;
	 	String nom;
	 	String prenom;
	 	String email;
	 	String telephone;
	 	String adresse;
	 	String ville;
	 	String code_postal;
	 	String creation;
	     
	    ResultSet resultat = prepared_statement.executeQuery();
     
	    while(resultat.next()) {
	    	id = resultat.getInt("id");
	    	role = resultat.getString("roles");
	    	siret = resultat.getString("siret");
	    	role = resultat.getString("roles");
   		 	nom = resultat.getString("nom");
   		 	prenom = resultat.getString("prenom");
   		 	email = resultat.getString("email");
   		 	telephone = resultat.getString("telephone");
   		 	adresse = resultat.getString("adresse");
   		 	ville = resultat.getString("ville");
   		 	code_postal	= resultat.getString("codepostal");
   		 	creation = resultat.getString("dateajout");
		 
   		 	User user = new User();
   		 	
   		 	user.setId(id);
   		 	user.setRoles(role);
   		 	if(siret != null) {
	 			user.setSiret(Integer.parseInt(siret));
	 		}
   		 	user.setNom(nom);
   		 	user.setPrenom(prenom);
   		 	user.setUsername();
   		 	user.setEmail(email);
   		 	user.setTelephone(telephone);
   		 	user.setAdresse(adresse);
   		 	user.setVille(ville);
   		 	user.setCodepostal(code_postal);
   		 	user.setDateajout(creation);
	   		
	   		users.add(user);
	    }
  
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
     	return users;
     }
}