package controller;

import application.Main;
import dao.AdministrateurDAO;
import dao.ConfigurationDAO;
import encryption.BCrypt;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConnexionController {
	@FXML
	private TextField email;
	@FXML
	private PasswordField motdepasse;
	@FXML
	private Button connexion;
	@FXML
	private Button configuration;
	@FXML
	private Button fermeture;
	@FXML
	private Stage main;
	@FXML 
	private AnchorPane mainpane;
	
	private double xOffset;
	private double yOffset;
	
	@FXML
	private void fermer(ActionEvent actionEvent) {
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void connecter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException {
		if(!email.getText().isEmpty() && !motdepasse.getText().isEmpty()) {
			boolean email_validation = AdministrateurDAO.validate(email.getText());
			
			if(email_validation == true) {
				
				if(motdepasse.getText().length() >= 12) {
					String mot_de_passe = motdepasse.getText();
					String hash = AdministrateurDAO.hash(email.getText());
						String nom = AdministrateurDAO.nom(email.getText(), hash);
			
							if(BCrypt.checkpw(mot_de_passe, hash)) {
									boolean super_administrateur = AdministrateurDAO.connexion_super_administrateur(email.getText(), hash);
									AdministrateurDAO.connexion_update(email.getText(), hash);
									int emailStatus = ConfigurationDAO.getEmail();
						
									if(emailStatus == 1) {
										boolean emailSent = AdministrateurDAO.email(email.getText());
							
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
											mainpane.getChildren().clear();
											FXMLLoader loader = new FXMLLoader();
											loader.setLocation(Main.class.getClassLoader().getResource("view/SuperAdministrateurMenu.fxml"));
											AnchorPane userFrame = (AnchorPane) loader.load();
											Scene sc =  new Scene(userFrame);
											Stage  stage = (Stage) mainpane.getScene().getWindow();
											stage.setScene(sc);
											SuperAdministrateurMenuController super_administrateur_menu_controller = loader.<SuperAdministrateurMenuController>getController();
											super_administrateur_menu_controller.nom(nom);
											super_administrateur_menu_controller.super_administrateur(super_administrateur);
										}catch (IOException e){
											e.printStackTrace();
										}
									}else {
										try {
											mainpane.getChildren().clear();
											FXMLLoader loader = new FXMLLoader();
											loader.setLocation(Main.class.getClassLoader().getResource("view/Menu.fxml"));
											AnchorPane userFrame = (AnchorPane) loader.load();
											Scene sc =  new Scene(userFrame);
											Stage  stage = (Stage) mainpane.getScene().getWindow();
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
			mainpane.getChildren().clear();
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: n°1");
			a1.setContentText("L'un ou les deux champs sont vide.");
			a1.setHeaderText(null);
			a1.showAndWait();
		 }
	}

	@FXML
	private void configurer(ActionEvent actionEvent){
        try {
        	mainpane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Configuration.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc =  new Scene(userFrame);
			Stage  stage = (Stage) mainpane.getScene().getWindow();
			stage.setScene(sc);
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
		
		mainpane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 xOffset = event.getSceneX();
				 yOffset = event.getSceneY();
				 
				 System.out.println(xOffset);
				 System.out.println(yOffset);
			}
		});
		
		mainpane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getPrimaryStage().setX(event.getScreenX()- xOffset);
				Main.getPrimaryStage().setY(event.getScreenY()- yOffset);
			}
		});
    }
}