fun main() {
    val map = MyMap<String, Int>()
    val isEmpty1 = map.isEmpty() // true
    val result1 = map.containsKey("123") //false
    val result2 = map.containsValue(123) //false
    map.put("first", 1)
    map.put("second", 2)
    map.put("third", 3)
    val keys = map.keys //[first, second, third[
    val values = map.values //[1,2,3]
    val result4 = map.containsKey("first")//true
    val result5 = map.containsValue(3)//true
    val size = map.size //3
    val isEmpty2 = map.isEmpty() // false
}