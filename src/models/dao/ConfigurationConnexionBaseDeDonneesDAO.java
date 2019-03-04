package models.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class ConfigurationConnexionBaseDeDonneesDAO {
	/*
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
	*/
	
	public static void modifier_les_modalites_de_connexion_a_la_base_de_donnees(String hostname, int port, String bdd, String utilisateur, String mdp, int email) throws SQLException, FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("library/local-db.txt", "UTF-8");
		writer.println(bdd);
		writer.println(hostname);
		writer.println(port);
		writer.println(utilisateur);
		writer.println(mdp);
		writer.println(email);
		writer.close();
		
		/*
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
        */
    }
	
	public static String obtenir_le_nom_d_hote(){
		String host = null;
		
		try {
			host = Files.readAllLines(Paths.get("local-db.txt")).get(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
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
        */
		
        return host;
    }
	
	public static int obtenir_le_numero_de_port(){
		String port = null;
		
		try {
			port = Files.readAllLines(Paths.get("local-db.txt")).get(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
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
        */
		
        return Integer.parseInt(port);
    }
	
	public static String obtenir_le_nom_de_la_base_de_donnees(){
		String db = null;
		
		try {
			db = Files.readAllLines(Paths.get("local-db.txt")).get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
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
        */
		
        return db;
    }
	
	public static String obtenir_le_nom_de_l_utilisateur(){
		String user = null;
		
		try {
			user = Files.readAllLines(Paths.get("local-db.txt")).get(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
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
        */
		
        return user;
    }
	
	public static String obtenir_le_mot_de_passe(){
		String mdp = null;
		
		try {
			mdp = Files.readAllLines(Paths.get("local-db.txt")).get(4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
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
        */
		
        return mdp;
    }
	
	public static boolean obtenir_le_status_de_l_option_d_envoi_d_email(){
		String email = null;
		
		try {
			email = Files.readAllLines(Paths.get("local-db.txt")).get(5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int e = Integer.parseInt(email);
		boolean status = false;
		
		if(e == 1) {
        	status = true;
        }
		
		/*
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
        */
		
        return status;
    }
}
