scalaVersion := "2.9.3"

resolvers += "Terracotta Snapshots" at "http://www.terracotta.org/download/reflector/snapshots"

resolvers += "Terracotta Releases" at "http://www.terracotta.org/download/reflector/releases"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "1.9.1" % "test",
	"org.slf4j" % "slf4j-log4j12" % "1.7.2",
	"net.sf.ehcache" % "ehcache" % "2.7.4",
	"javax.transaction" % "jta" % "1.1"
)
