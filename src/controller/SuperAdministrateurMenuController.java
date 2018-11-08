package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SuperAdministrateurMenuController {
	@FXML
	private Button deconnexion_bouton;
	@FXML
	private Button fermeture_bouton;
	@FXML
	private Button administrateur_bouton;
	@FXML
	private Button jeune_bouton;
	@FXML
	private Button partenaire_bouton;
	@FXML
	private Button offre_bouton;
	@FXML
	private Button statistique_bouton; 
	@FXML
	private Label nom_champ_de_texte; 
	@FXML
	private Label salutation_label;
	@FXML
	private AnchorPane mainPane;
	String nom;
	boolean super_administrateur;
	
	private double xOffset;
	private double yOffset;
	
	public void nom(String nom) {
		this.nom = nom;
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		nom_champ_de_texte.setText(nom);
		
		if(hours >= 5 && hours < 18) {
			salutation_label.setText("Bonjour");
		}else{
			salutation_label.setText("Bonsoir");
		}
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
	}
	
	@FXML
	private void deconnecter(ActionEvent actionEvent) {	
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
	private void fermer(ActionEvent actionEvent) {
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void administrateur(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Administrateur.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			AdministrateurController administrateur_controller = loader.<AdministrateurController>getController();
			administrateur_controller.nom(this.nom);
			administrateur_controller.super_administrateur(this.super_administrateur);
		 }catch(IOException e) {	
			 e.printStackTrace();
		   	}
	}
	
	@FXML
	private void jeune(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Jeune.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			JeuneController jeune_controller = loader.<JeuneController>getController();
			jeune_controller.nom(this.nom);
			jeune_controller.super_administrateur(this.super_administrateur);
		 }catch(IOException e) {	
			 e.printStackTrace();
		   	}
	}	
		
	@FXML
	private void partenaire(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Partenaire.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			PartenaireController partenaire_controller = loader.<PartenaireController>getController();
			partenaire_controller.nom(this.nom);
			partenaire_controller.super_administrateur(this.super_administrateur);
		 }catch(IOException e) {	
			 e.printStackTrace();
		   	}
	}	
		
	@FXML
	private void offre(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Offre.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			OffreController offre_controller = loader.<OffreController>getController();
			offre_controller.nom(this.nom);
			offre_controller.super_administrateur(this.super_administrateur);
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
		
	@FXML
	private void statistique(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Statistique.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			StatistiqueController statistique_controller = loader.<StatistiqueController>getController();
			statistique_controller.nom(this.nom);
			statistique_controller.super_administrateur(this.super_administrateur);
		}catch(IOException e){
			e.printStackTrace();
			}
	}
	
	@FXML
    private void initialize () throws SQLException  
    {        
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
