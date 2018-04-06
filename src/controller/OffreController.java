package controller;

import java.io.IOException;
import java.sql.SQLException;
import application.Main;
import classe.Offre;
import dao.OffreDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class OffreController 
{
	@FXML
	private Button 							deconnexion_bouton;
	@FXML
	private Button 							retour_bouton;
	@FXML
	private Button 							recherche_bouton;
	@FXML
	private Button							recherche_filtre_bouton;
	@FXML
	private Button							description_bouton;
	@FXML
	private Button 							supprimer_bouton;
	@FXML
	private TextField						recherche_champ_de_texte;
	@FXML
	private TableView<Offre> 				table;
	@FXML
	private TableColumn<Offre, String>  	formation_colonne;
	@FXML
	private TableColumn<Offre, String> 		partenaire_colonne;
	@FXML
	private TableColumn<Offre, String> 		nom_colonne;
	@FXML
	private TableColumn<Offre, String>	 	debut_colonne;
	@FXML
	private TableColumn<Offre, String> 		fin_colonne;
	@FXML
	private AnchorPane mainPane;
	String nom;
	
	public void nom(String nom) {
		this.nom = nom;
		System.out.println("Offre: " + this.nom);
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
		}catch (IOException e) {
		   e.printStackTrace();
		  }
	}
		
	@FXML
	private void recherche_filtre(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {	
		ObservableList<Offre> empData = OffreDAO.recherche_filtre(recherche_champ_de_texte.getText());
		table.setItems(empData);
			
	}
		
	@FXML
	private void recherche(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		ObservableList<Offre> empData = OffreDAO.recherche();
		table.setItems(empData);
	}
	
	@FXML
	private void description(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		if(table.getSelectionModel().getSelectedItem() != null) {
			Offre offre = table.getSelectionModel().getSelectedItem();
	        int id 				= offre.getOffre_id();
	        String nom 			= offre.getOffre_nom();
	        String partenaire 	= offre.getPartenaire_nom();
	        String formation 	= offre.getFormation_nom();
	        String description	= offre.getOffre_description();
	        String debut		= offre.getOffre_debut();
	        String fin			= offre.getOffre_fin();
	        String adresse	 	= offre.getOffre_adresse();
	        String ville		= offre.getOffre_ville();
	        String code_postal 	= offre.getOffre_code_postal();
	        System.out.println(id + nom +  nom + partenaire + formation + description + adresse + ville + code_postal);
	        
	        try {
				mainPane.getChildren().clear();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("view/OffreDescription.fxml"));
				AnchorPane userFrame = (AnchorPane) loader.load();
				Scene sc = mainPane.getScene();
				sc.setRoot(userFrame);
				System.out.println();
				
				OffreDescriptionController offre_description_controller = loader.<OffreDescriptionController>getController();
				offre_description_controller.offre(id, nom, partenaire, formation, description, debut, fin);
				offre_description_controller.nom(this.nom);
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
	}
		
	@FXML
	private void supprimer(ActionEvent actionEvent) throws NumberFormatException, ClassNotFoundException, SQLException {
		if(table.getSelectionModel().getSelectedItem() != null) {
	        Offre offre = table.getSelectionModel().getSelectedItem();
	        int id = offre.getOffre_id();
	        System.out.println(id);
	        OffreDAO.supprimer(id);
	    }
	}
		
		@FXML
	    private void initialize () throws ClassNotFoundException, SQLException  {
			ObservableList<Offre> empData = OffreDAO.recherche();
			
	        table.setItems(empData);
			formation_colonne.setCellValueFactory(cellData -> cellData.getValue().getFormation_nom_Prop());
			partenaire_colonne.setCellValueFactory(cellData -> cellData.getValue().getPartenaire_nom_Prop());
			nom_colonne.setCellValueFactory(cellData -> cellData.getValue().getOffre_nom_Prop());
			debut_colonne.setCellValueFactory(cellData -> cellData.getValue().getOffre_debut_Prop());
			fin_colonne.setCellValueFactory(cellData -> cellData.getValue().getOffre_fin_Prop());
	    }
}
