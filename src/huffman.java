import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;

class Node {
    Character ch;
    Integer freq;
    Node left = null;
    Node right = null;

    Node(Character ch, Integer freq) {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(Character ch, Integer freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}
public class huffman {

    public static void encode(Node root, String string, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }

        if (isLeaf(root)) {
            huffmanCode.put(root.ch, string.length() > 0 ? string : "1");
        }

        encode(root.left, string + '0', huffmanCode);
        encode(root.right, string + '1', huffmanCode);
    }

    public static int decode(Node root, int index, StringBuilder string) {
        if (root == null) {
            return index;
        }

        if (isLeaf(root)) {
            System.out.print(root.ch);
            return index;
        }

        index++;
        root = (string.charAt(index) == '0') ? root.left : root.right;
        index = decode(root, index, string);
        return index;
    }

    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    public static void createHuffmanTree(String string) {
        if (string == null || string.length() == 0) {
            return;
        }

        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: string.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0)+1);
        }

        System.out.println(frequency);

        PriorityQueue<Node> priQ;
        priQ = new PriorityQueue<>(Comparator.comparingInt(value -> value.freq));

        for (var v: frequency.entrySet()){
            priQ.add(new Node(v.getKey(), v.getValue()));
        }

        System.out.println(priQ);

        while (priQ.size() != 1) {
            Node left = priQ.poll();
            Node right = priQ.poll();

            int total = left.freq + right.freq;
            priQ.add(new Node(null, total, left, right));
        }

        Node root = priQ.peek();

        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        System.out.println("Huffman Codes: " + huffmanCode);
        System.out.println("Original String: " + string);

        StringBuilder encodedString = new StringBuilder();
        for (char c: string.toCharArray()) {
            encodedString.append(huffmanCode.get(c));
        }
        System.out.println("Encoded String: " + encodedString);
        System.out.print("Decoded String: ");

        if (isLeaf((root))) {
            while (root.freq > 0) {
                System.out.print(root.ch);
                root.freq = root.freq - 1;
            }
        }
        else {
            int index = -1;
            while (index < encodedString.length() - 1) {
                index = decode(root, index, encodedString);
            }
        }

    }

    public static void main(String[] args) {
        String t = "Huffman Code Test Works!";
        createHuffmanTree(t);
    }

}
