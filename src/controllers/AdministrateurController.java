package controllers;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import models.base.Administrateur;
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
import models.dao.AdministrateurDAO;

public class AdministrateurController {
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button retourButton;
	@FXML
	private Button rechercheGeneralButton;
	@FXML
	private Button rechercheFiltreButton;
	@FXML
	private Button inscriptionButton;
	@FXML
	private Button modificationButton;
	@FXML
	private Button suppressionButton;
	@FXML
	private TextField rechercheInput;
	@FXML
	private TableView<Administrateur> table;
	@FXML
	private TableColumn<Administrateur, String> nomColumn;
	@FXML
	private TableColumn<Administrateur, String> prenomColumn;
	@FXML
	private TableColumn<Administrateur, String> statusSuperAdministrateurColumn;
	@FXML
	private TableColumn<Administrateur, String> emailColumn;
	@FXML
	private TableColumn<Administrateur, String> telephoneColumn;
	@FXML
	private TableColumn<Administrateur, String> derniereConnexionColumn;
	@FXML
	private TableColumn<Administrateur, String> creationColumn;
	
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
	private void retourner_dans_la_page_precedente(ActionEvent actionEvent) {
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
	}
	
	@FXML
	private void recherche_general(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Administrateur> empData = AdministrateurDAO.lister_tout_les_administrateurs();
        table.setItems(empData);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Administrateur> empData = AdministrateurDAO.recherche_filtre(rechercheInput.getText());
        table.setItems(empData);
	}
		
	@FXML
	private void acceder_a_la_page_d_inscipriton_d_un_administrateur(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/AdministrateurInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			AdministrateurInscriptionController administrateurInscriptionController = loader.<AdministrateurInscriptionController>getController();
			administrateurInscriptionController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			administrateurInscriptionController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
	
	@FXML
	private void modifier_les_information_d_un_administrateur(ActionEvent actionEvent) {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Administrateur administrateur = table.getSelectionModel().getSelectedItem();
	        int id = administrateur.getAdministrateur_id();
	        String super_administrateur = administrateur.getAdministrateur_super();
	        String nom = administrateur.getAdministrateur_nom();
	        String prenom = administrateur.getAdministrateur_prenom();
	        String email = administrateur.getAdministrateur_email();
	        String telephone = administrateur.getAdministrateur_telephone();
	        String adresse = administrateur.getAdministrateur_adresse();
	        String ville = administrateur.getAdministrateur_ville();
	        String code_postal = administrateur.getAdministrateur_code_postal();
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/AdministrateurModification.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				AdministrateurModificationController administrateurModificationController = loader.<AdministrateurModificationController>getController();
				administrateurModificationController.recuperer_les_informations_de_l_administrateur_a_modifier(id, super_administrateur, nom, prenom, email, telephone, adresse, ville, code_postal);
				administrateurModificationController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				administrateurModificationController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
	
	@FXML
	private void suppression_d_un_administrateur(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Administrateur administrateur_selectionne = table.getSelectionModel().getSelectedItem();
	        int id = administrateur_selectionne.getAdministrateur_id();
	        AdministrateurDAO.supprimer_un_administrateur(id);
	    }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		ObservableList<Administrateur> empData = AdministrateurDAO.lister_tout_les_administrateurs();
        table.setItems(empData);
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_nom_Prop());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_prenom_Prop());
        statusSuperAdministrateurColumn.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_super_Prop());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_email_Prop());
        telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_telephone_Prop());
        derniereConnexionColumn.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_derniere_connexion_Prop());
        creationColumn.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_derniere_connexion_Prop());
        
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
