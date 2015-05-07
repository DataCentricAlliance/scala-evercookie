package net.facetz.evercookie.base

import net.facetz.evercookie.base.EvercookieBackendConfig._
import org.slf4j.LoggerFactory

trait EvercookieLogging {
  private val log = LoggerFactory.getLogger(getClass)

  def debugLogRequest(path: String, cookieName: String, cookieExists: Boolean) = {
    if (log.isDebugEnabled) {
      log.debug(s"Request to '/$cacheRoutePath': Cookie with name '$cacheCookieName' exists = $cookieExists")
    }
  }

}
