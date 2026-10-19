package com.sa.monotonic.challege;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MemoryChallenge {

    static class DataPayload {
        int id;

        DataPayload(int id) {
            this.id = id;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.print("F" + id + " ");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<DataPayload> activeList = new ArrayList<>();

        DataPayload obj1 = new DataPayload(1);
        DataPayload obj2 = new DataPayload(2);
        DataPayload obj3 = new DataPayload(3);

        activeList.add(obj1);
        WeakReference<DataPayload> weakRef = new WeakReference<>(obj2);

        obj1 = null;
        obj2 = null;

        System.gc();
        Thread.sleep(200);

        activeList.clear();
        obj3 = null;

        System.gc();
        Thread.sleep(200);

        System.out.print("Done");
    }
}

/*
*
* Your Test Questions:
* To prove you understand JVM garbage collection traps, answer these three questions:
*
* 1. The First GC: Right after the first System.gc(), which objects (1, 2, or 3) are actually eligible for collection,
*    and what will be printed by the finalize() method?
* 2. The List Trap: Why does clearing the list (activeList.clear()) alter the fate of obj1 later in the code?
* 3. The Output: Assuming the JVM respects both System.gc() requests immediately (for the sake of this puzzle),
*    what is the exact sequence of the print statements leading up to "Done"?
*
*/

/*
*
* Answer:
*
* First GC (F2 ): Object 2 is only held by a WeakReference. It gets collected immediately and prints F2 .
* Second GC (F1 F3  or F3 F1 ): After activeList.clear() and obj3 = null, both object 1 and object 3 become eligible for collection at the exact same time.
* Because they are collected in the same GC cycle, the JVM does not guarantee which one gets finalized first.
* It could be F1 F3  or F3 F1 .
* The "Done" Trap: The string "Done" is printed at the very end of the main method, after the second Thread.sleep(200).
* Because the main thread waits for the GC threads to finish processing the finalizations, "Done" will always print last.
*
* The Exact Output:
* The console will print either: { F2 F1 F3 Done } or { F2 F3 F1 Done }
*
*/
