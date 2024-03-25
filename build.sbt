ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val scalaReflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }

lazy val root = (project in file("."))
  .settings(
    name := "lying-games",
    idePackagePrefix := Some("org.audreyseo.lying"),
    libraryDependencies += scalaReflect.value
  )
