import sbtassembly.Log4j2MergeStrategy
import sbtrelease.Version

name := "lambda-event-app"

resolvers += Resolver.sonatypeRepo("public")
scalaVersion := "2.13.1"
releaseNextVersion := { ver =>
  Version(ver).map(_.bumpMinor.string).getOrElse("Error")
}
assemblyJarName in assembly := "lambda-event-app.jar"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-events" % "2.2.7",
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.0",
  "com.amazonaws" % "aws-lambda-java-log4j2" % "1.1.0",
  //"com.amazonaws" % "aws-java-sdk-sns" % "1.11.695",
  "io.github.mkotsur" %% "aws-lambda-scala" % "0.2.1-SNAPSHOT"
  //"org.slf4j" % "slf4j-api" % "1.7.28",
  //"org.slf4j" % "slf4j-simple" % "1.7.28"
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Xfatal-warnings"
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last == "Log4j2Plugins.dat" =>
    Log4j2MergeStrategy.plugincache
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
