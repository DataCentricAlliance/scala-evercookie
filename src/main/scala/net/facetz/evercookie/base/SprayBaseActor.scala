package net.facetz.evercookie.base

import akka.actor.{Actor, ActorLogging}
import spray.http.StatusCodes
import spray.routing._
import spray.util.LoggingContext

import scala.language.implicitConversions

abstract class SprayBaseActor extends Actor
with ActorLogging
with HttpService
with AbstractRoute {

  implicit def myExceptionHandler(implicit log: LoggingContext): ExceptionHandler =
    ExceptionHandler {
      case e: Exception =>
        requestUri {
          uri =>
            if (log.isDebugEnabled) {
              log.error(e, s"Request to $uri could not be handled normally")
            }
            complete(StatusCodes.InternalServerError, "Error processing request")
        }
    }

  def actorRefFactory = context

  lazy val computedRoute: Route = route

  def receive = {
    runRoute(computedRoute)
  }

}

