package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import models.base.User;
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
import models.dao.UserDAO;

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
	private TableView<User> table;
	@FXML
	private TableColumn<User, Integer> siretColumn;
	@FXML
	private TableColumn<User, String> nomColumn;
	@FXML
	private TableColumn<User, String> emailColumn;
	@FXML
	private TableColumn<User, String> telephoneColumn;
	@FXML
	private TableColumn<User,	String>	creationColumn;
	
	String nomDeLaPersonneConnecte;
	String roles;
	String [] roles_partenaire = {"[\"ROLE_PARTENAIRE\"]"};
	String role_admin = "[\"ROLE_ADMINISTRATEUR\"]";
	String role_super_admin = "[\"ROLE_SUPER_ADMINISTRATEUR\"]";
	
	private double xOffset;
	private double yOffset;
	
	public void recuperer_le_nom_de_la_personne_connecte(String nomDeLaPersonneConnecte) 
	{
		this.nomDeLaPersonneConnecte = nomDeLaPersonneConnecte;
	}
	
	public void recuperer_le_status_super_administrateur_de_la_personne_connecte(String roles) 
	{
		this.roles = roles;
	}
	
	@FXML
	private void deconnecter_l_utilisateur_connecte(ActionEvent actionEvent) 
	{	
		try 
		{
	    	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Connexion.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
		}catch(IOException e) 
		{
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void fermer_l_application(ActionEvent actionEvent) 
	{
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void retourrner_dans_la_page_precedente(ActionEvent actionEvent) 
	{
		if(roles.toLowerCase().indexOf(role_super_admin.toLowerCase()) != -1) 
		{
			try 
			{
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/SuperAdministrateurMenu.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				SuperAdministrateurMenuController superAdministrateurMenuController = loader.<SuperAdministrateurMenuController>getController();
				superAdministrateurMenuController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				superAdministrateurMenuController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
			}catch (IOException e) 
			{
			   e.printStackTrace();
			}
		}else if(roles.toLowerCase().indexOf(role_admin.toLowerCase()) != -1)
		{
			try 
			{
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Menu.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				MenuController menuController = loader.<MenuController>getController();
				menuController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				menuController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
			}catch (IOException e) 
			{
			   e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void recherche_general(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException 
	{
		ObservableList<User> listeDesPartenaires = UserDAO.obtenir_la_liste_de_tout_les_utilisateur_d_un_certain_role(roles_partenaire);
        table.setItems(listeDesPartenaires);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException 
	{
		ObservableList<User> listeDesPartenaires = UserDAO.obtenir_la_liste_filtre(rechercheInput.getText(), roles_partenaire);
        table.setItems(listeDesPartenaires);
	}
		
	@FXML
	private void accerder_a_la_page_d_inscription_des_partenaires(ActionEvent actionEvent) 
	{
		try 
		{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/PartenaireInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			PartenaireInscriptionController partenaireInscriptionController = loader.<PartenaireInscriptionController>getController();
			partenaireInscriptionController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			partenaireInscriptionController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
	
	@FXML
	private void acceder_a_la_page_de_modification_du_partenaire_selectionne(ActionEvent actionEvent) 
	{
		if(table.getSelectionModel().getSelectedItem() != null) 
		{
			User partenaire = table.getSelectionModel().getSelectedItem();
	        int id = partenaire.getId();
	        int siret = partenaire.getSiret();
	        String nom = partenaire.getNom();
	        String email = partenaire.getEmail();
	        String telephone = partenaire.getTelephone();
	        String adresse = partenaire.getAdresse();
	        String ville = partenaire.getVille();
	        String code_postal = partenaire.getCodepostal();
	        
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
				partenaireModificationController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
	
	@FXML
	private void supprimer_le_partenaire_selectionne(ActionEvent actionEvent) throws SQLException, ClassNotFoundException 
	{
		if(table.getSelectionModel().getSelectedItem() != null) 
		{
	        User partenaireSelectionne = table.getSelectionModel().getSelectedItem();
	        UserDAO.supprimer(partenaireSelectionne.getId());
	    }
	}
		
	@FXML
	private void initialize () throws ClassNotFoundException, SQLException  
	{
		ObservableList<User> empData = UserDAO.obtenir_la_liste_de_tout_les_utilisateur_d_un_certain_role(roles_partenaire);
		table.setItems(empData);
	    siretColumn.setCellValueFactory(cellData -> cellData.getValue().getSiretProp().asObject());
	    nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomProp());
	    emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProp());
	    telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().getTelephoneProp());
	    creationColumn.setCellValueFactory(cellData -> cellData.getValue().getDateajoutProp());
	    
	    mainPane.setOnMousePressed(new EventHandler<MouseEvent>() 
	    {
			@Override
			public void handle(MouseEvent event) 
			{
				 xOffset = event.getSceneX();
				 yOffset = event.getSceneY();
			}
		});
		
		mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent event) 
			{
				Main.obtenir_le_primaryStage().setX(event.getScreenX()- xOffset);
				Main.obtenir_le_primaryStage().setY(event.getScreenY()- yOffset);
			}
		});
	}
}
