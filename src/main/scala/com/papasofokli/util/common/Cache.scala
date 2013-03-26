package com.papasofokli.util.common

import scala.concurrent.Future
import akka.actor.ActorSystem
import spray.caching.{ LruCache, Cache }
import spray.util._
import scala.concurrent.duration._
import com.papasofokli.snippet.crewing.SeamanTable
/**
 * Simple caching to avoid hitting the db all the time, should be used for common queries
 */
object PCache {

  implicit val system = ActorSystem()

  private val cache: Cache[String] = LruCache(timeToLive = 30 seconds, timeToIdle = 15 seconds)

  private def getData[T](key: T): Future[String] = cache(key) {
    SeamanTable.data
  }

  def getSeamanTable = getData("seamanTable").await

}