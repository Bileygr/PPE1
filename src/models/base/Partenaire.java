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

public class Partenaire 
{
	private IntegerProperty partenaire_id;
	private IntegerProperty partenaire_siret;
	private StringProperty partenaire_nom;
	private StringProperty partenaire_mot_de_passe_hash;
	private StringProperty partenaire_email;
	private StringProperty partenaire_telephone;
	private StringProperty partenaire_adresse;
	private StringProperty partenaire_ville;
	private StringProperty partenaire_code_postal;
	private StringProperty partenaire_derniere_connexion;
	private StringProperty partenaire_creation;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public Partenaire()
	{
		this.partenaire_id = new SimpleIntegerProperty();
		this.partenaire_siret = new SimpleIntegerProperty();
		this.partenaire_nom = new SimpleStringProperty();
		this.partenaire_mot_de_passe_hash = new SimpleStringProperty();
		this.partenaire_email = new SimpleStringProperty();
		this.partenaire_telephone = new SimpleStringProperty();
		this.partenaire_adresse = new SimpleStringProperty();
		this.partenaire_ville = new SimpleStringProperty();
		this.partenaire_code_postal = new SimpleStringProperty();
		this.partenaire_derniere_connexion = new SimpleStringProperty();
		this.partenaire_creation = new SimpleStringProperty();
	}
	
	public void setPartenaire_id(IntegerProperty partenaire_id){this.partenaire_id = partenaire_id;}
	public void setPartenaire_siret(IntegerProperty partenaire_siret){this.partenaire_siret = partenaire_siret;}
	public void setPartenaire_nom(StringProperty partenaire_nom){this.partenaire_nom = partenaire_nom;}
	public void setPartenaire_mot_de_passe_hash(StringProperty partenaire_mot_de_passe_hash){this.partenaire_mot_de_passe_hash = partenaire_mot_de_passe_hash;}
	public void setPartenaire_email(StringProperty partenaire_email){this.partenaire_email = partenaire_email;}
	public void setPartenaire_telephone(StringProperty partenaire_telephone){this.partenaire_telephone = partenaire_telephone;}
	public void setPartenaire_adresse(StringProperty partenaire_adresse){this.partenaire_adresse = partenaire_adresse;}
	public void setPartenaire_ville(StringProperty partenaire_ville){this.partenaire_ville = partenaire_ville;}
	public void setPartenaire_code_postal(StringProperty partenaire_code_postal){this.partenaire_code_postal = partenaire_code_postal;}
	public void setPartenaire_derniere_connexion(StringProperty partenaire_derniere_connexion){this.partenaire_derniere_connexion = partenaire_derniere_connexion;}
	public void setPartenaire_creation(StringProperty partenaire_creation){this.partenaire_creation = partenaire_creation;}
	
	public IntegerProperty getPartenaire_ide_Prop(){return partenaire_id;}
	public IntegerProperty getPartenaire_siret_Prop(){return partenaire_siret;}
	public StringProperty getPartenaire_nom_Prop(){return partenaire_nom;}
	public StringProperty getPartenaire_mot_de_passe_hash_Prop(){return partenaire_mot_de_passe_hash;}
	public StringProperty getPartenaire_email_Prop(){return partenaire_email;}
	public StringProperty getPartenaire_telephone_Prop(){return partenaire_telephone;}	
	public StringProperty getPartenaire_adresse_Prop(){return partenaire_adresse;}
	public StringProperty getPartenaire_ville_Prop(){return partenaire_ville;}
	public StringProperty getPartenaire_code_postal_Prop(){return partenaire_code_postal;}
	public StringProperty getPartenaire_derniere_connexion_Prop(){return partenaire_derniere_connexion;}
	public StringProperty getPartenaire_creation_Prop(){return partenaire_creation;}
	
	public int getPartenaire_id(){return partenaire_id.get();}
		public void setPartenaire_id(int partenaire_id){this.partenaire_id.set(partenaire_id);
		}
	public int getPartenaire_siret(){return partenaire_siret.get();}
		public void setPartenaire_siret(int partenaire_siret){this.partenaire_siret.set(partenaire_siret);}
	public String getPartenaire_nom(){return partenaire_nom.get();}
		public void setPartenaire_nom(String partenaire_nom){this.partenaire_nom.set(partenaire_nom);}
	public String getPartenaire_mot_de_passe_hash(){return partenaire_mot_de_passe_hash.get();}
		public void setPartenaire_mot_de_passe_hash(String partenaire_mot_de_passe_hash){this.partenaire_mot_de_passe_hash.set(partenaire_mot_de_passe_hash);}
	public String getPartenaire_email(){return partenaire_email.get();}
		public void setPartenaire_email(String partenaire_email){this.partenaire_email.set(partenaire_email);}
	public String getPartenaire_telephone(){return partenaire_telephone.get();}
		public void setPartenaire_telephone(String partenaire_telephone){this.partenaire_telephone.set(partenaire_telephone);}
	public String getPartenaire_adresse(){return partenaire_adresse.get();}
		public void setPartenaire_adresse(String partenaire_adresse){this.partenaire_adresse.set(partenaire_adresse);}
	public String getPartenaire_ville(){return partenaire_ville.get();}
		public void setPartenaire_ville(String partenaire_ville){this.partenaire_ville.set(partenaire_ville);}
	public String getPartenaire_code_postal(){return partenaire_code_postal.get();}
		public void setPartenaire_code_postal(String partenaire_code_postal){this.partenaire_code_postal.set(partenaire_code_postal);}
	public String getPartenaire_derniere_connexion(){return partenaire_derniere_connexion.get();}
		public void setPartenaire_derniere_connexion(String partenaire_derniere_connexion){this.partenaire_derniere_connexion.set(partenaire_derniere_connexion);}	
	public String getPartenaire_creation(){return partenaire_creation.get();}
		public void setPartenaire_creation(String partenaire_creation){this.partenaire_creation.set(partenaire_creation);}

	public static boolean verifier_la_syntaxe_de_l_email(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
		return matcher.find();
	}
		
	public static boolean email_d_inscription(String destinataire) {
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
		
	@Override
	public String toString() {
		return "Partenaire [partenaire_id=" + partenaire_id + ", partenaire_siret=" + partenaire_siret
				+ ", partenaire_nom=" + partenaire_nom + ", partenaire_mot_de_passe_hash=" + partenaire_mot_de_passe_hash
				+ ", partenaire_email=" + partenaire_email + ", partenaire_telephone=" + partenaire_telephone
				+ ", partenaire_adresse=" + partenaire_adresse + ", partenaire_ville=" + partenaire_ville
				+ ", partenaire_code_postal=" + partenaire_code_postal + ", partenaire_derniere_connexion="
				+ partenaire_derniere_connexion + ", partenaire_creation=" + partenaire_creation + "]";
	}	
}
