## Project Name: 

application de conversion de documents sous le cloud (SAAS).

## Prerequisites :

vous avez besoin d'un browser et la connection a l'internet pour lancé ce programme.

## Application Architecture :

cette application est basé sur une collection des web services et servlets pour les conversions , permet de mettre les données à la
disposition d'utilisateurs qui n'ont pas forcément un accès direct aux données , rendez l'application accessible via une 
multitude d'applications exécutées sur des ordinateurs de bureau, des tablettes PC et des smartphones. 
l'ensemble des services:
  - service web send-email il s'agis denvoyer email vers le client .
  - service web download il s'agis de telecharger le document et le supprimé apres 2 min.
  - service web pour upload le document.
  - servlet pour faire la conversion.
  
la couche de données basé sur la base de données relationnelle qui utilise le langagee sql.
   on a utilisé cette architecture car on a pas utiliser des type de donnée complex 
   La technologie est mature (création il y a plusieurs dizaines d’années) ce qui fait qu’aujourd’hui le SQL est un    langage standard et normalisé
   On a une garantie que les transactions sont atomiques, cohérentes, isolées et durables — principe ACID (Atomic,    Consistent, Independant, Durable)
   La possibilité de mettre en œuvre des requêtes complexes (croisement multiple des données)
   Du fait du nombres d’années d’existence, un large support est disponible et il existe également de fortes          communautés.
   
la maniere de gestion des demandes des clients basé sur les thread , c'est pour Un usage plus efficace du processeur
,Un système plus fiable et Des performances améliorées sur les multiprocesseurs. et tant que nous somme dans le cloud
et notre resources est limité donc les threads c'est une bonne solution pour la gestion des demandes.

![eozy-beret-casquette-plate-homme-chapeau-de-soleil](https://user-images.githubusercontent.com/44319251/49107789-8e2e4c00-f27e-11e8-9db2-66c1bb244024.jpg)


cette application permet de cenverter les documents de types pdf vers word et l'inverse . 

## Installing :

tant que nous somme dans le cloud et cette application est comme 'SAAS' donc ona pas besoin d'installé aucun chose 
on a besion seulment le browser pour uploder le document et afficher le resultat.

## Files Includes With This Project :
 - WEB SERVICES :
    FileDownloadService.JAVA
    FileUploadService.JAVA
    EmailSend.JAVA
 - SERVLET : 
    Login.JAVA
    Registration.JAVA
    WordToPdf.JAVA
    PDF2WordExample.JAVA
   ### base de données :
     	|-------------------------|         |-------------------|
          |          client         |         |   documents       |
          |-------------------------|         |-------------------|
		| id_client               |         | id_doc            |  
		| name_client             |         | name_doc          |
		| email_client            |         | path_doc          |
		| password                |         | id_client         |
		|                         |         | type_doc          |
		|                         |         | typ               |
		|-------------------------|         |-------------------|

## Running The Application :

ouvrir le browser , tapez ce URL ( http://jws-app-cloud.a3c1.starter-us-west-1.openshiftapps.com/ ) ,  uploder le document , converter et voila  le resultat. 

## Deployment :

pour deploy cette application créer compte sur le GITHUB , créer  compte sur openshift 
ajouter les classes dans le repository dans le compte de GITHUB 
pendant la preparation des programmes d'environment de programation dans openshift ajouter le lien
du compte de repository de GITHUB pour importer les classes pour les exécutées , ajouter la base de données  

## Built With :

 - JAVA EE :
   La plate-forme étend Java Platform, Standard Edition (Java SE) en fournissant une API de mapping objet-            relationnel, des architectures distribuées et multitiers, et des services web3. La plate-forme se fonde            principalement sur des composants modulaires exécutés sur un serveur d'applications.
 - MYSQL database
    est un système de gestion de bases de données relationnelles (SGBDR). Il est distribué sous une double licence     GPL et propriétaire. Il fait partie des logiciels de gestion de base de données les plus utilisés au monde3,       autant par le grand public (applications web principalement) que par des professionnels, en concurrence avec       Oracle, PostgreSQL et Microsoft SQL Server.
    Son nom vient du prénom de la fille du cocréateur Michael Widenius, My. SQL fait référence au Structured Query     Language, le langage de requête utilisé.
 - OPENSHIFT PPLATFORM
    OpenShift is a family of containerization software developed by Red Hat. Its flagship product is the OpenShift     Container Platform—an on-premises platform as a service built around Docker containers orchestrated and managed     by Kubernetes on a foundation of Red Hat Enterprise Linux. The family's other products provide said platform       through different environments: OKD serves as the community-driven upstream (akin to CentOS), OpenShift Online     is the platform offered as software as a service, Openshift Dedicated is the platform offered as a managed         service, and OpenShift.io is an application development environment for the platform available online.
## Authors :

 - Bouchelouche Mohamed 
 - Safi Oussama
 - Touahri Ilies

## License :

 - Ce projet est sous licence MIT d'université de constantine

## Acknowledgments :

 - Remerciements à l'enseignante de module 
 - Remerciements à l'enseignant de TP 
