package com.sa.monotonic.challege;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComplexChallenge {

    interface transformation<T, R> extends Function<T, R> {
        default <V> transformation<T, V> combine(transformation<R, V> after) {
            return (T t) -> after.apply(this.apply(t));
        }
    }

    public static void main(String[] args) {
        List<Integer> inputs = Arrays.asList(0b101, 0x0A, 3, 012);

        BiFunction<Integer, Integer, Integer> bitmask = (a, b) -> (a ^ b) << 1;

        transformation<Integer, String> step1 = x -> String.valueOf(bitmask.apply(x, 4));
        transformation<String, Integer> step2 = s -> s.chars().map(c -> c - '0').sum();
        transformation<Integer, String> pipeline = (step1.combine(step2)).combine(String::valueOf);

        String result = inputs.stream()
                .filter(n -> (n & 1) != 0) // <-- 3, 5 (decimal)
                .map(pipeline)
                .collect(Collectors.joining(","));

        System.out.println(result); // <-- 2,5
    }
}

/*

* Your Test Questions
* To prove you understand the code, reply with the answers to these three questions:
*
* 1. The Inputs: What are the actual base-10 (decimal) integer values of the four numbers inside the inputs list?
* 2. The Filter: Which specific numbers from that list survive the .filter(n -> (n & 1) != 0) operation?
* 3. The Output: What is the exact final string printed to the console?

*/
