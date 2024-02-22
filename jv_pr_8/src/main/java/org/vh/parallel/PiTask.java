package org.vh.parallel;

import java.util.Random;
import java.util.concurrent.Callable;

public class PiTask implements Callable<Long> {
    private final int iterations;

    public PiTask(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public Long call() {
        long sum = 0;
        final Random random = new Random();

        for (int i = 0; i < iterations; i++) {
            final double x = random.nextDouble();
            final double y = random.nextDouble();
            if (x * x + y * y < 1) {
                sum++;
            }
        }

        return sum;
    }
}
