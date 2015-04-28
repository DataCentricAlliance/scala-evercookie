package net.facetz.evercookie.routes

import net.facetz.evercookie.EvercookieBackendActor
import net.facetz.evercookie.base.AbstractRoute
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.Route

/**
 * This is a Scala Spray port of evercookie_cache.php, the server-side
 * component of Evercookie's cacheData mechanism.
 */
trait EvercookieCacheRoute extends AbstractRoute {
  val cacheRoute =
    path(cacheRoutePath) {
      get {
        respondWithMediaType(MediaTypes.`text/html`) {
          optionalCookie(cacheCookieName) { evercookieCache =>
            val cookieExists = evercookieCache.isDefined

            if (cookieExists) {
              val cookieValue = evercookieCache.get.content
              respondWithHeaders(EvercookieBackendActor.headers) {
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
