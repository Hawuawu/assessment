
name := "Barclays-Assessment"

version := "0.1"

scalaVersion := "2.12.6"


lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest"                                 %%  "scalatest"                       % "3.0.5" % Test
    )
  )



