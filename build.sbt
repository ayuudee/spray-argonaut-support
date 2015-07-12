organization := "org.jesusthecat"

version := "0.1.0"

scalaVersion := "2.11.6"

name := "spray-argonaut-support"

libraryDependencies ++= Seq(
  "io.argonaut"         %%  "argonaut"    % "6.1-M4"  % "provided",
  "io.spray"            %%  "spray-http"  % "1.3.1"   % "provided",
  "io.spray"            %%  "spray-httpx" % "1.3.1"   % "provided",
  "com.typesafe.akka"   %%  "akka-actor"  % "2.3.4"   % "provided",
  "org.scalatest"       %%  "scalatest"   % "2.2.4"   % "test"
)
