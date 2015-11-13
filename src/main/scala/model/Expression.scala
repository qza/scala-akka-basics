package model

/**
  * Demonstrates case classes and pattern matching as alternative to object decomposition
  *
  * Appropriate when object hierarchy is complete and differs in methods
  *
  * Patterns can be:
  * - constructors, Number, Sum
  * - variables: n, e1
  * - wildcard patterns: _
  * - constants: 1, true
  *
  * MatchError can be thrown if no matches
  *
  */

trait Expression {

  def evaluate(): Int = evaluate(this)

  def show(): String = show(this)

  def evaluate(e: Expression): Int = e match {
    case Number(n) => n
    case Sum(e1, e2) => evaluate(e1) + evaluate(e2)
  }

  def show(e: Expression): String = e match {
    case Number(n) => n.toString
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
  }
}

case class Number(n: Int) extends Expression

case class Sum(left: Expression, right: Expression) extends Expression

// scala compiler adds apply method to case classes (factory method) - Number(1)


object ExpressionRunner extends App {

  val expression = Sum(Sum(Number(1), Number(2)), Number(3))

  println(expression.show + " = " + expression.evaluate)

}


