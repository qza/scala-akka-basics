package model

/**
  * Abstract class Natural representing non-negative integers (Piano numbers)
  *
  * Demonstrates usual object decomposition which is suitable in ever evolving model hierarchies
  *
  * As other object oriented languages, Scala implements dynamic binding (dynamic method dispatch)
  * (code that is executed depends on the runtime of type instance
  */
abstract class NaturalAbstract {

  def isZero: Boolean

  def predecessor: NaturalAbstract

  def successor: NaturalAbstract

  def +(other: NaturalAbstract): NaturalAbstract

  def -(other: NaturalAbstract): NaturalAbstract

}

object Zero extends NaturalAbstract {

  override def isZero: Boolean = true

  override def predecessor = throw new Error("predecessor of zero")

  override def successor = new Successor(this)

  override def +(other: NaturalAbstract) = other

  override def -(other: NaturalAbstract) = if (other.isZero) this else throw new Error("negative number")

}

class Successor(n: NaturalAbstract) extends NaturalAbstract {

  override def isZero: Boolean = false

  override def successor: NaturalAbstract = new Successor(this)

  override def predecessor: NaturalAbstract = n

  override def +(other: NaturalAbstract): NaturalAbstract = new Successor(n + other)

  override def -(other: NaturalAbstract): NaturalAbstract = if (other.isZero) this else n - other.predecessor

}
