package model

import java.util.NoSuchElementException

/**
  * Demonstrates usage of generics ~ type parameters, other form of polymorphism
  */
trait GenericList[T] {

  def isEmpty: Boolean

  def head: T

  def tail: GenericList[T]

}

/**
  * Cons list -> immutable list that contains two elements:
  *   Nil -> the empty list
  *   Cons -> the cell containing an element and the remainder of the list
  *
  * Notice val - counts as a field and implements abstract trait element
  * Difference between val and def is that val is evaluated immediately while def is evaluated when accessed
  *
  * @param head
  * @param tail
  * @tparam T - type parameters do NOT affect evaluation in scala (type erasure)
  */
class Cons[T](val head: T, var tail: GenericList[T]) extends GenericList[T] {
  override def isEmpty = false
}

/**
  * Nil list
  *
  * @tparam T
  */
class Nil[T] extends GenericList[T] {

  override def isEmpty: Boolean = true

  // Nothing is the subtype of any other type, thus implements GenericList trait

  override def head: Nothing = throw new NoSuchElementException("head is nil")

  override def tail: Nothing = throw new NoSuchElementException("tail is nil")

}

object GenericListRunner extends App {

  def nth[T](n: Int, list: GenericList[T]): T = {
    if (list.isEmpty) throw new IndexOutOfBoundsException
    else if (n == 0) list.head
    else nth(n - 1, list.tail)
  }

  val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))


  print("3rd element: ")
  println(nth(2, list))

  println("Negative element")
  println(nth(-1, list))

}
