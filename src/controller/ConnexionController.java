package controller;

import application.Main;
import dao.AdministrateurDAO;
import encryption.BCrypt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConnexionController {
	@FXML
	private TextField 		email_champ_de_texte;
	@FXML
	private PasswordField 	mot_de_passe_champ_de_texte;
	@FXML
	private Button 			connexion_bouton;
	@FXML 
	private AnchorPane  	mainPane;
	
	@FXML
	private void connexion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException {
		
		if(!email_champ_de_texte.getText().isEmpty() && !mot_de_passe_champ_de_texte.getText().isEmpty()) {
			boolean email_validation = AdministrateurDAO.validate(email_champ_de_texte.getText());
			
			if(email_validation == true) {
				
				if(mot_de_passe_champ_de_texte.getText().length() >= 12) {
					String mot_de_passe = mot_de_passe_champ_de_texte.getText();
					String hash = AdministrateurDAO.hash(email_champ_de_texte.getText());
					String nom = AdministrateurDAO.nom(email_champ_de_texte.getText(), hash);
					
					System.out.println("Mot de passe clair : " + mot_de_passe_champ_de_texte.getText());
					System.out.println("Mot de passe hash� : " + hash + "\nNombre de caract�re: " + hash.length());
			
					if(BCrypt.checkpw(mot_de_passe, hash)) {
						boolean super_administrateur = AdministrateurDAO.connexion_super_administrateur(email_champ_de_texte.getText(), hash);
						AdministrateurDAO.connexion_update(email_champ_de_texte.getText(), hash);
						AdministrateurDAO.email(email_champ_de_texte.getText());
						
						if(super_administrateur == true) {
							try {
								mainPane.getChildren().clear();
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(Main.class.getClassLoader().getResource("view/SuperAdministrateurMenu.fxml"));
								AnchorPane userFrame = (AnchorPane) loader.load();
								Scene sc =  new Scene(userFrame);
								Stage  stage = (Stage) mainPane.getScene().getWindow();
								stage.setScene(sc);
								System.out.println();
					
								SuperAdministrateurMenuController super_administrateur_menu_controller = loader.<SuperAdministrateurMenuController>getController();
								super_administrateur_menu_controller.nom(nom);
								super_administrateur_menu_controller.super_administrateur(super_administrateur);
							}catch (IOException e){
								e.printStackTrace();
							}
						}else {
							try {
								mainPane.getChildren().clear();
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(Main.class.getClassLoader().getResource("view/Menu.fxml"));
								AnchorPane userFrame = (AnchorPane) loader.load();
								Scene sc =  new Scene(userFrame);
								Stage  stage = (Stage) mainPane.getScene().getWindow();
								stage.setScene(sc);
								System.out.println();
					
								MenuController menu_controller = loader.<MenuController>getController();
								menu_controller.nom(nom);
								menu_controller.super_administrateur(super_administrateur);
							}catch (IOException e){
								e.printStackTrace();
							}
						}
					}else {
						Alert a1 = new Alert(Alert.AlertType.ERROR);
						a1.setTitle("Erreur: n�4");
						a1.setContentText("Cet utilisateur n'existe pas verifiez votre mot de passe et votre identifiant.");
						a1.setHeaderText(null);
						a1.showAndWait();
					}
				}else {
					Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur: n�3");
					a1.setContentText("Le mot de passe est trop court, veuillez vous adresser a votre superviseur.");
					a1.setHeaderText(null);
					a1.showAndWait();
				}
			}else {
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: n�2");
				a1.setContentText("Le format de l'email n'est pas correcte.");
				a1.setHeaderText(null);
				a1.showAndWait();
			}
		}else {
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: n�1");
			a1.setContentText("L'un ou les deux champs sont vide.");
			a1.setHeaderText(null);
			a1.showAndWait();
		 }
	}
}