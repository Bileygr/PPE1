package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.base.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class OffreDAO {
	public static ObservableList<PieChart.Data> recuperer_les_statistiques_des_offres_par_formation() throws SQLException {
		Connection connexion =  Connect.getInstance().getConnection();
        String requete = "SELECT formation.nom, COUNT(*) FROM offre JOIN formation ON offre.idformation_id = formation.id GROUP BY formation.nom";
        
        ObservableList<PieChart.Data> retour = FXCollections.observableArrayList();
        
        PreparedStatement prepared_statement = connexion.prepareStatement(requete);
        ResultSet resultat = prepared_statement.executeQuery();
        
        while(resultat.next()) {
        	retour.add(new PieChart.Data(resultat.getString("formation.nom"), resultat.getInt("COUNT(*)")));
        }
        
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
    	return retour;
	}
	
	public static ObservableList<PieChart.Data> recuperer_les_statistiques_des_offres_par_partenaire() throws SQLException {
		Connection connexion =  Connect.getInstance().getConnection();
        String requete = "SELECT user.nom, COUNT(*) FROM offre JOIN user ON offre.iduser_id = user.id GROUP BY user.nom";
        
        ObservableList<PieChart.Data> retour = FXCollections.observableArrayList();
        
        PreparedStatement prepared_statement = connexion.prepareStatement(requete);
        ResultSet resultat = prepared_statement.executeQuery();
        
        while(resultat.next()) {
        	retour.add(new PieChart.Data(resultat.getString("user.nom"), resultat.getInt("COUNT(*)")));
        }
        
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
    	return retour;
	}
	
	public static boolean sauvegarder_les_informations_d_une_offre(String description, int id) throws SQLException, ClassNotFoundException {
        Connection connexion =  Connect.getInstance().getConnection();
        String requete = "UPDATE offre SET offre.description = ? WHERE offre.id = ?";
        
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
	
	/*
	public static boolean modifier(Offre offre) throws SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "UPDATE offre SET siret = ?, roles = ?, nom = ?, prenom = ?, username = ?, email = ?, telephone = ?, adresse = ?, ville = ?, codepostal = ?"
						+ "WHERE id = ?";
		
		String [] roles = user.getRoles();
		String role = roles[0];
		boolean retour = false;
		
		PreparedStatement prepared_statement = null;
		prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, Integer.toString(user.getSiret()));
		prepared_statement.setString(2, role);
		prepared_statement.setString(3, user.getNom());
		prepared_statement.setString(4, user.getPrenom());
		prepared_statement.setString(5, user.getUsername());
		prepared_statement.setString(6, user.getEmail());
		prepared_statement.setString(7, user.getTelephone());
		prepared_statement.setString(8, user.getAdresse());
		prepared_statement.setString(9, user.getVille());
		prepared_statement.setString(10, user.getCodepostal());
		prepared_statement.setInt(11, user.getId());
		
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
	*/
	
	public static boolean supprimer_une_offre(int  id) throws SQLException, ClassNotFoundException {
        Connection connexion = Connect.getInstance().getConnection();
        String requete = "DELETE FROM offre WHERE offre.id = ?";
        
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
        
        System.out.println(retour);
        
        prepared_statement.close();
        connexion.close();
        return retour;
    }
	
	public static ObservableList<Offre> obtenir_la_liste_filtre_de_toutes_les_offres(String filtre) throws ClassNotFoundException, SQLException
    {     
		Connection connexion = Connect.getInstance().getConnection();
		String req = "SELECT offre.id, formation.nom, user.nom, offre.libelle, offre.description, offre.debut, offre.fin "
						+ "FROM offre INNER JOIN formation ON formation.id = offre.idformation_id "
						+ "INNER JOIN user ON user.id = offre.iduser_id "
						+ "WHERE formation.nom LIKE ?"
						+ "OR user.nom LIKE ?"
						+ "OR offre.libelle LIKE ?"
						+ "OR offre.debut LIKE ?"
						+ "OR offre.fin LIKE ?";
		
	 	ObservableList<Offre> listeDesOffres = FXCollections.observableArrayList();
	 	
	 	PreparedStatement prepared_statement = connexion.prepareStatement(req);
	 	prepared_statement.setString(1, "%" + filtre +  "%");
	 	prepared_statement.setString(2, "%" + filtre +  "%");
	 	prepared_statement.setString(3, "%" + filtre +  "%");
	 	prepared_statement.setString(4, "%" + filtre +  "%");
	 	prepared_statement.setString(5, "%" + filtre +  "%");
	 	
	 	int offre_id;
	   	String formation_nom;
	   	String partenaire_nom;
	   	String offre_nom;
	   	String offre_description;
	   	String offre_debut;
	   	String offre_fin;
	     
	    ResultSet resultat = prepared_statement.executeQuery();
    
	    while(resultat.next())
	    {
	    	offre_id = resultat.getInt("offre.id");
	   		formation_nom = resultat.getString("formation.nom");
	   		partenaire_nom = resultat.getString("user.nom");
	   		offre_nom = resultat.getString("offre.libelle");
	   		offre_description = resultat.getString("offre.description");
	   		offre_debut	= resultat.getString("offre.debut");
	   		offre_fin = resultat.getString("offre.fin");
			 
	   		Offre offre = new Offre();
			 
	   		offre.setOffre_id(offre_id);
	   		offre.setFormation_nom(formation_nom);
	   		offre.setPartenaire_nom(partenaire_nom);
	   		offre.setOffre_nom(offre_nom);
	   		offre.setOffre_description(offre_description);
	   		offre.setOffre_debut(offre_debut);
	   		offre.setOffre_fin(offre_fin);
   	 
	   		listeDesOffres.add(offre);
	    }
	    
	    resultat.close();
	    prepared_statement.close();
	    connexion.close();
    	return listeDesOffres;
    }
	
	public static ObservableList<Offre> obtenir_la_liste_de_toutes_les_offres() throws ClassNotFoundException, SQLException 
    {
		
   	 	Connection connexion = Connect.getInstance().getConnection();
   	 	String requete = "SELECT offre.id, formation.nom, user.nom, offre.libelle, offre.description, offre.debut, offre.fin "
				+ "FROM offre INNER JOIN formation ON formation.id = offre.idformation_id "
				+ "INNER JOIN user ON user.id = offre.iduser_id ";
   	 
   	 	ObservableList<Offre> offres = FXCollections.observableArrayList();
	 
   	 	int id;
   	 	String formation_nom;
   	 	String partenaire_nom;
   	 	String libelle;
   	 	String description;
   	 	String debut;
   	 	String fin;
	 
   	 	PreparedStatement prepared_statement = connexion.prepareStatement(requete);
	 
   	 	ResultSet resultat = prepared_statement.executeQuery();
   	 
   	 	while(resultat.next())
   	 	{
   	 		id = resultat.getInt("offre.id");
   	 		formation_nom = resultat.getString("formation.nom");
   	 		partenaire_nom = resultat.getString("user.nom");
   	 		libelle = resultat.getString("offre.libelle");
   	 		description = resultat.getString("offre.description");
   	 		debut	= resultat.getString("offre.debut");
   	 		fin = resultat.getString("offre.fin");
		 
   	 		Offre offre = new Offre();
		 
   	 		offre.setOffre_id(id);
   	 		offre.setFormation_nom(formation_nom);
   	 		offre.setPartenaire_nom(partenaire_nom);
   	 		offre.setOffre_nom(libelle);
   	 		offre.setOffre_description(description);
   	 		offre.setOffre_debut(debut);
   	 		offre.setOffre_fin(fin);
	 
   	 		offres.add(offre);
   	 	}
    
   	 	resultat.close();
   	 	prepared_statement.close();
   	 	connexion.close();
   	 	return offres;
    }
}
