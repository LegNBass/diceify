val scala2Version = "2.13.10"

lazy val root = project
  .in(file("."))
  .settings(
    name := "diceify",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala2Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.typelevel" %% "cats-core" % "2.9.0",
      "org.typelevel" %% "cats-effect" % "3.4.8",
      "com.sksamuel.scrimage" % "scrimage-core" % "4.0.33",
      "com.sksamuel.scrimage" %% "scrimage-scala" % "4.0.33"
    )
  )
