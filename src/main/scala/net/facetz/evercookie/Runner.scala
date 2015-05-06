package net.facetz.evercookie

import akka.actor.{ActorRef, Props}
import net.facetz.evercookie.base.{EvercookieBackendConfig, SprayConfigBase, SprayRunner}

object Runner extends App {
  val runner = new Runner
  runner.init(null)
  runner.start()
  Runtime.getRuntime.addShutdownHook(new Thread(new Runnable {
    override def run(): Unit = {
      runner.stop()
      runner.destroy()
    }
  }))
}

class Runner extends SprayRunner {
  override def serviceName: String = "evercookie-backend"

  override def sprayConfig: SprayConfigBase = EvercookieBackendConfig

  override def baseHandler: ActorRef = system.actorOf(Props.apply(classOf[EvercookieBackendActor]))
}
