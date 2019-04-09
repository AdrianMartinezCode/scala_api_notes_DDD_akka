/*name := "ScalaRestAPI"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(

  "com.typesafe.akka" %% "akka-actor" % "2.5.21",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.21" % Test,
  "com.typesafe.akka" %% "akka-http"   % "10.1.7",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19" // or whatever the latest version is


)

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io"
)*/


//enablePlugins(JavaAppPackaging)

name := "ScalaRestAPI"
organization := "com.adrianmartinezcode"
version := "1.0"
scalaVersion := "2.12.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.5.21"
  val akkaHttpV   = "10.1.7"
  val scalaTestV  = "3.0.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "org.scalatest"     %% "scalatest" % scalaTestV % "test",
    "io.spray" %%  "spray-json" % "1.2.5"
  )
}

//libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )