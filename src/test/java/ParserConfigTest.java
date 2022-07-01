
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

/*
 RIFERIMENTO: https://github.com/alibaba/fastjson/blob/1.2.79/src/test/java/com/alibaba/json/bvt/serializer/ParserConfigTest.java
 JACOCO REPORT: goto com.alibaba.fastjson/JSON/ parseObject(String, Type, ParserConfig, ParseProcess, int, Feature[])
 */



@RunWith(Parameterized.class)
public class ParserConfigTest {

    private String input;
    private Type classType;
    private ParserConfig conf;
    private int expected; //output
    public static final int error = -1; //usato come return per eventuali errori.


    //costruttore
    public ParserConfigTest(String input, Type classType, ClassLoader ContextClassLoader, int expected) {
        configure(input, classType,ContextClassLoader, expected); //nel costruttore uso configure.
    }


    //configure
    private void configure(String input, Type classType,ClassLoader ContextClassLoader, int expected)
    {
      this.input = input;
      this.classType = classType;
      this.conf = new ParserConfig(ContextClassLoader);
      this.expected = expected;
    }



    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{
                //input                 //ClassType          //ContextClassLoader                            //expected
                {"{\"value\":123}",     Model.class,     Thread.currentThread().getContextClassLoader(),        123},
                {null,     null,     Thread.currentThread().getContextClassLoader(),        error} //aumenta branch coverage (from 31% -> 37%)

        });
    }


    @Test
    public void test_1() {

        switch (expected)
        {

            case error:
                try{
                        Model model = JSON.parseObject(input, classType, conf);
                        Assert.assertEquals(expected, model.value);
                   }
                  catch(Exception e) {
                      Assert.assertTrue("Exception correctly captured", true);
                  }
                break;

            default:
                try{
                    Model model = JSON.parseObject(input, classType, conf);
                    Assert.assertEquals(expected, model.value);
                }
                catch(Exception e) {

                    Assert.fail("Exception captured but not expected");
                }
                break;


        }
    }


    public static class Model {
        public int value;
    }


}