package controller;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import dao.PartenaireDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
	
	public void nom(String nom) {
		this.nom = nom;
		System.out.println("Partenaire Modification: " + this.nom);
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
	private void modifier(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		boolean empdata = PartenaireDAO.modifier(Integer.parseInt(id_label.getText()), Integer.parseInt(siret_champ_de_texte.getText()), nom_champ_de_texte.getText(), 
				email_champ_de_texte.getText(), telephone_champ_de_texte.getText(), adresse_champ_de_texte.getText(), ville_champ_de_texte.getText(), 
				code_postal_champ_de_texte.getText());
		
		if(empdata == true) {
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
	}
}
