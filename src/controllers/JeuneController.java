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
	private TableView<User> table;
	@FXML
	private TableColumn<User, Integer> idColumn;
	@FXML
	private TableColumn<User, String> nomColumn;
	@FXML
	private TableColumn<User, String> prenomColumn;
	@FXML
	private TableColumn<User, String> usernameColumn;
	@FXML
	private TableColumn<User, String> emailColumn;
	@FXML
	private TableColumn<User, String> telephoneColumn;
	@FXML
	private TableColumn<User, String> creationColumn;
	@FXML
	private AnchorPane mainPane;
	
	String nomDeLaPersonneConnecte;
	String roles;
	String [] roles_jeune = {"[\"ROLE_JEUNE\"]"};
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
	private void fermer_l_application(ActionEvent actionEvent) 
	{
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void retourner_dans_la_page_precedente(ActionEvent actionEvent) 
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
		ObservableList<User> listeDesJeunes = UserDAO.obtenir_la_liste_de_tout_les_utilisateur_d_un_certain_role(roles_jeune);
        table.setItems(listeDesJeunes);
	}
	
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException 
	{
		ObservableList<User> listeFiltreDesJeunes = UserDAO.obtenir_la_liste_filtre(rechercheInput.getText(), this.roles_jeune);
        table.setItems(listeFiltreDesJeunes);
	}
		
	@FXML
	private void acceder_a_la_page_d_inscription_des_jeunes(ActionEvent actionEvent) 
	{
        try 
        {
        	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/JeuneInscription.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			JeuneInscriptionController jeuneInscriptionController = loader.<JeuneInscriptionController>getController();
			jeuneInscriptionController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
			jeuneInscriptionController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
        }catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void acceder_a_la_page_de_modification_des_informations_du_jeune_selectionne(ActionEvent actionEvent) 
	{
		if(table.getSelectionModel().getSelectedItem() != null) 
		{
			User jeune = table.getSelectionModel().getSelectedItem();
			
	        int id = jeune.getId();
	        String nom = jeune.getNom();
	        String prenom = jeune.getPrenom();
	        String email = jeune.getEmail();
	        String telephone = jeune.getTelephone();
	        String adresse = jeune.getAdresse();
	        String ville = jeune.getVille();
	        String code_postal = jeune.getCodepostal();
	        
	        try 
	        {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/JeuneModification.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				JeuneModificationController jeuneModificationController = loader.<JeuneModificationController>getController();
				jeuneModificationController.obtenir_les_informations_du_jeune_a_modifier(id, nom, prenom, email, telephone, adresse, ville, code_postal);
				jeuneModificationController.recuperer_le_nom_de_la_personne_connecte(this.nomDeLaPersonneConnecte);
				jeuneModificationController.recuperer_le_status_super_administrateur_de_la_personne_connecte(this.roles);
			}catch(IOException e) 
	        {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void supprimmer_un_jeune(ActionEvent actionEvent) throws SQLException, ClassNotFoundException 
	{
		if(table.getSelectionModel().getSelectedItem() != null) 
		{
	        User jeuneSelectionne = table.getSelectionModel().getSelectedItem();
	        int id = jeuneSelectionne.getId();
	        UserDAO.supprimer(id);
	    }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException 
	{
		ObservableList<User> empData = UserDAO.obtenir_la_liste_de_tout_les_utilisateur_d_un_certain_role(roles_jeune);
        table.setItems(empData);
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomProp());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomProp());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsernameProp());
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