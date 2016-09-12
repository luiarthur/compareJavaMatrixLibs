object Compare {
  def main(args: Array[String]) = {

    def msg(s: String) = print(Console.GREEN + s + Console.RESET)
    def timer[R](block: => R): R = {  
      val t0 = System.nanoTime()
      val result = block
      val t1 = System.nanoTime()
      msg( (t1 - t0) / 1E9 + "s\n" )
      result
    }

    val R = new scala.util.Random(0)
    val n = 1000
    val it = 100

    // ejml
    msg("ejml: ")
    import org.ejml.simple._
    val A = new SimpleMatrix(n,n)
    for (i <- 0 until A.numRows; j <- 0 until A.numCols) A.set(i,j,R.nextGaussian)
    timer { val y = A mult A } // .809
    //timer { for (i <- 1 to it) { val y = A mult A.transpose}} // 5.5s
    //timer { List(0 until it).foreach(i => {val y = A mult A.transpose}) } // .06s
    //timer { List(0 until it).par.foreach(i=> {val y = A mult A.transpose})} // .06s

    // la4j. Requires java >= 7 
    msg("la4j: ")
    val B = org.la4j.matrix.DenseMatrix.zero(n,n)
    for (i <- 0 until B.rows; j <- 0 until B.columns) B.set(i,j,R.nextGaussian)
    timer { val y = B multiply B } // 1.17

    // commons Math
    msg("apache commons math: ")
    import org.apache.commons.math3.linear._
    val C = new Array2DRowRealMatrix(n,n)
    for (i <- 0 until C.getRowDimension; j <- 0 until C.getColumnDimension) C.setEntry(i,j,R.nextGaussian)
    timer { val y = C multiply C } // 1.10

    // Jama
    msg("Jama: ")
    import Jama._ // most reliable / portable, smallest jar, fastest init of matrices, relatively nice syntax, but no parallel, no sparse matrix support, not the fastest (parallel colt) pure java solution, but not the slowest (commons)>
    val D = new Matrix(n,n)
    for(i <- 0 until D.getRowDimension; j <- 0 until D.getColumnDimension) D.set(i, j, R.nextGaussian)
    timer { val y = D times D } // 1.10

    // Breeze
    val E = breeze.linalg.DenseMatrix.fill(n,n)(R.nextGaussian)
    msg("Breeze: ")
    timer {val y = E * E } // .05s, on par with Julia (OpenBLAS). In absence of OpenBLAS, performs like Jama

    // Parallel Colt
    msg("Parallel Colt: ")
    import cern.colt.matrix.tdouble._
    import cern.jet.math.tdouble.DoubleFunctions._
    val alg = new cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra
    val F = DoubleFactory2D.dense.make(n,n)
    for(i <- 0 until F.rows; j <- 0 until F.columns) F.set(i, j, R.nextGaussian)
    timer { val x = alg.mult(F,F) } // .423

    msg("jblas, can i multithread? ")
    import org.jblas._
    val G = DoubleMatrix.randn(n,n)
    timer { val y = G mmul G } // .1745 single thread
  }
}
