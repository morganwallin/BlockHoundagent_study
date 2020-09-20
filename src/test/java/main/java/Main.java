package main.java;

import junit.framework.TestFailure;
import junit.framework.TestResult;
import org.junit.Before;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import reactor.blockhound.BlockHound;

import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;


public class Main {

    public static void main(String[] args) {
        BlockHound.install();

        if(args.length == 0) {
            System.err.println("No command line argument supplied.");
            return;
        }

        JUnitCore core = new JUnitCore();
        core.addListener(new ExecutionListener());
        Result testResult = core.run(MasterTester.suite(args[0]));

   
    }
}
