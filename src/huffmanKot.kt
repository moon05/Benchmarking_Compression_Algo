import java.util.*
import kotlin.collections.HashMap

class NodeKot {
    var ch: Char = '\u0000'
    var freq: Int
    var left: NodeKot? = null
    var right: NodeKot? = null

    constructor(ch: Char, freq: Int) {
        this.ch = ch
        this.freq = freq
    }

    constructor(ch: Char?, freq: Int, left: NodeKot?, right: NodeKot?) {
        if (ch != null) {
            this.ch = ch
        }
        this.freq = freq
        this.left = left
        this.right = right
    }
}

fun isLeaf(root: NodeKot): Boolean {
    return root.left == null && root.right == null
}

fun encode(root: NodeKot?, string: String, huffmanCode: HashMap<Char, String>) {
    if (root == null) {
        return
    }
    if (isLeaf(root)) {
        huffmanCode[root.ch] = if (string.isNotEmpty()) string else "1"
    }
    encode(root.left, string + '0', huffmanCode)
    encode(root.right, string + '1', huffmanCode)
}

fun decode(root: NodeKot?, index: Int, string: StringBuilder): Int {
    var root = root
    var index = index
    if (root == null) {
        return index
    }
    if (isLeaf(root)) {
        print(root.ch)
        return index
    }
    index++
    root = if (string[index] == '0') root.left else root.right
    index = decode(root, index, string)
    return index
}

fun createHuffmanTree(string: String?) {
    if (string == null || string.isEmpty()) {
        return
    }
    val frequency: MutableMap<Char, Int> = HashMap()
    for (c in string.toCharArray()) {
        frequency[c] = frequency.getOrDefault(c, 0) + 1
    }
    println(frequency)
    val priQ: PriorityQueue<NodeKot> = PriorityQueue(Comparator.comparingInt { value: NodeKot -> value.freq })
    for ((key, value) in frequency) {
        priQ.add(NodeKot(key, value))
    }
    println(priQ)
    while (priQ.size != 1) {
        val left = priQ.poll()
        val right = priQ.poll()
        val total = left.freq + right.freq
        priQ.add(NodeKot(null, total, left, right))
    }
    val root = priQ.peek()
    val huffmanCode: HashMap<Char, String> = HashMap()
    encode(root, "", huffmanCode)
    println("Huffman Codes: $huffmanCode")
    println("Original String: $string")
    val encodedString = StringBuilder()
    for (c in string.toCharArray()) {
        encodedString.append(huffmanCode[c])
    }
    println("Encoded String: $encodedString")
    print("Decoded String: ")
    if (isLeaf(root)) {
        while (root.freq > 0) {
            print(root.ch)
            root.freq = root.freq - 1
        }
    } else {
        var index = -1
        while (index < encodedString.length - 1) {
            index = decode(root, index, encodedString)
        }
    }
}

fun main(args : Array<String>) {
    val t = "Huffman Code Test Works in Kotlin!"
    huffman.createHuffmanTree(t)
}

