package controller;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import dao.ConfigurationDAO;
import dao.JeuneDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class JeuneModificationController {
	@FXML
	private Button			deconnexion_bouton;
	@FXML
	private Button			retour_bouton;
	@FXML
	private Button			modifier_bouton;
	@FXML
	private Label			id_label;
	@FXML
	private TextField		nom_champ_de_texte;
	@FXML
	private TextField		prenom_champ_de_texte;
	@FXML
	private TextField		identifiant_champ_de_texte;
	@FXML
	private TextField		email_champ_de_texte;
	@FXML
	private TextField		telephone_champ_de_texte;
	@FXML
	private TextField		adresse_champ_de_texte;
	@FXML
	private TextField		ville_champ_de_texte;
	@FXML
	private TextField		code_postal_champ_de_texte;
	@FXML
	private AnchorPane  	mainPane;
	String nom;
	int id;
	boolean super_administrateur;
	
	public void nom(String nom) {
		this.nom = nom;
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
	}
	
	public void jeune(int id, String nom, String prenom, String email,
			String telephone, String adresse, String ville, String code_postal) {
		this.id = id;
		id_label.setText("ID: " + Integer.toString(id));
		nom_champ_de_texte.setText(nom);
		prenom_champ_de_texte.setText(prenom);
		email_champ_de_texte.setText(email);
		telephone_champ_de_texte.setText(telephone);
		adresse_champ_de_texte.setText(adresse);
		ville_champ_de_texte.setText(ville);
		code_postal_champ_de_texte.setText(code_postal);
	}
	
	@FXML
	private void deconnexion(ActionEvent actionEvent) {	
		try {
	    	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Connexion.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
		}catch(IOException e) {
	        e.printStackTrace();
	     }
	}
	
	@FXML
	private void retour(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Jeune.fxml"));
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
	private void modifier(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		boolean email_validation = JeuneDAO.validate_email(email_champ_de_texte.getText());
		
		if(email_validation == true) {
			
			if(telephone_champ_de_texte.getText().length() == 10) {
			
				if(adresse_champ_de_texte.getText().length() <= 38) {
				
					if(ville_champ_de_texte.getText().length() <= 32) {
					
						if(code_postal_champ_de_texte.getText().length() == 5) {
							boolean empdata = JeuneDAO.modifier(id, nom_champ_de_texte.getText(), 
									prenom_champ_de_texte.getText(), email_champ_de_texte.getText(), 
									telephone_champ_de_texte.getText(), adresse_champ_de_texte.getText(), ville_champ_de_texte.getText(), 
									code_postal_champ_de_texte.getText());
							
							if(empdata == true) {
								int emailStatus = ConfigurationDAO.getEmail();
								
								if(emailStatus == 1) {
									boolean emailSent = JeuneDAO.email_modification(email_champ_de_texte.getText());
									
									if(emailSent == false) {
										Alert a1 = new Alert(Alert.AlertType.ERROR);
										a1.setTitle("Erreur: n°7");
										a1.setContentText("L'envoi d'email ne fonctionne pas vous pouvez désactiver la fonctionnalité dans le menu de configuration.");
										a1.setHeaderText(null);
										a1.showAndWait();
									}
								}
								
								try {
									mainPane.getChildren().clear();
									FXMLLoader loader = new FXMLLoader();
									loader.setLocation(Main.class.getClassLoader().getResource("view/Jeune.fxml"));
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
								a1.setTitle("Erreur: n°n°6");
								a1.setContentText("Erreur lors de l'ajout d'un partenaire.");
								a1.setHeaderText(null);
								a1.showAndWait();
							}
						}else {
							Alert a1 = new Alert(Alert.AlertType.ERROR);
							a1.setTitle("Erreur: n°5");
							a1.setContentText("Le code postal devrait avoir un maximum de 5 caractères.");
							a1.setHeaderText(null);
							a1.showAndWait();
						}
					}else {
						Alert a1 = new Alert(Alert.AlertType.ERROR);
						a1.setTitle("Erreur: n°4");
						a1.setContentText("La ville devrait avoir un maximum de 32 caractères.");
						a1.setHeaderText(null);
						a1.showAndWait();
					}
				}else {
					Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur: n°3");
					a1.setContentText("L'addresse devrait avoir un maximum de 38 caractères.");
					a1.setHeaderText(null);
					a1.showAndWait();
				}
			}else {
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: n°2");
				a1.setContentText("Le n° de téléphone devrait avoir 10 chiffre.");
				a1.setHeaderText(null);
				a1.showAndWait();
			}
		}else {
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: n°1");
			a1.setContentText("Le format de l'email est incorrect.");
			a1.setHeaderText(null);
			a1.showAndWait();
		}
	}
}
