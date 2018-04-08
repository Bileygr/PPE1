package controller;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import dao.OffreDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class OffreDescriptionController {
	@FXML
	private Button			deconnexion_bouton; 	
	@FXML
	private Button			retour_bouton;
	@FXML
	private Button			supprimer_bouton;
	@FXML
	private Label			id_label;
	@FXML
	private Label			nom_label;
	@FXML
	private Label			partenaire_label;
	@FXML
	private Label			formation_label;
	@FXML
	private Label			debut_label;
	@FXML
	private Label			fin_label;
	@FXML
	private TextArea		description_text_area;
	@FXML
	private AnchorPane  	mainPane;
	String nom;
	boolean super_administrateur;
	
	public void nom(String nom) {
		this.nom = nom;
		System.out.println("Offre Description: " + this.nom);
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
		System.out.println("Offre description (super administrateur): " + super_administrateur);
	}
	
	public void offre(int id, String nom, String formation, String partenaire, String description, String debut, String fin) {
		id_label.setText("ID: " + Integer.toString(id));
		nom_label.setText(nom);
		partenaire_label.setText("Offert par : " + formation);
		formation_label.setText("Type de formation : " + partenaire);
		description_text_area.setText(description);
		debut_label.setText("Début : " + debut);
		fin_label.setText("Fin : " + fin);
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
			loader.setLocation(Main.class.getClassLoader().getResource("view/Offre.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			System.out.println();
			
			OffreController offre_controller = loader.<OffreController>getController();
			offre_controller.nom(this.nom);
			offre_controller.super_administrateur(this.super_administrateur);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void supprimer(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		boolean empdata = OffreDAO.supprimer(Integer.parseInt(id_label.getText()));
		
		if(empdata == true) {
			try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("view/Offre.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				System.out.println();
			}catch (IOException e) {
			   e.printStackTrace();
			  }
		}
	}
}
