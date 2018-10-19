package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import classe.Jeune;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JeuneDAO {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate_email(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
		return matcher.find();
	}
	
	public static boolean email_inscription(String destinataire) {
		boolean resultat = false;
		try {
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			 
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp" );
            props.put("mail.smtp.starttls.enable","true" );
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.auth", "true" );
            props.put("mail.smtp.port", "587" );
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session session = Session.getDefaultInstance(props, null);
            
            
            Transport transport = session.getTransport("smtp");
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("btssioppechesirkei@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinataire));
            msg.setSubject("Offres (site web)");
            
            msg.setText("Vous êtes maintenant inscrit. \n" + format.format(date));
            transport.connect("smtp.gmail.com", "btssioppechesirkei@gmail.com","Jh6@hV^4AW5y34aZ");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            resultat = true;
            System.out.println("Email envoyé.");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
		return resultat;
	} 
	
	public static boolean email_modification(String destinataire) {
		boolean resultat = false;
		try {
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			 
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp" );
            props.put("mail.smtp.starttls.enable","true" );
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.auth", "true" );
            props.put("mail.smtp.port", "587" );
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session session = Session.getDefaultInstance(props, null);
            
            
            Transport transport = session.getTransport("smtp");
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("btssioppechesirkei@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinataire));
            msg.setSubject("Offres (site web)");
            
            msg.setText("Vos informations ont été modifié. \n" + format.format(date));
            transport.connect("smtp.gmail.com", "btssioppechesirkei@gmail.com","Jh6@hV^4AW5y34aZ");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            resultat = true;
            System.out.println("Email envoyé.");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return resultat;
	} 
	
	public static boolean inscrire(String nom, String prenom, String hash, String email, String telephone, String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "INSERT INTO jeune(jeune_nom, jeune_prenom, jeune_mot_de_passe_hash, jeune_email, jeune_telephone, jeune_adresse, jeune_ville, jeune_code_postal, "
							+ "jeune_derniere_connexion, jeune_creation) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
		boolean retour = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, nom);
		prepared_statement.setString(2, prenom);
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
	
	public static boolean modifier(int id, String nom, String prenom, String email, String telephone, String adresse, String ville, String code_postal) throws SQLException, ClassNotFoundException {
        Connection connexion =  Connect.getInstance().getConnection();
        String requete = "UPDATE jeune SET jeune_nom = ?, jeune_prenom = ?, jeune_email = ?, jeune_telephone = ?, jeune_adresse = ?, jeune_ville = ?, jeune_code_postal = ? WHERE jeune_id = ?";
        
        boolean retour = false;
        
        PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requete);
        prepared_statement.setString(1, nom);
        prepared_statement.setString(2, prenom);
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
    	String requete = "SELECT jeune_id, jeune_nom, jeune_prenom, jeune_email, jeune_telephone, jeune_adresse,"
    			+ "jeune_ville, jeune_code_postal, jeune_derniere_connexion, jeune_creation FROM jeune "
	 					+ "WHERE jeune_nom LIKE ?"
	 					+ "OR jeune_prenom LIKE ?"
	 					+ "OR jeune_email LIKE ?"
	 					+ "OR jeune_telephone LIKE ?";
    	
    	ObservableList<Jeune> retour = FXCollections.observableArrayList();
    	
    	PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 	prepared_statement.setString(1, "%" + filtre +  "%");
	 	prepared_statement.setString(2, "%" + filtre +  "%");
	 	prepared_statement.setString(3, "%" + filtre +  "%");
	 	prepared_statement.setString(4, "%" + filtre +  "%");
	 	
	 	int 	id;
	 	String 	nom;
	 	String 	prenom;
	 	String 	email;
	 	String 	telephone;
	 	String	adresse;
	 	String 	ville;
	 	String 	code_postal;
	 	String 	derniere_connexion;
	 	String 	creation;
	     
	    ResultSet resultat = prepared_statement.executeQuery();
     
	    while(resultat.next()) {
	    	id					= resultat.getInt("jeune_id");
   		 	nom 		 		= resultat.getString("jeune_nom");
   		 	prenom 	 			= resultat.getString("jeune_prenom");
   		 	email 		 		= resultat.getString("jeune_email");
   		 	telephone 		 	= resultat.getString("jeune_telephone");
   		 	adresse				= resultat.getString("jeune_adresse");
   		 	ville				= resultat.getString("jeune_ville");
   		 	code_postal			= resultat.getString("jeune_code_postal");
   		 	derniere_connexion	= resultat.getString("jeune_derniere_connexion");
   		 	creation	 		= resultat.getString("jeune_creation");
		 
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
	   		jeune.setJeune_creation(creation);
	   		
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
    	 String requete = "SELECT jeune_id, jeune_nom, jeune_prenom, jeune_email, jeune_telephone, jeune_adresse,"
    	 		+ "jeune_ville, jeune_code_postal, jeune_derniere_connexion, jeune_creation FROM jeune";
    	 
    	 ObservableList<Jeune> retour = FXCollections.observableArrayList();
    	 
    	 int 	id;
    	 String nom;
    	 String prenom;
    	 String email;
    	 String telephone;
    	 String	adresse;
    	 String ville;
    	 String code_postal;
    	 String derniere_connexion;
    	 String creation;
	 
    	 PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 
    	 ResultSet resultat = prepared_statement.executeQuery();
    	 
    	 while(resultat.next()) {
    		 id					= resultat.getInt("jeune_id");
    		 nom 					= resultat.getString("jeune_nom");
    		 prenom 				= resultat.getString("jeune_prenom");
    		 email 					= resultat.getString("jeune_email");
    		 telephone 				= resultat.getString("jeune_telephone");
    		 adresse				= resultat.getString("jeune_adresse");
    		 ville					= resultat.getString("jeune_ville");
    		 code_postal			= resultat.getString("jeune_code_postal");
    		 derniere_connexion 	= resultat.getString("jeune_derniere_connexion");
    		 creation 				= resultat.getString("jeune_creation");
		 
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
    		 jeune.setJeune_creation(creation);
		 
    		 retour.add(jeune);
    		 System.out.println(jeune);
    	 }
    	 
    	 resultat.close();
    	 prepared_statement.close();
	 	 connexion.close();
	 	 return retour;
     }
}
