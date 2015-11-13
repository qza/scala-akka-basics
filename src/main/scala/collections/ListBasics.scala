package collections

/**
  * Demonstrates basic List methods
  */
object ListBasics extends App {


  val list = List(1, 2, 3, 4, 5, 6)


  println("length: " + list.length)

  println("last: " + list.last)

  println("init: " + list.init)

  println("take 2: " + list.take(2))

  println("drop 2: " + list.drop(2))

  println("add 7,8: " + (list ++ List(7, 8)))

  println("where is 3: on " + list.indexOf(3))

  println("has 7: " + list.contains(7))


  /**
    * x :: xs matches head followed by the tail (remaining elements of the list)
    */
  def concat(l1: List[Int], l2: List[Int]): List[Int] = l1 match {
    case List() => l2
    case y :: ys => y :: concat(ys, l2) // at lease one element
  }

  println("add 7,8 again: " + (concat(list, List(7, 8))))


  def reverse(l: List[Int]): List[Int] = l match {
    case List() => l
    case y :: ys => reverse(ys) ++ List(y)
    // quadratic complexity (reverse, concat)
  }

  println("reverse: " + reverse(list))


  def removeAt(n: Int, list: List[Int]) = list.take(n) ::: list.drop(n + 1) // concat lists

  println("remove 3rd: " + removeAt(3, list))

}
