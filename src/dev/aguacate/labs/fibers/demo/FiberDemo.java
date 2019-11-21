package dev.aguacate.labs.fibers.demo;

import java.time.Instant;
import java.util.stream.IntStream;

import static java.lang.String.format;

public class FiberDemo {

  public static void main(String[] args) {

    final Instant deadLine = Instant.now().plusSeconds(3);
    ContinuationScope scope = new ContinuationScope("");
    try (final FiberScope fiberScope = FiberScope.open(deadLine, FiberScope.Option.PROPAGATE_CANCEL)) {
      IntStream.range(0, 1_000_000)
          .forEach(index -> schedule(index, fiberScope));
    }

  }

  private static void schedule(final Integer index, final FiberScope fiberScope) {
    fiberScope.schedule(() ->
        Fiber.current()
            .ifPresent(current -> {
                System.out.println(format("Fiber[%d]: %s", index, current));

            })
    );
  }
  
}