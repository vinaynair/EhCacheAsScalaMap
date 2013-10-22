/**
 * Created by vinay on 10/21/13.
 */

import net.sf.ehcache.config.{Configuration, CacheConfiguration, MemoryUnit, TerracottaConfiguration, TerracottaClientConfiguration}
import net.sf.ehcache.{Cache, CacheManager}

/**
 * Build the Cache up using the provided cache definition
 */
class CacheBuilder(val managerName: String = "cacheManager",
                   val name: String = "cache",
                   val maxEntriesInHeap: Int = 1000,
                   val maxBytesLocalOffHeapInMB: Long = 0,
                   val eternal: Boolean = false,
                   val timeToIdle: Int = 0,
                   val timeToLive: Int = 0,
                   val terracottaServerUrl: String = null) {
  /**
   * create the cache given the definition. if the cache already exists, suffix _
   * @return
   */
  def build = {
    //build the cacheManager
    val managerConfiguration = new Configuration().name(managerName)
    if (terracottaServerUrl != null) {
      managerConfiguration.terracotta(new TerracottaClientConfiguration().url(terracottaServerUrl))
    }
    val cacheManager = CacheManager.newInstance(managerConfiguration)

    //build the cache
    var cacheName = name
    while (cacheManager.cacheExists(cacheName)) {
      // cache already exists.
      cacheName += "_"
    }
    if (!name.equals(cacheName)) {
      println("WARNING: cache [" + name + "] already existed, therefore creating a cache with name[" + cacheName + "]")
    }
    val cacheConfiguration =
      new CacheConfiguration()
        .name(cacheName)
        .maxEntriesLocalHeap(maxEntriesInHeap)
        .eternal(eternal)
        .timeToIdleSeconds(timeToIdle)
        .timeToLiveSeconds(timeToLive)
    //add offheap, if set
    if (maxBytesLocalOffHeapInMB > 0) cacheConfiguration.maxBytesLocalOffHeap(maxBytesLocalOffHeapInMB, MemoryUnit.MEGABYTES)
    //add clustered, if set
    if (terracottaServerUrl != null) {
      cacheConfiguration.terracotta(new TerracottaConfiguration())
    }

    val cache = new Cache(cacheConfiguration)
    cacheManager.addCache(cache)
    //return the cache from the cacheManager
    cacheManager.getCache(cacheName)
  }

}
