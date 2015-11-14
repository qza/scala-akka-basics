package collections

/**
  * Fold (Reduce) combinator functions -> insert operator between adjacent elements of the list
  */
object ListReduction extends App {

  // Using reduce

  def sum(list: List[Int]) = (0 :: list) reduceLeft ((x, y) => x + y)

  def sumV2(list: List[Int]) = (0 :: list) reduceLeft (_ + _) // every underscore represents one parameter

  def product(list: List[Int]) = (1 :: list) reduceLeft ((x, y) => x * y)

  def productV2(list: List[Int]) = (1 :: list) reduceLeft (_ * _)


  // Using fold - similar to reduce except it takes an accumulator which is returned
  // when foldLeft is called on empty list

  def sumV3(list: List[Int]) = list.foldLeft(0)((x, y) => x + y)

  def sumV4(list: List[Int]) = list.foldLeft(0)(_ + _)


  val numbers = List(1, 2, 3, 4, 5)

  println("sum = " + sum(numbers))
  println("sumV2 = " + sumV2(numbers))
  println("sumV3 = " + sumV3(numbers))
  println("sumV4 = " + sumV4(numbers))
  println("product = " + product(numbers))
  println("productV2 = " + productV2(numbers))

}
