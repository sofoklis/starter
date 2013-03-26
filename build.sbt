name := "starter"

EclipseKeys.withSource := true

// Dont check checksums, some problems with lift snapshot jars
checksums := Nil

liftVersion := "2.5-SNAPSHOT"

scalaVersion := "2.10.0"

version := "1.0"

jettyVersion := "8.1.7.v20120910"

squerylVersion := "0.9.5-6"

h2Version := "1.3.163"

log4jVersion := "1.2.16"

junitVersion := "4.8.2"

javaxServletVersion := "2.5"

slf4jVersion := "1.6.1" 

scalacOptions ++= Seq("-deprecation")

resolvers += "Typesafe releases" at "http://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Typesafe Snapshots" at "http://oss.sonatype.org/content/repositories/releases"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

seq(com.github.siasia.WebPlugin.webSettings :_*)

scalacOptions ++= Seq("-deprecation", "-unchecked")

// If using JRebel
scanDirectories in Compile := Nil

logLevel := Level.Info
//Level.Info.Debug

libraryDependencies <++= 
	(scalaVersion, liftVersion, jettyVersion, squerylVersion, h2Version, slf4jVersion, junitVersion)
{
	case(scala, lift, jetty, squeryl, h2, slf4j, junit) => Seq(
	"com.typesafe.akka"	%% "akka-actor" 	% "2.1.2", 
  	"com.typesafe.akka" %% "akka-testkit" 	% "2.1.2",
	"io.spray" % "spray-caching" % "1.1-M7",
	"org.scalaz"  %% "scalaz-core" % "7.0.0-M8",
	"net.liftweb" %% "lift-webkit" % lift % "compile",
	"net.liftweb" %% "lift-mapper" % lift % "compile",
	"org.squeryl" %% "squeryl" % squeryl % "compile->default",
	"com.h2database" % "h2" % h2, //% "compile->default",
	"org.slf4j" % "slf4j-log4j12" % slf4j % "compile->default",
	"cc.co.scala-reactive" %% "reactive-web" % "0.3.0",
	"com.lambdaworks" % "scrypt" % "1.3.3",
	"org.scalatest" %% "scalatest" % "2.0.M5b" % "test",
	"org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
	"org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,compile",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,compile" artifacts Artifact("javax.servlet", "jar", "jar")) 
}
