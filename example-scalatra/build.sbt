import org.scalatra.sbt._


val ScalatraVersion = "2.5.1"

ScalatraPlugin.scalatraSettings

scalateSettings

organization := "com.example"

name := "example-scalatra"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "com.typesafe" % "config" % "1.3.0",
  "com.google.inject" % "guice" % "3.0",
  "com.github.mideo" % "mongo-kit_2.11" % "0.0.2-SNAPSHOT"
)

enablePlugins(JettyPlugin)