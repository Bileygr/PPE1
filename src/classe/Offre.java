package classe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Offre 
{
	private IntegerProperty offre_id;
	private StringProperty	partenaire_nom;
	private StringProperty  formation_nom;
	private StringProperty 	offre_nom;
	private StringProperty	offre_description;
	private StringProperty	offre_adresse;
	private StringProperty	offre_ville;
	private StringProperty	offre_code_postal;
	private StringProperty	offre_debut;
	private StringProperty	offre_fin;
	private StringProperty	offre_creation;
	
	public Offre()
	{
		this.offre_id 				= new SimpleIntegerProperty();
		this.partenaire_nom 		= new SimpleStringProperty();
		this.formation_nom 			= new SimpleStringProperty();
		this.offre_nom				= new SimpleStringProperty();
		this.offre_description 		= new SimpleStringProperty();
		this.offre_adresse 			= new SimpleStringProperty();
		this.offre_ville 			= new SimpleStringProperty();
		this.offre_code_postal		= new SimpleStringProperty();
		this.offre_debut 			= new SimpleStringProperty();
		this.offre_fin 				= new SimpleStringProperty();
		this.offre_creation		= new SimpleStringProperty();
	}
	
	public void setOffre_id(IntegerProperty offre_id){this.offre_id = offre_id;}
	public void setPartenaire_nom(StringProperty partenaire_nom){this.partenaire_nom = partenaire_nom;}
	public void setFormation_nom(StringProperty formation_nom){this.formation_nom = formation_nom;}
	public void setOffre_nom(StringProperty offre_nom){this.offre_nom = offre_nom;}
	public void setOffre_description(StringProperty offre_description){this.offre_description = offre_description;}
	public void setOffre_adresse(StringProperty offre_adresse){this.offre_adresse = offre_adresse;}
	public void setOffre_ville(StringProperty offre_ville){this.offre_ville = offre_ville;}
	public void setOffre_code_postal(StringProperty offre_code_postal){this.offre_code_postal = offre_code_postal;}
	public void setOffre_debut(StringProperty offre_debut){this.offre_debut = offre_debut;}
	public void setOffre_fin(StringProperty offre_fin){this.offre_fin = offre_fin;}	
	public void setOffre_creation(StringProperty offre_creation){this.offre_creation = offre_creation;}
	
	public IntegerProperty getOffre_id_Prop(){return offre_id;}
	public StringProperty getPartenaire_nom_Prop(){return partenaire_nom;}
	public StringProperty getFormation_nom_Prop(){return formation_nom;}
	public StringProperty getOffre_nom_Prop(){return offre_nom;}
	public StringProperty getOffre_description_Prop(){return offre_description;}
	public StringProperty getOffre_adresse_Prop(){return offre_adresse;}	
	public StringProperty getOffre_ville_Prop(){return offre_ville;}
	public StringProperty getOffre_code_postal_Prop(){return offre_code_postal;}
	public StringProperty getOffre_debut_Prop(){return offre_debut;}
	public StringProperty getOffre_fin_Prop(){return offre_fin;}
	public StringProperty getOffre_creation_Prop(){return offre_creation;}
	
	public int getOffre_id(){return offre_id.get();}
		public void setOffre_id(int offre_id){this.offre_id.set(offre_id);}
	public String getPartenaire_nom(){return partenaire_nom.get();}
		public void setPartenaire_nom(String partenaire_nom){this.partenaire_nom.set(partenaire_nom);}
	public String getFormation_nom(){return formation_nom.get();}
		public void setFormation_nom(String formation_nom) {this.formation_nom.set(formation_nom);}
	public String getOffre_nom(){return offre_nom.get();}
		public void setOffre_nom(String offre_nom){this.offre_nom.set(offre_nom);}
	public String getOffre_description(){return offre_description.get();}
		public void setOffre_description(String offre_description){this.offre_description.set(offre_description);}
	public String getOffre_adresse(){return offre_adresse.get();}
		public void setOffre_adresse(String offre_adresse){this.offre_adresse.set(offre_adresse);}
	public String getOffre_ville(){return offre_ville.get();}
		public void setOffre_ville(String offre_ville){this.offre_ville.set(offre_ville);}
	public String getOffre_code_postal(){return offre_code_postal.get();}
		public void setOffre_code_postal(String offre_code_postal){this.offre_code_postal.set(offre_code_postal);}
	public String getOffre_debut(){return offre_debut.get();}
		public void setOffre_debut(String offre_debut){this.offre_debut.set(offre_debut);}
	public String getOffre_fin(){return offre_fin.get();}
		public void setOffre_fin(String offre_fin){this.offre_fin.set(offre_fin);}
	public String getOffre_creation(){return offre_creation.get();}
		public void setOffre_creation(String offre_creation){this.offre_creation.set(offre_creation);}

	@Override
	public String toString() {
		return "Offre [offre_id=" + offre_id + ", partenaire_nom=" + partenaire_nom + ", formation_nom="
				+ formation_nom + ", offre_nom=" + offre_nom + ", offre_description=" + offre_description
				+ ", offre_adresse=" + offre_adresse + ", offre_ville=" + offre_ville + ", offre_code_postal="
				+ offre_code_postal + ", offre_debut=" + offre_debut + ", offre_fin=" + offre_fin
				+ ", offre_creation=" + offre_creation + "]";
	}
}