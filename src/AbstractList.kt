interface AbstractList<T> {
    val size: Int

    fun add(value: T)

    fun add(value: T, index: Int)

    operator fun get(index: Int): T

    operator fun set(index: Int, value: T)

    fun delete(index: Int)

    fun first(): T

    fun last(): T

    fun sort()
}