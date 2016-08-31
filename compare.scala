object Compare {
  def main(args: Array[String]) = {

    def timer[R](block: => R): R = {  
      val t0 = System.nanoTime()
      val result = block
      val t1 = System.nanoTime()
      println("\nElapsed time: " + (t1 - t0) / 1E9 + "s\n")
      result
    }

    val R = new scala.util.Random(0)
    val (n,k) = (1000,100)
    val it = 100

    // ejml
    print("ejml")
    import org.ejml.simple._
    val A = new SimpleMatrix(n, k)
    for (i <- 0 until A.numRows; j <- 0 until A.numCols) A.set(i,j,R.nextGaussian)
    timer { for (i <- 1 to it) { val y = A mult A.transpose}} // 5.5s
    //timer { List(0 until it).foreach(i => {val y = A mult A.transpose}) } // .06s
    //timer { List(0 until it).par.foreach(i=> {val y = A mult A.transpose})} // .06s

    // la4j. Requires java >= 7 
    print("la4j")
    import org.la4j.matrix._
    import org.la4j.linear._
    val B = DenseMatrix.zero(n, k)
    for (i <- 0 until B.rows; j <- 0 until B.columns) B.set(i,j,R.nextGaussian)
    timer { for (i <- 1 to it) { val y = B multiply B.transpose}} // 8s
    //timer { List(0 until it).foreach(i=> { val y = B multiply B.transpose})} // .085s
    //timer { List(0 until it).par.foreach(i=> { val y = B multiply B.transpose})} // .083s

    // commons Math
    print("apache commons math")
    import org.apache.commons.math3.linear._
    val C = new Array2DRowRealMatrix(n,k)
    for (i <- 0 until C.getRowDimension; j <- 0 until C.getColumnDimension) C.setEntry(i,j,R.nextGaussian)
    timer { for (i <- 1 to it) { val y = C multiply C.transpose}} // 11s
    //timer { List(0 until it).foreach(i=> { val y = C multiply C.transpose})} // .11s
    //timer { List(0 until it).par.foreach(i=> { val y = C multiply C.transpose})} // .12s

    // Jama
    print("Jama")
    import Jama._ // most reliable / portable, smallest jar, fastest init of matrices, relatively nice syntax, but no parallel, no sparse matrix support, not the fastest (parallel colt) pure java solution, but not the slowest (commons)>
    val D = new Matrix(n, k)
    for(i <- 0 until D.getRowDimension; j <- 0 until D.getColumnDimension) D.set(i, j, R.nextGaussian)
    timer { for (i <- 1 to it) { val y = D times D.transpose}} // 8.22s
    //timer { List(0 until it).foreach(i => {val y = D times D.transpose}) } // .086s
    //timer { List(0 until it).par.foreach(i=> {val y = D times D.transpose})} // .087s

    // Breeze
    print("Breeze")
    import breeze.linalg.DenseMatrix
    val E = DenseMatrix.fill(n,k)(R.nextGaussian)
    timer {for (i <- 1 to it) {val y = E * E.t}} // .35s, on par with Julia (OpenBLAS). In absence of OpenBLAS, performs like Jama
    //timer { List(0 until it).foreach(i => {val y = E * E.t}) } // .0035s

    // Parallel Colt
    print("Parallel Colt")
    import cern.colt.matrix.tdouble._
    import cern.jet.math.tdouble.DoubleFunctions._
    val alg = new cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra
    val F = DoubleFactory2D.dense.make(n,k)
    for(i <- 0 until F.rows; j <- 0 until F.columns) F.set(i, j, R.nextGaussian)
    timer { for (i <- 0 until it) { val x = alg.mult(F,alg.transpose(F))}} // 2.34
    timer { List(0 until it).foreach {i=> val x = alg.mult(F,alg.transpose(F))}} // .022 => not 100x faster
  }
}
