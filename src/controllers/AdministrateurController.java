package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
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
import models.base.User;
import models.dao.UserDAO;

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
	private TableView<User> table;
	@FXML
	private TableColumn<User, String> nomColumn;
	@FXML
	private TableColumn<User, String> prenomColumn;
	@FXML
	private TableColumn<User, String> statusSuperAdministrateurColumn;
	@FXML
	private TableColumn<User, String> emailColumn;
	@FXML
	private TableColumn<User, String> telephoneColumn;
	@FXML
	private TableColumn<User, String> creationColumn;
	
	String nomDeLaPersonneConnecte;
	String roles;
	String [] role = {"ADMINISTRATEUR"};
	
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
	private void deconnecter_l_utilisateur(ActionEvent actionEvent) 
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
	private void retourner_dans_la_page_precedente(ActionEvent actionEvent) 
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
	}
	
	@FXML
	private void recherche_general(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException 
	{
		ObservableList<User> empData = UserDAO.obtenir_la_liste_de_tout_les_utilisateur_d_un_certain_role(this.role);
        table.setItems(empData);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException 
	{
		ObservableList<User> empData = UserDAO.obtenir_la_liste_filtre(rechercheInput.getText(), this.role);
        table.setItems(empData);
	}
		
	@FXML
	private void acceder_a_la_page_d_inscipriton_d_un_administrateur(ActionEvent actionEvent) 
	{
		try 
		{
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/AdministrateurInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			AdministrateurInscriptionController administrateurInscriptionController = loader.<AdministrateurInscriptionController>getController();
			administrateurInscriptionController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			administrateurInscriptionController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
		}catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	private void modifier_les_information_d_un_administrateur(ActionEvent actionEvent) 
	{
		if(table.getSelectionModel().getSelectedItem() != null) 
		{
			User administrateur = table.getSelectionModel().getSelectedItem();
	        int id = administrateur.getId();
	        String role = administrateur.getRoles();
	        String nom = administrateur.getNom();
	        String prenom = administrateur.getPrenom();
	        String email = administrateur.getEmail();
	        String telephone = administrateur.getTelephone();
	        String adresse = administrateur.getAdresse();
	        String ville = administrateur.getVille();
	        String code_postal = administrateur.getCodepostal();
	        
	        try 
	        {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/AdministrateurModification.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				AdministrateurModificationController administrateurModificationController = loader.<AdministrateurModificationController>getController();
				administrateurModificationController.recuperer_les_informations_de_l_administrateur_a_modifier(id, role, nom, prenom, email, telephone, adresse, ville, code_postal);
				administrateurModificationController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				administrateurModificationController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
			}catch(IOException e) 
	        {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void suppression_d_un_administrateur(ActionEvent actionEvent) throws SQLException, ClassNotFoundException 
	{
		if(table.getSelectionModel().getSelectedItem() != null) 
		{
	        User administrateur_selectionne = table.getSelectionModel().getSelectedItem();
	        int id = administrateur_selectionne.getId();
	        UserDAO.supprimer(id);
	    }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException 
	{
		ObservableList<User> empData = UserDAO.obtenir_la_liste_de_tout_les_utilisateur_d_un_certain_role(this.role);
        table.setItems(empData);
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomProp());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomProp());
        statusSuperAdministrateurColumn.setCellValueFactory(cellData -> cellData.getValue().getRolesProp());
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