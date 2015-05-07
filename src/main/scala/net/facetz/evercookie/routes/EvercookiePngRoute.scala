package net.facetz.evercookie.routes

import java.awt.image.BufferedImage
import java.awt.{Color, RenderingHints}
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

import net.facetz.evercookie.base.{EvercookieLogging, AbstractRoute}
import net.facetz.evercookie.base.EvercookieBackendConfig._
import spray.http.{MediaTypes, StatusCodes}
import spray.routing._

/**
 * This is a Scala Spray port of evercookie_png.php, the server-side component
 * of Evercookie that stores values in force-cached PNG image data.
 */
trait EvercookiePngRoute extends AbstractRoute {
  this: EvercookieLogging =>
  val pngRoute =
    path(pngRoutePath) {
      get {
        respondWithMediaType(MediaTypes.`image/png`) {
          optionalHeaderValueByName(ifNoneMatchHeader) { ifNoneMatchHeader =>
            optionalCookie(pngCookieName) { evercookieEtag =>
              val cookieExists = evercookieEtag.isDefined

              debugLogRequest(pngRoutePath, pngCookieName, cookieExists)

              if (cookieExists) {
                respondWithHeaders(headers) {
                  val cookieValue = evercookieEtag.get.content

                  // Generate a PNG image from the cookie value.
                  val image = new BufferedImage(200, 1, BufferedImage.TYPE_INT_ARGB)
                  image.createGraphics().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)

                  var x = 0
                  for {i <- 0 until cookieValue.length by 3} {
                    // Treat every 3 chars of the cookie value as an {R,G,B} triplet.
                    val c = new Color(cookieValue.charAt(i), cookieValue.charAt(i + 1), cookieValue.charAt(i + 2))
                    image.setRGB(x, 0, c.getRGB)
                    x += 1
                  }

                  // Send the generate image data as the response body.
                  val baos = new ByteArrayOutputStream()
                  try {
                    ImageIO.write(image, "png", baos)
                  } finally {
                    baos.close()
                  }

                  complete(StatusCodes.OK, baos.toByteArray)
                }
              } else {
                // If the cookie doesn't exist, send 304 Not Modified and exit.
                complete(StatusCodes.NotModified)
              }
            }
          }
        }
      }
    }

  override def route: Route = {
    pngRoute ~ super.route
  }
}
