import java.util.Random;

/**
 * Head and Tail generator
 * 
 * @author jaeminbaek
 * 
 */
public class CoinFlipper {
	private Random gen;

	/**
	 * Default Constructor
	 */
	public CoinFlipper() {
		this(new Random());
	}

	/**
	 * Constructor
	 * 
	 * @param gen
	 *            random
	 */
	public CoinFlipper(Random gen) {
		this.gen = gen;
	}

	/**
	 * Flip the Coin!
	 * 
	 * @return Head or Tail
	 */
	public Coin flipCoin() {
		if (gen.nextBoolean())
			return Coin.HEADS;
		else
			return Coin.TAILS;

	}

	/**
	 * Head and Tail sign
	 * @author jaeminbaek
	 *
	 */
	public static enum Coin {
		HEADS, TAILS
	}
}
