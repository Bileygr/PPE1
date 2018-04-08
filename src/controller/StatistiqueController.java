package controller;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import dao.OffreDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class StatistiqueController {
	@FXML
	private Button 						deconnexion_bouton;
	@FXML
	private Button						retour_bouton;
	@FXML
	private Button						partenaire_bouton;
	@FXML
	private Button						formation_bouton;
	@FXML
	private PieChart 					StatPieChart;
	@FXML
	private AnchorPane mainPane;
	String nom;
	boolean super_administrateur;
	
	public void nom(String nom) {
		this.nom = nom;
		System.out.println("Statistique: " + this.nom);
	}
	
	public void super_administrateur(boolean super_administrateur) {
		this.super_administrateur = super_administrateur;
	}
	
	@FXML
	private void deconnexion(ActionEvent actionEvent) {	
		try {
	    	mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("view/Connexion.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
			System.out.println();
		}catch(IOException e) {
	        e.printStackTrace();
	     }
	}
	
	@FXML
	private void retour(ActionEvent actionEvent) {
		if(super_administrateur = true) {
			try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("view/SuperAdministrateurMenu.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				System.out.println();
				
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
				loader.setLocation(Main.class.getClassLoader().getResource("view/Menu.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				System.out.println();
				
				MenuController menu_controller = loader.<MenuController>getController();
				menu_controller.nom(this.nom);
				menu_controller.super_administrateur(this.super_administrateur);
			}catch (IOException e) {
			   e.printStackTrace();
			  }
		}
	}
	
	@FXML
	private void formation(ActionEvent actionEvent) throws SQLException {
		ObservableList<PieChart.Data> pieChartData = OffreDAO.formation_statistique();
		StatPieChart.setData(pieChartData) ;
	}
	
	@FXML
	private void partenaire(ActionEvent actionEvent) throws SQLException {
		ObservableList<PieChart.Data> pieChartData = OffreDAO.partenaire_statistique();
		StatPieChart.setData(pieChartData) ;
	}
		
	@FXML
    private void initialize () throws SQLException  
    {        
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				OffreDAO.formation_statistique()); 
	
		    StatPieChart.setData(pieChartData) ;
    }
}