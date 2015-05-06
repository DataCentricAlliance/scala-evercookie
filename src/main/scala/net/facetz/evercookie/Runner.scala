package net.facetz.evercookie

import akka.actor.{ActorRef, Props}
import net.facetz.evercookie.base.{EvercookieBackendConfig, SprayConfigBase, SprayRunner}
import org.apache.commons.daemon.DaemonContext
import org.slf4j.LoggerFactory

object Runner extends App {
  val log = LoggerFactory.getLogger(getClass)
  val runner = new Runner
  runner.init(null)
  runner.start()
  Runtime.getRuntime.addShutdownHook(new Thread(new Runnable {
    override def run(): Unit = {
      log.info("Shutdown hook was received, stopping")
      runner.stop()
      runner.destroy()
    }
  }))
}

class Runner extends SprayRunner {
  import Runner.log

  override def serviceName: String = "evercookie-backend"

  override def sprayConfig: SprayConfigBase = EvercookieBackendConfig

  override def baseHandler: ActorRef = system.actorOf(Props.apply(classOf[EvercookieBackendActor]))

  override def start(): Unit = {
    log.info(s"Starting scala-evercookie at ${sprayConfig.host}:${sprayConfig.port}")
    super.start()
    log.info(s"Started")
  }

  override def stop(): Unit = {
    log.debug("stop")
    super.stop()
  }

  override def init(context: DaemonContext): Unit = {
    log.debug("init")
    super.init(context)
  }

  override def destroy(): Unit = {
    log.debug("destroy")
    super.destroy()
  }
}
