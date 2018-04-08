package controller;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import dao.PartenaireDAO;
import encryption.BCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PartenaireInscriptionController {
	@FXML
	private Button 						deconnexion_bouton;
	@FXML
	private Button						retour_bouton;
	@FXML
	private Button						inscrire_bouton;
	@FXML
	private TextField					siret_champ_de_texte;
	@FXML
	private TextField					nom_champ_de_texte;
	@FXML
	private TextField					mot_de_passe_champ_de_texte;
	@FXML
	private TextField					email_champ_de_texte;
	@FXML
	private TextField					telephone_champ_de_texte;
	@FXML
	private TextField					adresse_champ_de_texte;
	@FXML
	private TextField					ville_champ_de_texte;
	@FXML
	private TextField					code_postal_champ_de_texte;
	@FXML
	private AnchorPane mainPane;
	String nom;
	boolean super_administrateur;
	
	public void nom(String nom) {
		this.nom = nom;
		System.out.println("Partenaire Inscription: " + this.nom);
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
		System.out.println("Partenaire inscription (super administrateur): " + super_administrateur);
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
			System.out.println();
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
			System.out.println();
			
			PartenaireController partenaire_controller = loader.<PartenaireController>getController();
			partenaire_controller.nom(this.nom);
			partenaire_controller.super_administrateur(this.super_administrateur);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void inscrire(ActionEvent actionEvent) throws SQLException {
		if(!siret_champ_de_texte.getText().isEmpty() &&  !nom_champ_de_texte.getText().isEmpty() && !mot_de_passe_champ_de_texte.getText().isEmpty() 
				&& !email_champ_de_texte.getText().isEmpty() && !telephone_champ_de_texte.getText().isEmpty() && !adresse_champ_de_texte.getText().isEmpty() 
				&& !ville_champ_de_texte.getText().isEmpty() && !code_postal_champ_de_texte.getText().isEmpty()) {
			boolean email_validation = PartenaireDAO.validate(email_champ_de_texte.getText());
			
			String mot_de_passe = mot_de_passe_champ_de_texte.getText();
			String hash = BCrypt.hashpw(mot_de_passe_champ_de_texte.getText(), BCrypt.gensalt());
			
			System.out.println("Nom: " + nom_champ_de_texte.getText());
			System.out.println("Mot de passe clair : " + mot_de_passe);
			System.out.println("Mot de passe hashe :" + hash + " Nombre de caractères: " + hash.length());
			
			if(siret_champ_de_texte.getText().length() == 9) {
				
				if(mot_de_passe_champ_de_texte.getText().length() >= 12) {
				
					if(email_validation == true) {
					
						if(telephone_champ_de_texte.getText().length() == 10) {
						
							if(adresse_champ_de_texte.getText().length() <= 38) {
							
								if(ville_champ_de_texte.getText().length() <= 32) {
								
									if(code_postal_champ_de_texte.getText().length() == 5) {
										boolean empdata = PartenaireDAO.inscrire(Integer.parseInt(siret_champ_de_texte.getText()), nom_champ_de_texte.getText(), hash, 
												email_champ_de_texte.getText(), telephone_champ_de_texte.getText(), adresse_champ_de_texte.getText(), ville_champ_de_texte.getText(), code_postal_champ_de_texte.getText());
		
										if(empdata == true) {
											PartenaireDAO.email_inscription(email_champ_de_texte.getText());
											
											try {
												mainPane.getChildren().clear();
												FXMLLoader loader = new FXMLLoader();
												loader.setLocation(Main.class.getClassLoader().getResource("view/Partenaire.fxml"));
												AnchorPane userFrame = (AnchorPane) loader.load();
												Scene sc = mainPane.getScene();
												sc.setRoot(userFrame);
												System.out.println();
				
												PartenaireController partenaire_controller = loader.<PartenaireController>getController();
												partenaire_controller.nom(this.nom);
												partenaire_controller.super_administrateur(this.super_administrateur);
											}catch (IOException e) {
												e.printStackTrace();
											}
										}else {
											Alert a1 = new Alert(Alert.AlertType.ERROR);
											a1.setTitle("Erreur: n°9");
											a1.setContentText("Erreur lors de l'ajout d'un partenaire.");
											a1.setHeaderText(null);
											a1.showAndWait();
										}
									}else {
										Alert a1 = new Alert(Alert.AlertType.ERROR);
										a1.setTitle("Erreur: n°8");
										a1.setContentText("Le code postal devrait avoir un maximum de 5 caractères.");
										a1.setHeaderText(null);
										a1.showAndWait();
									}
								}else {
									Alert a1 = new Alert(Alert.AlertType.ERROR);
									a1.setTitle("Erreur: n°7");
									a1.setContentText("La ville devrait avoir un maximum de 32 caractères.");
									a1.setHeaderText(null);
									a1.showAndWait();
								}
							}else {
								Alert a1 = new Alert(Alert.AlertType.ERROR);
								a1.setTitle("Erreur: n°6");
								a1.setContentText("L'addresse devrait avoir un maximum de 38 caractères.");
								a1.setHeaderText(null);
								a1.showAndWait();
							}
						}else {
							Alert a1 = new Alert(Alert.AlertType.ERROR);
							a1.setTitle("Erreur: n°5");
							a1.setContentText("Le n° de téléphone devrait avoir 10 chiffre.");
							a1.setHeaderText(null);
							a1.showAndWait();
						}
					}else {
						Alert a1 = new Alert(Alert.AlertType.ERROR);
						a1.setTitle("Erreur: n°4");
						a1.setContentText("Le format de l'email est incorrect.");
						a1.setHeaderText(null);
						a1.showAndWait();
					}
				}else {
					Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur: n°3");
					a1.setContentText("Le mot de passe devrait avoir au moins 12 caractères.");
					a1.setHeaderText(null);
					a1.showAndWait();
				}
			}else {
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: n°2");
				a1.setContentText("Le SIRET devrait avoir 9 chiffres.");
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
}