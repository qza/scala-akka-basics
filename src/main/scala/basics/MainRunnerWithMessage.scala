package basics

/**
  * Runs main method and prints message
  */
object MainRunnerWithMessage {

  def main(args:Array[String]) {

    printf("""|Welcome to scala!
             |This raw string can contain anything
             |It starts and ends with triple quotes
             |New lines are marked with pipe character
             |Call to the stripMargin for nice printing""".stripMargin);

  }

}