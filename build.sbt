name := "ScalaRestAPI"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(

  "com.typesafe.akka" %% "akka-actor" % "2.5.21",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.21" % Test,
  "com.typesafe.akka" %% "akka-http"   % "10.1.7",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19" // or whatever the latest version is
)

//libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )