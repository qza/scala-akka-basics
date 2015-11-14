package collections

/**
  * Demonstrates math.Ordering and usage of implicit parameters
  */
object Orderings extends App {

  def msort[T](list: List[T])(implicit order: Ordering[T]): List[T] = {
    val n = list.length / 2;
    if (n == 0) list
    else {
      val (fst, snd) = list.splitAt(n)
      merge(msort(fst), msort(snd))
    }
  }

  def merge[T](l1: List[T], l2: List[T])(implicit order: Ordering[T]): List[T] = (l1, l2) match {
    case (Nil, x2) => x2
    case (x1, Nil) => x1
    case (x :: xs, y :: ys) => if (order.lt(x, y)) x :: merge(xs, l2) else y :: merge(l1, ys)
  }

  val numbers = List(1, -2, 5, 7, 10)

  val fruits = List("banana", "apple", "lemon")

  println(numbers + " : " + msort(numbers))

  println(fruits + " : " + msort(fruits))

}
