package net.facetz.evercookie.base

import com.typesafe.config.ConfigFactory
import spray.http.HttpHeaders.RawHeader
import scala.collection.JavaConverters._

trait SprayConfigBase {
  final val config = ConfigFactory.load()

  val host = config.getString("server.host")
  val port = config.getInt("server.port")
}

object EvercookieBackendConfig extends SprayConfigBase {
  private val pathParam = "path"
  private val cookieParam = "cookie"
  private val rootConfig = "evercookie"
  private val cacheConfigParam = "cache"
  private val etagConfigParam = "etag"
  private val pngConfigParam = "png"
  private val headersParam = "headers"
  private val headerParam = "header"

  private final val evercookieConfig = config.getConfig(rootConfig)

  private final val cacheConfig = evercookieConfig.getConfig(cacheConfigParam)
  private final val etagConfig = evercookieConfig.getConfig(etagConfigParam)
  private final val pngConfig = evercookieConfig.getConfig(pngConfigParam)
  private final val headersConfig = evercookieConfig.getConfig(headersParam)

  val cacheRoutePath = cacheConfig.getString(pathParam)
  val etagRoutePath = etagConfig.getString(pathParam)
  val pngRoutePath = pngConfig.getString(pathParam)

  val cacheCookieName = cacheConfig.getString(cookieParam)
  val etagCookieName = etagConfig.getString(cookieParam)
  val pngCookieName = pngConfig.getString(cookieParam)

  val etagHeader = etagConfig.getString(headerParam)

  val headers = headersConfig.entrySet().asScala.map(entry => RawHeader(entry.getKey, entry.getValue.render())).toList
}
