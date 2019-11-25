package dev.aguacate.labs.fibers.demo;

import java.util.stream.IntStream;

import static java.lang.String.*;
import static java.lang.System.out;

class UglyThreads {

  public static void main(String[] args) {
    IntStream
        .range(0, 1_000_000)
        .forEach(index -> {
          var thread = new Thread(() -> out.println(format("Thread[%d]: %s", index, Thread.currentThread().getName())));
          thread.start();
        });
  }

}
