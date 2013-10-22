import net.sf.ehcache.Cache
import scala.collection.Iterator

/**
 * Created by vinay on 10/21/13.
 */
class KVIterator[A, B](val cacheAsMap: CacheAsMap[A, B]) extends Iterator[(A, B)] {
  val keyIterator=cacheAsMap.internalCache.getKeys.iterator()

  def hasNext: Boolean = keyIterator.hasNext

  def next(): (A, B) = {
    val key = keyIterator.next.asInstanceOf[A]
    (key, cacheAsMap.getValue(key))
  }
}
