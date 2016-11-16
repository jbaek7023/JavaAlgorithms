/**
 * Running Huffman. Part 2 works. Extra Credit 1 doesn't work. Extra Credit 2
 * works.
 * 
 * @author jaeminbaek
 * 
 */
public class runningHuffman {

	public static void main(String[] args) {
		String str = "a man a plan panama";
		System.out.println("Input          : " + str);
		HuffmanEncoder he = new HuffmanEncoder(str);
		System.out.println("Frequency Table: " + he.makeFrequencyTable(str));
		System.out.println("Characters Bits: "
				+ he.makeEncoder(he.makeFrequencyTable(str)));

		String encodedText = he.encode(str);
		System.out.println("Encoded String : " + encodedText);
		System.out.println("-------------------------------------------");
		System.out.println("AdaptiveFile Encoding from selected file :");
		// I couldn't do this problem. :(
		System.out.println(he.encodeAdaptive("aMan.txt"));
		System.out.println("-------------------------------------------");
		// create data structure
		// we need to put encoded text and the tree itself
		// because the direction which overgrow from root can vary.
		System.out.println("Decoded String : "
				+ he.decode(encodedText, he.makeHuffmanTree()));
	}

}
