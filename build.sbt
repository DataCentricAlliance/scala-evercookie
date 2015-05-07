import sbt.Keys._

scalaVersion := "2.11.6"

name := "scala-evercookie"

version := "1.0"

organization := "net.facetz"

organizationName := "FACETz"

organizationHomepage := Some(url("http://facetz.net/"))

libraryDependencies ++= {
  val akkaV = "2.3.10"
  val sprayV = "1.3.3"
  val commonsDaemonV = "1.0.15"
  val akkaSlf4jV = "2.3.10"
  val slf4jV = "1.7.12"
  val log4jV = "1.2.17"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "commons-daemon" % "commons-daemon" % commonsDaemonV,
    "org.slf4j" % "slf4j-api" % slf4jV,
    "org.slf4j" % "slf4j-log4j12" % slf4jV,
    "com.typesafe.akka" % "akka-slf4j_2.11" % akkaSlf4jV,
    "log4j" % "log4j" % log4jV,
    "log4j" % "apache-log4j-extras" % log4jV
  )
}

// compile task depends on scalastyle checks
lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value

(compile in Compile) <<= (compile in Compile) dependsOn compileScalastyle

// sbt-native-packager to build dev without dpkg packages (ever on mac)
enablePlugins(JDebPackaging, JavaServerAppPackaging)

import com.typesafe.sbt.packager.archetypes.ServerLoader.SystemV

// use systemV for apply the script from /etc/default
serverLoading in Debian := SystemV

maintainer := "Sergey Tolmachev <s.tolmachev@datacentric.ru>"

rpmVendor := "FACETz"

packageSummary := "Evercookie Scala Spray backend"

packageDescription := "Evercookie Scala Spray backend"

mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
  // we are using the reference.conf as default application.conf
  // the user can override settings here
  val conf = src  / "main" / "resources" / "reference.conf"
  conf -> "conf/application.conf"
}

mappings in Universal <+= (packageBin in Compile, sourceDirectory) map { (_, src) =>
  val log4j = src / "main" / "resources" / "log4j.properties"
  log4j -> "conf/log4j.properties"
}

// sbt-assembly to build runnable jar
mainClass in assembly := Some("net.facetz.evercookie.Runner")

assemblyJarName in assembly := s"${name.value}_${scalaBinaryVersion.value}-${version.value}.jar"

artifact in(Compile, assembly) := {
  val art = (artifact in(Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in(Compile, assembly), assembly).settings