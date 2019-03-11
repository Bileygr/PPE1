package models.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ConfigurationConnexionBaseDeDonneesDAO {
	public static String encryption(String mdp) {
		String mdp_crypte = null;
		
		if(mdp != null) 
		{
			try 
		    {
		        String key = "Bar12345Bar12345";
		        
		        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        
		        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		        byte[] encrypted = cipher.doFinal(mdp.getBytes());
		        StringBuilder sb = new StringBuilder();
		        
		        for (byte b: encrypted) {
	                sb.append((char)b);
	            }

		        mdp_crypte = sb.toString();
		    }catch(Exception e) 
		    {
		    	e.printStackTrace();
		    }
		}else if(mdp == null) {
			try 
		    {
				String password = "rk-mSx4f$+w*kVFL";
		        String key = "Bar12345Bar12345";
		        
		        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        
		        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		        byte[] encrypted = cipher.doFinal(password.getBytes());
		        StringBuilder sb = new StringBuilder();
		        
		        for (byte b: encrypted) {
	                sb.append((char)b);
	            }

		        mdp_crypte = sb.toString();
		    }catch(Exception e) 
		    {
		    	e.printStackTrace();
		    }
		}

		return mdp_crypte;
	}
	
	public static void creation_des_fichiers_necessaire() throws FileNotFoundException, UnsupportedEncodingException {
		File theDir = new File("C:/ppe (Cheik-Siramakan Keita)");
		String encrypted = ConfigurationConnexionBaseDeDonneesDAO.encryption(null);

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
				writer.println("ppe");
				writer.println("cheikkeita.ddns.net");
				writer.println("9766");
				writer.println("ppe");
				writer.println(encrypted);
				writer.println("0");
				writer.close();
		        System.out.println("DIR created");  
		    }
		}
	}
	
	public static void modifier_les_modalites_de_connexion_a_la_base_de_donnees(String hostname, int port, String bdd, String utilisateur, String mdp, int email) throws SQLException, FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt", "UTF-8");
		String password = ConfigurationConnexionBaseDeDonneesDAO.encryption(mdp);
		
		writer.println(bdd);
		writer.println(hostname);
		writer.println(port);
		writer.println(utilisateur);
		writer.println(password);
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
		String decrypted = null;
		
		try {
			mdp = Files.readAllLines(Paths.get("C:/ppe (Cheik-Siramakan Keita)/bdd-info.txt")).get(4);
			System.out.println("Obtenir le mot de passe: " + mdp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
            byte[] bb = new byte[mdp.length()];
            
            for (int i=0; i<mdp.length(); i++) {
                bb[i] = (byte) mdp.charAt(i);
            }
            
            String key = "Bar12345Bar12345";
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decrypted = new String(cipher.doFinal(bb));
		}catch(Exception e) 
        {
            e.printStackTrace();
        }
		
        return decrypted;
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