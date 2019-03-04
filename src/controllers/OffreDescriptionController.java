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
	private AnchorPane mainPane;
	@FXML
	private Button deconnexionButton; 	
	@FXML
	private Button fermetureButton; 	
	@FXML
	private Button retourButton;
	@FXML
	private Button suppressionButton;
	@FXML
	private Label idLabel;
	@FXML
	private Label nomLabel;
	@FXML
	private Label partenaireLabel;
	@FXML
	private Label formationLabel;
	@FXML
	private Label debutLabel;
	@FXML
	private Label finLabel;
	@FXML
	private TextArea descriptionInput;

	String nomDeLaPersonneConnecte;
	String roles;
	String role_admin = "[\"ROLE_ADMINISTRATEUR\"]";
	String role_super_admin = "[\"ROLE_SUPER_ADMINISTRATEUR\"]";
	
	private double xOffset;
	private double yOffset;
	
	public void recuperer_le_nom_de_la_personne_connecte(String nomDeLaPersonneConnecte) 
	{
		this.nomDeLaPersonneConnecte = nomDeLaPersonneConnecte;
	}
	
	public void recuperer_le_status_super_administrateur_de_la_personne_connecte(String roles) 
	{
		this.roles = roles;
	}
	
	public void recuperer_les_informations_de_l_offre_selectionne(int id, String nom, String formation, String partenaire, String description, String debut, String fin) {
		idLabel.setText("ID: " + Integer.toString(id));
		nomLabel.setText(nom);
		partenaireLabel.setText("Offert par : " + formation);
		formationLabel.setText("Type de formation : " + partenaire);
		descriptionInput.setText(description);
		debutLabel.setText("Début : " + debut);
		finLabel.setText("Fin : " + fin);
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
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Offre.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			OffreController offreController = loader.<OffreController>getController();
			offreController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			offreController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	@FXML
	private void supprimer_l_offre(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		boolean verificationSuppressionOffre = OffreDAO.supprimer_une_offre(Integer.parseInt(idLabel.getText()));
		
		if(verificationSuppressionOffre == true) {
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
