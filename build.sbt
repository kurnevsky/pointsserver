val scalatest = "org.scalatest" %% "scalatest" % "2.2.1" % "test"
val scalamock = "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1" % "test"
val scalacheck = "org.scalacheck" %% "scalacheck" % "1.12.1" % "test"
val nscalaTime = "com.github.nscala-time" %% "nscala-time" % "1.6.0"
val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.0"
val akkaLib = "com.typesafe.akka" %% "akka-actor" % "2.3.8"

val commonSettings = Seq(
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.11.4",
  scalacOptions := Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-optimise",
    "-encoding", "utf8",
    "-Xfuture",
    "-Xlint"
  ),
  libraryDependencies ++= Seq(
    scalatest,
    scalamock,
    scalacheck,
    scalaz
  )
)

lazy val pointsEngine = project.in(file("./modules/points-engine"))
  .settings(commonSettings: _*)
  .settings(name := "points-engine")

lazy val akkaNetwork = project.in(file("./modules/akka-network"))
  .settings(commonSettings: _*)
  .settings(name := "akka-network")
  .settings(libraryDependencies += akkaLib)
