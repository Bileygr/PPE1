package classe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Formation 
{
	private IntegerProperty	formation_id;
	private StringProperty 	formation_nom;
	
	public Formation()
	{
		this.formation_id  = new SimpleIntegerProperty();
		this.formation_nom = new SimpleStringProperty();
	}
	
	public void setFormation_id(IntegerProperty formation_id){this.formation_id = formation_id;}
	public void setFormation_nom(StringProperty formation_nom){this.formation_nom = formation_nom;}
	
	public IntegerProperty getFormation_id_Prop(){return formation_id;}
	public StringProperty getFormation_nom_Prop(){return formation_nom;}
	
	public int getFormation_id(){return formation_id.get();}
		public void setFormation_id(int formation_id){this.formation_id.set(formation_id);}
	public String getFormation_nom(){return formation_nom.get();}
		public void setFormation_nom(String formation_nom){this.formation_nom.set(formation_nom);}

	@Override
	public String toString() {
		return "Formation [formation_id=" + formation_id + ", formation_nom=" + formation_nom + "]";
	}
}
