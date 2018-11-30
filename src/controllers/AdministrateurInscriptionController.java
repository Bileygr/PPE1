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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import library.encryption.BCrypt;
import models.base.Administrateur;
import models.dao.AdministrateurDAO;
import models.dao.ConfigurationConnexionBaseDeDonneesDAO;

public class AdministrateurInscriptionController {
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button retourButton;
	@FXML
	private Button inscriptionButton;
	@FXML
	private TextField nomInput;
	@FXML
	private TextField prenomInput;
	@FXML
	private TextField identifiantInput;
	@FXML
	private TextField motDePasseInput;
	@FXML
	private TextField emailInput;
	@FXML
	private TextField telephoneInput;
	@FXML
	private TextField adresseInput;
	@FXML
	private TextField villeInput;
	@FXML
	private TextField codePostalInput;
	@FXML
	private CheckBox statusSuperAdministrateurCheckbox;
	
	String nomDeLaPersonneConnecte;
	boolean statusSuperAdministrateur;
	
	private double xOffset;
	private double yOffset;
	
	public void recuperer_le_nom_de_la_personne_connecte(String nomDeLaPersonneConnecte) {
		this.nomDeLaPersonneConnecte = nomDeLaPersonneConnecte;
	}
	
	public void recuperer_le_status_super_administrateur_de_la_personne_connecte(boolean statusSuperAdministrateur) {
		this.statusSuperAdministrateur = statusSuperAdministrateur;
	}
	
	@FXML
	private void deconnecter_l_utilisateur(ActionEvent actionEvent) {	
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
	private void fermer_l_application(ActionEvent actionEvent) {
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void retourner_dans_la_page_precedente(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Administrateur.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			AdministrateurController administrateurController = loader.<AdministrateurController>getController();
			administrateurController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			administrateurController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void inscrire_un_nouvel_administrateur(ActionEvent actionEvent) throws SQLException {
		if(!nomInput.getText().isEmpty() && !prenomInput.getText().isEmpty() && !motDePasseInput.getText().isEmpty() 
				&& !emailInput.getText().isEmpty() && !telephoneInput.getText().isEmpty() && !adresseInput.getText().isEmpty() 
				&& !villeInput.getText().isEmpty() && !codePostalInput.getText().isEmpty()) {
			if(motDePasseInput.getText().length() >= 12) {
				boolean verificationDeLaSyntaxeEmail = Administrateur.verifier_la_syntaxe_de_l_email(emailInput.getText());
				int super_administrateur = 0;
				
				String hash = BCrypt.hashpw(motDePasseInput.getText(), BCrypt.gensalt());
				
				if(statusSuperAdministrateurCheckbox.isSelected()){
					super_administrateur = 1;
				}
				
				if(verificationDeLaSyntaxeEmail == true) {
					if(telephoneInput.getText().length() == 10) {
						if(adresseInput.getText().length() <= 38) {
							if(villeInput.getText().length() <= 32) {
								if(codePostalInput.getText().length() == 5) {
									boolean empdata = AdministrateurDAO.ajouter_un_administrateur(super_administrateur, nomInput.getText(), prenomInput.getText(), hash, 
											emailInput.getText(), telephoneInput.getText(), adresseInput.getText(), 
											villeInput.getText(), codePostalInput.getText());
									if(empdata == true) {
										boolean statusOptionEnvoiEmail = ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_status_de_l_option_d_envoi_d_email();
										if(statusOptionEnvoiEmail == true) {
											boolean verificationEnvoiEmail = Administrateur.email_d_inscription(emailInput.getText());
											
											if(verificationEnvoiEmail == false) {
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
											loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Administrateur.fxml"));
											AnchorPane userFrame = (AnchorPane) loader.load();
											Scene sc = mainPane.getScene();
											sc.setRoot(userFrame);
											AdministrateurController administrateurController = loader.<AdministrateurController>getController();
											administrateurController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
											administrateurController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
										}catch (IOException e) {
											e.printStackTrace();
										}
									}else {
										Alert a1 = new Alert(Alert.AlertType.ERROR);
										a1.setTitle("Erreur: n°8");
										a1.setContentText("L'inscription a échoué dû a une erreur de connexion.");
										a1.setHeaderText(null);
										a1.showAndWait();
									}
								}else {
									Alert a1 = new Alert(Alert.AlertType.ERROR);
									a1.setTitle("Erreur: n°7");
									a1.setContentText("Le code postal devrait avoir un maximum de 5 caractères.");
									a1.setHeaderText(null);
									a1.showAndWait();
								}
							}else {
								Alert a1 = new Alert(Alert.AlertType.ERROR);
								a1.setTitle("Erreur: n°6");
								a1.setContentText("La ville devrait avoir un maximum de 32 caractères.");
								a1.setHeaderText(null);
								a1.showAndWait();
							}
						}else {
							Alert a1 = new Alert(Alert.AlertType.ERROR);
							a1.setTitle("Erreur: n°5");
							a1.setContentText("L'addresse devrait avoir un maximum de 38 caractères.");
							a1.setHeaderText(null);
							a1.showAndWait();
						}
					}else {
						Alert a1 = new Alert(Alert.AlertType.ERROR);
						a1.setTitle("Erreur: n°4");
						a1.setContentText("Le n° de téléphone devrait avoir 10 chiffre.");
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
				a1.setContentText("Le mot de passe devrait avoir au moins 12 caractères.");
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
			}
		});
		
		mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.obtenir_le_primaryStage().setX(event.getScreenX()- xOffset);
				Main.obtenir_le_primaryStage().setY(event.getScreenY()- yOffset);
			}
		});
    }
}
