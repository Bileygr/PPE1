package controllers;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.dao.OffreDAO;

public class StatistiqueController {
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Button deconnexionButton;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button retourButton;
	@FXML
	private Button partenaireButton;
	@FXML
	private Button formationButton;
	@FXML
	private PieChart StatPieChart;
	
	String nomDeLaPersonneConnecte;
	String roles;
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
	private void afficher_le_camembert_des_offres_par_formation(ActionEvent actionEvent) throws SQLException 
	{
		ObservableList<PieChart.Data> donneesDuCamembert = OffreDAO.recuperer_les_statistiques_des_offres_par_formation();
		StatPieChart.setData(donneesDuCamembert) ;
	}
	
	@FXML
	private void afficher_le_camembert_des_offres_par_partenaire(ActionEvent actionEvent) throws SQLException 
	{
		ObservableList<PieChart.Data> donneesDuCamembert = OffreDAO.recuperer_les_statistiques_des_offres_par_partenaire();
		StatPieChart.setData(donneesDuCamembert) ;
	}
		
	@FXML
    private void initialize () throws SQLException  
    {        
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				OffreDAO.recuperer_les_statistiques_des_offres_par_formation()); 
		StatPieChart.setData(pieChartData) ;
		
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