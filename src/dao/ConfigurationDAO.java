package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ConfigurationDAO {
	private static Connection connect() {
        String url = "jdbc:sqlite:src/librairie/ppe-config.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public static String hostname(){
        String sql = "SELECT hostname FROM configuration";
        String hostname = "";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 hostname = rs.getString("hostname");
            	 System.out.println(rs.getString("hostname"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hostname;
    }
	
	public static int port(){
        String sql = "SELECT port FROM configuration";
        int port = 0;
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 port = rs.getInt("port");
            	 System.out.println(rs.getInt("port"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return port;
    }
	
	public static String bdd(){
        String sql = "SELECT bdd FROM configuration";
        String bdd = "";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 bdd = rs.getString("bdd");
            	 System.out.println(rs.getString("bdd"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bdd;
    }
	
	public static String utilisateur(){
        String sql = "SELECT utilisateur FROM configuration";
        String utilisateur = "";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 utilisateur = rs.getString("utilisateur");
            	 System.out.println(rs.getString("utilisateur"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return utilisateur;
    }
	
	public static String mdp(){
        String sql = "SELECT mdp FROM configuration";
        String mdp = "";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 mdp = rs.getString("mdp");
            	 System.out.println(rs.getString("mdp"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mdp;
    }
}
