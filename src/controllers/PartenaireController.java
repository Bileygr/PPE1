package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import classe.Partenaire;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import models.dao.PartenaireDAO;

public class PartenaireController 
{
	@FXML
	private Button deconnexion_bouton;
	@FXML
	private Button fermeture_bouton;
	@FXML
	private Button retour_bouton;
	@FXML
	private Button inscrire_bouton;
	@FXML
	private Button modifier_bouton;
	@FXML
	private Button recherche_bouton;
	@FXML
	private Button recherche_filtre_bouton;
	@FXML
	private Button supprimer_bouton;
	@FXML
	private TextField recherche_champ_de_texte;
	@FXML
	private TableView<Partenaire> table;
	@FXML
	private TableColumn<Partenaire, Integer> siret_colonne;
	@FXML
	private TableColumn<Partenaire, String> nom_colonne;
	@FXML
	private TableColumn<Partenaire, String> email_colonne;
	@FXML
	private TableColumn<Partenaire, String> telephone_colonne;
	@FXML
	private TableColumn<Partenaire, String> derniere_connexion_colonne;
	@FXML
	private TableColumn<Partenaire,	String>	creation_colonne;
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
	private void retour(ActionEvent actionEvent) {
		if(super_administrateur == true) {
			try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("view/SuperAdministrateurMenu.fxml"));
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
				loader.setLocation(Main.class.getClassLoader().getResource("view/Menu.fxml"));
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
	private void recherche(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Partenaire> empData = PartenaireDAO.recherche();
        table.setItems(empData);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Partenaire> empData = PartenaireDAO.recherche_filtre(recherche_champ_de_texte.getText());
        table.setItems(empData);
	}
		
	@FXML
	private void inscrire(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/PartenaireInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			PartenaireInscriptionController partenaire_inscription_controller = loader.<PartenaireInscriptionController>getController();
			partenaire_inscription_controller.nom(this.nom);
			partenaire_inscription_controller.super_administrateur(this.super_administrateur);
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
	
	@FXML
	private void modifier(ActionEvent actionEvent) {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Partenaire partenaire = table.getSelectionModel().getSelectedItem();
	        int id 				= partenaire.getPartenaire_id();
	        int siret 			= partenaire.getPartenaire_siret();
	        String nom 			= partenaire.getPartenaire_nom();
	        String email 		= partenaire.getPartenaire_email();
	        String telephone 	= partenaire.getPartenaire_telephone();
	        String adresse	 	= partenaire.getPartenaire_adresse();
	        String ville		= partenaire.getPartenaire_ville();
	        String code_postal 	= partenaire.getPartenaire_code_postal();
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("view/PartenaireModification.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				PartenaireModificationController partenaire_modification_controller = loader.<PartenaireModificationController>getController();
				partenaire_modification_controller.partenaire(id, siret, nom, email, telephone, adresse, ville, code_postal);
				partenaire_modification_controller.nom(this.nom);
				partenaire_modification_controller.super_administrateur(this.super_administrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
	
	@FXML
	private void supprimer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Partenaire partenaire = table.getSelectionModel().getSelectedItem();
	        int id = partenaire.getPartenaire_id();
	        PartenaireDAO.supprimer(id);
	    }
	}
		
	@FXML
	private void initialize () throws ClassNotFoundException, SQLException  {
		ObservableList<Partenaire> empData = PartenaireDAO.recherche();
		table.setItems(empData);
	    siret_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_siret_Prop().asObject());
	    nom_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_nom_Prop());
	    email_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_email_Prop());
	    telephone_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_telephone_Prop());
	    derniere_connexion_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_derniere_connexion_Prop());
	    creation_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_creation_Prop());
	    
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
