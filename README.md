# dayoff_manager

**Ce projet utilise MAVEN !**

## Architecture du projet

Le package `db` contient pour chaque entité :
- la classe représentant l'entité (*ne pas modfier*)
- l'interface définissant le DAO (interface)
- l'implémentation DAO (avec DB)
- implémentation DAO mock (sans DB, pour tests)

Pour ajouter une fonction, il faut l'ajouter dans l'interface puis l'implémenter dans les deux classes (Impl+MockImpl)

Le package `servlets` contient les servlets de l'application (*il faut également les déclarer dans le web.xml !*)

## Infos divers
Lorsque la base est vide, une fonction dans la classe Utils crée automatiquement des enregistrements. 
Pour voir le contenu de la base de données : Servlet ShowDBContentsServlet

## Stockage des données
**ATTENTION : les DAO ne doivent pas être modifié sans accord chef projet !**

Le stockage des données est implémenté sous deux formes :
- avec une DB réelle
- en mémoire (Mock, utile pour les tests)

Il existe cinq entités (table) dans l'application :
- **dayoff** : Congés
- **dayoff_count** : Nombre de jours restants par congé par employé
- **dayoff_type** : Type de congé
- **department** : Service
- **employee** : Employé

Pour chaque entité il existe deux DAO :
- un finissant par **DaoImpl** : DAO se connectant à une base de données
- un finissant par **DaoMockImpl** : DAO stockant les entités en mémoire (pour test)

Par défaut, il faut utiliser les **DaoMockImpl**. Pour changer le type de DAO utilisé, il faut modifier la variable *MOCK_MODE* dans la classe *DaoProvider*.

Pour accéder un DAO, il suffit d'utiliser la classe **DaoProvider** qui contient un getter par DAO.

## Servlets
Chaque servlet représente une fonctionnalité du site. Pour en créer un, il faut créer une classe dans le package **servlets** avec un nom finissant par **Servlet**. 

Il faut ensuite le déclarer, pour pouvoir l'utiliser. Il faut donc ajouter une annotation. Voici un exemple : 

```java
@WebServlet(  
  name = "DefaultPageServlet",  /*nom servlet*/
  description = "Default page",  /*descriptions*/
  urlPatterns = {"/", "/default"}  /*quel(s) url(s) doivent appeller ce servlet ?
)  
public class DefaultPageServlet extends HttpServlet {
	...
}
```

## Procédure de mise en place et test de la base de données

### Etape 1 (si besoin)
Dans le dossier `src/main/resources/META-INF/`, créer un fichier `persistence.xml` qui contient :

```xml
<?xml version="1.0" encoding="UTF-8"?>  
<persistence xmlns="http://java.sun.com/xml/ns/persistence" version="2.0">  
	 <persistence-unit name="dayoff_manager">  
	 <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>  
	 <properties>
		  <!-- URL connection BDD, forme : jdbc:mysql://localhost/<<database name>> -->  
		  <property name="hibernate.connection.url" value="jdbc:mysql://localhost/dayoff_manager"/>  
		  <!-- Nom d'utilisateur BDD -->  
		  <property name="hibernate.connection.username" value="root"/>  
		  <!-- Mot de passe BDD -->  
		  <property name="hibernate.connection.password" value="root"/>  
  
		  <!-- Ne pas modifier le reste -->  
		  <property name="hibernate.connection.driver_class" value="com.mysql.cj.jdbc.Driver"/>  
		  <property name="hibernate.archive.autodetection" value="class"/>  
		  <property name="hibernate.show_sql" value="true"/>    
		  <property name="hibernate.hbm2ddl.auto" value="update"/>  
	 </properties>
	</persistence-unit>
</persistence>
```

### Etape 2
Lancer MYSQL et créer une database avec le nom spécifié dans la propriété `hibernate.connection.url`
(commande : `CREATE DATABASE <database name>`)

### Etape 3
Décommenter la partie `testDB` dans le fichier `src/main/webapp/WEB-INF/web.xml`

### Etape 4
Lancer l'application et attendre, puis ouvrir la page `index.jsp`
Une fois la page affichée, chercher dans le log cette ligne : `"[DBTestFilter] OK!!!"`
Si elle est présente, tout va bien, sinon il faut corriger l'erreur (voir logs pour plus de détails)

### Etape 5
Si OK, remettre en commentaire la partie `testDB` dans le fichier `src/main/webapp/WEB-INF/web.xml`
