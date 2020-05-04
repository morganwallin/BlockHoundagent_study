package main.java;

import junit.framework.TestFailure;
import junit.framework.TestResult;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.File;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;


public class Main {

    public static void main(String[] args) {
        BlockHound.install();
        TestResult testResult = new TestResult();
        Objects.requireNonNull(MasterTester.suite()).run(testResult);
        System.out.println("Number of tests ran: " + testResult.runCount());
        System.out.println("Number of tests errors: " + testResult.errorCount());
        System.out.println("Number of tests failures: " + testResult.failureCount());
        System.out.println("Test successful: " + testResult.wasSuccessful());

        Enumeration<TestFailure> errors = testResult.errors();
        while(errors.hasMoreElements()) {
            System.out.println(errors.nextElement());
        }

    }
}
