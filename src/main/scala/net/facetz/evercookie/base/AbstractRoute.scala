package net.facetz.evercookie.base

import spray.routing._

trait AbstractController {

  import scala.concurrent.ExecutionContext.Implicits

  implicit lazy val globalContext = Implicits.global
}

trait AbstractRoute extends Directives with AbstractController {
  def route: Route = reject
}
