package dev.aguacate.labs.fibers.demo;

import static java.lang.System.out;

class ContinuationsDemo {

  public static void main(String[] args) {

    final var scope = new ContinuationScope("demo-continuations");
    final var continuation = new Continuation(scope, () -> {
      out.println("Hello from a Continuation!");
      Continuation.yield(scope);
      out.println("After first yield!");
      Continuation.yield(scope);
      out.println("After second yield!");
      Continuation.yield(scope);
      out.println("After third yield!");
    });

    while (!continuation.isDone()) {
      out.println("Run...");
      continuation.run();
    }
  }
}