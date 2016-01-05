lazy val sprayArgonautSupport = Project(id = "spray-argonaut-support", base = file(".")).
  settings(
    organization := "org.jesusthecat",
    name := "spray-argonaut-support",
    version := "0.1.2",
    scalaVersion := "2.11.6",
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    libraryDependencies ++= Seq(
      "io.argonaut"         %%  "argonaut"    % "6.1"     % "provided",
      "io.spray"            %%  "spray-http"  % "1.3.3"   % "provided",
      "io.spray"            %%  "spray-httpx" % "1.3.3"   % "provided",
      "com.typesafe.akka"   %%  "akka-actor"  % "2.4.1"   % "provided",
      "org.scalatest"       %%  "scalatest"   % "2.2.4"   % "test"
    )
  )
