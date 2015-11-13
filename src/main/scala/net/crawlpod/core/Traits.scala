package net.crawlpod.core

import org.json4s.JsonAST.JObject
import net.crawlpod.external.MongodbQueue
import net.crawlpod.external.MongodbJsonStore
import net.crawlpod.external.MongodbRawStore

/**
 * @author sakthipriyan
 */

object Queue {
  def apply(name: String): Queue = name match {
    case "MongodbQueue" => new MongodbQueue
    case _              => throw new RuntimeException(s"Invalid provider name for the queue $name")
  }
}

object RawStore {
  def apply(name: String): MongodbRawStore = name match {
    case "MongodbRawStore" => new MongodbRawStore
    case _                 => throw new RuntimeException(s"Invalid provider name for the raw store $name")
  }
}

object JsonStore {
  def apply(name: String): MongodbJsonStore = name match {
    case "MongodbJsonStore" => new MongodbJsonStore
    case _                  => throw new RuntimeException(s"Invalid provider name for the json store $name")
  }
}

trait Queue {
  def enqueue(r: List[CrawlRequest])
  def dequeue: Option[CrawlRequest]
  def failed(req: CrawlRequest, res: CrawlResponse)
  def size: Long
  def completed: Long
  def empty: Unit
  def shutdown: Unit
}

trait RawStore {
  def put(res: CrawlResponse)
  def get(req: CrawlRequest): Option[CrawlResponse]
  def count: Long
  def empty: Unit
  def shutdown: Unit
}

trait JsonStore {
  def write(json: List[JObject])
  def count: Long
  def empty: Unit
  def shutdown: Unit
}