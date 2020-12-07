import heapq
from heapq import heappop, heappush


def isLeaf(root):
	return root.left is None and root.right is None

class Node:
	def __init__(self, char, freq, left=None, right=None):
		self.char = char
		self.freq = freq
		self.left = left
		self.right = right

	#This overriding is later used by heapq for comparing Nodes
	#Without overriding you would get the following error:
	#TypeError: '<' not supported between instances of 'Node' and 'Node'
	def __lt__(self, other):
		return self.freq < other.freq



def encode(root, string, huffman_code):

	if root is None:
		return

	#this is for going bottom-up
	if isLeaf(root):
		huffman_code[root.char] = string if len(string) > 0 else "1"

	encode(root.left, string + "0", huffman_code)
	encode(root.right, string + "1", huffman_code)

def decode(root, index, string):
	if root is None:
		return index

	if isLeaf(root):
		print(root.char, end="")
		return index

	index = index + 1
	root = root.left if string[index] == "0" else root.right
	return decode(root, index, string)

def createHuffmanTree(string):

	if len(string) == 0:
		return

	#count frequency of characters
	frequency = {i: string.count(i) for i in set(string)}

	#priority queue with Nodes for character and frequency pairs
	priQ = [Node(k, v) for k, v in frequency.items()]
	heapq.heapify(priQ)

	while len(priQ) != 1:

		#Remove 2 nodes of lowest frequency
		left = heappop(priQ)
		right = heappop(priQ)

		#add the frequencies to create a new node
		total = left.freq + right.freq
		heappush(priQ, Node(None, total, left, right))

	root = priQ[0]
	huffmanCode = dict()

	encode(root, "", huffmanCode)

	print ("Huffman Codes: ", huffmanCode)
	print ("Original String: ", string)

	#this encodedString is to be given to decode!
	encodedString = ""
	for s in string:
		encodedString += huffmanCode.get(s)

	print ("Encoded String: ", encodedString)
	print ("Decoded String: ", end=" ")

	if isLeaf(root):
		#for repeat characters
		while root.freq > 0:
			print (root.char, end="")
			root.freq = root.freq - 1
	else:
		#since index gets incremented inside decode()
		index = -1
		while index < len(encodedString) - 1:
			index = decode(root, index, encodedString)
	print()

if __name__ == "__main__":
	string = "Huffman Code Test Works!"
	createHuffmanTree(string)


