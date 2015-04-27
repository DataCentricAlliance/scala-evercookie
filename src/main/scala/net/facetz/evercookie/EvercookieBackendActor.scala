package net.facetz.evercookie

import net.facetz.evercookie.base.SprayBaseActor
import net.facetz.evercookie.routes._
import spray.http.HttpHeaders.RawHeader

object EvercookieBackendActor {
  val headers = List(
    RawHeader("Last-Modified", "Wed, 30 Jun 2010 21:36:48 GMT"),
    RawHeader("Expires", "Tue, 31 Dec 2030 23:30:45 GMT"),
    RawHeader("Cache-Control", "private, max-age=630720000")
  )
}

class EvercookieBackendActor extends SprayBaseActor
with EvercookieCacheRoute
with EvercookieEtagRoute
with EvercookiePngRoute