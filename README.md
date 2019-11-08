# dayoff_manager

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