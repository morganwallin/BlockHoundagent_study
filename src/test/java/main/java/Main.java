package main.java;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.time.Duration;


public class Main {

    public static void main(String[] args) {
        BlockHound.install();
        Result result = JUnitCore.runClasses(MainTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
