import sbt._

name := "mongo-kit"

version := "0.0.1-SNAPSHOT"

organization := "com.github.mideo"


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
  "com.typesafe.play" % "play-json_2.11" % "2.6.0-RC1",
  "org.reactivemongo" % "play2-reactivemongo_2.11" % "0.12.3",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % Test
)