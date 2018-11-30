package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import models.base.Partenaire;
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
	private AnchorPane mainPane;
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button retourButton;
	@FXML
	private Button inscriptionButton;
	@FXML
	private Button modificationButton;
	@FXML
	private Button rechercheGeneralButton;
	@FXML
	private Button rechercheFiltreButton;
	@FXML
	private Button suppressionButton;
	@FXML
	private TextField rechercheInput;
	@FXML
	private TableView<Partenaire> table;
	@FXML
	private TableColumn<Partenaire, Integer> siretColumn;
	@FXML
	private TableColumn<Partenaire, String> nomColumn;
	@FXML
	private TableColumn<Partenaire, String> emailColumn;
	@FXML
	private TableColumn<Partenaire, String> telephoneColumn;
	@FXML
	private TableColumn<Partenaire, String> derniereConnexionColumn;
	@FXML
	private TableColumn<Partenaire,	String>	creationColumn;
	
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
	private void retourrner_dans_la_page_precedente(ActionEvent actionEvent) {
		if(statusSuperAdministrateur == true) {
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
	private void recherche_general(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Partenaire> listeDesPartenaires = PartenaireDAO.obtenir_la_liste_de_tout_les_partenaires();
        table.setItems(listeDesPartenaires);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Partenaire> listeDesPartenaires = PartenaireDAO.obtenir_la_liste_filtre_de_tout_les_partenaires(rechercheInput.getText());
        table.setItems(listeDesPartenaires);
	}
		
	@FXML
	private void accerder_a_la_page_d_inscription_des_partenaires(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/PartenaireInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			PartenaireInscriptionController partenaireInscriptionController = loader.<PartenaireInscriptionController>getController();
			partenaireInscriptionController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			partenaireInscriptionController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
	
	@FXML
	private void acceder_a_la_page_de_modification_du_partenaire_selectionne(ActionEvent actionEvent) {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Partenaire partenaire = table.getSelectionModel().getSelectedItem();
	        int id = partenaire.getPartenaire_id();
	        int siret = partenaire.getPartenaire_siret();
	        String nom = partenaire.getPartenaire_nom();
	        String email = partenaire.getPartenaire_email();
	        String telephone = partenaire.getPartenaire_telephone();
	        String adresse = partenaire.getPartenaire_adresse();
	        String ville = partenaire.getPartenaire_ville();
	        String code_postal = partenaire.getPartenaire_code_postal();
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/PartenaireModification.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				PartenaireModificationController partenaireModificationController = loader.<PartenaireModificationController>getController();
				partenaireModificationController.recuperer_les_information_d_partenaire_selectionne(id, siret, nom, email, telephone, adresse, ville, code_postal);
				partenaireModificationController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				partenaireModificationController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
	
	@FXML
	private void supprimer_le_partenaire_selectionne(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Partenaire partenaireSelectionne = table.getSelectionModel().getSelectedItem();
	        //int id = partenaireSelectionne.getPartenaire_id();
	        PartenaireDAO.supprimer_un_partenaire(partenaireSelectionne.getPartenaire_id());
	    }
	}
		
	@FXML
	private void initialize () throws ClassNotFoundException, SQLException  {
		ObservableList<Partenaire> empData = PartenaireDAO.obtenir_la_liste_de_tout_les_partenaires();
		table.setItems(empData);
	    siretColumn.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_siret_Prop().asObject());
	    nomColumn.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_nom_Prop());
	    emailColumn.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_email_Prop());
	    telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_telephone_Prop());
	    derniereConnexionColumn.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_derniere_connexion_Prop());
	    creationColumn.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_creation_Prop());
	    
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
