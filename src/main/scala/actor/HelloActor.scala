package actor

import akka.actor.{Props, ActorSystem, Actor}

case object Start

case object End

/**
  * Simple actor receiving messages
  */
class HelloActor extends Actor {

  override def receive = {
    case Start => {
      println("working")
    }
    case End => {
      println("done")
      context.stop(self)
    }
  }

}

object HelloActorRunner extends App {

  val sys = ActorSystem("actor-system")
  val hac = sys.actorOf(Props[HelloActor], "hello-actor")

  hac ! Start
  hac ! End

  sys.shutdown()
}
