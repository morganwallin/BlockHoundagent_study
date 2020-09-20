package main.java;


import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatchman;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;


@RunWith(Suite.class)
@Suite.SuiteClasses({})
public class MasterTester {
    public static TestSuite suite(String packageName) {
            //File jarPath = new File(pathName);
            List<Class<?>> classList = ClasspathInspector.getClassesInPackage(packageName);
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
            suite.setName(packageName);
            suite.addTest(new JUnit4TestAdapter(BlockhoundUnitTest.class));

            return suite;
    }
}
