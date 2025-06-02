package com.example.bank;

public class test {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        Thread t1 = new Thread(() -> a.methodA(b));
        Thread t2 = new Thread(() -> b.methodB(a));

        t1.start();
        t2.start();
    }


    static class A {
        synchronized void methodA(B b) {
            System.out.println("Thread 1: holding lock on A, waiting for B...");
            synchronized (b) {
                System.out.println("Thread 1: acquired lock on B");
            }
        }
    }

    static class B {
        synchronized void methodB(A a) {
            System.out.println("Thread 2: holding lock on B, waiting for A...");
            synchronized (a) {
                System.out.println("Thread 2: acquired lock on A");
            }
        }
    }

}
