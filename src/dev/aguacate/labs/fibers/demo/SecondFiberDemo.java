package dev.aguacate.labs.fibers.demo;

import java.time.Instant;
import java.util.concurrent.SynchronousQueue;

import static java.lang.String.format;
import static java.lang.System.out;

class SecondFiberDemo {

  public static void main(String[] args) throws Exception {
    var queue = new SynchronousQueue<Integer>(true);

    var deadline = Instant.now().plusSeconds(1);
    try (var fiberScope = FiberScope.open(deadline, FiberScope.Option.PROPAGATE_CANCEL)) {
      fiberScope.schedule(() -> {
        var value = 0;
        while (true) {
          queue.put(value++);
        }
      });

      try (var anotherScope = FiberScope.open()) {
        var nestedTotal = anotherScope.schedule(() -> sum(queue, 10)).join();
        out.println(format("Sum of the slice (nested): %d", nestedTotal));
      }

      var total = fiberScope.schedule(() -> sum(queue, 5));
      out.println(format("Sum of the slice: %d", total.join()));
    }

  }

  private static Integer sum(final SynchronousQueue<Integer> queue, final int sizeSlice) {
    try {
      Fiber.current()
          .ifPresent(fiber -> out.println(format("Fiber: %s", fiber.toString())));
      var total = 0;
      for (int i = 0; i < sizeSlice; i++) {
        var value = queue.take();
        out.println(value);
        total += value;
      }
      return total;
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }

  }
}
