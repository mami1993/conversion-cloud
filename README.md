## Project Name: 

application de conversion de documents sous le cloud (SAAS).

## Prerequisites :

vous avez besoin d'un browser et la connection a l'internet pour lancé ce programme.

## Application Architecture :

cette application est basé sur une collection de web services pour les conversions , permet de mettre les données à la
disposition d'utilisateurs qui n'ont pas forcément un accès direct aux données , rendez l'application accessible via une 
multitude d'applications exécutées sur des ordinateurs de bureau, des tablettes PC et des smartphones. 
l'ensemble des services : 
  - service web de conversion il s'agis de converter le document.
  - service web authentication il s'agis de validé le client .
  - service web send-email il s'agis denvoyer email vers le client .
  - service web download il s'agis de telecharger le document et le supprimé apres 5 min.
  - service web pour upload le document.

la couche de données basé sur la base de données relationnelle qui utilise le langagee sql.

la maniere de gestion des demandes des clients basé sur les thread , c'est pour Un usage plus efficace du processeur
,Un système plus fiable et Des performances améliorées sur les multiprocesseurs. et tant que nous somme dans le cloud
et notre resources est limité donc les threads c'est une bonne solution pour la gestion des demandes.

![eozy-beret-casquette-plate-homme-chapeau-de-soleil](https://user-images.githubusercontent.com/44319251/49107789-8e2e4c00-f27e-11e8-9db2-66c1bb244024.jpg)


cette application permet de cenverter les documents de types txt vers word et l'inverse , permet aussi de converter 
les documents de types pdfs vers word et l'inverse. 

## Installing :

tant que nous somme dans le cloud et cette application est comme 'SAAS' donc ona pas besoin d'installé aucun chose 
on a besion seulment le browser pour uploder le document et afficher le resultat.

## Files Includes With This Project :
source code pas encore 
   ### base de données :
     	|-------------------------|         |-------------------|
            |          client         |         |   documents       |
            |-------------------------|         |-------------------|
		| id_client               |         | id_document       |  
		| name                    |         | name              |
		| email                   |         | id_client         |
		| conversion_nbr          |         |                   |
		|-------------------------|         |-------------------|

## Running The Application :

ouvrir le browser , tapez ce URL ( pas encore ) ,  uploder le document et voila  le resultat. 

## Deployment :

pour deploy cette application créer compte sur le GITHUB , créer  compte sur openshift 
ajouter les classes dans le repository dans le compte de GITHUB 
pendant la preparation des programmes d'environment de programation dans openshift ajouter le lien
du compte de repository de GITHUB pour importer les classes pour les exécutées , ajouter la base de données  

## Built With :

 - Java EE
 - Oracle database
 - Openshift platform 

## Authors :

 - Bouchelouche Mohamed 
 - Safi Oussama
 - Touahri Ilies

## License :

 - Ce projet est sous licence MIT d'université de constantine

## Acknowledgments :

 - Remerciements à l'enseignante de module 
 - Remerciements à l'enseignant de TP 
