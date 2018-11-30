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

public class Jeune 
{
	private IntegerProperty jeune_id;
	private StringProperty jeune_nom;
	private StringProperty jeune_prenom;
	private StringProperty jeune_mot_de_passe_hash;
	private StringProperty jeune_email;
	private StringProperty jeune_telephone;
	private StringProperty jeune_adresse;
	private StringProperty jeune_ville;
	private StringProperty jeune_code_postal;
	private StringProperty jeune_derniere_connexion;
	private StringProperty jeune_creation;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public Jeune()
	{
		this.jeune_id = new SimpleIntegerProperty();
		this.jeune_nom = new SimpleStringProperty();
		this.jeune_prenom = new SimpleStringProperty();
		this.jeune_mot_de_passe_hash = new SimpleStringProperty();
		this.jeune_email = new SimpleStringProperty();
		this.jeune_telephone = new SimpleStringProperty();
		this.jeune_adresse = new SimpleStringProperty();
		this.jeune_ville = new SimpleStringProperty();
		this.jeune_code_postal = new SimpleStringProperty();
		this.jeune_derniere_connexion = new SimpleStringProperty();
		this.jeune_creation = new SimpleStringProperty();
	}
	
	public void setJeune_id(IntegerProperty jeune_id){this.jeune_id = jeune_id;}
	public void setJeune_nom(StringProperty jeune_nom){this.jeune_nom = jeune_nom;}
	public void setJeune_prenom(StringProperty jeune_prenom){this.jeune_prenom = jeune_prenom;}
	public void setJeune_mot_de_passe_hash (StringProperty jeune_mot_de_passe_hash){this.jeune_mot_de_passe_hash = jeune_mot_de_passe_hash;}
	public void setJeune_email(StringProperty jeune_email){this.jeune_email = jeune_email;}
	public void setJeune_telephone(StringProperty jeune_telephone){this.jeune_telephone = jeune_telephone;}
	public void setJeune_adresse(StringProperty jeune_adresse){this.jeune_adresse = jeune_adresse;}
	public void setJeune_ville(StringProperty jeune_ville){this.jeune_ville = jeune_ville;}
	public void setJeune_code_postal(StringProperty jeune_code_postal){this.jeune_code_postal = jeune_code_postal;}
	public void setJeune_derniere_connexion(StringProperty jeune_derniere_connexion){this.jeune_derniere_connexion = jeune_derniere_connexion;}
	public void setJeune_creation(StringProperty jeune_creation){this.jeune_creation = jeune_creation;}
	
	public IntegerProperty getJeune_id_Prop(){return jeune_id;}
	public StringProperty getJeune_nom_Prop(){return jeune_nom;}
	public StringProperty getJeune_prenom_Prop(){return jeune_prenom;}
	public StringProperty getJeune_mot_de_passe_hash_Prop(){return jeune_mot_de_passe_hash;}
	public StringProperty getJeune_email_Prop(){return jeune_email;}
	public StringProperty getJeune_telephone_Prop(){return jeune_telephone;}
	public StringProperty getJeune_adresse_Prop(){return jeune_adresse;}
	public StringProperty getJeune_ville_Prop(){return jeune_ville;}
	public StringProperty getJeune_code_postal_Prop(){return jeune_code_postal;}
	public StringProperty getJeune_derniere_connexion_Prop(){return jeune_derniere_connexion;}
	public StringProperty getJeune_creation_Prop(){return jeune_creation;}
	
	public int getJeune_id(){return jeune_id.get();}
		public void setJeune_id(int jeune_id){this.jeune_id.set(jeune_id);}
	public String getJeune_nom(){return jeune_nom.get();}
		public void setJeune_nom(String jeune_nom){this.jeune_nom.set(jeune_nom);}
	public String getJeune_prenom(){return jeune_prenom.get();}
		public void setJeune_prenom(String jeune_prenom){this.jeune_prenom.set(jeune_prenom);}
	public String getJeune_mot_de_passe_hash(){return jeune_mot_de_passe_hash.get();}
		public void setJeune_mot_de_passe_hash(String jeune_mot_de_passe_hash){this.jeune_mot_de_passe_hash.set(jeune_mot_de_passe_hash);}
	public String getJeune_email(){return jeune_email.get();}
		public void setJeune_email(String jeune_email){this.jeune_email.set(jeune_email);}
	public String getJeune_telephone(){return jeune_telephone.get();}
		public void setJeune_telephone(String jeune_telephone){this.jeune_telephone.set(jeune_telephone);}
	public String getJeune_adresse(){return jeune_adresse.get();}
		public void setJeune_adresse(String jeune_adresse){this.jeune_adresse.set(jeune_adresse);}
	public String getJeune_ville(){return jeune_ville.get();}
		public void setJeune_ville(String jeune_ville){this.jeune_ville.set(jeune_ville);}
	public String getJeune_code_postal(){return jeune_code_postal.get();}
		public void setJeune_code_postal(String jeune_code_postal){this.jeune_code_postal.set(jeune_code_postal);}
	public String getJeune_derniere_connexion(){return jeune_derniere_connexion.get();}
		public void setJeune_derniere_connexion(String jeune_derniere_connexion){this.jeune_derniere_connexion.set(jeune_derniere_connexion);}	
	public String getJeune_creation(){return jeune_creation.get();}
		public void setJeune_creation(String jeune_creation){this.jeune_creation.set(jeune_creation);}
		
	public static boolean verifier_la_syntaxe_de_l_email(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
		return matcher.find();
	}
	
	public static boolean envoyer_un_email_lors_de_l_inscription(String destinataire) {
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
	
	public static boolean envoyer_un_email_lors_de_la_modification(String destinataire) {
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

	@Override
	public String toString() {
		return "Jeune [jeune_id=" + jeune_id + ", jeune_nom=" + jeune_nom + ", jeune_prenom=" + jeune_prenom
				+ ", jeune_mot_de_passe_hash=" + jeune_mot_de_passe_hash + ", jeune_email=" + jeune_email 
				+ ", jeune_adresse=" + jeune_adresse + ", jeune_ville=" + jeune_ville + ", jeune_code_postal=" 
				+ jeune_code_postal + ", jeune_telephone=" + jeune_telephone + ", jeune_derniere_connexion=" 
				+ jeune_derniere_connexion + ", jeune_date_ajout=" + jeune_creation + "]";
	}
}
