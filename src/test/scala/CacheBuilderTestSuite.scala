import net.sf.ehcache.Element
import org.scalatest.FunSuite

/**
 * Created by vch on 10/21/13.
 */
class CacheBuilderTestSuite extends FunSuite {
  test("default names") {
    val cache = new CacheBuilder().build
    assert(cache.getName == "cache")
    assert(cache.getCacheManager.getName == "cacheManager")
  }
  test("basic onheap cache") {
    val cache = new CacheBuilder(name = "onHeapCache").build
    assert(cache != null)
    cache.put(new Element("a", "b"))
    assert(cache.get("a").getObjectValue == "b")
  }
  test("basic offheap supported cache") {
    val cache = new CacheBuilder(name = "offHeapCache", maxBytesLocalOffHeap = 256, maxEntriesInHeap = 1).build
    assert(cache != null)
    for (i <- 1 to 2000)
      cache.put(new Element(i, i))

    for (i <- 1 to 2000)
      println("element " + i + "=" + cache.isElementOffHeap(i))


  }

}
