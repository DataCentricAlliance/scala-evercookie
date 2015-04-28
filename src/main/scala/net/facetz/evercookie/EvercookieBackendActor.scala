package net.facetz.evercookie

import net.facetz.evercookie.base.SprayBaseActor
import net.facetz.evercookie.routes._
import spray.http.HttpHeaders.RawHeader

class EvercookieBackendActor extends SprayBaseActor
with EvercookieCacheRoute
with EvercookieEtagRoute
with EvercookiePngRoute
