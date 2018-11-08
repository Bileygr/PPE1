package controller;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import classe.Administrateur;
import dao.AdministrateurDAO;
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

public class AdministrateurController {
	@FXML
	private Button fermeture_bouton;
	@FXML
	private Button retour_bouton;
	@FXML
	private Button jeune_bouton;
	@FXML
	private Button partenaire_bouton;
	@FXML
	private Button offre_bouton;
	@FXML
	private Button statistique_bouton; 
	@FXML
	private Button recherche_bouton;
	@FXML
	private Button recherche_filtre_bouton;
	@FXML
	private Button inscrire_bouton;
	@FXML
	private Button modifier_bouton;
	@FXML
	private Button supprimer_bouton;
	@FXML
	private TextField recherche_champ_de_texte;
	@FXML
	private TableView<Administrateur> table;
	@FXML
	private TableColumn<Administrateur, String> nom_colonne;
	@FXML
	private TableColumn<Administrateur, String> prenom_colonne;
	@FXML
	private TableColumn<Administrateur, String> email_colonne;
	@FXML
	private TableColumn<Administrateur, String> telephone_colonne;
	@FXML
	private TableColumn<Administrateur, String> derniere_connexion_colonne;
	@FXML
	private TableColumn<Administrateur, String> creation_colonne;
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
	}
	
	@FXML
	private void recherche(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Administrateur> empData = AdministrateurDAO.recherche();
        table.setItems(empData);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Administrateur> empData = AdministrateurDAO.recherche_filtre(recherche_champ_de_texte.getText());
        table.setItems(empData);
	}
		
	@FXML
	private void inscrire(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/AdministrateurInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			AdministrateurInscriptionController administrateur_inscription_controller = loader.<AdministrateurInscriptionController>getController();
			administrateur_inscription_controller.nom(this.nom);
			administrateur_inscription_controller.super_administrateur(this.super_administrateur);
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
	
	@FXML
	private void modifier(ActionEvent actionEvent) {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Administrateur administrateur = table.getSelectionModel().getSelectedItem();
	        int id 						= administrateur.getAdministrateur_id();
	        int super_administrateur	= administrateur.getAdministrateur_super();
	        String nom 					= administrateur.getAdministrateur_nom();
	        String prenom 				= administrateur.getAdministrateur_prenom();
	        String email 				= administrateur.getAdministrateur_email();
	        String telephone 			= administrateur.getAdministrateur_telephone();
	        String adresse	 			= administrateur.getAdministrateur_adresse();
	        String ville				= administrateur.getAdministrateur_ville();
	        String code_postal 			= administrateur.getAdministrateur_code_postal();
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("view/AdministrateurModification.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				AdministrateurModificationController administrateur_modification_controller = loader.<AdministrateurModificationController>getController();
				administrateur_modification_controller.administrateur(id, super_administrateur, nom, prenom, email, telephone, adresse, ville, code_postal);
				administrateur_modification_controller.nom(this.nom);
				administrateur_modification_controller.super_administrateur(this.super_administrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
	
	@FXML
	private void supprimer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Administrateur administrateur_selectionne = table.getSelectionModel().getSelectedItem();
	        int id = administrateur_selectionne.getAdministrateur_id();
	        AdministrateurDAO.supprimer(id);
	    }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		ObservableList<Administrateur> empData = AdministrateurDAO.recherche();
        table.setItems(empData);
        nom_colonne.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_nom_Prop());
        prenom_colonne.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_prenom_Prop());
        email_colonne.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_email_Prop());
        telephone_colonne.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_telephone_Prop());
        derniere_connexion_colonne.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_derniere_connexion_Prop());
        creation_colonne.setCellValueFactory(cellData -> cellData.getValue().getAdministrateur_derniere_connexion_Prop());
        
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
