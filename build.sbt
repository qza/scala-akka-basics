name := "scala-akka-basics"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.3.12"
  Seq(
    "com.typesafe.akka"       %% "akka-actor"                           % akkaV
  )
}