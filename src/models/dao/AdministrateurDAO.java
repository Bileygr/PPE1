package models.dao;

import models.base.Administrateur;

import java.security.NoSuchAlgorithmException;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdministrateurDAO {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
		return matcher.find();
	}
	
	public static boolean email(String destinataire) {
		boolean result = false;
		try {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
            msg.setSubject("Connexion à l'application PPE1: Gestion utilisateurs");
            msg.setText("Vous vous êtes connecté à l'application. \n" + format.format(date));
            transport.connect("smtp.gmail.com", "btssioppechesirkei@gmail.com","Jh6@hV^4AW5y34aZ");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            result = true;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return result;
	} 
	
	public static boolean email_inscription(String destinataire) {
		boolean result = false;
		
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
            result = true;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return result;
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
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return resultat;
	} 
		
	public static String hash(String email) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_mot_de_passe_hash FROM administrateur WHERE administrateur_email = ?";
		String retour = "";
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			retour = resultat.getString("administrateur_mot_de_passe_hash");
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static boolean connexion_update(String email, String hash) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "UPDATE administrateur SET administrateur_derniere_connexion =  NOW() "
				+ "WHERE administrateur_email = ? AND administrateur_mot_de_passe_hash = ?";
		boolean retour = false;
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
        	retour = true;
        } else {
        	retour = false;
        	}
        
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static boolean connexion_super_administrateur(String email, String hash) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT * FROM administrateur WHERE administrateur_email = ? AND administrateur_mot_de_passe_hash = ? AND administrateur_super = 1";
		boolean retour = false;
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		prepared_statement.setString(2, hash);
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			retour = true;
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static String nom(String identifiant, String hash) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_nom, administrateur_prenom FROM administrateur WHERE administrateur_email = ? AND administrateur_mot_de_passe_hash = ?";
		String retour = "";
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, identifiant);
		prepared_statement.setString(2, hash);
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			retour = (resultat.getString("administrateur_prenom") + " " +resultat.getString("administrateur_nom"));
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static boolean inscrire(int administrateur_super, String nom, String prenom, String hash, String email, String telephone,
			String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "INSERT INTO administrateur(administrateur_super, administrateur_nom, administrateur_prenom, administrateur_mot_de_passe_hash, "
				+ "administrateur_email, administrateur_telephone, administrateur_adresse, administrateur_ville, administrateur_code_postal,"
				+ "administrateur_derniere_connexion, administrateur_creation) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		boolean retour = false;
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
        	retour = true;
        } else { 
        	retour = false;
        	}
        
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static boolean modifier(int id, int administrateur_super, String nom, String prenom, String email, String telephone,
			String adresse, String ville, String code_postal) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "UPDATE administrateur SET administrateur_super = ?, administrateur_nom = ?, administrateur_prenom = ?, administrateur_email = ?, "
				+ "administrateur_telephone = ?, administrateur_adresse = ?, administrateur_ville = ?, administrateur_code_postal = ? WHERE administrateur_id = ?";
		boolean retour = false;
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
	
	public static ObservableList<Administrateur> recherche() throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_id, administrateur_super, administrateur_nom, administrateur_prenom, administrateur_email, administrateur_telephone,"
				+ "administrateur_adresse, administrateur_ville, administrateur_code_postal, administrateur_derniere_connexion, administrateur_creation FROM administrateur";
		ObservableList<Administrateur> retour = FXCollections.observableArrayList();
		int 	id;
		int 	administrateur_super;
		String 	nom;
		String 	prenom;
		String 	email;
		String	telephone;
		String 	adresse;
		String	ville;
		String code_postal;
		String  derniere_connexion;
		String	creation;
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		ResultSet resultat = prepared_statement.executeQuery();
		
		while(resultat.next()) {
			id						= resultat.getInt("administrateur_id");
			administrateur_super	= resultat.getInt("administrateur_super");
			nom 		 			= resultat.getString("administrateur_nom");
			prenom 	 				= resultat.getString("administrateur_prenom");
			email		 			= resultat.getString("administrateur_email");
			telephone 		 		= resultat.getString("administrateur_telephone");
			adresse 		 		= resultat.getString("administrateur_adresse");
			ville 		 			= resultat.getString("administrateur_ville");
			code_postal 		 	= resultat.getString("administrateur_code_postal");
			derniere_connexion		= resultat.getString("administrateur_derniere_connexion");
			creation				= resultat.getString("administrateur_creation");
			
			Administrateur instance = new Administrateur();
			
			instance.setAdministrateur_id(id);
			instance.setAdministrateur_super(administrateur_super);
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
		ObservableList<Administrateur> retour  = FXCollections.observableArrayList();
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, "%" + filtre +  "%");
		prepared_statement.setString(2, "%" + filtre +  "%");
		prepared_statement.setString(3, "%" + filtre +  "%");
		prepared_statement.setString(4, "%" + filtre +  "%");
		prepared_statement.setString(5, "%" + filtre +  "%");
		prepared_statement.setString(6, "%" + filtre +  "%");
		int 	id;
		int 	administrateur_super;
		String 	nom;
		String 	prenom;
		String 	email;
		String	telephone;
		String 	adresse;
		String	ville;
		String	code_postal;
		String	derniere_connexion;
		String 	creation;
		ResultSet resultat = prepared_statement.executeQuery();
    
		while(resultat.next()) {
			id						= resultat.getInt("administrateur_id");
			administrateur_super	= resultat.getInt("administrateur_super");
			nom 		 			= resultat.getString("administrateur_nom");
			prenom 	 				= resultat.getString("administrateur_prenom");
			email		 			= resultat.getString("administrateur_email");
			telephone 		 		= resultat.getString("administrateur_telephone");
			adresse 		 		= resultat.getString("administrateur_adresse");
			ville 		 			= resultat.getString("administrateur_ville");
			code_postal 		 	= resultat.getString("administrateur_code_postal");
			derniere_connexion		= resultat.getString("administrateur_derniere_connexion");
			creation				= resultat.getString("administrateur_creation");
			
			Administrateur instance = new Administrateur();
			
			instance.setAdministrateur_id(id);
			instance.setAdministrateur_super(administrateur_super);
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
}