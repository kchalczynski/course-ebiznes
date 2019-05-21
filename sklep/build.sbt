/*
name := "sklep"
 
version := "1.0" 
      
lazy val `sklep` = Project(id="sklep", base = file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"

// https://mvnrepository.com/artifact/com.typesafe.slick/slick
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.0.3"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
*/

name := "sklep"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

//crossScalaVersions := Seq("2.11.12", "2.12.8")

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.3"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3"
//libraryDependencies += "org.xerial"        %  "sqlite-jdbc" % "3.21.0"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"


//libraryDependencies += "com.h2database" % "h2" % "1.4.197"

libraryDependencies += specs2 % Test

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      