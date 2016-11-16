import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * HaffmanEncoder
 * 
 * @author jaeminbaek
 * 
 */
public class HuffmanEncoder {
	/**
	 * instance which holds frequencyTable for the convenience
	 */
	private Map<Character, Integer> frequencyTable;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            the text
	 */
	public HuffmanEncoder(String text) {
		this.frequencyTable = makeFrequencyTable(text);
	}

	/**
	 * Encoding
	 * 
	 * @param text
	 *            the input
	 * @return the output
	 */
	public String encode(String text) {
		// make encoding table.
		Map<Character, String> encodingTable = makeEncoder(frequencyTable);
		if (encodingTable == null)
			return text;
		String encoded = new String();
		for (char key : text.toCharArray()) {
			String huffCode = encodingTable.get(key);
			if (huffCode != null)
				encoded = encoded + huffCode;

		}
		return encoded;
	}

	/**
	 * Encoding Adaptively
	 * 
	 * @param filename
	 *            the filename
	 * @return output
	 */
	@SuppressWarnings("resource")
	public String encodeAdaptive(String filename) {
		String output = "";
		File file = new File(filename);
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			output = output + line;
		}
		return encode(output);
	}

	/**
	 * Decoding
	 * 
	 * @param encodedText
	 *            the encoded text
	 * @param decodetable
	 *            the root of the tree structure
	 * @return
	 */
	public String decode(String encodedText, HuffmanNode decodetable) {
		HuffmanNode huff = makeHuffmanTree();
		String decoded = new String();
		HuffmanNode cur = huff;
		if (cur == null) {
			return decoded;
		}
		if (cur.leftNode == null && cur.rightNode == null)
			return decoded + cur.character;
		char[] huffChar = encodedText.toCharArray();
		int i = 0;
		while (i < huffChar.length) {
			char cd = huffChar[i];
			if (cd == '0')
				cur = cur.leftNode;
			else
				cur = cur.rightNode;
			if (cur.leftNode == null && cur.rightNode == null) {
				decoded = decoded + cur.character;
				cur = huff;
			}
			i++;
		}
		return decoded;
	}

	/**
	 * Make the frequency table
	 * 
	 * @param str
	 *            the input
	 * @return the frequency table
	 */
	public Map<Character, Integer> makeFrequencyTable(String str) {
		HashMap<Character, Integer> table = new HashMap<>();
		for (char key : str.toCharArray()) {
			Integer prev = table.remove(key);
			Integer next;
			if (prev != null)
				next = prev + 1;
			else
				next = 1;
			table.put(key, next);
		}
		return table;
	}

	/**
	 * Make the encoder table
	 * 
	 * @param frequencyTable
	 *            the frequency table
	 * @return the encoder table
	 */
	public Map<Character, String> makeEncoder(
			Map<Character, Integer> frequencyTable) {
		if (frequencyTable.size() == 0)
			return new HashMap<>();
		HuffmanNode tree = makeHuffmanTree();
		if (tree.leftNode == null && tree.rightNode == null)
			return null;
		HashMap<Character, String> output = new HashMap<>();
		makeEncoding(tree, new String(), output);
		return output;
	}

	/**
	 * Recursive call
	 * 
	 * @param tree
	 *            the tree
	 * @param preOutput
	 *            the pre-output we will add to.
	 * @param map
	 *            the map.
	 */
	private void makeEncoding(HuffmanNode tree, String preOutput,
			HashMap<Character, String> map) {
		if (tree.leftNode == null && tree.rightNode == null)
			map.put(tree.character, preOutput);
		if (tree.leftNode != null) {
			String nextEnc = new String();
			makeEncoding(tree.leftNode, nextEnc.concat(preOutput + "0"), map);
		}
		if (tree.rightNode != null) {
			String nextEnc = new String();
			makeEncoding(tree.rightNode, nextEnc.concat(preOutput + "1"), map);
		}
	}

	/**
	 * Make huffman tree
	 * 
	 * @return the root node.
	 */
	public HuffmanNode makeHuffmanTree() {
		PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
		for (Map.Entry<Character, Integer> pair : this.frequencyTable
				.entrySet()) {
			if (this.frequencyTable.size() == 1)
				for (int i = 0; i < pair.getValue(); i++)
					pq.add(new HuffmanNode(pair.getKey(), pair.getValue()));
			pq.add(new HuffmanNode(pair.getKey(), pair.getValue()));
		}
		while (pq.size() > 1) {
			HuffmanNode a = pq.poll();
			HuffmanNode b = pq.poll();
			pq.offer(new HuffmanNode(a, b));
		}
		return pq.poll();
	}

	/**
	 * My data structure.
	 * 
	 * @author jaeminbaek
	 * 
	 */
	private class HuffmanNode implements Comparable<HuffmanNode> {
		private int counter = 0;
		public final int frequency;
		public final char character;
		public final int count;
		public final HuffmanNode leftNode;
		public final HuffmanNode rightNode;

		/**
		 * Constructor
		 * 
		 * @param character
		 *            the character
		 * @param frequency
		 *            the frequency
		 */
		public HuffmanNode(char character, int frequency) {
			this.count = counter++;
			this.character = character;
			this.frequency = frequency;
			this.leftNode = null;
			this.rightNode = null;
		}

		/**
		 * Constuctor
		 * 
		 * @param less
		 *            the less node
		 * @param more
		 *            the more node
		 */
		public HuffmanNode(HuffmanNode less, HuffmanNode more) {
			this.count = counter++;
			this.character = 0;
			this.frequency = less.frequency + more.frequency;
			this.leftNode = less;
			this.rightNode = more;
		}

		/**
		 * Overriding
		 */
		@Override
		public int compareTo(HuffmanNode that) {
			if (this.frequency == that.frequency) {
				if (this.character == that.character)
					return this.count - that.count;
				else
					return this.character - that.character;

			} else
				return this.frequency - that.frequency;

		}

		/**
		 * Overriding
		 */
		@Override
		public boolean equals(Object obj) {
			// compare object
			if (obj instanceof HuffmanNode)
				return ((HuffmanNode) obj).count == count;
			return false;
		}

		/**
		 * Overriding
		 */
		@Override
		public int hashCode() {
			return count;
		}
	}
}
