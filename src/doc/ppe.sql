###################################################
#                                                 #
#  Projet: ppe                                    #
#  Auteur: Cheik-Siramakan Keita                  # 
#  Description: Base de donnée utilisé par une    # 
#               application Java et un site web.  #
#  date de création: 05/04/2018          #
#                                                 #     
###################################################

###################################################
-- Suprimme la base de donnée si elle existe --
###################################################
DROP DATABASE IF EXISTS ppe;

###################################################
-- Création de la base de donnée --
###################################################
CREATE DATABASE ppe CHARACTER SET utf8 COLLATE utf8_general_ci;

###################################################
-- Sélectionne la base de donnée --
###################################################
USE ppe;

###################################################
-- Création de la table Administrateur --
###################################################
CREATE TABLE IF NOT EXISTS administrateur(
  administrateur_id                   INT(11) NOT NULL AUTO_INCREMENT,
  super_administrateur                TINYINT(1) NOT NULL,
  administrateur_nom                  VARCHAR(50) NOT NULL,
  administrateur_prenom               VARCHAR(50) NOT NULL,
  administrateur_mot_de_passe_hash    CHAR(64) NOT NULL,
  administrateur_email                VARCHAR(50) NOT NULL,
  administrateur_telephone            VARCHAR(10) NOT NULL,
  administrateur_adresse              VARCHAR(38) NOT NULL,
  administrateur_ville                VARCHAR(32) NOT NULL,
  administrateur_code_postal          VARCHAR(5) NOT NULL,
  administrateur_derniere_connexion   DATETIME NOT NULL,
  administrateur_date_ajout           DATETIME NOT NULL,
  PRIMARY KEY (administrateur_id),
  UNIQUE (administrateur_id, administrateur_email, administrateur_telephone)
);

###################################################
-- Création de la table Partenaire --
###################################################
CREATE TABLE IF NOT EXISTS partenaire(
  partenaire_id                   INT(11) NOT NULL AUTO_INCREMENT,
  partenaire_siret                INT(9) NOT NULL,
  partenaire_nom                  VARCHAR(50) NOT NULL,
  partenaire_mot_de_passe_hash    CHAR(64) NOT NULL,
  partenaire_email                VARCHAR(30) NOT NULL,
  partenaire_telephone            VARCHAR(10) NOT NULL,
  partenaire_adresse              VARCHAR(38) NOT NULL,
  partenaire_ville                VARCHAR(32) NOT NULL,
  partenaire_code_postal          VARCHAR(5) NOT NULL,
  partenaire_derniere_connexion   DATETIME NOT NULL,
  partenaire_date_ajout           DATETIME NOT NULL,
  PRIMARY KEY (partenaire_id),
  UNIQUE (partenaire_id, partenaire_siret, partenaire_email, partenaire_telephone)
);

###################################################
-- Création de la table Jeune --
###################################################
CREATE TABLE IF NOT EXISTS jeune(
  jeune_id                  INT(11) NOT NULL AUTO_INCREMENT,
  jeune_nom                 VARCHAR(50) NOT NULL,
  jeune_prenom              VARCHAR(50) NOT NULL,
  jeune_mot_de_passe_hash   CHAR(64) NOT NULL,
  jeune_email               VARCHAR(50) NOT NULL,
  jeune_telephone           VARCHAR(10) NOT NULL,
  jeune_adresse             VARCHAR(38) NOT NULL,
  jeune_ville               VARCHAR(32) NOT NULL,
  jeune_code_postal         VARCHAR(5) NOT NULL,
  jeune_derniere_connexion  DATETIME NOT NULL,
  jeune_date_ajout          DATETIME NOT NULL,
  PRIMARY KEY (jeune_id),
  UNIQUE (jeune_id, jeune_email, jeune_telephone)
);

###################################################
-- Création de la table Formation --
###################################################
CREATE TABLE IF NOT EXISTS formation(
  formation_id  INT(11) NOT NULL AUTO_INCREMENT,
  formation_nom VARCHAR(50) NOT NULL,
  PRIMARY KEY (formation_id),
  UNIQUE (formation_id, formation_nom)
);

###################################################
-- Création de la table Offre --
###################################################
CREATE TABLE IF NOT EXISTS offre(
  offre_id          INT(11) NOT NULL AUTO_INCREMENT,
  partenaire_id     INT(11) NOT NULL,
  formation_id      INT(11) NOT NULL,
  offre_nom         VARCHAR(50) NOT NULL,
  offre_description TEXT NOT NULL,
  offre_adresse     VARCHAR(38) NOT NULL,
  offre_ville       VARCHAR(32) NOT NULL,
  offre_code_postal VARCHAR(5) NOT NULL,
  offre_debut       DATE NOT NULL,
  offre_fin         DATE NOT NULL,
  offre_date_ajout  DATETIME NOT NULL,
  PRIMARY KEY (offre_id),
  CONSTRAINT FK_offre_partenaire_id FOREIGN KEY (partenaire_id) REFERENCES partenaire(partenaire_id),
  CONSTRAINT FK_offre_formation_id FOREIGN KEY (formation_id) REFERENCES formation(formation_id),
  UNIQUE (offre_id)
);


###################################################
-- Insertion de tuples dans les tables --
###################################################

###################################################
-- Insertion des administrateurs --
###################################################
INSERT INTO administrateur(super_administrateur, administrateur_nom, administrateur_prenom,
  administrateur_mot_de_passe_hash, administrateur_email, administrateur_telephone, 
  administrateur_adresse, administrateur_ville, administrateur_code_postal, 
  administrateur_derniere_connexion, administrateur_date_ajout)
  VALUES(1, 'Guerfi', 'Souhila', '$2a$10$onBk9GyFQeQdKorQnOfmtOZlzoc0B5dqVypAnYKjqEV24qv8YwldK', 'sguerfi12@yahoo.fr', '0605557801', '1 Rue Deleau','Paris', '75116', NOW(), NOW());

  INSERT INTO administrateur(super_administrateur, administrateur_nom, administrateur_prenom,
  administrateur_mot_de_passe_hash, administrateur_email, administrateur_telephone, 
  administrateur_adresse, administrateur_ville, administrateur_code_postal, 
  administrateur_derniere_connexion, administrateur_date_ajout)
  VALUES(0, 'Keita', 'Cheik', '$2a$10$4Z.GQpuHzBUCvJYA5nln5.oiwHhFhN5N7pznDqrGgwQWH/2ozdq/6', 'chesirkei@hotmail.fr', '0605557802', '57 Boulevard de l Yerres','Evry', '91000', NOW(), NOW());

###################################################
-- Insertion de partenaires -- 
###################################################
INSERT INTO partenaire(partenaire_siret, partenaire_nom, partenaire_mot_de_passe_hash, partenaire_email, 
  partenaire_telephone, partenaire_adresse, partenaire_ville, partenaire_code_postal, partenaire_derniere_connexion, partenaire_date_ajout)
  VALUES(123456781, 'ImmoCorp', '$2a$10$o7VVD8RuTAQce3kH5yAYCOM411x8XIqviTbO/w2xVdoSGkgaU4T5G', 'infamousimmo@gmail.com', '0605557803', '425 Rue Du Paradis','Marseille', '13008',NOW(), NOW());

INSERT INTO partenaire(partenaire_siret, partenaire_nom, partenaire_mot_de_passe_hash, partenaire_email, 
  partenaire_telephone, partenaire_adresse, partenaire_ville, partenaire_code_postal, partenaire_derniere_connexion, partenaire_date_ajout)
  VALUES(123456782, 'Prop', '$2a$10$Q2eJWqodstmzUFitGy0EFe0UvpWy.fFNi.X0gME0H5SkiWmGq4ab2', 'infamousprop@gmail.com', '0605557804', '110 Rue Moncey','Lyon', '69003', NOW(), NOW());

###################################################
-- Insertion de jeunes --
###################################################
INSERT INTO jeune(jeune_nom, jeune_prenom, jeune_mot_de_passe_hash, jeune_email, jeune_telephone, jeune_adresse, jeune_ville,
jeune_code_postal, jeune_derniere_connexion, jeune_date_ajout)
  VALUES('Benoit', 'Florian', '$2a$10$0Pr0LNSTAFZoYLJUy3m6Vu0U6PZMxMor5Hd5l6q6CuMEE9OUtemum', 'benflo@gmail.com', '0605557805', '8 Rue de la Charente','Toulouse', '31100', NOW(), NOW());

INSERT INTO jeune(jeune_nom, jeune_prenom, jeune_mot_de_passe_hash, jeune_email, jeune_telephone, jeune_adresse, jeune_ville,
jeune_code_postal, jeune_derniere_connexion, jeune_date_ajout)
  VALUES('Badri', 'Brahim', '$2a$10$dGWKzf8hOXELLTMI9qc2CucUmhCB9EYbyqXYAWpsByKZLdns33MU6', 'badbra@gmail.com', '0605557806', '9 Avenue Romain Rolland','Nice', '06100', NOW(), NOW());

###################################################
-- Insetion de formations --
###################################################
INSERT INTO formation(formation_nom) VALUES('Réseau');
INSERT INTO formation(formation_nom) VALUES('Développement');
INSERT INTO formation(formation_nom) VALUES('Dépannage informatique');
###################################################
-- Insetion d'offres --
###################################################
INSERT INTO offre(partenaire_id, formation_id, offre_nom, offre_description, offre_adresse, offre_ville, 
  offre_code_postal, offre_debut, offre_fin, offre_date_ajout)
  VALUES(1, 1,'Immo offre', 'Offre de immo pour le booster dans CS:GO!', '3 Rue Maréchal Joffre','Nantes', '44000', '2018-03-08', '2018-04-08', NOW());

INSERT INTO offre(partenaire_id, formation_id, offre_nom, offre_description, offre_adresse, offre_ville, 
  offre_code_postal, offre_debut, offre_fin, offre_date_ajout)
  VALUES(2, 2, 'Prop offre', 'Offre de prop veux booster Immo!', '19 Rue des Juifs', 'Strasbourg', '67000', '2018-04-13', '2018-05-13', NOW());
  
INSERT INTO offre(partenaire_id, formation_id, offre_nom, offre_description, offre_adresse, offre_ville, 
  offre_code_postal, offre_debut, offre_fin, offre_date_ajout)
  VALUES(2, 3, 'Immo offre immobilière', 'Je cherche a vendre ma maison!', '19 Rue des Juifs', 'Strasbourg', '67000', '2018-04-13', '2018-05-13', NOW());
############################################################################################################################################################
                                                                  -- Fin du script --