package models.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class ConfigurationConnexionBaseDeDonneesDAO {
	public static void creation_des_fichiers_necessaire() throws FileNotFoundException, UnsupportedEncodingException {
		File theDir = new File("C:/ppe (Cheik-Siramakan Keita)");

		if (!theDir.exists()) {
		    System.out.println("creating directory: " + theDir.getName());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {  
		    	PrintWriter writer = new PrintWriter("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt", "UTF-8");
				writer.println("lycee_du_parc_de_villegenis");
				writer.println("127.0.0.1");
				writer.println("3306");
				writer.println("root");
				writer.println("");
				writer.println("0");
				writer.close();
		        System.out.println("DIR created");  
		    }
		}
	}
	
	public static void modifier_les_modalites_de_connexion_a_la_base_de_donnees(String hostname, int port, String bdd, String utilisateur, String mdp, int email) throws SQLException, FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt", "UTF-8");
		writer.println(bdd);
		writer.println(hostname);
		writer.println(port);
		writer.println(utilisateur);
		writer.println(mdp);
		writer.println(email);
		writer.close();
    }
	
	public static String obtenir_le_nom_d_hote(){
		String host = null;
		
		try {
			host = Files.readAllLines(Paths.get("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt")).get(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return host;
    }
	
	public static int obtenir_le_numero_de_port(){
		String port = null;
		
		try {
			port = Files.readAllLines(Paths.get("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt")).get(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return Integer.parseInt(port);
    }
	
	public static String obtenir_le_nom_de_la_base_de_donnees(){
		String db = null;
		
		try {
			db = Files.readAllLines(Paths.get("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt")).get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return db;
    }
	
	public static String obtenir_le_nom_de_l_utilisateur(){
		String user = null;
		
		try {
			user = Files.readAllLines(Paths.get("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt")).get(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return user;
    }
	
	public static String obtenir_le_mot_de_passe(){
		String mdp = null;
		
		try {
			mdp = Files.readAllLines(Paths.get("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt")).get(4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return mdp;
    }
	
	public static boolean obtenir_le_status_de_l_option_d_envoi_d_email(){
		String email = null;
		
		try {
			email = Files.readAllLines(Paths.get("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt")).get(5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int e = Integer.parseInt(email);
		boolean status = false;
		
		if(e == 1) {
        	status = true;
        }
		
        return status;
    }
}