package dao;

import classe.Administrateur;

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
	
	public static void email(String destinataire) {
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
            msg.setSubject("Connexion a l'application PPE1: Gestion utilisateurs");
            
            msg.setText("Vous vous etes connecte a l'application. \n" + format.format(date));
            transport.connect("smtp.gmail.com", "cheiksiramakankeita@gmail.com","crownclown91");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println(" Message envoyé");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(AdministrateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
	} 
		
	public static boolean connexion(String email, String mot_de_passe) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT * FROM administrateur WHERE administrateur_email = ? AND administrateur_mot_de_passe = ?";
		
		boolean retour = false;
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		prepared_statement.setString(2, mot_de_passe);
		
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			retour = true;
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static boolean connexion_super_administrateur(String email, String mot_de_passe) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT * FROM administrateur WHERE administrateur_email = ? AND administrateur_mot_de_passe = ? AND administrateur_principale = TRUE";
		
		boolean retour = false;
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		prepared_statement.setString(2, mot_de_passe);
		
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			retour = true;
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static String nom(String identifiant, String mot_de_passe) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_nom, administrateur_prenom FROM administrateur WHERE administrateur_email = ? AND administrateur_mot_de_passe = ?";
		
		String retour = "";
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, identifiant);
		prepared_statement.setString(2, mot_de_passe);
		
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			retour = (resultat.getString("administrateur_prenom") + " " +resultat.getString("administrateur_nom"));
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static ObservableList<Administrateur> recherche() throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_nom, administrateur_prenom FROM administrateur WHERE administrateur_identifiant = ? AND administrateur_mot_de_passe = ?";
		
		ObservableList<Administrateur> retour = FXCollections.observableArrayList();
		
		int 	id;
		String 	nom;
		String 	prenom;
		String 	identifiant;
		String 	email;
		String	telephone;
		String  derniere_connexion;
		String	date_ajout;
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		ResultSet resultat = prepared_statement.executeQuery();
		
		while(resultat.next()) {
			id 		 			= resultat.getInt("administrateur_id");
			nom 		 		= resultat.getString("administrateur_nom");
			prenom 	 			= resultat.getString("administrateur_prenom");
			identifiant 		= resultat.getString("administrateur_identifiant");
			email		 		= resultat.getString("administrateur_email");
			telephone 		 	= resultat.getString("administrateur_telephone");
			derniere_connexion	= resultat.getString("administrateur_derniere_connexion");
			date_ajout			= resultat.getString("administrateur_date_ajout");
			
			Administrateur instance = new Administrateur();
			
			instance.setAdministrateur_id(id);
			instance.setAdministrateur_nom(nom);
			instance.setAdministrateur_prenom(prenom);
			instance.setAdministrateur_identifiant(identifiant);
			instance.setAdministrateur_email(email);
			instance.setAdministrateur_telephone(telephone);
			instance.setAdministrateur_derniere_connexion(derniere_connexion);
			instance.setAdministrateur_date_ajout(date_ajout);
   		 	
			retour.add(instance);
			System.out.println(instance);
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
	
	public static ObservableList<Administrateur> recherche_filtre(String filtre) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT administrateur_id, administrateur_identifiant, administrateur_nom, administrateur_prenom, "
							+ "administrateur_email, administrateur_telephone, administrateur_derniere_connexion, administrateur_date_ajout "
							+ "FROM administrateur WHERE administrateur_id LIKE ?"
							+ "OR administrateur_identifiant LIKE ?"
							+ "OR administrateur_nom LIKE ?"
							+ "OR administrateur_prenom LIKE ?"
							+ "OR administrateur_telephone LIKE ?"
							+ "OR administrateur_email LIKE ?"
							+ "OR administrateur_derniere_connexion LIKE ?"
							+ "OR administrateur_date_ajout LIKE ?";
		
		ObservableList<Administrateur> retour  = FXCollections.observableArrayList();
		
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
		prepared_statement.setString(5, "%" + filtre +  "%");
		prepared_statement.setString(6, "%" + filtre +  "%");
		prepared_statement.setString(7, "%" + filtre +  "%");
		prepared_statement.setString(8, "%" + filtre +  "%");
		
		int 	id;
		String 	nom;
		String 	prenom;
		String 	identifiant;
		String 	email;
		String	telephone;
		String	derniere_connexion;
		String 	date_ajout;
    
		ResultSet resultat = prepared_statement.executeQuery();
    
		while(resultat.next()) {
			id 		 			 = resultat.getInt("administrateur_id");
			nom 		 		 = resultat.getString("administrateur_nom");
			prenom 	 			 = resultat.getString("administrateur_prenom"); 
			identifiant 		 = resultat.getString("administrateur_identifiant");
			telephone 		  	 = resultat.getString("administrateur_telephone");
			email		 		 = resultat.getString("administrateur_email");
			derniere_connexion	 = resultat.getString("administrateur_derniere_connexion");
			date_ajout	 		 = resultat.getString("administrateur_date_ajout");
			
			Administrateur instance = new Administrateur();
			
			instance.setAdministrateur_id(id);
			instance.setAdministrateur_nom(nom);
			instance.setAdministrateur_prenom(prenom);
			instance.setAdministrateur_identifiant(identifiant);
			instance.setAdministrateur_email(email);
			instance.setAdministrateur_telephone(telephone);
			instance.setAdministrateur_derniere_connexion(derniere_connexion);
			instance.setAdministrateur_date_ajout(date_ajout);
   		 	
			retour.add(instance);
			System.out.println(instance);
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return retour;
	}
}