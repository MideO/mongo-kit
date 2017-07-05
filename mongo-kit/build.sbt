import sbt._

name := "mongo-kit"

version := "0.0.1-SNAPSHOT"

organization := "com.github.mideo"

scalaVersion := "2.11.7"

useGpg := true

lazy val `mongo-kit` = (project in file("."))
  .settings(
    scalacOptions := Seq(
      "-unchecked",
      "-deprecation",
      "-encoding",
      "utf8",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps",
      "-language:reflectiveCalls",
      "-Yrangepos"
    )
  )

fork in run := true

resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Sonatypes" at "https://oss.sonatype.org/content/repositories/releases",
  "Maven Repo" at "http://mvnrepository.com/maven2/",
  "Maven Central" at "http://central.maven.org/maven2/"
)

libraryDependencies ++= Seq(
  "org.mongodb" % "bson" % "3.4.2",
  "com.typesafe.play" % "play_2.11" % "2.5.9",
  "com.typesafe.play" % "play-json_2.11" % "2.5.9",
  "org.reactivemongo" % "play2-reactivemongo_2.11" % "0.12.3",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "org.mockito" % "mockito-core" % "2.8.9" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % Test
)

pomIncludeRepository := { _ => false }

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php"))

homepage := Some(url("https://github.com/MideO/mongo-kit"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/MideO/mongo-kit"),
    "scm:git@github.com/MideO/mongo-kit.git"
  )
)

developers := List(
  Developer(
    id = "mideo",
    name = "Mide Ojikutu",
    email = "mide.ojikutu@gmail.com",
    url = url("https://github.com/MideO")
  )
)