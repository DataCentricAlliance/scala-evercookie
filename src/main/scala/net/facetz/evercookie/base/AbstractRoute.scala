package net.facetz.evercookie.base

import spray.routing._
import scala.concurrent.ExecutionContext.Implicits

trait AbstractRoute extends Directives {
  implicit lazy val globalContext = Implicits.global

  def route: Route = reject
}
