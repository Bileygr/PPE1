package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import classe.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class OffreDAO {
	public static ObservableList<PieChart.Data> formation_statistique() throws SQLException {
		Connection connexion =  Connect.getInstance().getConnection();
        String requete = "SELECT formation.formation_nom, COUNT(*) FROM offre JOIN formation ON offre.formation_id = formation.formation_id GROUP BY formation.formation_nom";
        
        ObservableList<PieChart.Data> retour = FXCollections.observableArrayList();
        
        PreparedStatement prepared_statement = connexion.prepareStatement(requete);
        ResultSet resultat = prepared_statement.executeQuery();
        
        while(resultat.next()) {
        	retour.add(new PieChart.Data(resultat.getString("formation.formation_nom"), resultat.getInt("COUNT(*)")));
        }
        
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
    	return retour;
	}
	
	public static ObservableList<PieChart.Data> partenaire_statistique() throws SQLException {
		Connection connexion =  Connect.getInstance().getConnection();
        String requete = "SELECT partenaire.partenaire_nom, COUNT(*) FROM offre JOIN partenaire ON offre.partenaire_id = partenaire.partenaire_id GROUP BY partenaire.partenaire_nom";
        
        ObservableList<PieChart.Data> retour = FXCollections.observableArrayList();
        
        PreparedStatement prepared_statement = connexion.prepareStatement(requete);
        ResultSet resultat = prepared_statement.executeQuery();
        
        while(resultat.next()) {
        	retour.add(new PieChart.Data(resultat.getString("partenaire.partenaire_nom"), resultat.getInt("COUNT(*)")));
        }
        
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
    	return retour;
	}
	
	public static boolean sauvegarder(String description, int id) throws SQLException, ClassNotFoundException {
        Connection connexion =  Connect.getInstance().getConnection();
        String requete = "UPDATE offre SET offre_description = ? WHERE offre_id = ?";
        
        boolean retour = false;
        
        PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requete);
        prepared_statement.setString(1, description);
        prepared_statement.setInt(2, id);
        
        int nblignes = 0;
        
        try {
            nblignes = prepared_statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
          	}
        
        if(nblignes == 1) {
        	retour= true;
        }else {
        	retour = false;
        }
        
        prepared_statement.close();
        connexion.close();
        return retour;
     }
	
	public static boolean supprimer(int  id) throws SQLException, ClassNotFoundException {
        Connection connexion = Connect.getInstance().getConnection();
        String requete = "DELETE FROM offre WHERE offre_id = ?";
        
        boolean retour = false;
        
        PreparedStatement prepared_statement = null;
        prepared_statement = connexion.prepareStatement(requete);
        prepared_statement.setInt(1, id);
        
        int nblignes = 0;
        
        try {
            nblignes = prepared_statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
          	}
        
        if(nblignes == 1) {
        	retour = true;
        }else {
        	retour = false;
        }
        
        prepared_statement.close();
        connexion.close();
        return retour;
     }
	
	public static ObservableList<Offre> recherche_filtre(String filtre) throws ClassNotFoundException, SQLException
    {     
		Connection connexion = Connect.getInstance().getConnection();
		String req = "SELECT offre.offre_id, formation.formation_nom, partenaire.partenaire_nom, offre.offre_nom, offre.offre_description, offre.offre_debut, offre.offre_fin "
						+ "FROM offre INNER JOIN formation ON formation.formation_id = offre.formation_id "
						+ "INNER JOIN partenaire ON partenaire.partenaire_id = offre.partenaire_id "
						+ "WHERE formation.formation_nom LIKE ?"
						+ "OR partenaire.partenaire_nom LIKE ?"
						+ "OR offre.offre_nom LIKE ?"
						+ "OR offre.offre_debut LIKE ?"
						+ "OR offre.offre_fin LIKE ?";
		
	 	ObservableList<Offre> retour = FXCollections.observableArrayList();
	 	
	 	filtre = filtre
	 			.replace("!", "!!")
	 			.replace("%", "!%")
	 			.replace("_", "!_")
	 			.replace("[", "![");
	 	
	 	PreparedStatement prepared_statement = connexion.prepareStatement(req);
	 	prepared_statement.setString(1, "%" + filtre +  "%");
	 	prepared_statement.setString(2, "%" + filtre +  "%");
	 	prepared_statement.setString(3, "%" + filtre +  "%");
	 	prepared_statement.setString(4, "%" + filtre +  "%");
	 	prepared_statement.setString(5, "%" + filtre +  "%");
	 	
	 	int    offre_id;
	   	String formation_nom;
	   	String partenaire_nom;
	   	String offre_nom;
	   	String offre_description;
	   	String offre_debut;
	   	String offre_fin;
	     
	    ResultSet resultat = prepared_statement.executeQuery();
    
	    while(resultat.next())
	    {
	    	offre_id 			= resultat.getInt("offre.offre_id");
	   		formation_nom		= resultat.getString("formation.formation_nom");
	   		partenaire_nom		= resultat.getString("partenaire.partenaire_nom");
	   		offre_nom			= resultat.getString("offre.offre_nom");
	   		offre_description 	= resultat.getString("offre.offre_description");
	   		offre_debut			= resultat.getString("offre.offre_debut");
	   		offre_fin			= resultat.getString("offre.offre_fin");
			 
	   		Offre offre = new Offre();
			 
	   		offre.setOffre_id(offre_id);
	   		offre.setFormation_nom(formation_nom);
	   		offre.setPartenaire_nom(partenaire_nom);
	   		offre.setOffre_nom(offre_nom);
	   		offre.setOffre_description(offre_description);
	   		offre.setOffre_debut(offre_debut);
	   		offre.setOffre_fin(offre_fin);
   	 
	    	retour.add(offre);
	    	System.out.println(offre);
	    }
	    
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
    	return retour;
    }
	
	public static ObservableList<Offre> recherche() throws ClassNotFoundException, SQLException 
    {
   	 Connection connexion = Connect.getInstance().getConnection();
   	 String requete = "SELECT offre.offre_id, formation.formation_nom, partenaire.partenaire_nom, offre.offre_nom, offre.offre_description, offre.offre_debut, offre.offre_fin "
				+ "FROM offre INNER JOIN formation ON formation.formation_id = offre.formation_id "
				+ "INNER JOIN partenaire ON partenaire.partenaire_id = offre.partenaire_id ";
   	 
   	ObservableList<Offre> retour = FXCollections.observableArrayList();
	 
   	int    offre_id;
   	String formation_nom;
   	String partenaire_nom;
   	String offre_nom;
   	String offre_description;
   	String offre_debut;
   	String offre_fin;
	 
   	 PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 
   	 ResultSet resultat = prepared_statement.executeQuery();
   	 
   	while(resultat.next())
    {
    	offre_id 			= resultat.getInt("offre.offre_id");
   		formation_nom		= resultat.getString("formation.formation_nom");
   		partenaire_nom		= resultat.getString("partenaire.partenaire_nom");
   		offre_nom			= resultat.getString("offre.offre_nom");
   		offre_description 	= resultat.getString("offre.offre_description");
   		offre_debut			= resultat.getString("offre.offre_debut");
   		offre_fin			= resultat.getString("offre.offre_fin");
		 
   		Offre offre = new Offre();
		 
   		offre.setOffre_id(offre_id);
   		offre.setFormation_nom(formation_nom);
   		offre.setPartenaire_nom(partenaire_nom);
   		offre.setOffre_nom(offre_nom);
   		offre.setOffre_description(offre_description);
   		offre.setOffre_debut(offre_debut);
   		offre.setOffre_fin(offre_fin);
	 
    	retour.add(offre);
    	System.out.println(offre);
    }
    
    resultat.close();
    prepared_statement.close();
    connexion.close();
	return retour;
    }
}
