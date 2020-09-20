package main.java;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.blockhound.integration.BlockHoundIntegration;
import reactor.core.scheduler.ReactorBlockHoundIntegration;
import reactor.core.scheduler.Schedulers;

import java.util.ServiceLoader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertThat;

public class BlockhoundUnitTest {

    @Test
    public void blockHoundTest() {

        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });
            Schedulers.parallel().schedule(task);

            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (ExecutionException e) {
            System.out.println("Successfully integrated blockhound.");
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError, "detected");
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}