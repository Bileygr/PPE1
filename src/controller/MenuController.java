package controller;

import java.io.IOException;
import java.util.Calendar;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MenuController{
	@FXML
	private Button 						deconnexion_bouton;
	@FXML
	private Button 						menu_bouton;
	@FXML
	private Button 						jeune_bouton;
	@FXML
	private Button 						partenaire_bouton;
	@FXML
	private Button 						offre_bouton;
	@FXML
	private Button 						statistique_bouton; 
	@FXML
	private Label 						nom_champ_de_texte; 
	@FXML
	private Label						salutation_label;
	@FXML
	private AnchorPane mainPane;
	String nom;
	boolean super_administrateur;
	
	public void nom(String nom) {
		this.nom = nom;
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		nom_champ_de_texte.setText(nom); 
		
		System.out.println("Heure: " + hours);
		System.out.println("Menu: " + this.nom);
		
		if(hours >= 5 && hours < 18) {
			salutation_label.setText("Bonjour");
		}else{
			salutation_label.setText("Bonsoir");
		}
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
		System.out.println("Menu (super administrateur): " + super_administrateur);
	}
	
	@FXML
	private void deconnexion(ActionEvent actionEvent){	
		try{
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
	private void jeune(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Jeune.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			System.out.println();
			
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
			System.out.println();
			
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
			System.out.println();
			
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
			System.out.println();
			
			StatistiqueController statistique_controller = loader.<StatistiqueController>getController();
			statistique_controller.nom(this.nom);
			statistique_controller.super_administrateur(this.super_administrateur);
		}catch(IOException e){
			e.printStackTrace();
			}
	}
	
}