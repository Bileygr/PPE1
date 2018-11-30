package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import models.base.Jeune;
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
import models.dao.JeuneDAO;

public class JeuneController {
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button retourButton;
	@FXML
	private Button jeuneButton;
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
	private TableView<Jeune> table;
	@FXML
	private TableColumn<Jeune, Integer> idColumn;
	@FXML
	private TableColumn<Jeune, String>  nomColumn;
	@FXML
	private TableColumn<Jeune, String> 	prenomColumn;
	@FXML
	private TableColumn<Jeune, String> 	emailColumn;
	@FXML
	private TableColumn<Jeune, String> 	telephoneColumn;
	@FXML
	private TableColumn<Jeune, String> 	derniereConnexionColumn;
	@FXML
	private TableColumn<Jeune, String> 	creationColumn;
	@FXML
	private AnchorPane mainPane;
	
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
		ObservableList<Jeune> listeDesJeunes = JeuneDAO.obtenir_la_liste_de_tout_les_jeunes();
        table.setItems(listeDesJeunes);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Jeune> listeFiltreDesJeunes = JeuneDAO.obtenir_la_liste_filtre_des_jeunes(rechercheInput.getText());
        table.setItems(listeFiltreDesJeunes);
	}
		
	@FXML
	private void acceder_a_la_page_d_inscription_des_jeunes(ActionEvent actionEvent) {
        try {
        	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/JeuneInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			JeuneInscriptionController jeuneInscriptionController = loader.<JeuneInscriptionController>getController();
			jeuneInscriptionController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			jeuneInscriptionController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void acceder_a_la_page_de_modification_des_informations_du_jeune_selectionne(ActionEvent actionEvent) {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Jeune jeune = table.getSelectionModel().getSelectedItem();
	        int id = jeune.getJeune_id();
	        String nom = jeune.getJeune_nom();
	        String prenom = jeune.getJeune_prenom();
	        String email = jeune.getJeune_email();
	        String telephone = jeune.getJeune_telephone();
	        String adresse = jeune.getJeune_adresse();
	        String ville = jeune.getJeune_ville();
	        String code_postal = jeune.getJeune_code_postal();
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/JeuneModification.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				JeuneModificationController jeuneModificationController = loader.<JeuneModificationController>getController();
				jeuneModificationController.obtenir_les_informations_du_jeune_a_modifier(id, nom, prenom, email, telephone, adresse, ville, code_postal);
				jeuneModificationController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				jeuneModificationController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.statusSuperAdministrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
	
	@FXML
	private void supprimmer_un_jeune(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Jeune jeuneSelectionne = table.getSelectionModel().getSelectedItem();
	        int id = jeuneSelectionne.getJeune_id();
	        JeuneDAO.supprimer_un_jeune(id);
	    }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		ObservableList<Jeune> empData = JeuneDAO.obtenir_la_liste_de_tout_les_jeunes();
        table.setItems(empData);
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getJeune_nom_Prop());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getJeune_prenom_Prop());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getJeune_email_Prop());
        telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().getJeune_telephone_Prop());
        derniereConnexionColumn.setCellValueFactory(cellData -> cellData.getValue().getJeune_derniere_connexion_Prop());
        creationColumn.setCellValueFactory(cellData -> cellData.getValue().getJeune_creation_Prop());
        
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
