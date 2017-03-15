package jteissler.csci1302.simplesql;

/**
 * A class which wraps two objects, allowing them to be grouped together.
 *
 * @author J Teissler
 * @date 2/8/17
 */
public class Pair<L, R>
{
	/** The left value to store */
	private final L l;
	/** The right value to store */
	private final R r;

	/**
	 * Private constructor of the pair.
	 *
	 * @param l The left value.
	 * @param r The right value.
	 */
	private Pair(L l, R r)
	{
		this.l = l;
		this.r = r;
	}

	/**
	 * @return The left value of the pair.
	 */
	public L getLeft()
	{
		return l;
	}

	/**
	 * @return The right value of the pair.
	 */
	public R getRight()
	{
		return r;
	}

	/**
	 * Create a pair of two objects.
	 *
	 * @param l The left object.
	 * @param r The right object.
	 *
	 * @param <L> The class of the left object.
	 * @param <R> The class of the right object.
	 *
	 * @return A pair of the two provided objects.
	 */
	public static <L, R> Pair<L, R> of(L l, R r)
	{
		return new Pair<>(l, r);
	}

}
