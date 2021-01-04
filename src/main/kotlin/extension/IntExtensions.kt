package extension

/**
 * Checks if [this] is in the range [begin,end].
 * Returns [this] if begin<=[this]<=end. otherwise throws an IllegalArgumentException.
 *
 * Example: 10.inRange(0,20) = 10
 */
fun Int.inRange(begin: Int, end: Int):Int{
    if (this in begin..end) {
        return this
    }
    throw IllegalArgumentException("Value [$this] is out of range [$begin,$end]")
}

/**
 * Return representation of [this] as an 32bit binary string.
 *
 * Example: 1.to32BinaryString() = "00000000000000000000000000000001"
 */
fun Int.to32BinaryString():String {
    var int = this
    val out = CharArray(32)
    for (i in 0 until 32){
        out[i] = if (int.and(-0x80000000) != 0) '1' else '0'
        int = int.shl(1)
    }
    return String(out)
}