# EhCacheAsScalaMap
Expose select EhCache features as a [scala mutable Map](http://docs.scala-lang.org/overviews/collections/maps.html), providing interesting possibilities for integration.
Apart from the obvious type-safety, other obvious map features include:-

### Add
```java
val m = new CacheAsMap[Int, String]
//add key+value pair using the following syntax
m(1) = "a"
//or
m += (2 -> "b")
//or since its mutable
m+(3->"c")
//or add to cache maps
val n = new CacheAsMap[Int,String]()
n(4)="d"
n++=m
```

### Remove
```java
m-=2
//or, remove more than one key at a time
n -=(1,2)
```

### Get
```java
println(m(1))
```

### Filter
```java
val m = new CacheAsMap[String, String]()
//add elements
m("apple") = "fruit"
m("potato") = "vegetable"
m("grapes") = "fruit"
//now filter
//For a key value pair, kv, kv._1 is the key and kv._2 is the value

val filteredResults= m.filter( kv => "fruit".equals(kv._2) )
```


### Transform the values using a closure
```java
 val m = new CacheAsMap[Int,Int]()
    m(1)=1
    m(2)=2
    m(3)=3
    // increment all the values by 1
    m transform ( (k,v) => v+1 )
```


