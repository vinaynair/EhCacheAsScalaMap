/**
 * Created by vinay on 10/20/13.
 */


import net.sf.ehcache.Element
import scala.collection.JavaConverters._
import scala.collection.{immutable, JavaConversions}
import scala.collection.mutable.Map
import scala.Some


class CacheAsMap[A, B](val cacheBuilder: CacheBuilder=new CacheBuilder)
  extends Map[A, B]() {

  val internalCache = cacheBuilder.build

  def getValue(key: A): B = {
    val value = internalCache.get(key)
    if (value != null) value.getObjectValue.asInstanceOf[B] else null.asInstanceOf[B]
  }

  def get(key: A): Option[B] = {
    val value = getValue(key)
    if (value != null) Some(value) else None
  }


  def iterator: Iterator[(A, B)] = {
    new KVIterator(this)
  }

  override def +[B1 >: B](kv: (A, B1)): Map[A,B1] = {
    //todo: this.type is sufficient ??
    internalCache.put(new Element(kv._1, kv._2))
    this.asInstanceOf[Map[A,B1]]
  }

  override def -(key: A): Map[A, B] = {
    internalCache.remove(key)
    this
  }

  override def +=(kv: (A, B)): this.type = {
    internalCache.put(new Element(kv._1, kv._2))
    this
  }

  // Should we return previous value directly or as Option ?
  def putIfAbsent(key: A, value: B): Option[B] = {
    val prev = internalCache.putIfAbsent(new Element(key, value))
    if (prev != null) Some(prev.getObjectValue.asInstanceOf[B]) else None
  }


  override def -=(key: A): this.type = {
    internalCache.remove(key)
    this
  }

  override def update(key: A, value: B) {
    this += ((key, value))
  }

  override def apply(key: A): B = {
    val element = internalCache.get(key)
    if (element == null) throw new NoSuchElementException()
    getValue(key)
  }


  override def filter(p: ((A, B)) => Boolean): Map[A, B] = {
    val m:Map[A,B]=Map() ++ new KVIterator(this)
    m.map(kv => (kv._1, kv._2)).filter(p)
  }

  override def empty: Map[A, B] = {
    internalCache.removeAll
    this
  }

  override def size: Int = internalCache.getSize

  override def foreach[U](f: ((A, B)) => U) {
    val iterator = new KVIterator(this)
    while (iterator.hasNext) {
      val kv = (iterator.next())
      f(kv)
    }
  }

  def toMap: immutable.Map[A, B] = iterator.toMap

  /**
   * Removes old key-value pairs that have timestamp earlier than `threshTime`
   */
  def clearOldValues(threshTime: Long) {
    //do nothing, ehcache config will take care of it
  }

  private def currentTime: Long = System.currentTimeMillis()
}
