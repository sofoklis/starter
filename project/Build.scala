import sbt._
import Keys._
object HelloBuild extends Build {

	val slf4jVersion = SettingKey[String]("slf4j-version")
	val javaxServletVersion = SettingKey[String]("javax-servel-version")
	val junitVersion = SettingKey[String]("junit-version")
	val log4jVersion = SettingKey[String]("log4j-version")
	val h2Version = SettingKey[String]("h2-version")
	val squerylVersion = SettingKey[String]("squeryl-version")
	val liftVersion = SettingKey[String]("lift-version")
	val jettyVersion = SettingKey[String]("jetty-version")
	val scalaMajorVersion = SettingKey[String]("scala-major-version")

	override lazy val settings = super.settings ++
		Seq(resolvers := Seq())

 	lazy val root = Project(id = "starter",
													base = file("."),
                          settings = Project.defaultSettings)
}

