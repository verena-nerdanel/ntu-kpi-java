package org.vh.parallel;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Варіант 1. Обчислення наближеного значення числа Пі методом Монте-Карло
 * У цьому завданні вам слід написати паралельну програму, яка обчислює значення
 * числа Пі. Метод обчислення дуже простий:
 *  Площа квадрата одиничної довжини дорівнює 1
 *  Площа сектора 90 ° для одиничного кола: π/4
 *  «Кидаємо» величезну кількість випадкових точок в
 * одиничний квадрат
 *  Рахуємо кількість точок, що потрапили в межі кола,
 * тобто відстань від яких до (0,0) менше або дорівнює 1
 *  Частка точок, які потрапили в коло дорівнює
 * наближеному значенню π/4
 * Деталі реалізації
 * Ваше завдання написати паралельну реалізацію (ParallelMonteCarloPi.java). При
 * написанні програми дотримуйтесь інструкцій:
 * • Першим і єдиним вхідним аргументом програми є кількість потоків
 * • В результаті програма виводить наступні дані:
 * PI is 3.14221
 * THREADS 8
 * ITERATIONS 1,000,000,000
 * TIME 12.83ms
 * Крім написання програми і виведення результату подумайте над наступними
 * питаннями:
 * • Як впливає кількість ітерацій (кинутих точок) на кінцевий результат?
 * • При однаковій кількості точок, як впливає на результат різна кількість потоків?
 * • Як кількість потоків впливає на продуктивність вашої програми? (Для явних
 * результатів вам ймовірно знадобиться набагато більша кількість семплів
 * (ітерацій).
 */
public class ParallelMonteCarloPi {

    public static void main(String[] args) throws Exception {
//        int n = 10_000_000;
//        PiTask piTask = new PiTask(n);
//        long result = piTask.call();
//        double pi = result * 4.0 / n;
//        System.out.println(pi);

        final int threadsCount = 8;
//        final int iterationsCount = 1_000_000_000;

//        for (int threadsCount = 3; threadsCount <= 8; threadsCount++) {
//            run(threadsCount, iterationsCount);
//        }

        for (int iterationsCount = 10; iterationsCount <= 1_000_000_000; iterationsCount *= 10) {
            run(threadsCount, iterationsCount);
        }
    }

    private static void run(int threadsCount, int iterationsCount) throws InterruptedException, ExecutionException {
        // prepare
        final long start = System.nanoTime();
        final int iterationsPerThread = iterationsCount / threadsCount;

        try (ExecutorService pool = Executors.newFixedThreadPool(threadsCount)) {
            final List<? extends Callable<Long>> callables = IntStream.range(0, threadsCount)
                    .mapToObj(i -> new PiTask(iterationsPerThread))
                    .collect(toList());

            // compute
            long sum = 0L;
            for (Future<Long> future : pool.invokeAll(callables)) {
                sum += future.get();
            }

            // output
            final double result = 4 * sum / (double) (threadsCount * iterationsPerThread);
            System.out.println("PI is " + result);
//            System.out.println("Threads: " + threadsCount);
            System.out.printf("Iterations: %,d%n", threadsCount * iterationsPerThread);
//            System.out.printf("Time: %.2f ms%n", (System.nanoTime() - start) / 1_000_000.0);
            System.out.printf("Accuracy: ± %.6f %%%n", Math.abs(result - Math.PI) / Math.PI * 100);
            System.out.println("===================================================");
            pool.shutdown();
        }
    }
}
