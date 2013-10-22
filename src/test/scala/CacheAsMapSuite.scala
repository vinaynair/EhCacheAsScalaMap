import org.scalatest.{BeforeAndAfter, FunSuite}


/**
 * Created by vinay on 10/21/13.
 */
class CacheAsMapSuite extends FunSuite {

  test("add, remove and get on a cache") {
    val m = new CacheAsMap[Int, String]
    //add key+value pair using the following syntax
    m(1) = "a"
    //or
    m += (2 -> "b")
    //or, since its mutable
    m+(3->"c")
    assert(3 == m.size)
    //add to cache maps
    val n = new CacheAsMap[Int,String]()
    n(4)="d"
    //or, add the entire cache map over
    n++=m
    assert(4==n.size)
    assert(3==m.size)

    //remove
    m -= 2
    assert(2 == m.size)
    //or, remove more than one key at a time
    n -=(1,2)
    assert(2 == n.size)
    //get
    assert("a" == m(1))


  }

  test("filter on a cache") {
    val m = new CacheAsMap[String, String]()
    m("apple") = "fruit"
    m("potato") = "vegetable"
    m("grapes") = "fruit"
    assert(m.filter(kv => "fruit".equals(kv._2)).size == 2)
  }

  test("transform on a cache using a function"){
    val m = new CacheAsMap[Int,Int]()
    m(1)=1
    m(2)=2
    m(3)=3
    assert(1==m(1))
    m transform ((k,v)=>v+1)
    assert(2==m(1))

  }

  test("other common map ops on cache"){
    val m = new CacheAsMap[String,String]()
    //getOrElse
    val thingType=m getOrElse("mango","fruit")
    assert("fruit"==thingType)
    m("pineapple")="fruits"
    assert(m contains "pineapple")
    m+("orange"->"fruits")
    assert(2==m.size)
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
