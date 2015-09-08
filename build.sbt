import com.banno.license.Plugin.LicenseKeys._
import com.banno.license.Licenses._
import net.virtualvoid.sbt.graph.Plugin._

organization  := "org.github.arkadius"
name := "ljug-akka"
version := "1.0"

licenseSettings
license := apache2("Copyright 2015 the original author or authors.")
removeExistingHeaderBlock := true

graphSettings

scalaVersion  := "2.11.7"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
)

libraryDependencies ++= {
  val akkaV = "2.4.0-RC2"
  val logbackV = "1.1.3"
  val scalaTestV = "3.0.0-M8"
  val scalaCheckV = "1.12.4"
  Seq(
    "com.typesafe.akka"       %% "akka-actor"                    % akkaV,
    "com.typesafe.akka"       %% "akka-agent"                    % akkaV,
    "org.scalatest"           %% "scalatest"                     % scalaTestV    % "test",
    "org.scalacheck"          %% "scalacheck"                    % scalaCheckV   % "test",
    "com.typesafe.akka"       %% "akka-testkit"                  % akkaV         % "test",
    "com.typesafe.akka"       %% "akka-slf4j"                    % akkaV         % "test",
    "ch.qos.logback"           % "logback-classic"               % logbackV      % "test"
  )
}
