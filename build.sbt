import Dependencies._
import Versions._


lazy val commonSettings: Seq[Def.Setting[_]] = Defaults.coreDefaultSettings ++ Seq(
    organization := "com.aruba",
    crossPaths := false,
    scalaVersion := "2.11.7",
    version := sys.props.getOrElse("version", "0.1.0"),
    resolvers ++= Dependencies.repos,
    credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
    dependencyOverrides += "org.scala-lang" % "scala-library" % scalaVersion.value,
    parallelExecution in Test := false,
    scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature"),
    // We need to exclude jms/jmxtools/etc because it causes undecipherable SBT errors  :(
    ivyXML :=
      <dependencies>
        <exclude module="jms"/>
        <exclude module="jmxtools"/>
        <exclude module="jmxri"/>
      </dependencies>
  )


lazy val cassandraExample = Project(id = "SJSCassandraExample", base = file("cassandra-example"))
  .settings(commonSettings)
  .settings(Assembly.settings)
  .settings(
     description := "SJS Cassandra Example ",
     name := "cassandra-example",
     libraryDependencies ++= sparkDeps ++ typeSafeConfigDeps ++ sparkExtraDeps ++ coreTestDeps
        ++ jobserverDeps ++ cassandraConnectorDeps ++ otherDeps,
     test in assembly := {},
     fork in Test := true
  )

lazy val rootSettings = Seq(
  // Must run Spark tests sequentially because they compete for port 4040!
  parallelExecution in Test := false,

  // disable test for root project
  test := {}
)

lazy val root = Project(id = "root", base = file("."))
  .settings(commonSettings)
  .settings(Defaults.itSettings)
  .settings(rootSettings)
  .aggregate(cassandraExample)
  .dependsOn(cassandraExample)
