package controllers;

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

public class MenuController{
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button menuGestionJeuneButton;
	@FXML
	private Button menuGestionPartenaireButton;
	@FXML
	private Button menuGestionOffreButton;
	@FXML
	private Button menuGestionStatistiqueButon; 
	@FXML
	private Label nomLabel; 
	@FXML
	private Label salutationLabel;

	String nomDeLaPersonneConnecte;
	boolean statusSuperAdministrateur;
	
	private double xOffset;
	private double yOffset;
	
	public void recuperer_le_nom_de_la_personne_connecte(String nomDeLaPersonneConnecte) {
		this.nomDeLaPersonneConnecte = nomDeLaPersonneConnecte;
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		nomLabel.setText(nomDeLaPersonneConnecte);
		
		if(hours >= 5 && hours < 18) {
			salutationLabel.setText("Bonjour");
		}else{
			salutationLabel.setText("Bonsoir");
		}
	}
	
	public void recuperer_le_status_super_administrateur_de_la_personne_connecte(boolean statusSuperAdministrateur) {
		this.statusSuperAdministrateur = statusSuperAdministrateur;
	}
	
	@FXML
	private void deconnecter_l_utilisateur(ActionEvent actionEvent) {	
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
	private void acceder_au_menu_de_gestion_des_jeunes(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Jeune.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			JeuneController jeuneController = loader.<JeuneController>getController();
			jeuneController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			jeuneController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		 }catch(IOException e) {	
			 e.printStackTrace();
		   	}
	}	
		
	@FXML
	private void acceder_au_menu_de_gestion_des_partenaires(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Partenaire.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			PartenaireController partenaireController = loader.<PartenaireController>getController();
			partenaireController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			partenaireController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		 }catch(IOException e) {	
			 e.printStackTrace();
		   	}
	}	
		
	@FXML
	private void acceder_au_menu_de_gestion_des_offres(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Offre.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			OffreController offreController = loader.<OffreController>getController();
			offreController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			offreController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
		
	@FXML
	private void acceder_au_menu_de_gestion_des_statistiques(ActionEvent actionEvent){
		try{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Statistique.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			StatistiqueController statistiqueController = loader.<StatistiqueController>getController();
			statistiqueController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			statistiqueController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		}catch(IOException e){
			e.printStackTrace();
			}
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 xOffset = event.getSceneX();
				 yOffset = event.getSceneY();
				 
				 System.out.println("xOffset: " + xOffset);
				 System.out.println("yOffset: " + yOffset);
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