package net.facetz.evercookie.routes

import net.facetz.evercookie.base.{EvercookieLogging, AbstractRoute}
import spray.http.HttpHeaders.RawHeader
import spray.http.{MediaTypes, StatusCodes}
import spray.routing._

import net.facetz.evercookie.base.EvercookieBackendConfig._

/**
 * This is a Scala Spray port of evercookie_etag.php, the server-side component
 * of Evercookie that uses the If-None-Match and Etag headers to keep track of
 * persistent values.
 */
trait EvercookieEtagRoute extends AbstractRoute with EvercookieLogging {
  val etagRoute =
    path(etagRoutePath) {
      get {
        respondWithMediaType(MediaTypes.`text/html`) {
          optionalHeaderValueByName(ifNoneMatchHeader) { ifNoneMatchHeader =>
            optionalCookie(etagCookieName) { evercookieEtag =>
              val cookieExists = evercookieEtag.isDefined

              debugLogRequest(etagRoutePath, etagCookieName, cookieExists)

              if (cookieExists) {
                val cookieValue = evercookieEtag.get.content
                // Cookie set; send cookie value as Etag header/response body.
                respondWithHeader(RawHeader(etagHeader, cookieValue)) {
                  complete(StatusCodes.OK, cookieValue)
                }
              } else {
                // No cookie; set the body to the request's If-None-Match value.
                complete(StatusCodes.OK, ifNoneMatchHeader.getOrElse(""))
              }
            }
          }
        }
      }
    }

  override def route: Route = {
    etagRoute ~ super.route
  }
}
