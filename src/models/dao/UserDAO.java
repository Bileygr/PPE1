package models.dao;

import models.base.User;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAO {
	public static String obtenir_le_hache_correspondant_a_l_email_de_connexion(String email) throws ClassNotFoundException, SQLException {
		Connection connexion = Connect.getInstance().getConnection();
		String requete = "SELECT password "+
						 "FROM user "+
						 "WHERE email = ?";
		
		String resultatDeLaRequete = "";
		
		PreparedStatement prepared_statement = connexion.prepareStatement(requete);
		prepared_statement.setString(1, email);
		ResultSet resultat = prepared_statement.executeQuery();
		
		if(resultat.next()) {
			resultatDeLaRequete = resultat.getString("password");
		}
		
		resultat.close();
		prepared_statement.close();
		connexion.close();
		return resultatDeLaRequete;
	}
}
