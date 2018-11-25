package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ConfigurationConnexionBaseDeDonneesDAO {
	private static Connection connexion_a_la_base_de_donnees() {
        String url = "jdbc:sqlite:src/library/ppe-config.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public static boolean modifier_les_modalites_de_connexion_a_la_base_de_donnees(String hostname, int port, String bdd, String utilisateur, String mdp, int email) throws SQLException{
        String sql = "UPDATE configuration "+
        			 "SET hostname = ?, port = ?, bdd = ?, utilisateur = ?, mdp = ?, email = ? "+
        			 "WHERE id = 1";
        
        boolean resultatDeLaRequete = false;
        
        try (Connection conn = connexion_a_la_base_de_donnees();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            	pstmt.setString(1, hostname);
            	pstmt.setInt(2, port);
            	pstmt.setString(3, bdd);
            	pstmt.setString(4, utilisateur);
            	pstmt.setString(5, mdp);
            	pstmt.setInt(6, email);
            	pstmt.executeUpdate();
            	resultatDeLaRequete = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return resultatDeLaRequete;
    }
	
	public static String obtenir_le_nom_d_hote(){
        String sql = "SELECT hostname "+
        			 "FROM configuration";
        String resultatDeLaRequete = "";
        
        try (Connection conn = connexion_a_la_base_de_donnees();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 resultatDeLaRequete = rs.getString("hostname");
            	 System.out.println("Le nom d'hôte de la base de donnée est " + rs.getString("hostname"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultatDeLaRequete;
    }
	
	public static int obtenir_le_numero_de_port(){
        String sql = "SELECT port "+
        			 "FROM configuration";
        int resultatDeLaRequete = 0;
        
        try (Connection conn = connexion_a_la_base_de_donnees();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 resultatDeLaRequete = rs.getInt("port");
            	 System.out.println("Le numéro de port est " + rs.getInt("port"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultatDeLaRequete;
    }
	
	public static String obtenir_le_nom_de_la_base_de_donnees(){
        String sql = "SELECT bdd "+
        			 "FROM configuration";
        
        String resultatDeLaRequete = "";
        
        try (Connection conn = connexion_a_la_base_de_donnees();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 resultatDeLaRequete = rs.getString("bdd");
            	 System.out.println("Le nom de la base de données est " + rs.getString("bdd"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultatDeLaRequete;
    }
	
	public static String obtenir_le_nom_de_l_utilisateur(){
        String sql = "SELECT utilisateur "+
        			 "FROM configuration";
        
        String resultatDeLaRequete = "";
        
        try (Connection conn = connexion_a_la_base_de_donnees();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 resultatDeLaRequete = rs.getString("utilisateur");
            	 System.out.println("Le nom de l'utilisateur est " + rs.getString("utilisateur"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return resultatDeLaRequete;
    }
	
	public static String obtenir_le_mot_de_passe(){
        String sql = "SELECT mdp "+
        			 "FROM configuration";
        
        String resultatDeLaRequete = "";
        
        try (Connection conn = connexion_a_la_base_de_donnees();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 resultatDeLaRequete = rs.getString("mdp");
            	 System.out.println("Le mot de passe est " + rs.getString("mdp"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultatDeLaRequete;
    }
	
	public static boolean obtenir_le_status_de_l_option_d_envoi_d_email(){
        String sql = "SELECT email "+
        			 "FROM configuration";
        int email = 0;
        boolean status = false;
        
        try (Connection conn = connexion_a_la_base_de_donnees();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 email = rs.getInt("email");
            	 System.out.println("Le status de l'option d'envoi d'emails est " + rs.getInt("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        if(email == 1) {
        	status = true;
        }
        
        return status;
    }
}
