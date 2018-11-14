package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import models.base.Offre;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.dao.OffreDAO;

public class OffreController 
{
	@FXML
	private Button deconnexion_bouton;
	@FXML
	private Button fermeture_bouton;
	@FXML
	private Button retour_bouton;
	@FXML
	private Button recherche_bouton;
	@FXML
	private Button recherche_filtre_bouton;
	@FXML
	private Button description_bouton;
	@FXML
	private Button supprimer_bouton;
	@FXML
	private TextField recherche_champ_de_texte;
	@FXML
	private TableView<Offre> table;
	@FXML
	private TableColumn<Offre, String> formation_colonne;
	@FXML
	private TableColumn<Offre, String> partenaire_colonne;
	@FXML
	private TableColumn<Offre, String> nom_colonne;
	@FXML
	private TableColumn<Offre, String> debut_colonne;
	@FXML
	private TableColumn<Offre, String> fin_colonne;
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
		this.super_administrateur=super_administrateur;
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
		if(this.super_administrateur == true) {
			try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/SuperAdministrateurMenu.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				SuperAdministrateurMenuController super_administrateur_menu_controller = loader.<SuperAdministrateurMenuController>getController();
				super_administrateur_menu_controller.nom(this.nom);
				super_administrateur_menu_controller.super_administrateur(this.super_administrateur);
		}catch (IOException e) {
		   e.printStackTrace();
		  }
		}else {
			try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Menu.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				MenuController menu_controller = loader.<MenuController>getController();
				menu_controller.nom(this.nom);
				menu_controller.super_administrateur(this.super_administrateur);
			}catch (IOException e) {
			   e.printStackTrace();
			  }
		}
		
	}
		
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {	
		ObservableList<Offre> empData = OffreDAO.recherche_filtre(recherche_champ_de_texte.getText());
		table.setItems(empData);
			
	}
		
	@FXML
	private void recherche(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		ObservableList<Offre> empData = OffreDAO.recherche();
		table.setItems(empData);
	}
	
	@FXML
	private void description(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Offre offre = table.getSelectionModel().getSelectedItem();
	        int id 				= offre.getOffre_id();
	        String nom 			= offre.getOffre_nom();
	        String partenaire 	= offre.getPartenaire_nom();
	        String formation 	= offre.getFormation_nom();
	        String description	= offre.getOffre_description();
	        String debut		= offre.getOffre_debut();
	        String fin			= offre.getOffre_fin();
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/OffreDescription.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				OffreDescriptionController offre_description_controller = loader.<OffreDescriptionController>getController();
				offre_description_controller.offre(id, nom, partenaire, formation, description, debut, fin);
				offre_description_controller.nom(this.nom);
				offre_description_controller.super_administrateur(this.super_administrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
		
	@FXML
	private void supprimer(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Offre offre = table.getSelectionModel().getSelectedItem();
	        int id = offre.getOffre_id();
	        OffreDAO.supprimer(id);
	    }
	}
		
	@FXML
	private void initialize () throws ClassNotFoundException, SQLException  {
		ObservableList<Offre> empData = OffreDAO.recherche();
	    table.setItems(empData);
	    formation_colonne.setCellValueFactory(cellData -> cellData.getValue().getFormation_nom_Prop());
		partenaire_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_nom_Prop());
		nom_colonne.setCellValueFactory(cellData -> cellData.getValue().getOffre_nom_Prop());
		debut_colonne.setCellValueFactory(cellData -> cellData.getValue().getOffre_debut_Prop());
		fin_colonne.setCellValueFactory(cellData -> cellData.getValue().getOffre_fin_Prop());
		
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
