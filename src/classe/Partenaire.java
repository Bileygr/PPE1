package classe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partenaire 
{
	private IntegerProperty partenaire_id;
	private IntegerProperty partenaire_siret;
	private StringProperty	partenaire_nom;
	private StringProperty	partenaire_mot_de_passe;
	private StringProperty	partenaire_email;
	private StringProperty	partenaire_telephone;
	private StringProperty	partenaire_adresse;
	private StringProperty	partenaire_ville;
	private StringProperty	partenaire_code_postal;
	private StringProperty	partenaire_derniere_connexion;
	private StringProperty	partenaire_date_ajout;
	
	public Partenaire()
	{
		this.partenaire_id 						= new SimpleIntegerProperty();
		this.partenaire_siret 					= new SimpleIntegerProperty();
		this.partenaire_nom 					= new SimpleStringProperty();
		this.partenaire_mot_de_passe 			= new SimpleStringProperty();
		this.partenaire_email 					= new SimpleStringProperty();
		this.partenaire_telephone 				= new SimpleStringProperty();
		this.partenaire_adresse 				= new SimpleStringProperty();
		this.partenaire_ville					= new SimpleStringProperty();
		this.partenaire_code_postal				= new SimpleStringProperty();
		this.partenaire_derniere_connexion		= new SimpleStringProperty();
		this.partenaire_date_ajout				= new SimpleStringProperty();
	}
	
	public void setPartenaire_id(IntegerProperty partenaire_id){this.partenaire_id = partenaire_id;}
	public void setPartenaire_siret(IntegerProperty partenaire_siret){this.partenaire_siret = partenaire_siret;}
	public void setPartenaire_nom(StringProperty partenaire_nom){this.partenaire_nom = partenaire_nom;}
	public void setPartenaire_mot_de_passe(StringProperty partenaire_mot_de_passe){this.partenaire_mot_de_passe = partenaire_mot_de_passe;}
	public void setPartenaire_email(StringProperty partenaire_email){this.partenaire_email = partenaire_email;}
	public void setPartenaire_telephone(StringProperty partenaire_telephone){this.partenaire_telephone = partenaire_telephone;}
	public void setPartenaire_adresse(StringProperty partenaire_adresse){this.partenaire_adresse = partenaire_adresse;}
	public void setPartenaire_ville(StringProperty partenaire_ville){this.partenaire_ville = partenaire_ville;}
	public void setPartenaire_code_postal(StringProperty partenaire_code_postal){this.partenaire_code_postal = partenaire_code_postal;}
	public void setPartenaire_derniere_connexion(StringProperty partenaire_derniere_connexion){this.partenaire_derniere_connexion = partenaire_derniere_connexion;}
	public void setPartenaire_date_ajout(StringProperty partenaire_date_ajout){this.partenaire_date_ajout = partenaire_date_ajout;}
	
	public IntegerProperty getPartenaire_ide_Prop(){return partenaire_id;}
	public IntegerProperty getPartenaire_siret_Prop(){return partenaire_siret;}
	public StringProperty getPartenaire_nom_Prop(){return partenaire_nom;}
	public StringProperty getPartenaire_mot_de_passe_Prop(){return partenaire_mot_de_passe;}
	public StringProperty getPartenaire_email_Prop(){return partenaire_email;}
	public StringProperty getPartenaire_telephone_Prop(){return partenaire_telephone;}	
	public StringProperty getPartenaire_adresse_Prop(){return partenaire_adresse;}
	public StringProperty getPartenaire_ville_Prop(){return partenaire_ville;}
	public StringProperty getPartenaire_code_postal_Prop(){return partenaire_code_postal;}
	public StringProperty getPartenaire_derniere_connexion_Prop(){return partenaire_derniere_connexion;}
	public StringProperty getPartenaire_date_ajout_Prop(){return partenaire_date_ajout;}
	
	public int getPartenaire_id(){return partenaire_id.get();}
		public void setPartenaire_id(int partenaire_id){this.partenaire_id.set(partenaire_id);
		}
	public int getPartenaire_siret(){return partenaire_siret.get();}
		public void setPartenaire_siret(int partenaire_siret){this.partenaire_siret.set(partenaire_siret);}
	public String getPartenaire_nom(){return partenaire_nom.get();}
		public void setPartenaire_nom(String partenaire_nom){this.partenaire_nom.set(partenaire_nom);}
	public String getPartenaire_mot_de_passe(){return partenaire_mot_de_passe.get();}
		public void setPartenaire_mot_de_passe(String partenaire_mot_de_passe){this.partenaire_mot_de_passe.set(partenaire_mot_de_passe);}
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
	public String getPartenaire_date_ajout(){return partenaire_date_ajout.get();}
		public void setPartenaire_date_ajout(String partenaire_date_ajout){this.partenaire_date_ajout.set(partenaire_date_ajout);}

	@Override
	public String toString() {
		return "Partenaire [partenaire_id=" + partenaire_id + ", partenaire_siret=" + partenaire_siret
				+ ", partenaire_nom=" + partenaire_nom + ", partenaire_mot_de_passe=" + partenaire_mot_de_passe
				+ ", partenaire_email=" + partenaire_email + ", partenaire_telephone=" + partenaire_telephone
				+ ", partenaire_adresse=" + partenaire_adresse + ", partenaire_ville=" + partenaire_ville
				+ ", partenaire_code_postal=" + partenaire_code_postal + ", partenaire_derniere_connexion="
				+ partenaire_derniere_connexion + ", partenaire_date_ajout=" + partenaire_date_ajout + "]";
	}	
}
