package advent_of_code.utils;

import java.util.Collection;

public class ModularInverse {

    /**
     * Calculates the modular inverse of m modulo n using the Extended Euclidean Algorithm.
     *
     * The modular inverse of m modulo n is a number x such that (m * x) ≡ 1 (mod n).
     * This inverse exists if and only if gcd(m, n) = 1 (m and n are coprime).
     *
     * @param m the number for which to find the modular inverse
     * @param n the modulus
     * @return the modular inverse of m modulo n
     * @throws IllegalArgumentException if the modular inverse doesn't exist (gcd(m, n) != 1)
     * @throws IllegalArgumentException if n <= 0
     *
     * Time Complexity: O(log(min(m, n)))
     * Space Complexity: O(1)
     *
     * Example:
     * - modularInverse(3, 11) returns 4 because (3 * 4) % 11 = 1
     * - modularInverse(7, 13) returns 2 because (7 * 2) % 13 = 1
     */
    public static long modularInverse(long m, long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Modulus must be positive");
        }

        // Normalize m to be positive
        m = ((m % n) + n) % n;

        // Apply Extended Euclidean Algorithm
        long[] result = extendedGCD(m, n);
        long gcd = result[0];
        long x = result[1];

        // Check if inverse exists
        if (gcd != 1) {
            throw new IllegalArgumentException(
                "Modular inverse doesn't exist. gcd(" + m + ", " + n + ") = " + gcd + " != 1"
            );
        }

        // Make sure the result is positive
        return ((x % n) + n) % n;
    }

    /**
     * Extended Euclidean Algorithm implementation.
     *
     * Finds longegers x and y such that ax + by = gcd(a, b).
     *
     * @param a first longeger
     * @param b second longeger
     * @return array containing [gcd(a, b), x, y] where ax + by = gcd(a, b)
     */
    private static long[] extendedGCD(long a, long b) {
        if (b == 0) {
            return new long[] { a, 1, 0 };
        }

        long[] result = extendedGCD(b, a % b);
        long gcd = result[0];
        long x1 = result[1];
        long y1 = result[2];

        long x = y1;
        long y = x1 - (a / b) * y1;

        return new long[] { gcd, x, y };
    }

    public static Long solve(Collection<Pair<Long, Long>> input) {
        var totalModulus = input.stream().mapToLong(Pair::second).reduce(1, (a, b) -> a * b);
        var result = 0L;
        for (var pair : input) {
            var remainder = pair.first();
            var modulus = pair.second();
            var mi = totalModulus / modulus;
            var inverse = modularInverse(mi, modulus);
            result += inverse * mi * remainder;
        }

        return result % totalModulus;
    }
}
