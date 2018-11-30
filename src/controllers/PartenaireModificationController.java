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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.base.Partenaire;
import models.dao.ConfigurationConnexionBaseDeDonneesDAO;
import models.dao.PartenaireDAO;

public class PartenaireModificationController {
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button retourButton;
	@FXML
	private Button modificationButton;
	@FXML
	private Label idLabel;
	@FXML
	private TextField siretInput;
	@FXML
	private TextField nomInput;
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
	
	public void recuperer_les_information_d_partenaire_selectionne(int id, int siret, String nom, String email, String telephone, String adresse, String ville, String code_postal) {
		idLabel.setText(Integer.toString(id));
		siretInput.setText(Integer.toString(siret));
		nomInput.setText(nom);
		emailInput.setText(email);
		telephoneInput.setText(telephone);
		adresseInput.setText(adresse);
		villeInput.setText(ville);
		codePostalInput.setText(code_postal);
	}
	
	@FXML
	private void deconnecter_l_utilisateur_connecte(ActionEvent actionEvent) {	
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
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Partenaire.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			PartenaireController partenaireController = loader.<PartenaireController>getController();
			partenaireController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			partenaireController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void modifier_les_informations_du_parteenaire_selectionne(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		boolean verificationDeLaSyntaxeDeLEmail = Partenaire.verifier_la_syntaxe_de_l_email(emailInput.getText());
		
		if(siretInput.getText().length() == 9) {
			
			if(verificationDeLaSyntaxeDeLEmail == true) {
				
				if(telephoneInput.getText().length() == 10) {
					
					if(adresseInput.getText().length() <= 38) {
						
						if(villeInput.getText().length() <= 32) {
							
							if(codePostalInput.getText().length() == 5) {
								boolean empdata = PartenaireDAO.modifier_les_informations_d_un_partenaire(Integer.parseInt(idLabel.getText()), Integer.parseInt(siretInput.getText()), nomInput.getText(), 
										emailInput.getText(), telephoneInput.getText(), adresseInput.getText(), villeInput.getText(), 
										codePostalInput.getText());
							
								if(empdata == true) {
									boolean verficationStatusOptionEnvoiEmail = ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_status_de_l_option_d_envoi_d_email();
									
									if(verficationStatusOptionEnvoiEmail == true) {
										boolean verificationEnvoiEmail = Partenaire.email_de_modification(emailInput.getText());
										
										if(verificationEnvoiEmail == false) {
											Alert a1 = new Alert(Alert.AlertType.ERROR);
											a1.setTitle("Erreur: n°8");
											a1.setContentText("L'envoi d'email ne fonctionne pas vous pouvez désactiver la fonctionnalité dans le menu de configuration.");
											a1.setHeaderText(null);
											a1.showAndWait();
										}
									}
									
									try {
										mainPane.getChildren().clear();
										FXMLLoader loader = new FXMLLoader();
										loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Partenaire.fxml"));
										AnchorPane userFrame = (AnchorPane) loader.load();
										Scene sc = mainPane.getScene();
										sc.setRoot(userFrame);
										PartenaireController partenaireController = loader.<PartenaireController>getController();
										partenaireController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
										partenaireController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
									}catch (IOException e) {
										e.printStackTrace();
									}
								}else {
									Alert a1 = new Alert(Alert.AlertType.ERROR);
									a1.setTitle("Erreur: n°7");
									a1.setContentText("Erreur lors de l'ajout d'un partenaire.");
									a1.setHeaderText(null);
									a1.showAndWait();
								}
							}else {
								Alert a1 = new Alert(Alert.AlertType.ERROR);
								a1.setTitle("Erreur: n°6");
								a1.setContentText("Le code postal devrait avoir un maximum de 5 caractères.");
								a1.setHeaderText(null);
								a1.showAndWait();
							}
						}else {
							Alert a1 = new Alert(Alert.AlertType.ERROR);
							a1.setTitle("Erreur: n°5");
							a1.setContentText("La ville devrait avoir un maximum de 32 caractères.");
							a1.setHeaderText(null);
							a1.showAndWait();
						}
					}else {
						Alert a1 = new Alert(Alert.AlertType.ERROR);
						a1.setTitle("Erreur: n°4");
						a1.setContentText("L'addresse devrait avoir un maximum de 38 caractères.");
						a1.setHeaderText(null);
						a1.showAndWait();
					}
				}else {
					Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur: n°3");
					a1.setContentText("Le n° de téléphone devrait avoir 10 chiffre.");
					a1.setHeaderText(null);
					a1.showAndWait();
				}
			}else {
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: n°2");
				a1.setContentText("Le format de l'email est incorrect.");
				a1.setHeaderText(null);
				a1.showAndWait();
			}
		}else {
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: n°1");
			a1.setContentText("Le SIRET devrait avoir 9 chiffres.");
			a1.setHeaderText(null);
			a1.showAndWait();
		}
	}
	
	@FXML
	private void initialize () throws ClassNotFoundException, SQLException  {
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
				Main.obtenir_le_primaryStage().setX(event.getScreenX()- xOffset);
				Main.obtenir_le_primaryStage().setY(event.getScreenY()- yOffset);
			}
		});
	}
}