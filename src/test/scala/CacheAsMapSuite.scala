import org.scalatest.{BeforeAndAfter, FunSuite}


/**
 * Created by vinay on 10/21/13.
 */
class CacheAsMapSuite extends FunSuite {

  test("filter") {
    val m = new CacheAsMap[String, String]()
    m("apple") = "fruit"
    m("potato") = "vegetable"
    m("grapes") = "fruit"
    assert(m.filter(kv => "fruit".equals(kv._2)).size == 2)
  }

  test("add, remove and get"){
    val m= new CacheAsMap[Int,String]
    m(1)="a"
    m+=(2->"b")
    assert(2==m.size)
    m-=2
    assert(1==m.size)
    assert("a"==m(1))
  }

}
