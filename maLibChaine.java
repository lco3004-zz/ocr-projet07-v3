package fr.moi;

/**
 * Hello world!
 *
 * After installing the JARs your need to add the repository in your pom.xml file:
 *
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * <repositories>
 *     <repository>
 *         <id>maven-repository</id>
 *         <url>file:///${project.basedir}/maven-repository</url>
 *     </repository>
 * </repositories>
 * Then you can add the dependency into your pom.xml
 *
 * 1
 * 2
 * 3
 * 4
 * 5
 * <dependency>
 * 	<groupId>com.roufid.tutorials</groupId>
 * 	<artifactId>example-app</artifactId>
 * 	<version>1.0</version>
 * </dependency>
 * pour la suite :
 * <dependency>
 * 	<groupId>com.roufid.tutorials</groupId>
 * 	<artifactId>example-app</artifactId>
 * 	<version>1.0</version>
 * 	<scope>system</scope>
 * 	<systemPath>${basedir}/lib/yourJar.jar</systemPath>
 * </dependency>
 *
 * aussi
 * After installing the JARs your need to add the repository in your pom.xml file:
 *
 * <repositories>
 *     <repository>
 *         <id>maven-repository</id>
 *         <url>file:///${project.basedir}/maven-repository</url>
 *     </repository>
 * </repositories>
 * Then you can add the dependency into your pom.xml
 *
 * <dependency>
 * 	<groupId>com.roufid.tutorials</groupId>
 * 	<artifactId>example-app</artifactId>
 * 	<version>1.0</version>
 * </dependency>
 *
 * pour l'install
 *<!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-install-plugin -->
 * <dependency>
 *     <groupId>org.apache.maven.plugins</groupId>
 *     <artifactId>maven-install-plugin</artifactId>
 *     <version>3.0.0-M1</version>
 * </dependency>
 * <plugin>
 *     <groupId>org.apache.maven.plugins</groupId>
 *     <artifactId>maven-install-plugin</artifactId>
 *     <version>3.0.0-M1</version>
 *     <executions>
 *         <execution>
 *             <phase>initialize</phase>
 *             <goals>
 *                 <goal>install-file</goal>
 *             </goals>
 *             <configuration>
 *                 <groupId>com.roufid.tutorials</groupId>
 *                 <artifactId>example-app</artifactId>
 *                 <version>1.0</version>
 *                 <packaging>jar</packaging>
 *                 <file>${basedir}/lib/app.jar</file>
 *             </configuration>
 *         </execution>
 *     </executions>
 * </plugin>
 *
 *  deploy:deploy-file
 *  -Dfile=<path-to-file> -DgroupId=<group-id>
 *      -DartifactId=<artifact-id>
 *      -Dversion=<version>
 *      -Dpackaging=jar
 *  -Durl=file:./maven-repository/
 *  -DrepositoryId=maven-repository
 *  -DupdateReleaseInfo=true
 */
public class maLibChaine
{
    private String machaine="->";

    public String getMachaine() {
        return machaine;
    }

    public void setMachaine(String machaine) {
        this.machaine = machaine;
    }

    public maLibChaine(String x){
        machaine.concat(x);
    }
}
