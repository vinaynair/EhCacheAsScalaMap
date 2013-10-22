import net.sf.ehcache.Element
import org.scalatest.FunSuite

/**
 * Created by vch on 10/21/13.
 */
class CacheBuilderTestSuite extends FunSuite {

  test("basic onheap cache") {
    val cache = new CacheBuilder(name = "onHeapCache").build
    assert(cache != null)
    cache.put(new Element("a", "b"))
    assert(cache.get("a").getObjectValue == "b")
  }

  /**
   * requires BigMemory with terracotta-license
   */
  /*
  test("basic offheap supported cache") {
    val cache = new CacheBuilder(name = "offHeapCache", maxBytesLocalOffHeapInMB = 256, maxEntriesInHeap = 1).build
    assert(cache != null)
    for (i <- 1 to 2000)
      cache.put(new Element(i, i))

    for (i <- 1 to 2000)
      println("element " + i + "=" + cache.isElementOffHeap(i))


  }
  */

}
