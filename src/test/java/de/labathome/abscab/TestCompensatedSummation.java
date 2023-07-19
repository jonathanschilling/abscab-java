package de.labathome.abscab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestCompensatedSummation {

	@Test
	void testCompensatedSummation() {

		final double[] storage = new double[3];

		// first add 1.0
		CompensatedSummation.compensatedAdd(storage, 1.0);

		// then add 1e6 * 1e-20
		// -> should end up at 1.0 + 1.0e-14
		final int N = 1_000_000;
		final double contrib = 1.0e-20;
		double normalSum = 1.0;
		for (int i = 0; i < N; ++i) {
			CompensatedSummation.compensatedAdd(storage, contrib);
			normalSum += contrib;
		}
		final double actualSum = storage[0] + storage[1] + storage[2];

		// make sure straightforward summation actually fails to capture the many small contributions
		Assertions.assertEquals(1.0, normalSum);

		// now check that compensated summation saves the day
		final double expectedSum = 1.0 + N * contrib;
		Assertions.assertEquals(expectedSum, actualSum);
	}
}
