package de.labathome.abscab;

/**
 * 2nd-order Kahan-Babuska summation algorithm as listed in A. Klein, "A
 * Generalized Kahan-Babuska-Summation-Algorithm", Computing 76, 279-293 (2006)
 * doi: 10.1007/s00607-005-0139-x
 */
public class CompensatedSummation {

	/** Use the static method {@link #compensatedAdd(double[], double)} instead of instiating this class. */
	private CompensatedSummation() { }

	/**
	 * Add a single contribution to the sum.
	 *
	 * @param storage [3: s, cs, ccs] summation variables; sum those 3 entries to get the total sum
	 * @param contribution contribution to add to the sum
	 */
	public static void compensatedAdd(final double[] storage, final double contribution) {

		/** summation variable */
		double s = storage[0];

		/** first-order correction */
		double cs = storage[1];

		/** second-order correction */
		double ccs = storage[2];

		final double t = s + contribution;
		final double c;
		if (Math.abs(s) >= Math.abs(contribution)) {
			c = (s - t) + contribution;
		} else {
			c = (contribution - t) + s;
		}
		s = t;

		final double t2 = cs + c;
		final double cc;
		if (Math.abs(cs) >= Math.abs(c)) {
			cc = (cs - t2) + c;
		} else {
			cc = (c - t2) + cs;
		}
		cs = t2;
		ccs += cc;

		storage[0] = s;
		storage[1] = cs;
		storage[2] = ccs;
	}
}
