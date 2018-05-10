package controller;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import dao.PartenaireDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PartenaireModificationController {
	@FXML
	private Button			deconnexion_bouton;
	@FXML
	private Button			retour_bouton;
	@FXML
	private Button			modifier_bouton;
	@FXML
	private Label			id_label;
	@FXML
	private TextField		siret_champ_de_texte;
	@FXML
	private TextField		nom_champ_de_texte;
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
	boolean super_administrateur;
	
	public void nom(String nom) {
		this.nom = nom;
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
	}
	
	public void partenaire(int id, int siret, String nom, String email, String telephone, String adresse, String ville, String code_postal) {
		id_label.setText(Integer.toString(id));
		siret_champ_de_texte.setText(Integer.toString(siret));
		nom_champ_de_texte.setText(nom);
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
			loader.setLocation(Main.class.getClassLoader().getResource("view/Partenaire.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			PartenaireController partenaire_controller = loader.<PartenaireController>getController();
			partenaire_controller.nom(this.nom);
			partenaire_controller.super_administrateur(this.super_administrateur);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void modifier(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		boolean email_validation = PartenaireDAO.validate(email_champ_de_texte.getText());
		
		if(siret_champ_de_texte.getText().length() == 9) {
			
			if(email_validation == true) {
				
				if(telephone_champ_de_texte.getText().length() == 10) {
					
					if(adresse_champ_de_texte.getText().length() <= 38) {
						
						if(ville_champ_de_texte.getText().length() <= 32) {
							
							if(code_postal_champ_de_texte.getText().length() == 5) {
								boolean empdata = PartenaireDAO.modifier(Integer.parseInt(id_label.getText()), Integer.parseInt(siret_champ_de_texte.getText()), nom_champ_de_texte.getText(), 
										email_champ_de_texte.getText(), telephone_champ_de_texte.getText(), adresse_champ_de_texte.getText(), ville_champ_de_texte.getText(), 
										code_postal_champ_de_texte.getText());
							
								if(empdata == true) {
									PartenaireDAO.email_modification(email_champ_de_texte.getText());
									
									try {
										mainPane.getChildren().clear();
										FXMLLoader loader = new FXMLLoader();
										loader.setLocation(Main.class.getClassLoader().getResource("view/Partenaire.fxml"));
										AnchorPane userFrame = (AnchorPane) loader.load();
										Scene sc = mainPane.getScene();
										sc.setRoot(userFrame);
										PartenaireController partenaire_controller = loader.<PartenaireController>getController();
										partenaire_controller.nom(this.nom);
										partenaire_controller.super_administrateur(this.super_administrateur);
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
}