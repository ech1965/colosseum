
import de.johoop.jacoco4sbt.JacocoPlugin.jacoco

lazy val root = (project in file(".")).enablePlugins(PlayJava)

name := "colosseum"

version := "1.0.0-SNAPSHOT"

resolvers := (
  "Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository"
  ) +: resolvers.value

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  cache,
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.1.7",
  "org.hamcrest" % "hamcrest-all" % "1.3",
  "com.google.inject" % "guice" % "3.0",
  "com.google.inject.extensions" % "guice-multibindings" % "3.0",
  "com.google.guava" % "guava" % "18.0",
  "commons-codec" % "commons-codec" % "1.10",
  "com.google.code.findbugs" % "jsr305" % "1.3.9",
  "io.github.cloudiator.sword" % "service" % "1.0.0-SNAPSHOT" exclude("javax.ws.rs", "jsr311-api"),
  "io.github.cloudiator.lance" % "client" % "1.0.0-SNAPSHOT",
  "io.github.cloudiator" % "common" % "1.0.0-SNAPSHOT",
  "io.github.cloudiator" % "visor-rest-client" % "1.0.0-SNAPSHOT",
  "io.github.cloudiator.axe" % "axe-aggregator-common" % "1.0.0-SNAPSHOT",
  "org.reflections" % "reflections" % "0.9.10"
)

libraryDependencies ++= Seq(
  "com.github.oxo42" % "stateless4j" % "2.5.0",
  "commons-lang" % "commons-lang" % "2.6",
  "com.rabbitmq" % "amqp-client" %  "3.4.4",
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13"
)

TwirlKeys.templateImports += "dtos._"

jacoco.settings

javaOptions in Test += "-Dconfig.file=conf/test.conf"

// disable the generation of general scaladoc, due to bug
// https://issues.scala-lang.org/browse/SI-4744
// the api-doc task will still generate scala and java doc
// but ignores the problamatic files.

publishArtifact in (Compile, packageDoc) := false

publishArtifact in packageDoc := false

sources in (Compile,doc) := Seq.empty

ApiDocSettings.apiDocTask

