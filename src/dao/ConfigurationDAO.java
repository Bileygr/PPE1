package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	public static boolean modifier(String hostname, int port, String bdd, String utilisateur, String mdp, int email) throws SQLException{
        String sql = "UPDATE configuration SET hostname = ?, port = ?, bdd = ?, utilisateur = ?, mdp = ?, email = ? WHERE id = 1";
        boolean result = false;
        
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            	pstmt.setString(1, hostname);
            	pstmt.setInt(2, port);
            	pstmt.setString(3, bdd);
            	pstmt.setString(4, utilisateur);
            	pstmt.setString(5, mdp);
            	pstmt.setInt(6, email);
            	pstmt.executeUpdate();
            	result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return result;
    }
	
	public static String getHostname(){
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
	
	public static int getPort(){
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
	
	public static String getBDD(){
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
	
	public static String getUtilisateur(){
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
	
	public static String getMDP(){
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
	
	public static int getEmail(){
        String sql = "SELECT email FROM configuration";
        int email = 0;
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             while (rs.next()){
            	 email = rs.getInt("email");
            	 System.out.println(rs.getInt("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return email;
    }
}
