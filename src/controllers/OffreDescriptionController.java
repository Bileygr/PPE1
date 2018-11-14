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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.dao.OffreDAO;

public class OffreDescriptionController {
	@FXML
	private Button deconnexion_bouton; 	
	@FXML
	private Button fermeture_bouton; 	
	@FXML
	private Button retour_bouton;
	@FXML
	private Button supprimer_bouton;
	@FXML
	private Label id_label;
	@FXML
	private Label nom_label;
	@FXML
	private Label partenaire_label;
	@FXML
	private Label formation_label;
	@FXML
	private Label debut_label;
	@FXML
	private Label fin_label;
	@FXML
	private TextArea description_text_area;
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
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Offre.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
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
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Offre.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
			}catch (IOException e) {
			   e.printStackTrace();
			  }
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
				Main.getPrimaryStage().setX(event.getScreenX()- xOffset);
				Main.getPrimaryStage().setY(event.getScreenY()- yOffset);
			}
		});
	}
}
