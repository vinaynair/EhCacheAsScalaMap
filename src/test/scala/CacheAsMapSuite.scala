import org.scalatest.{BeforeAndAfter, FunSuite}


/**
 * Created by vinay on 10/21/13.
 */
class CacheAsMapSuite extends FunSuite {

  test("add, remove and get on a cache") {
    val m = new CacheAsMap[Int, String]
    m(1) = "a"
    m += (2 -> "b")
    assert(2 == m.size)
    m -= 2
    assert(1 == m.size)
    assert("a" == m(1))
  }

  test("filter on a cache") {
    val m = new CacheAsMap[String, String]()
    m("apple") = "fruit"
    m("potato") = "vegetable"
    m("grapes") = "fruit"
    assert(m.filter(kv => "fruit".equals(kv._2)).size == 2)
  }



  test("create a cache given constraints") {
    val m = new CacheAsMap[Int, String](new CacheBuilder(name = "myCache", maxEntriesInHeap = 1000))
    for (i <- 1 to 2000) {
      m(i) = "value-" + i
    }
    //not a real check since the cache can expand to beyond 1000 and stay that way for a while or
    // at least until it feels the memory pressure
    assert(m.size >= 1000 && m.size < 2000)
  }

  /** Requires Terracotta BigMemory license for offheap support
    * Add terracotta-license.key & add direct memory to the JVM to allocate appropriate offheap space for use
    */
  /*
  test("create a off-heap cache given constraints") {
    val m = new CacheAsMap[Int, String](new CacheBuilder(name = "myCache", maxEntriesInHeap = 1000, maxBytesLocalOffHeapInMB = 256))
    for (i <- 1 to 2000) {
      m(i) = "value-" + i
    }
    assert(m.size >= 1000)
  }
  */

}
