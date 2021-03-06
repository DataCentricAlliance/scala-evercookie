package net.facetz.evercookie

import net.facetz.evercookie.base.{EvercookieLogging, SprayBaseActor}
import net.facetz.evercookie.routes._

class EvercookieBackendActor extends SprayBaseActor
with EvercookieCacheRoute
with EvercookieEtagRoute
with EvercookiePngRoute
with EvercookieLogging