package controllers;

import application.Main;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.encryption.BCrypt;
import models.base.User;
import models.dao.UserDAO;
import models.dao.ConfigurationConnexionBaseDeDonneesDAO;

public class ConnexionController {
	@FXML
	private Stage main;
	@FXML 
	private AnchorPane mainPane;
	@FXML
	private TextField emailInput;
	@FXML
	private PasswordField motDePasseInput;
	@FXML
	private Button connexionButton;
	@FXML
	private Button configurationButton;
	@FXML
	private Button fermetureButton;
	
	private double xOffset;
	private double yOffset;
	
	@FXML
	private void fermer_l_application(ActionEvent actionEvent) 
	{
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void connecter_un_utilisateur(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException 
	{
		if(!emailInput.getText().isEmpty() && !motDePasseInput.getText().isEmpty()) 
		{
			boolean statusEmailSyntaxeVerification = User.verifier_la_syntaxe_de_l_email(emailInput.getText());
			
			if(statusEmailSyntaxeVerification == true) 
			{	
				String motDePasse = motDePasseInput.getText();
				String hashDuMotDePasse = UserDAO.obtenir_le_hache_correspondant_a_l_email_de_connexion(emailInput.getText());
				String nomDeLaPersonneConnecte = UserDAO.obtenir_le_nom_de_la_personne_connecte(emailInput.getText(), hashDuMotDePasse);
				String roles = UserDAO.obtenir_le_role(emailInput.getText());
				String role_admin = "[\"ROLE_ADMINISTRATEUR\"]";
				String role_super_admin = "[\"ROLE_SUPER_ADMINISTRATEUR\"]";
				
				if(BCrypt.checkpw(motDePasse, hashDuMotDePasse)) 
				{
					boolean statusOptionEnvoiEmail = ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_status_de_l_option_d_envoi_d_email();
									
					if(statusOptionEnvoiEmail == true) 
					{
						boolean verificationEnvoiEmail = User.envoi_d_email(emailInput.getText());
						
						if(verificationEnvoiEmail == false) 
						{
							Alert a1 = new Alert(Alert.AlertType.ERROR);
							a1.setTitle("Erreur: n°5");
							a1.setContentText("L'envoi d'email ne fonctionne pas veuillez désactiver la fonctionnalité dans le menu de configuration si vous ne voulez plus voir ce message.\n"+
											  "Pour accéder au menu veuillez cliquer sur l'icône dans le coin en bas à droite.");
							a1.setHeaderText(null);
							a1.showAndWait();
						}
					}
					
					if(roles.toLowerCase().indexOf(role_super_admin.toLowerCase()) != -1)
					{
						try 
						{
							mainPane.getChildren().clear();
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/SuperAdministrateurMenu.fxml"));
							AnchorPane userFrame = (AnchorPane) loader.load();
							Scene sc =  new Scene(userFrame);
							Stage stage = (Stage) mainPane.getScene().getWindow();
							stage.setScene(sc);
							SuperAdministrateurMenuController superAdministrateurController = loader.<SuperAdministrateurMenuController>getController();
							superAdministrateurController.recuperer_le_nom_de_la_personne_connecte(nomDeLaPersonneConnecte);
							superAdministrateurController.recuperer_le_status_super_administrateur_de_la_personne_connecte(roles);
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
							Scene sc =  new Scene(userFrame);
							Stage  stage = (Stage) mainPane.getScene().getWindow();
							stage.setScene(sc);
							MenuController menuController = loader.<MenuController>getController();
							menuController.recuperer_le_nom_de_la_personne_connecte(nomDeLaPersonneConnecte);
							menuController.recuperer_le_status_super_administrateur_de_la_personne_connecte(roles);
						}catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}else 
				{
					Alert a1 = new Alert(Alert.AlertType.ERROR);
					a1.setTitle("Erreur: n°4");
					a1.setContentText("Cet utilisateur n'existe pas verifiez votre mot de passe et votre email.");
					a1.setHeaderText(null);
					a1.showAndWait();
				}
			}else 
			{
				Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur: n°2");
				a1.setContentText("Le format de l'email est incorrecte.");
				a1.setHeaderText(null);
				a1.showAndWait();
			}
		}else 
		{
			mainPane.getChildren().clear();
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setTitle("Erreur: n°1");
			a1.setContentText("L'un ou les deux champs sont vide.");
			a1.setHeaderText(null);
			a1.showAndWait();
		 }
	}

	@FXML
	private void configurer_la_connexion_la_base_de_donnees(ActionEvent actionEvent)
	{
        try 
        {
        	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Configuration.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc =  new Scene(userFrame);
			Stage  stage = (Stage) mainPane.getScene().getWindow();
			stage.setScene(sc);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException 
	{
		String hostname = ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_nom_d_hote();
		
		try 
		{
			String adresseIP = hostname;
		    InetAddress inet = InetAddress.getByName(adresseIP);
		    System.out.println("Envoi d'une requête ping à " + adresseIP);
		    
		    if (inet.isReachable(5000))
		    {
		        System.out.println("L'hôte " + adresseIP + " est joignable.");
		    }else 
		    {
		    	Alert a1 = new Alert(Alert.AlertType.ERROR);
				a1.setTitle("Erreur");
				a1.setContentText("La base de données n'est pas joignable, changez les modalités de connexion dans le menu de configuration. \n"+
								  "Pour accéder au menu de configuration cliquez sur l'icône dans le coin en bas à droite.");
				a1.setHeaderText(null);
				a1.showAndWait();
		     }
		}catch ( Exception e ) 
		{
			System.out.println("Exception:" + e.getMessage());
		}
		
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