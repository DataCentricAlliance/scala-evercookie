package net.facetz.evercookie

package object routes {
  val ifNoneMatchHeader = "If-None-Match"

  val etagRoutePath = "evercookie_etag.php"
  val etagCookieName = "evercookie_etag"
  val etagHeader = "Etag"

  val cacheRoutePath = "evercookie_cache.php"
  val cacheCookieName = "evercookie_cache"

  val pngRoutePath = "evercookie_png.php"
  val pngCookieName = "evercookie_png"
}
