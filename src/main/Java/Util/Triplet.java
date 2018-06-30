package main.Java.Util;

/**
 * Utility class for handling a set of three elements
 */
public class Triplet<L,M,R>
{
    /**
     * First element of the Triplet
     */
    private L element0;

    /**
     * Second element of the Triplet
     */
    private M element1;

    /**
     * Third element of the Triplet
     */
    private R element2;

    /**
     * Constructor for a Triplet
     * @param element0 First element of the Triplet
     * @param element1 Second elememt of the Triplet
     * @param element2 Third element of the Triplet
     */
    public Triplet(L element0, M element1, R element2)
    {
        this.element0 = element0;
        this.element1 = element1;
        this.element2 = element2;
    }

    /**
     * Returns the first element of the Triplet
     * @return First element of the Triplet
     */
    public L getLeft() {
        return element0;
    }

    /**
     * Returns the second element of the Triplet
     * @return Second element of the Triplet
     */
    public M getMiddle() {
        return element1;
    }

    /**
     * Returns the third element of the Triplet
     * @return Third element of the Triplet
     */
    public R getRight() {
        return element2;
    }
}
