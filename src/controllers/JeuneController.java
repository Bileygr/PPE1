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
	private Button deconnexion_bouton;
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
	private TableView<Jeune> table;
	@FXML
	private TableColumn<Jeune, Integer> id_colonne;
	@FXML
	private TableColumn<Jeune, String>  nom_colonne;
	@FXML
	private TableColumn<Jeune, String> 	prenom_colonne;
	@FXML
	private TableColumn<Jeune, String> 	email_colonne;
	@FXML
	private TableColumn<Jeune, String> 	telephone_colonne;
	@FXML
	private TableColumn<Jeune, String> 	derniere_connexion_colonne;
	@FXML
	private TableColumn<Jeune, String> 	creation_colonne;
	@FXML
	private AnchorPane mainPane;
	
	String nomDeLaPersonneConnecte;
	boolean super_administrateur;
	
	private double xOffset;
	private double yOffset;
	
	public void recuperer_le_nom_de_la_personne_connecte(String nomDeLaPersonneConnecte) {
		this.nomDeLaPersonneConnecte = nomDeLaPersonneConnecte;
	}
	
	public void recuperer_le_status_super_administrateur_de_la_personne_connecte(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
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
		if(super_administrateur == true) {
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
	private void recherche(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Jeune> empData = JeuneDAO.recherche();
        table.setItems(empData);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
		ObservableList<Jeune> empData = JeuneDAO.recherche_filtre(recherche_champ_de_texte.getText());
        table.setItems(empData);
	}
		
	@FXML
	private void inscrire(ActionEvent actionEvent) {
        try {
        	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/JeuneInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			JeuneInscriptionController jeune_inscription_controller = loader.<JeuneInscriptionController>getController();
			jeune_inscription_controller.nom(this.nom);
			jeune_inscription_controller.super_administrateur(this.super_administrateur);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void modifier(ActionEvent actionEvent) {
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
				JeuneModificationController jeune_modification_controller = loader.<JeuneModificationController>getController();
				jeune_modification_controller.jeune(id, nom, prenom, email, telephone, adresse, ville, code_postal);
				jeune_modification_controller.nom(this.nom);
				jeune_modification_controller.super_administrateur(this.super_administrateur);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
	
	@FXML
	private void supprimer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Jeune jeune_selectionne = table.getSelectionModel().getSelectedItem();
	        int id = jeune_selectionne.getJeune_id();
	        JeuneDAO.supprimer(id);
	    }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		ObservableList<Jeune> empData = JeuneDAO.recherche();
        table.setItems(empData);
        nom_colonne.setCellValueFactory(cellData -> cellData.getValue().getJeune_nom_Prop());
        prenom_colonne.setCellValueFactory(cellData -> cellData.getValue().getJeune_prenom_Prop());
        email_colonne.setCellValueFactory(cellData -> cellData.getValue().getJeune_email_Prop());
        telephone_colonne.setCellValueFactory(cellData -> cellData.getValue().getJeune_telephone_Prop());
        derniere_connexion_colonne.setCellValueFactory(cellData -> cellData.getValue().getJeune_derniere_connexion_Prop());
        creation_colonne.setCellValueFactory(cellData -> cellData.getValue().getJeune_creation_Prop());
        
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
