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
	
	public void nom(String nom) {
		this.nom = nom;
		System.out.println("Partenaire Inscription: " + this.nom);
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
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void inscrire(ActionEvent actionEvent) throws SQLException {
		if(!siret_champ_de_texte.getText().isEmpty() &&  !nom_champ_de_texte.getText().isEmpty() && !mot_de_passe_champ_de_texte.getText().isEmpty() 
				&& !email_champ_de_texte.getText().isEmpty() && !telephone_champ_de_texte.getText().isEmpty() && !adresse_champ_de_texte.getText().isEmpty() 
				&& !ville_champ_de_texte.getText().isEmpty() && !code_postal_champ_de_texte.getText().isEmpty()) {
			
			boolean empdata = PartenaireDAO.inscrire(Integer.parseInt(siret_champ_de_texte.getText()), nom_champ_de_texte.getText(), mot_de_passe_champ_de_texte.getText(), 
					email_champ_de_texte.getText(), telephone_champ_de_texte.getText(), adresse_champ_de_texte.getText(), ville_champ_de_texte.getText(), code_postal_champ_de_texte.getText());
		
			if(empdata == true) {
				try {
					mainPane.getChildren().clear();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getClassLoader().getResource("view/Partenaire.fxml"));
					AnchorPane userFrame = (AnchorPane) loader.load();
					Scene sc = mainPane.getScene();
					sc.setRoot(userFrame);
					System.out.println();
				
					MenuController menu_controller = loader.<MenuController>getController();
					menu_controller.nom(this.nom);
				}catch (IOException e) {
					e.printStackTrace();
					}
			}else {
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: 2");
				a1.setContentText("L'inscription a échoué dû a une erreur de connexion.");
				a1.setHeaderText(null);
				a1.showAndWait();
			}
		}else {
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: 1");
			a1.setContentText("L'un ou les deux champs sont vide.");
			a1.setHeaderText(null);
			a1.showAndWait();
		}
	}
}
