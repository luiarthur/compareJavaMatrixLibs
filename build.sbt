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
 
  // https://mvnrepository.com/artifact/org.apache.commons/commons-math3
  // kinda slow... single threaded, 1.9MB
  "org.apache.commons" % "commons-math3" % "3.6.1"
)
