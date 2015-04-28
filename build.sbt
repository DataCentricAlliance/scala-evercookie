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
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" % "spray-routing_2.11" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "commons-daemon" % "commons-daemon" % "1.0.15"
  )
}

mainClass in assembly := Some("net.facetz.evercookie.Runner")

// compile task depends on scalastyle
lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value

(compile in Compile) <<= (compile in Compile) dependsOn compileScalastyle
