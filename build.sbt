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
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "commons-daemon" % "commons-daemon" % commonsDaemonV
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

// sbt-assembly to build runnable jar
mainClass in assembly := Some("net.facetz.evercookie.Runner")

assemblyJarName in assembly := s"${name.value}_${scalaBinaryVersion.value}-${version.value}.jar"

artifact in(Compile, assembly) := {
  val art = (artifact in(Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in(Compile, assembly), assembly).settings