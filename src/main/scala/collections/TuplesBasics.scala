package collections

/**
  * Actions with Tuple class with demonstration of tuple pattern match
  */
object TuplesBasics extends App {


  val pair = ("order-id", 1)

  println("pair : " + pair._1 + ", " + pair._2)

  println("pair class: " + pair.getClass.getName)


  var triple = ("order", "quantity", 100)

  println("triple : " + triple._1 + ", " + triple._2 + ", " + triple._3)

  println("triple class: " + triple.getClass.getName)


  // Demonstrates tuple pattern match

  def merge(l1: List[Int], l2: List[Int]): List[Int] = (l1, l2) match {
    case (Nil, x2) => x2
    case (x1, Nil) => x1
    case (x :: xs, y :: ys) => if (x < y) x :: merge(xs, l2) else y :: merge(l1, ys)
  }

  println("merge 1,2,3,4: " + merge(List(1, 2), List(3, 4)))
}
