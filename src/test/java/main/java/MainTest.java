package main.java;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import reactor.blockhound.BlockHound;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        FirstUnitTest.class,
        SecondUnitTest.class
})
public class MainTest {
}