name := "example-playframework"

version := "1.0"

organization := "com.github.mideo.mongo"

lazy val `example` = (project in file(".")) .settings(
  resolvers +="Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
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
).enablePlugins(PlayScala)


scalaVersion := "2.11.7"

resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Sonatypes" at "http://oss.sonatype.org/content/repositories/releases",
  "Maven Repo" at "http://mvnrepository.com/maven2/…",
  "Maven Central" at "http://central.maven.org/maven2/"
)

libraryDependencies ++= Seq(
  cache , ws , specs2 % Test,
  "com.google.inject" % "guice" % "3.0",
  "org.reactivemongo" % "play2-reactivemongo_2.11" % "0.12.3",
  "org.apache.httpcomponents" % "httpcore" % "4.4.4",
  "org.apache.httpcomponents" % "httpclient" % "4.5.2",
  "com.github.mideo" % "mongo-kit_2.11" % "0.0.1-SNAPSHOT"
)

javaOptions in Test += "-Dconfig.file=conf/test.conf"