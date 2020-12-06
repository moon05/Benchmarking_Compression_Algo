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



def encode(root, string, huffman_code):

	if root in None:
		return

	if isLeaf(root):
		huffman_code[root.char] = string if len(string) > 0 else "1"

	encode(root.left, string + "0", huffman_code)
	encode(root.right, string + "1", huffman_code)
