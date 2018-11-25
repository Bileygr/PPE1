package models.base;

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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.dao.AdministrateurDAO;

public class Administrateur 
{
	private IntegerProperty administrateur_id;
	private StringProperty administrateur_super;
	private StringProperty administrateur_nom;
	private StringProperty administrateur_prenom;
	private StringProperty administrateur_mot_de_passe_hash;
	private StringProperty administrateur_email;
	private StringProperty administrateur_telephone;
	private StringProperty administrateur_adresse;
	private StringProperty administrateur_ville;
	private	StringProperty administrateur_code_postal;
	private StringProperty  administrateur_derniere_connexion;
	private StringProperty administrateur_creation;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public Administrateur()
	{
		this.administrateur_id = new SimpleIntegerProperty();
		this.administrateur_super = new SimpleStringProperty();
		this.administrateur_nom = new SimpleStringProperty();
		this.administrateur_prenom = new SimpleStringProperty();
		this.administrateur_mot_de_passe_hash = new SimpleStringProperty();
		this.administrateur_email = new SimpleStringProperty();
		this.administrateur_telephone = new SimpleStringProperty();
		this.administrateur_adresse = new SimpleStringProperty();
		this.administrateur_ville = new SimpleStringProperty();
		this.administrateur_code_postal = new SimpleStringProperty();
		this.administrateur_derniere_connexion = new SimpleStringProperty();
		this.administrateur_creation = new SimpleStringProperty();
	}
	
	public void setAdministrateur_id(IntegerProperty administrateur_id){this.administrateur_id = administrateur_id;}
	public void setAdministrateur_super(StringProperty administrateur_super) {this.administrateur_super = administrateur_super;}
	public void setAdministrateur_nom(StringProperty administrateur_nom){this.administrateur_nom = administrateur_nom;}
	public void setAdministrateur_prenom(StringProperty administrateur_prenom){this.administrateur_prenom = administrateur_prenom;}
	public void setAdministrateur_mot_de_passe_hash(StringProperty administrateur_mot_de_passe_hash){this.administrateur_mot_de_passe_hash = administrateur_mot_de_passe_hash;}
	public void setAdministrateur_email(StringProperty administrateur_email){this.administrateur_email = administrateur_email;}
	public void setAdministrateur_telephone(StringProperty administrateur_telephone){this.administrateur_telephone = administrateur_telephone;}
	public void setAdministrateur_adresse(StringProperty administrateur_adresse){this.administrateur_adresse = administrateur_adresse;}
	public void setAdministrateur_ville(StringProperty administrateur_ville){this.administrateur_ville = administrateur_ville;}
	public void setAdministrateur_code_postal(StringProperty administrateur_code_postal){this.administrateur_code_postal = administrateur_code_postal;}
	public void setAdministrateur_derniere_connexion(StringProperty administrateur_derniere_connexion){this.administrateur_derniere_connexion = administrateur_derniere_connexion;}
	public void setAdministrateur_creation(StringProperty administrateur_creation){this.administrateur_creation = administrateur_creation;}
	
	public IntegerProperty 	getAdministrateur_id_Prop(){return administrateur_id;}
	public StringProperty 	getAdministrateur_super_Prop(){return administrateur_super;}
	public StringProperty 	getAdministrateur_nom_Prop(){return administrateur_nom;}
	public StringProperty 	getAdministrateur_prenom_Prop(){return administrateur_prenom;}
	public StringProperty 	getAdministrateur_mot_de_passe_hash_Prop(){return administrateur_mot_de_passe_hash;}
	public StringProperty 	getAdministrateur_email_Prop(){return administrateur_email;}
	public StringProperty 	getAdministrateur_telephone_Prop(){return administrateur_telephone;}
	public StringProperty 	getAdministrateur_adresse_Prop(){return administrateur_adresse;}
	public StringProperty 	getAdministrateur_ville_Prop(){return administrateur_ville;}
	public StringProperty 	getAdministrateur_code_postal_Prop(){return administrateur_code_postal;}
	public StringProperty 	getAdministrateur_derniere_connexion_Prop(){return administrateur_derniere_connexion;}
	public StringProperty 	getAdministrateur_creation_Prop(){return administrateur_creation;}
	
	public int getAdministrateur_id(){return administrateur_id.get();}
		public void setAdministrateur_id(int administrateur_id){this.administrateur_id.set(administrateur_id);}
	public String getAdministrateur_super(){return administrateur_super.get();}
		public void setAdministrateur_super(String administrateur_super){this.administrateur_super.set(administrateur_super);}
	public String getAdministrateur_nom(){return administrateur_nom.get();}
		public void setAdministrateur_nom(String administrateur_nom){this.administrateur_nom.set(administrateur_nom);}
	public String getAdministrateur_prenom(){return administrateur_prenom.get();}
		public void setAdministrateur_prenom(String administrateur_prenom){this.administrateur_prenom.set(administrateur_prenom);}
	public String getAdministrateur_mot_de_passe_hash(){return administrateur_mot_de_passe_hash.get();}
		public void setAdministrateur_mot_de_passe_hash(String administrateur_mot_de_passe_hash){this.administrateur_mot_de_passe_hash.set(administrateur_mot_de_passe_hash);}
	public String getAdministrateur_email(){return administrateur_email.get();}
		public void setAdministrateur_email(String administrateur_email){this.administrateur_email.set(administrateur_email);}	
	public String getAdministrateur_telephone(){return administrateur_telephone.get();}
		public void setAdministrateur_telephone(String administrateur_telephone){this.administrateur_telephone.set(administrateur_telephone);}
	public String getAdministrateur_adresse(){return administrateur_adresse.get();}
		public void setAdministrateur_adresse(String administrateur_adresse){this.administrateur_adresse.set(administrateur_adresse);}
	public String getAdministrateur_ville(){return administrateur_ville.get();}
		public void setAdministrateur_ville(String administrateur_ville){this.administrateur_ville.set(administrateur_ville);}
	public String getAdministrateur_code_postal(){return administrateur_code_postal.get();}
		public void setAdministrateur_code_postal(String administrateur_code_postal){this.administrateur_code_postal.set(administrateur_code_postal);}
	public String getAdministrateur_derniere_connexion(){return administrateur_derniere_connexion.get();}
		public void setAdministrateur_derniere_connexion(String administrateur_derniere_connexion){this.administrateur_derniere_connexion.set(administrateur_derniere_connexion);}
	public String getAdministrateur_creation(){return administrateur_creation.get();}
		public void setAdministrateur_creation(String administrateur_creation){this.administrateur_creation.set(administrateur_creation);}
		
	public static boolean verifier_la_syntaxe_de_l_email(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
		return matcher.find();
	}
		
	public static boolean envoi_d_email(String destinataire) {
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
	        msg.setSubject("Connexion � l'application PPE1: Gestion utilisateurs");
	        msg.setText("Vous vous �tes connect� � l'application. \n" + format.format(date));
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
		
	public static boolean email_d_inscription(String destinataire) {
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
	            msg.setText("Vous �tes maintenant inscrit. \n" + format.format(date));
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
		
	public static boolean email_de_modification(String destinataire) {
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
	        msg.setText("Vos informations ont �t� modifi�. \n" + format.format(date));
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

	@Override
	public String toString() {
		return "Administrateur [administrateur_id=" + administrateur_id + ", administrateur_super=" 
				+ administrateur_super + ", administrateur_nom="
				+ administrateur_nom + ", administrateur_prenom=" + administrateur_prenom
				+ ", administrateur_identifiant=" + ", administrateur_mot_de_passe="
				+ administrateur_mot_de_passe_hash + ", administrateur_email=" + administrateur_email
				+ ", administrateur_telephone=" + administrateur_telephone + ", administrateur_adresse="
				+ administrateur_adresse + ", administrateur_ville=" + administrateur_ville
				+ ", administrateur_code_postal=" + administrateur_code_postal
				+ ", administrateur_derniere_connexion=" + administrateur_derniere_connexion
				+ ", administrateur_creation=" + administrateur_creation + "]";
		}
}