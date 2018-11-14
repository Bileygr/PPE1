package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import library.encryption.BCrypt;
import models.dao.ConfigurationDAO;
import models.dao.JeuneDAO;

public class JeuneInscriptionController {
	@FXML
	private Button deconnexion_bouton;
	@FXML
	private Button fermeture_bouton;
	@FXML
	private Button retour_bouton;
	@FXML
	private Button inscrire_bouton;
	@FXML
	private TextField nom_champ_de_texte;
	@FXML
	private TextField prenom_champ_de_texte;
	@FXML
	private TextField identifiant_champ_de_texte;
	@FXML
	private TextField mot_de_passe_champ_de_texte;
	@FXML
	private TextField email_champ_de_texte;
	@FXML
	private TextField telephone_champ_de_texte;
	@FXML
	private TextField adresse_champ_de_texte;
	@FXML
	private TextField ville_champ_de_texte;
	@FXML
	private TextField code_postal_champ_de_texte;
	@FXML
	private AnchorPane mainPane;
	String nom;
	boolean super_administrateur;
	
	private double xOffset;
	private double yOffset;
	
	public void nom(String nom) {
		this.nom = nom;
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
	}
	
	@FXML
	private void deconnecter(ActionEvent actionEvent) {	
		try {
	    	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Connexion.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
		}catch(IOException e) {
	        e.printStackTrace();
	     }
	}
	
	@FXML
	private void fermer(ActionEvent actionEvent) {
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void retour(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Jeune.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			JeuneController jeune_controller = loader.<JeuneController>getController();
			jeune_controller.nom(this.nom);
			jeune_controller.super_administrateur(this.super_administrateur);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void inscrire(ActionEvent actionEvent) throws SQLException {
		if(!nom_champ_de_texte.getText().isEmpty() && !prenom_champ_de_texte.getText().isEmpty() && !mot_de_passe_champ_de_texte.getText().isEmpty() 
				&& !email_champ_de_texte.getText().isEmpty() && !telephone_champ_de_texte.getText().isEmpty() && !adresse_champ_de_texte.getText().isEmpty() 
				&& !ville_champ_de_texte.getText().isEmpty() && !code_postal_champ_de_texte.getText().isEmpty()) {
			
			if(mot_de_passe_champ_de_texte.getText().length() >= 12) {
				boolean email_validation = JeuneDAO.validate_email(email_champ_de_texte.getText());
				String hash = BCrypt.hashpw(mot_de_passe_champ_de_texte.getText(), BCrypt.gensalt());
				
				if(email_validation == true) {
				
					if(telephone_champ_de_texte.getText().length() == 10) {
					
						if(adresse_champ_de_texte.getText().length() <= 38) {
						
							if(ville_champ_de_texte.getText().length() <= 32) {
							
								if(code_postal_champ_de_texte.getText().length() == 5) {
									boolean empdata = JeuneDAO.inscrire(nom_champ_de_texte.getText(), prenom_champ_de_texte.getText(),hash, 
											email_champ_de_texte.getText(), telephone_champ_de_texte.getText(), adresse_champ_de_texte.getText(), 
											ville_champ_de_texte.getText(), code_postal_champ_de_texte.getText());
		
									if(empdata == true) {
										int emailStatus = ConfigurationDAO.getEmail();
										
										if(emailStatus == 1) {
											boolean emailSent = JeuneDAO.email_inscription(email_champ_de_texte.getText());
											
											if(emailSent == false) {
												Alert a1 = new Alert(Alert.AlertType.ERROR);
												a1.setTitle("Erreur: n°9");
												a1.setContentText("L'envoi d'email ne fonctionne pas vous pouvez désactiver la fonctionnalité dans le menu de configuration.");
												a1.setHeaderText(null);
												a1.showAndWait();
											}
										}

										try {
											mainPane.getChildren().clear();
											FXMLLoader loader = new FXMLLoader();
											loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Jeune.fxml"));
											AnchorPane userFrame = (AnchorPane) loader.load();
											Scene sc = mainPane.getScene();
											sc.setRoot(userFrame);
											JeuneController jeune_controller = loader.<JeuneController>getController();
											jeune_controller.nom(this.nom);
											jeune_controller.super_administrateur(this.super_administrateur);
										}catch (IOException e) {
											e.printStackTrace();
										}
									}else {
										Alert a1 = new Alert(Alert.AlertType.ERROR);
										a1.setTitle("Erreur: n°8");
										a1.setContentText("L'inscription a ï¿½chouï¿½ dï¿½ a une erreur de connexion.");
										a1.setHeaderText(null);
										a1.showAndWait();
									}
								}else {
									Alert a1 = new Alert(Alert.AlertType.ERROR);
									a1.setTitle("Erreur: n°7");
									a1.setContentText("Le code postal devrait avoir un maximum de 5 caractï¿½res.");
									a1.setHeaderText(null);
									a1.showAndWait();
								}
							}else {
								Alert a1 = new Alert(Alert.AlertType.ERROR);
								a1.setTitle("Erreur: n°6");
								a1.setContentText("La ville devrait avoir un maximum de 32 caractï¿½res.");
								a1.setHeaderText(null);
								a1.showAndWait();
							}
						}else {
							Alert a1 = new Alert(Alert.AlertType.ERROR);
							a1.setTitle("Erreur: n°5");
							a1.setContentText("L'addresse devrait avoir un maximum de 38 caractï¿½res.");
							a1.setHeaderText(null);
							a1.showAndWait();
						}
					}else {
						Alert a1 = new Alert(Alert.AlertType.ERROR);
						a1.setTitle("Erreur: n°4");
						a1.setContentText("Le nï¿½ de tï¿½lï¿½phone devrait avoir 10 chiffre.");
						a1.setHeaderText(null);
						a1.showAndWait();
					}
				}else {
					Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur: n°3");
					a1.setContentText("Le format de l'email est incorrect.");
					a1.setHeaderText(null);
					a1.showAndWait();
				}
			}else {
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: n°2");
				a1.setContentText("Le mot de passe devrait avoir au moins 12 caractï¿½res.");
				a1.setHeaderText(null);
				a1.showAndWait();
			}
		}else {
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: n°1");
			a1.setContentText("L'un des champs est vide.");
			a1.setHeaderText(null);
			a1.showAndWait();
		}
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 xOffset = event.getSceneX();
				 yOffset = event.getSceneY();
				 
				 System.out.println(xOffset);
				 System.out.println(yOffset);
			}
		});
		
		mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getPrimaryStage().setX(event.getScreenX()- xOffset);
				Main.getPrimaryStage().setY(event.getScreenY()- yOffset);
			}
		});
    }
}
