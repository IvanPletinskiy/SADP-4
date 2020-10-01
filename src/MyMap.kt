import java.lang.Math.abs

class MyMap<K, V> : Map<K, V> {
    val baskets = Array<MutableList<Entry<K, V>>>(16) { mutableListOf() }

    override val entries: Set<Entry<K, V>>
        get() {
            return baskets.flatMap { it }.toSet()
        }

    override val keys: Set<K>
        get() {
            return baskets.flatMap { it }.map { it.key }.toSet()
        }

    override val size: Int
        get() = baskets.sumBy { it.size }
    override val values: Collection<V>
        get() {
            return baskets.flatMap { it }.map { it.value }.toSet()
        }

    override fun containsKey(key: K): Boolean {
        return baskets[getBasketId(key)].any { it.key == key }
    }

    override fun containsValue(value: V): Boolean {
        return baskets.any { it.any { it.value == value } }
    }

    override fun get(key: K): V? {
        return baskets[getBasketId(key)].firstOrNull { it.key == key }?.value
    }

    override fun isEmpty(): Boolean {
        return baskets.sumBy { it.size } == 0
    }

    private fun getBasketId(key: K): Int {
        return abs(key.hashCode()) % baskets.size
    }

    fun put(key: K, value: V) {
        val basket = baskets[getBasketId(key)]
        val elementIndex = basket.indexOfFirst { it.value == value }
        if(elementIndex > -1) {
            basket[elementIndex].value = value
        } else {
            basket.add(Entry(key, value))
        }
    }

    data class Entry<K, V>(override val key: K, override var value: V) : Map.Entry<K, V>
}