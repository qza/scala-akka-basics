package actor

import scala.concurrent.duration._

import akka.actor._

import akka.event.LoggingReceive


/**
  * Account model
  * @param id
  * @param amount
  */
case class Account(val id: Long, val amount: BigDecimal)


/**
  * Accounts balances consisting of checking and saving accounts balances
  * @param checking
  * @param savings
  */
case class AccountBalances(val checking: Option[List[Account]], val savings: Option[List[Account]])


/**
  * Checking accounts balances
  * @param balances
  */
case class AccountCheckingBalances(val balances: Option[List[Account]])


/**
  * Saving accounts balances
  * @param balances
  */
case class AccountSavingBalances(val balances: Option[List[Account]])


/**
  * Trait representing saving accounts proxy (to be implemented)
  */
trait SavingAccountsProxy extends Actor


/**
  * Trait representing checking accounts proxy (to be implemented)
  */
trait CheckingAccountsProxy extends Actor


/**
  * Request for balances
  *
  * @param id
  */
case class GetBalances(id: Long)


/**
  * Companion object for AccountBalanceResponseHandler with factory method for actor props
  */
object AccountBalanceResponseHandler {

  case object BalanceRetrievalTimeout

  /**
    * Factory method used for constructing actor props
    */
  def props(savingAccountsProxy: ActorRef, checkingAccountsProxy: ActorRef, originalSender: ActorRef): Props = {
    Props(new AccountBalanceResponseHandler(savingAccountsProxy, checkingAccountsProxy, originalSender))
  }

}


/**
  * Actor that handles balance responses. Manages state to collect all balances
  *
  * @param savingAccountsProxy
  * @param checkingAccountsProxy
  * @param originalSender
  */
class AccountBalanceResponseHandler(savingAccountsProxy: ActorRef,
                                    checkingAccountsProxy: ActorRef,
                                    originalSender: ActorRef) extends Actor with ActorLogging {

  import AccountBalanceResponseHandler._

  var checkingBalances, savingsBalances: Option[List[Account]] = None // state

  def receive = LoggingReceive {

    case AccountCheckingBalances(balances) =>
      log.debug(s"received checking balances; $balances")
      checkingBalances = balances
      collectBalances

    case AccountSavingBalances(balances) =>
      log.debug(s"received savings balances; $balances")
      savingsBalances = balances
      collectBalances

    case BalanceRetrievalTimeout =>
      log.debug(s"received retrieval timeout")
      respondAndShutdown(BalanceRetrievalTimeout)
  }

  def collectBalances = (checkingBalances, savingsBalances) match {
    case (Some(c), Some(s)) =>
      log.debug(s"received all balances")
      respondAndShutdown(AccountBalances(checkingBalances, savingsBalances))
      scheduledTimeout.cancel
    case _ =>
  }

  def respondAndShutdown(response: Any) = {
    originalSender ! response
    log.debug(s"exiting")
    context.stop(self)
  }


  import context.dispatcher

  /**
    * Self-send timeout event after 1 second, unless is canceled
    */
  val scheduledTimeout = context.system.scheduler.
    scheduleOnce(1000 milliseconds) {
      self ! BalanceRetrievalTimeout
    }

}

/**
  * Actor handling account balances request. Invokes referenced proxies to retrieve balances
  *
  * @param savingAccountProxy
  * @param checkingAccountProxy
  */
class AccountBalanceRequestHandler(savingAccountProxy: ActorRef,
                                   checkingAccountProxy: ActorRef) extends Actor with ActorLogging {

  def receive = {

    case GetBalances(id) =>
      val originalSender = sender
      val responseHandler = context.actorOf(
        AccountBalanceResponseHandler
          .props(savingAccountProxy, checkingAccountProxy, originalSender), "response-handler")

      savingAccountProxy.tell(GetBalances(id), responseHandler)
      checkingAccountProxy.tell(GetBalances(id), responseHandler)

  }

}


class CheckingAccountsProxyStub extends CheckingAccountsProxy with ActorLogging {

  val accountMap = Map[Long, List[Account]](
    1L -> List(Account(10L, 1000)),
    2L -> List(Account(20L, 2000), Account(21L, 2100))
  )

  def receive = LoggingReceive {
    case GetBalances(id: Long) =>
      log.debug(s"received get checking balance request [account: $id]")
      accountMap.get(id) match {
        case Some(data) => sender ! AccountCheckingBalances(Some(data))
        case None => sender ! AccountCheckingBalances(Some(List()))
      }
  }

}

class SavingAccountsProxyStub extends SavingAccountsProxy with ActorLogging {

  val accountMap = Map[Long, List[Account]](
    1L -> List(Account(100L, 10000)),
    2L -> List(Account(200L, 20000), Account(210L, 21000))
  )

  def receive = LoggingReceive {
    case GetBalances(id: Long) =>
      log.debug(s"received get saving balance request [account: $id]")
      accountMap.get(id) match {
        case Some(data) => sender ! AccountSavingBalances(Some(data))
        case None => sender ! AccountSavingBalances(Some(List()))
      }
  }

}


/**
  * Demonstrates account balance actor system
  */
object AccountBalanceDemoRunner extends App {

  case object Execute

  class RequestMaker(requestHandler: ActorRef) extends Actor with ActorLogging {

    import AccountBalanceResponseHandler._

    def receive = {

      case Execute =>
        requestHandler ! GetBalances(2L)

      case AccountBalances(checking, saving) =>
        log.info(s"received balances response: [checking: $checking, saving: $saving]")

      case BalanceRetrievalTimeout =>
        log.info(s"received balances retrieve timeout")
    }

  }

  val system = ActorSystem("account-balance-actor-system")

  val savingsAccountsProxy = system.actorOf(Props[SavingAccountsProxyStub], "saving-accounts-proxy-stub")

  val checkingAccountsProxy = system.actorOf(Props[CheckingAccountsProxyStub], "checking-accounts-proxy-stub")

  val requestHandler = system.actorOf(Props(new AccountBalanceRequestHandler(savingsAccountsProxy, checkingAccountsProxy)))

  val requestMaker = system.actorOf(Props(new RequestMaker(requestHandler)))

  requestMaker ! Execute

  Thread.sleep(1000);

  system.shutdown()

}