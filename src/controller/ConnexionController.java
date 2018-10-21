package controller;

import application.Main;
import dao.AdministrateurDAO;
import dao.ConfigurationDAO;
import encryption.BCrypt;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
	private Button 			configuration_bouton;
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
			
							if(BCrypt.checkpw(mot_de_passe, hash)) {
									boolean super_administrateur = AdministrateurDAO.connexion_super_administrateur(email_champ_de_texte.getText(), hash);
									AdministrateurDAO.connexion_update(email_champ_de_texte.getText(), hash);
									int emailStatus = ConfigurationDAO.getEmail();
						
									if(emailStatus == 1) {
										boolean emailSent = AdministrateurDAO.email(email_champ_de_texte.getText());
							
										if(emailSent == false) {
											Alert a1 = new Alert(Alert.AlertType.ERROR);
											a1.setTitle("Erreur: n°5");
											a1.setContentText("L'envoi d'email ne fonctionne pas veuillez désactiver la fonctionnalité dans le menu de configuration si vous ne voulez plus voir ce message.\n"+
																"Pour accéder au menu veuillez cliquer sur l'icône dans le coin en bas à droite.");
											a1.setHeaderText(null);
											a1.showAndWait();
										}
									}
						
									if(super_administrateur == true) {
										try {
											mainPane.getChildren().clear();
											FXMLLoader loader = new FXMLLoader();
											loader.setLocation(Main.class.getClassLoader().getResource("view/SuperAdministrateurMenu.fxml"));
											AnchorPane userFrame = (AnchorPane) loader.load();
											Scene sc =  new Scene(userFrame);
											Stage  stage = (Stage) mainPane.getScene().getWindow();
											stage.setScene(sc);
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
											MenuController menu_controller = loader.<MenuController>getController();
											menu_controller.nom(nom);
											menu_controller.super_administrateur(super_administrateur);
										}catch (IOException e){
											e.printStackTrace();
										}
									}
							}else {
								Alert a1 = new Alert(Alert.AlertType.ERROR);
								a1.setTitle("Erreur: n°4");
								a1.setContentText("Cet utilisateur n'existe pas verifiez votre mot de passe et votre email.");
								a1.setHeaderText(null);
								a1.showAndWait();
							}
				}else {
					Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur: n°3");
					a1.setContentText("Le mot de passe est trop court, vérifier que ce que vous avez taper est correcte ou veuillez vous adresser à votre superviseur.");
					a1.setHeaderText(null);
					a1.showAndWait();
				}
			}else {
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: n°2");
				a1.setContentText("Le format de l'email est incorrecte.");
				a1.setHeaderText(null);
				a1.showAndWait();
			}
		}else {
			mainPane.getChildren().clear();
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: n°1");
			a1.setContentText("L'un ou les deux champs sont vide.");
			a1.setHeaderText(null);
			a1.showAndWait();
		 }
	}

	@FXML
	private void configuration(ActionEvent actionEvent){
		Parent root;
        try {
        	root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Configuration.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Configuration");
            stage.setScene(new Scene(root, 329, 549));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		String hostname = ConfigurationDAO.getHostname();
		 try {
		      String ipAddress = hostname;
		      InetAddress inet = InetAddress.getByName(ipAddress);
		      System.out.println("Sending Ping Request to " + ipAddress);
		      if (inet.isReachable(5000)){
		        System.out.println(ipAddress + " is reachable.");
		      } else {
		    	  Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur");
					a1.setContentText("La base de données n'est pas joignable, changez les modalités de connexion dans le menu de configuration. \n"
										+"Pour accéder au menu de configuration cliquez sur l'icône dans le coin en bas à droite.");
					a1.setHeaderText(null);
					a1.showAndWait();
		      }
		    } catch ( Exception e ) {
		      System.out.println("Exception:" + e.getMessage());
		   }
    }
}