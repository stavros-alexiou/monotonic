package com.sa.monotonic.challege;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyChallenge {

    private static final AtomicInteger state = new AtomicInteger(0);
    private static volatile int check = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread worker1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                state.accumulateAndGet(2, (current, given) -> {
                    check++;
                    return current + given;
                });
            }
        });

        Thread worker2 = new Thread(() -> {
            while (state.get() < 6) {
                // Spinning active wait
            }
            check = 100;
        });

        worker2.start();
        worker1.start();

        worker1.join();
        worker2.join();

        System.out.println("State: " + state.get());
        System.out.println("Check: " + check);
    }
}

/*

* Your Test Questions
* To prove you can read and predict concurrent execution, answer these three questions:
*
* 1. The Lambda side effect: Is the check++ inside the accumulateAndGet lambda guaranteed to execute exactly 5 times? Why or why not?
* 2. The Spin Lock: What is the minimum value state must reach for worker2 to break out of its while loop?
* 3. The Trap: What is the catch with the final value of check printed to the console? Is it guaranteed to be 100, 105, or is it unpredictable (a race condition)?

*/

/*
*
* The correct answer is:
* The final value of check is a race condition (unpredictable), and it can even be greater than 102.
*
* Here is why:
*
* 1.
* The accumulateAndGet Retry TrapAtomicInteger.accumulateAndGet uses a Compare-And-Swap (CAS) loop under the hood.
* If two threads try to update the atomic variable at the same time, or if the update fails,
* the accumulateAndGet method silently retries the lambda expression.
* Because the lambda has a side effect (check++), if a retry happens, check++ will execute more than 5 times total,
* even though the for loop only runs 5 times!
*
* 2.
* The Thread Scheduling Race
* You rightly noticed that worker2 starts first, but thread scheduling is entirely up to the Operating System.
* Scenario A --> worker1 could run so fast that it finishes all 5 loops (state = 10, check = 5) before worker2
* even wakes up to check the condition. Then worker2 breaks the loop and sets check = 100. Final check = 100.
* Scenario B --> worker2 catches it at state = 6, sets check = 100, and then worker1 does two more loops. F
* inal check = 102.
* Scenario C --> worker2 sets check = 100 right between the moment worker1 increments check and the moment it finishes the atomic update.
*
* Summary of Final Answers
* The Lambda side-effect:
* 1. No, check++ is not guaranteed to run 5 times because CAS retries can execute the lambda multiple times.
* 2. The Spin Lock: state must be >= 6 (which happens at 6).
* 3. The Trap: The final value of check is unpredictable. It will usually be 100, 101, or 102, depending e
*    ntirely on OS thread timing! (state will always be 10, though).
*/