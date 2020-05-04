package main.java;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;

@RunWith(Suite.class)
@Suite.SuiteClasses(MainTest.class)
public class MasterTester {
    public static TestSuite suite() {
        try {

            File jarPath = new File(com.github.davidmoten.rx2.Flowables.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath());
            List<Class<?>> classList = ClasspathInspector.getClassesFromJarFile(jarPath);
            TestSuite suite = new TestSuite();
            for(Class<?> clazz : classList) {
                Method[] methods = clazz.getMethods();
                for(Method method : methods) {
                    if(method.isAnnotationPresent(Test.class)) {
                        suite.addTest(new JUnit4TestAdapter(clazz));
                        break;
                    }
                }
            }
            suite.addTest(new JUnit4TestAdapter(com.github.davidmoten.rx2.ActionsTest.class));

            return suite;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    return null;
    }
}
