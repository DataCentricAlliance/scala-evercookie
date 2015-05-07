package net.facetz.evercookie.routes

import net.facetz.evercookie.base.{EvercookieLogging, AbstractRoute}
import net.facetz.evercookie.base.EvercookieBackendConfig._
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.Route

/**
 * This is a Scala Spray port of evercookie_cache.php, the server-side
 * component of Evercookie's cacheData mechanism.
 */
trait EvercookieCacheRoute extends AbstractRoute with EvercookieLogging {
  val cacheRoute =
    path(cacheRoutePath) {
      get {
        respondWithMediaType(MediaTypes.`text/html`) {
          optionalCookie(cacheCookieName) { evercookieCache =>
            val cookieExists = evercookieCache.isDefined

            debugLogRequest(cacheRoutePath, cacheCookieName, cookieExists)

            if (cookieExists) {
              val cookieValue = evercookieCache.get.content
              respondWithHeaders(headers) {
                complete(StatusCodes.OK, cookieValue)
              }
            } else {
              // If the cookie doesn't exist, send 304 Not Modified and exit.
              complete(StatusCodes.NotModified)
            }
          }
        }
      }
    }

  override def route: Route = {
    cacheRoute ~ super.route
  }
}
