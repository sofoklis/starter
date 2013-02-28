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

resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

seq(com.github.siasia.WebPlugin.webSettings :_*)

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies <++= 
	(scalaVersion, liftVersion, jettyVersion, squerylVersion, h2Version, slf4jVersion, junitVersion)
{
	case(scala, lift, jetty, squeryl, h2, slf4j, junit) => Seq(
	"net.liftweb" %% "lift-webkit" % lift % "compile",
	"org.squeryl" %% "squeryl" % squeryl % "compile->default",
	"com.h2database" % "h2" % h2 % "compile->default",
	"org.slf4j" % "slf4j-log4j12" % slf4j % "compile->default",
	"cc.co.scala-reactive" %% "reactive-web" % "0.3.0",
	"org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,compile",
   "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,compile" artifacts Artifact("javax.servlet", "jar", "jar")) 
}
