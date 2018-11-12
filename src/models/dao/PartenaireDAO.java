package models.dao;

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

import classe.Partenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartenaireDAO {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String email) {
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
            msg.setFrom(new InternetAddress("cheiksiramakankeita@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinataire));
            msg.setSubject("Offres (site web)");
            
            msg.setText("Vous êtes maintenant inscrit. \n" + format.format(date));
            transport.connect("smtp.gmail.com", "cheiksiramakankeita@gmail.com","crownclown91");
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
            msg.setFrom(new InternetAddress("cheiksiramakankeita@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinataire));
            msg.setSubject("Offres (site web)");
            
            msg.setText("Vos informations ont été modifié. \n" + format.format(date));
            transport.connect("smtp.gmail.com", "cheiksiramakankeita@gmail.com","crownclown91");
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
	
	public static boolean inscrire(int siret, String nom, String hash, String email, String telephone, String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "INSERT INTO partenaire(partenaire_siret, partenaire_nom, partenaire_mot_de_passe_hash, partenaire_email, "
							+ "partenaire_telephone, partenaire_adresse, partenaire_ville, partenaire_code_postal, "
							+ "partenaire_derniere_connexion, partenaire_creation) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
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
				+ "partenaire_adresse, partenaire_ville, partenaire_code_postal, partenaire_derniere_connexion, partenaire_creation FROM partenaire";
		
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
			creation			= resultat.getString("partenaire_creation");
			
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
			partenaire.setPartenaire_creation(creation);
			
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
						+ "partenaire_derniere_connexion, partenaire_creation FROM partenaire WHERE  partenaire_siret LIKE ? ESCAPE '!' "
																						   	     + "OR partenaire_nom   LIKE ? ESCAPE '!' "
																						   	     + "OR partenaire_email   LIKE ? ESCAPE '!'"
																						   	     + "OR partenaire_telephone LIKE ? ESCAPE '!'";
	 	
		ObservableList<Partenaire> retour = FXCollections.observableArrayList();
		
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
			creation 			= resultat.getString("partenaire_creation");
			
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
			partenaire.setPartenaire_creation(creation);
    	 
	    	retour.add(partenaire);
	    	System.out.println(partenaire);
	    }
	    
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
     	return retour;
	}
}
