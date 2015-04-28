package net.facetz.evercookie.base

import com.typesafe.config.ConfigFactory

trait SprayConfigBase {
  final val config = ConfigFactory.load()

  lazy val host = config.getString("server.host")
  lazy val port = config.getInt("server.port")
}

object EvercookieBackendConfig extends SprayConfigBase
