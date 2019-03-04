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
import models.dao.UserDAO;

public class User 
{
	private IntegerProperty id;
	private StringProperty roles;
	private StringProperty username;
	private StringProperty nom;
	private StringProperty prenom;
	private IntegerProperty siret;
	private StringProperty password;
	private StringProperty telephone;
	private StringProperty email;
	private StringProperty adresse;
	private StringProperty ville;
	private	StringProperty codepostal;
	private StringProperty dateajout;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public User()
	{
		this.id = new SimpleIntegerProperty();
		this.roles = new SimpleStringProperty();
		this.username = new SimpleStringProperty();
		this.nom = new SimpleStringProperty();
		this.prenom = new SimpleStringProperty();
		this.siret = new SimpleIntegerProperty();
		this.password = new SimpleStringProperty();
		this.telephone = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.adresse = new SimpleStringProperty();
		this.ville = new SimpleStringProperty();
		this.codepostal = new SimpleStringProperty();
		this.dateajout = new SimpleStringProperty();
	}
	
	public void setIdProp(IntegerProperty administrateur_id){this.id = administrateur_id;}
	public void setRolesProp(StringProperty roles){this.roles = roles;}
	public void setUsernameProp(StringProperty administrateur_super) {this.username = administrateur_super;}
	public void setNomProp(StringProperty nom){this.nom = nom;}
	public void setPrenomProp(StringProperty prenom){this.prenom = prenom;}
	public void setSiretProp(IntegerProperty siret){this.siret = siret;}
	public void setPasswordProp(StringProperty password){this.password = password;}
	public void setEmailProp(StringProperty email){this.email = email;}
	public void setTelephoneProp(StringProperty telephone){this.telephone = telephone;}
	public void setadresseProp(StringProperty adresse){this.adresse = adresse;}
	public void setvilleProp(StringProperty ville){this.ville = ville;}
	public void setcodepostalProp(StringProperty codepostal){this.codepostal = codepostal;}
	public void setDateajoutProp(StringProperty dateajout){this.dateajout = dateajout;}
	
	public IntegerProperty getIdProp(){return this.id;}
	public StringProperty getRolesProp(){return this.roles;}
	public StringProperty getUsernameProp(){return username;}
	public StringProperty getPrenomProp(){return prenom;}
	public StringProperty getNomProp(){return nom;}
	public IntegerProperty getSiretProp(){return siret;}
	public StringProperty getPasswordProp(){return password;}
	public StringProperty getEmailProp(){return email;}
	public StringProperty getTelephoneProp(){return telephone;}
	public StringProperty getAdresseProp(){return adresse;}
	public StringProperty getVilleProp(){return ville;}
	public StringProperty getCodepostalProp(){return codepostal;}
	public StringProperty getDateajoutProp(){return dateajout;}
	
	public int getId(){return id.get();}
		public void setId(int id){this.id.set(id);}
	public String getUsername(){return username.get();}
		public void setUsername()
		{
			String prenom = this.prenom.get();
			
			if(prenom != null)
	        {
				String premiereLettreDuPrenom = prenom.substring(0, 1);
				String premiereLettreDuPrenomEnMinuscule = premiereLettreDuPrenom.toLowerCase();
				String nomDeFamille = this.nom.get();
				String nomDeFamilleEnMinuscule = nomDeFamille.toLowerCase();
				this.username.setValue(premiereLettreDuPrenomEnMinuscule+nomDeFamilleEnMinuscule);
	        }else{
	        	String nom = this.nom.get();
	            this.username.setValue(nom.toLowerCase());
	        }
		}
	public String getNom(){return nom.get();}
		public void setNom(String nom){this.nom.set(nom);}
	public String getPrenom(){return prenom.get();}
		public void setPrenom(String prenom){this.prenom.set(prenom);}
	public int getSiret(){return siret.get();}
		public void setSiret(int siret){this.siret.set(siret);}
	public String getRoles(){return this.roles.get();}
		public void setRoles(String roles){this.roles.set(roles);}
	public String getPassword(){return password.get();}
		public void setPassword(String password){this.password.set(password);}
	public String getEmail(){return email.get();}
		public void setEmail(String email){this.email.set(email);}	
	public String getTelephone(){return telephone.get();}
		public void setTelephone(String telephone){this.telephone.set(telephone);}
	public String getAdresse(){return adresse.get();}
		public void setAdresse(String adresse){this.adresse.set(adresse);}
	public String getVille(){return ville.get();}
		public void setVille(String ville){this.ville.set(ville);}
	public String getCodepostal(){return codepostal.get();}
		public void setCodepostal(String codepostal){this.codepostal.set(codepostal);}
	public String getDateajout(){return dateajout.get();}
		public void setDateajout(String dateajout){this.dateajout.set(dateajout);}
		
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
	        msg.setSubject("Connexion à l'application PPE1: Gestion utilisateurs");
	        msg.setText("Vous vous êtes connecté à l'application. \n" + format.format(date));
	        transport.connect("smtp.gmail.com", "btssioppechesirkei@gmail.com","Jh6@hV^4AW5y34aZ");
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();
	        result = true;
	        } catch (NoSuchProviderException ex) {
	        	Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (AddressException ex) {
	        	Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (MessagingException ex) {
	        	Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
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
	            msg.setText("Vous êtes maintenant inscrit. \n" + format.format(date));
	            transport.connect("smtp.gmail.com", "btssioppechesirkei@gmail.com","Jh6@hV^4AW5y34aZ");
	            transport.sendMessage(msg, msg.getAllRecipients());
	            transport.close();
	            result = true;
	            } catch (NoSuchProviderException ex) {
	            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (AddressException ex) {
	        	Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (MessagingException ex) {
	            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
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
	        msg.setText("Vos informations ont été modifié. \n" + format.format(date));
	        transport.connect("smtp.gmail.com", "btssioppechesirkei@gmail.com","Jh6@hV^4AW5y34aZ");
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();
	        resultat = true;
	        } catch (NoSuchProviderException ex) {
	        	Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (AddressException ex) {
	            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (MessagingException ex) {
	            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	        }
			
			return resultat;
	}
}