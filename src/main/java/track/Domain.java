package track;

import java.util.Arrays;

/**
 * below implementation
 * f(0) = 1
 * f(2) = 2
 * other value of n :
 * f(n) = (
 * if n mod 2 = 0 then
 * f(n - 1) + f(n - 2) + f(n - 3) + f(n - 4)
 * else
 * askServer(n)
 * )
 * (askServer server returns the value of the recursive called  with a fixed seed value)
 */
public class Domain {

    String seed;
    Integer[] memo;

    public Domain(Integer n, String seed) {
        this.seed = seed;
        memo = new Integer[n + 1];
        Arrays.fill(memo, 0);
    }

    public Integer calculate(Integer val) {
        AskServerService askServerService = new AskServerService(seed);

        if (memo[val] != 0) return memo[val];

        switch (val) {
            case 0:
                return 1;
            case 2:
                return 2;
            default:
                if (val % 2 == 0) {
                    memo[val] = calculate(val - 1) + calculate(val - 2) + calculate(val - 3) + calculate(val - 4);
                } else {
                    memo[val] = askServerService.request(val);
                }
                return memo[val];
        }
    }
}
