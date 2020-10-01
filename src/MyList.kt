import java.util.*

class MyList<T> : AbstractList<T> {

    private var first: Node<T>? = null
    private var last: Node<T>? = null

    override var size: Int = 0
        private set

    override fun get(index: Int): T {
        if (index >= size) {
            throw IndexOutOfBoundsException()
        }
        return getNode(index)!!.value
    }

    override fun set(index: Int, value: T) {
        if (index >= size) {
            throw IndexOutOfBoundsException()
        }

        val node = getNode(index)
        node?.value = value
    }

    override fun add(value: T) = add(value, size)

    override fun add(value: T, index: Int) {
        if (size == 0) {
            first = Node(value)
            last = first
        } else {
            when (index) {
                size -> {
                    addLast(getNode(size - 1), Node(value))
                }
                in 0..size -> {
                    val previousNode = getNode(index)
                    val newNode = Node(value)
                    previousNode?.previous?.next = newNode
                    newNode.previous = previousNode?.previous
                    newNode.next = previousNode
                    previousNode?.previous = newNode
                }
                else -> {
                    throw IndexOutOfBoundsException()
                }
            }
        }
        size++
    }

    private fun addLast(previousNode: Node<T>?, newNode: Node<T>) {
        previousNode?.next = newNode
        newNode.previous = previousNode
        last = newNode
    }

    override fun delete(index: Int) {
        if (index >= size) {
            throw IndexOutOfBoundsException()
        }
        val node = getNode(index)
        node?.previous?.next = node?.next
        node?.next?.previous = node?.previous
        if (index == 0) {
            first = node?.next
        }
        if (index == size - 1) {
            last = node?.previous
        }
        size--
    }

    override fun first(): T {
        return if (first != null) {
            first!!.value
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    override fun last(): T {
        return if (last != null) {
            last!!.value
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    private fun getNode(index: Int): Node<T>? {
        if (index !in 0..size) {
            throw IndexOutOfBoundsException()
        }
        return if (index < size / 2) {
            var node: Node<T>? = first

            for (i in 0 until index) {
                node = node?.next
            }
            node
        } else {
            var node: Node<T>? = last
            for (i in (size - 1) downTo (index + 1)) {
                node = node?.previous
            }
            node
        }
    }

    override fun sort() {
        fun swap(node1: Node<T>, node2: Node<T>) {
            val temp = node1.value
            node1.value = node2.value
            node2.value = temp
        }

        var node1 = first
        var node2: Node<T>?
        while (node1 != null) {
            node2 = node1.next
            while (node2 != null) {
                if (comparator.compare(node1, node2) > 0) {
                    swap(node1, node2)
                }
                node2 = node2.next
            }
            node1 = node1.next
        }
    }

    private val comparator = object : Comparator<Node<T>> {
        override fun compare(o1: Node<T>?, o2: Node<T>?): Int {
            return when {
                o1?.value as Int > o2?.value as Int -> 1
                (o1.value as Int) < (o2.value as Int) -> -1
                else -> 0
            }
        }
    }

    companion object {
        fun <T> from(array: Array<T>): MyList<T> {
            val myList = MyList<T>()
            array.forEach {
                myList.add(it)
            }
            return myList
        }
    }

    class Node<T>(var value: T, var next: Node<T>? = null, var previous: Node<T>? = null)
}

fun <T> Array<T>.toMyList(): MyList<T> {
    val myList = MyList<T>()
    this.forEach {
        myList.add(it)
    }
    return myList
}