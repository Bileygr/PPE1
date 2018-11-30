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
	private AnchorPane mainPane;
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button retourButton;
	@FXML
	private Button rechercheGeneralButton;
	@FXML
	private Button rechercheFiltreButton;
	@FXML
	private Button descriptionButton;
	@FXML
	private Button suppressionButton;
	@FXML
	private TextField rechercheInput;
	@FXML
	private TableView<Offre> table;
	@FXML
	private TableColumn<Offre, String> formationColumn;
	@FXML
	private TableColumn<Offre, String> partenaireColumn;
	@FXML
	private TableColumn<Offre, String> nomColumn;
	@FXML
	private TableColumn<Offre, String> debutColumn;
	@FXML
	private TableColumn<Offre, String> finColumn;

	String nomDeLaPersonneConnecte;
	boolean statusSuperAdministrateur;
	
	private double xOffset;
	private double yOffset;
	
	public void recuperer_le_nom_de_la_personne_connecte(String nomDeLaPersonneConnecte) {
		this.nomDeLaPersonneConnecte = nomDeLaPersonneConnecte;
	}
	
	public void recuperer_le_status_super_administrateur_de_la_personne_connecte(boolean statusSuperAdministrateur) {
		this.statusSuperAdministrateur = statusSuperAdministrateur;
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
		if(this.statusSuperAdministrateur == true) {
			try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/SuperAdministrateurMenu.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				SuperAdministrateurMenuController superAdministrateurMenuController = loader.<SuperAdministrateurMenuController>getController();
				superAdministrateurMenuController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				superAdministrateurMenuController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
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
				MenuController menuController = loader.<MenuController>getController();
				menuController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				menuController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
			}catch (IOException e) {
			   e.printStackTrace();
			  }
		}
		
	}
		
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {	
		ObservableList<Offre> listeDesOffres = OffreDAO.obtenir_la_liste_filtre_de_toutes_les_offres(rechercheInput.getText());
		table.setItems(listeDesOffres);
			
	}
		
	@FXML
	private void recherche_general(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		ObservableList<Offre> listeDesOffres = OffreDAO.obtenir_la_liste_de_toutes_les_offres();
		table.setItems(listeDesOffres);
	}
	
	@FXML
	private void acceder_a_la_page_de_description_de_l_offre_selectionne(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Offre offreSelectionne = table.getSelectionModel().getSelectedItem();
	        int id = offreSelectionne.getOffre_id();
	        String nom = offreSelectionne.getOffre_nom();
	        String partenaire = offreSelectionne.getPartenaire_nom();
	        String formation = offreSelectionne.getFormation_nom();
	        String description = offreSelectionne.getOffre_description();
	        String debut = offreSelectionne.getOffre_debut();
	        String fin = offreSelectionne.getOffre_fin();
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/OffreDescription.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				OffreDescriptionController offreDescriptionController = loader.<OffreDescriptionController>getController();
				offreDescriptionController.recuperer_les_informations_de_l_offre_selectionne(id, nom, partenaire, formation, description, debut, fin);
				offreDescriptionController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				offreDescriptionController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
		
	@FXML
	private void supprimer_l_offre_selectionne(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Offre offreSelectionne = table.getSelectionModel().getSelectedItem();
	        int id = offreSelectionne.getOffre_id();
	        OffreDAO.supprimer_une_offre(id);
	    }
	}
		
	@FXML
	private void initialize () throws ClassNotFoundException, SQLException  {
		ObservableList<Offre> listeDesOffres = OffreDAO.obtenir_la_liste_de_toutes_les_offres();
	    table.setItems(listeDesOffres);
	    formationColumn.setCellValueFactory(cellData -> cellData.getValue().getFormation_nom_Prop());
		partenaireColumn.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_nom_Prop());
		nomColumn.setCellValueFactory(cellData -> cellData.getValue().getOffre_nom_Prop());
		debutColumn.setCellValueFactory(cellData -> cellData.getValue().getOffre_debut_Prop());
		finColumn.setCellValueFactory(cellData -> cellData.getValue().getOffre_fin_Prop());
		
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
				Main.obtenir_le_primaryStage().setX(event.getScreenX()- xOffset);
				Main.obtenir_le_primaryStage().setY(event.getScreenY()- yOffset);
			}
		});
	}
}
