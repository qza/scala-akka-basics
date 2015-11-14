package collections

/**
  * Demonstrates higher order list functions
  */
object ListFunctions extends App {

  val numbers = List(1, 2, 3, 4, 5)

  println("numbers: " + numbers)


  val numbersScaleWith2 = numbers.map(x => x * 2)

  println("scaled with 2: " + numbersScaleWith2)


  val numbersFilterUnder3 = numbers.filter(x => x < 3)

  println("filtered under 3: " + numbersFilterUnder3)


  val numbersPartitionEven = numbers.partition(x => (x % 2 == 0)) // pair of filter and filterNot

  println("partition even: " + numbersPartitionEven)


  val numbersTakeUnder3 = numbers.takeWhile(x => x < 3)

  println("taken under 3: " + numbersTakeUnder3)


  val numbersDropUnder3 = numbers.dropWhile(x => x < 3)

  println("dropped under 3: " + numbersDropUnder3)


  val numbersSpanEven = numbers.span(x => (x % 2 == 0)) // similar to partition, pair of takeWhile and dropWhile

  println("spanned even: " + numbersSpanEven)


  /**
    * Function pack that packs consecutive duplicates of the list into sub-lists
    *
    * @param list
    * @tparam T
    * @return
    */
  def pack[T](list: List[T]): List[List[T]] = list match {
    case Nil => Nil
    case x :: xs =>
      val (first, rest) = xs.span(s => s == x)
      (List(x) ++ first) :: pack(rest)
  }


  val duplicates = List("a", "a", "a", "b", "b", "c", "d", "d", "d")

  val duplicatedPack = pack(duplicates)

  println("duplicates: " + duplicates)

  println("packed: " + duplicatedPack)


  /**
    * Function encode that encodes consecutive duplicates into sub-list of pairs,
    * each consisting of element and number of duplications
    *
    * @param list
    * @tparam T
    * @return
    */
  def encode[T](list: List[T]): List[(T, Int)] = list match {
    case Nil => Nil
    case x :: xs =>
      pack(list).map(x => (x.head, x.length))
  }


  val duplicatedEncoded = encode(duplicates)

  println("encoded: " + duplicatedEncoded)

}
