val nd4jVersion = "0.9.1"
libraryDependencies ++= Seq(
  // https://mvnrepository.com/artifact/org.la4j/la4j
  // Dense and Sparse, 237K (meant for small problems, andrdoi, single-threaded)
  "org.la4j" % "la4j" % "0.6.0",

  // https://mvnrepository.com/artifact/com.googlecode.efficient-java-matrix-library/ejml
  // 1MB. single threaded, may be multithreaded one day
  "com.googlecode.efficient-java-matrix-library" % "ejml" % "0.25",

  // https://mvnrepository.com/artifact/gov.nist.math/jama
  // Dense only, 35K, single-threaded
  "gov.nist.math" % "jama" % "1.0.2",

  // Breeze
  // 35MB
  "org.scalanlp" %% "breeze" % "latest.integration",
  "org.scalanlp" %% "breeze-natives" % "0.12",

  // https://mvnrepository.com/artifact/net.sourceforge.parallelcolt/parallelcolt
  // 4.2MB
  "net.sourceforge.parallelcolt" % "parallelcolt" % "0.10.0",

  // https://mvnrepository.com/artifact/org.jblas/jblas
  // 10.2 MB
  "org.jblas" % "jblas" % "1.2.4",

  // ND4J (ND4S)
  "org.nd4j" % "nd4j-native-platform" % nd4jVersion,
  "org.nd4j" %% "nd4s" % nd4jVersion,
 
  // https://mvnrepository.com/artifact/org.ojalgo/ojalgo
  // "org.ojalgo" % "ojalgo" % "46.3.0",

  // https://mvnrepository.com/artifact/org.apache.commons/commons-math3
  // kinda slow... single threaded, 1.9MB
  "org.apache.commons" % "commons-math3" % "3.6.1"
)

//resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
