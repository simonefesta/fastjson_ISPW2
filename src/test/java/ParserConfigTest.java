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

@RunWith(Parameterized.class) //la classe di test definisce il runner JUnit.
public class ParserConfigTest extends TestCase {

    //tali parametri sono presi vedendo cosa richiede parseObject. I parametri sono attributi privati delle classi di test.
    private String input;
    private Type classType;
    private ParserConfig conf;
    private int expected; //output

    //costruttore
    public ParserConfigTest(String input, Type classType, ParserConfig conf, int expected) {
        configure(input, classType, conf, expected); //nel costruttore uso configure.
    }


    //configure
    private void configure(String input, Type classType, ParserConfig conf, int expected)
    {
      this.input = input;
      this.classType = classType;
      this.conf = conf;
      this.expected = expected;
    }


    /*normalmente sarebbe stato @Parameters con i valori stabiliti dal test.
    vedere slide 12 (9 se conto il pie di pagina del prof), li usa parameters normale e da subito i valori.*/
    @Parameterized.Parameters //il metodo di configurazione deve tornare una java.util.collection
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] { //poichè tipi diversi uso Object.Fossero stati tutti int, avrei usato new Integer.
                {"{\"value\":123}", Model.class, new ParserConfig(Thread.currentThread().getContextClassLoader()), 123}
        });
      /*        string input, classType e conf, expected (è 123 perchè il test originale usava 123).
      questi valori sono presi dal test originale, non li ho inventati io. /*
      Se avessi avuto altri test da proporre, li avrei potuti mettere subito dopo il primo test, in un'altra graffa come linea 44.
       */
        }


 //see ref:https://www.tutorialspoint.com/junit/junit_parameterized_test.htm
    @Test
    public void test_1() throws Exception {
        Model model = JSON.parseObject(input, classType, conf);
        Assert.assertEquals(this.expected, model.value);
    }

    /*public void test_1() throws Exception {
        ParserConfig config = new ParserConfig(Thread.currentThread().getContextClassLoader());

        Model model = JSON.parseObject("{\"value\":123}", Model.class, config);
        Assert.assertEquals(123, model.value);
    }
    rispetto a prima, metto i parametri, non valori "esatti" */

    public static class Model {
        public int value;
    }


}