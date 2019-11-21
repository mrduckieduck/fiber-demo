package dev.aguacate.labs.fibers.demo;

import java.util.stream.IntStream;
import java.lang.Continuation;
import java.lang.ContinuationScope;
import java.lang.Runnable;

class ContinuationsDemo {

  public static void main(String[] args) {

    final ContinuationScope continuationScope = new ContinuationScope("demo-continuation-scope");
    final Continuation continuation = new Continuation(continuationScope, 
      () -> {
        System.out.println("Hello from a Continuation!");
        Continuation.yield(continuationScope);
        dummyCall();
        System.out.println("After yield!");
      });
    
    continuation.run();
    
    System.out.println(String.format("Before continues! %s", continuation.isDone()));
    
    //continuation.run();

    System.out.println(String.format("After continues! %s", continuation.isDone()));
  }

  public static void dummyCall() {
    IntStream.range(1, 1000).forEach(System.out::print);
    System.out.println("");
  }

  //Result: 
  //Hello from a Continuation!
  //Before continues! false
  //After continues! false

}