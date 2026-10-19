package com.sa.monotonic.challege;

public class GenericsChallenge {

    static class Node<T> {
        public T data;

        public Node(T data) {
            this.data = data;
        }

        public void setData(T data) {
            System.out.print("Node ");
            this.data = data;
        }
    }

    static class MyNode extends Node<Integer> {
        public MyNode(Integer data) {
            super(data);
        }

        @Override
        public void setData(Integer data) {
            System.out.print("MyNode ");
            super.setData(data);
        }
    }

    public static void main(String[] args) {
        MyNode mn = new MyNode(5);
        Node n = mn;            // Raw type assignment

        try {
            n.setData("Hello");
        } catch (ClassCastException e) {
            System.out.print("CCE ");
        }

        System.out.print("Data: " + mn.data);
    }
}

/*
*
* Your Test Questions:
*
* 1. The Runtime Type: Due to Type Erasure, what type does T data inside the compiled Node class actually become?
* 2. The Signature Trap: At the line n.setData("Hello");, n is a raw Node. Does this line successfully invoke a method,
*    or does it crash with a ClassCastException before entering any method?
* 3. The Final Output: What is the exact sequence printed to the console? (Does it print Node, MyNode, CCE, or something else?)
*
* */
