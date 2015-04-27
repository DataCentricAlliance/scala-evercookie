package net.facetz.evercookie.base

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import akka.io.IO
import org.apache.commons.daemon.{Daemon, DaemonContext}
import spray.can.Http

import scala.concurrent.duration.Duration

trait SprayRunner extends Daemon {

  def serviceName: String

  def sprayConfig: SprayConfigBase

  def baseHandler: ActorRef

  implicit val system = ActorSystem(serviceName, sprayConfig.config)
  val waitTimeout = Duration(30, TimeUnit.SECONDS)

  override def start() {
    IO(Http) ! Http.Bind(baseHandler, interface = sprayConfig.host, port = sprayConfig.port)
  }

  override def stop() {
    IO(Http) ! Http.CloseAll
    system.shutdown()
    system.awaitTermination(waitTimeout)
  }

  override def init(context: DaemonContext) {}

  override def destroy() {}

}
