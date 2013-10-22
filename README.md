# EhCacheAsScalaMap
Expose select EhCache features as a [scala mutable Map](), providing interesting possibilities for integration.
Features include:-

## Add
```java
val m = new CacheAsMap[Int, String]
//add key+value pair using the following syntax
m(1) = "a"
//or
m += (2 -> "b")
```

## Remove
```java
m-=2
```

## Get
```java
println(m(1))
```





