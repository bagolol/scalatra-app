import sbt._

val ScalatraVersion = "2.6.3"
val Json4sVersion = "3.5.2"

organization := "com.example"

name := "scalatra-let-shout"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.4"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.9.v20180320" % "container",
  "org.json4s" %% "json4s-jackson" % Json4sVersion,
  "org.json4s" %% "json4s-native" % Json4sVersion,
  "org.json4s" %% "json4s-ext" % Json4sVersion,
  "org.dispatchhttp" %% "dispatch-core" % "0.14.0",

  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "io.cucumber" % "cucumber-core" % "2.0.1" % "test",
  "io.cucumber" %% "cucumber-scala" % "2.0.1" % "test",
  "io.cucumber" % "cucumber-jvm" % "2.0.1" % "test",
  "io.cucumber" % "cucumber-junit" % "2.0.1" % "test",
  "com.github.tomakehurst" % "wiremock" % "2.1.11" % "test"
)


CucumberPlugin.glue := "steps"
CucumberPlugin.monochrome := false

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
enablePlugins(CucumberPlugin)
