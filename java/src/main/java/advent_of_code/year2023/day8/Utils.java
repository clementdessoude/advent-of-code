package advent_of_code.year2023.day8;

public final class Utils {

    public static long lcm(Long[] elements) {
        long lcm = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < elements.length; i++) {
                // lcm (n1, n2, ... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm.

                if (elements[i] == 0) {
                    return 0;
                } else if (elements[i] < 0) {
                    elements[i] = elements[i] * (-1);
                }
                if (elements[i] == 1) {
                    counter++;
                }

                // Divide elements by devisor if complete
                // division i.e. without remainder then replace
                // number with quotient; used for find next factor
                if (elements[i] % divisor == 0) {
                    divisible = true;
                    elements[i] = elements[i] / divisor;
                }
            }

            // If divisor able to completely divide any number
            // from array multiply with lcm
            // and store into lcm and continue
            // to same divisor for next factor finding.
            // else increment divisor
            if (divisible) {
                lcm = lcm * divisor;
            } else {
                divisor++;
            }

            // Check if all elements is 1 indicate
            // we found all factors and terminate while loop.
            if (counter == elements.length) {
                return lcm;
            }
        }
    }
}
