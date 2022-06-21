

import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/*
ORIGINAL TEST: https://github.com/alibaba/fastjson/blob/1.2.79/src/test/java/com/alibaba/json/bvt/serializer/ObjectArraySerializerTest.java
RIFERIMENTO JACOCO: goto com.alibaba.fastjson.serializer/JSONSerializer/write(Object) (essa richiama write(SerializeWriter, object)
 */

@RunWith(Enclosed.class)
public class ObjectArraySerializerTest {

    @RunWith(Parameterized.class)
    public static class ObjectArraySerializerTest012 {

        private SerializeWriter out;
        private Object[] obj;
        private String expected;

        public ObjectArraySerializerTest012(int initialSize, Object[] obj, String expected)
        {
            configure(initialSize, obj, expected);
        }

        public void configure(int initialSize, Object[] obj, String expected)
        {
            this.out = new SerializeWriter(initialSize);
            this.obj = obj;
            this.expected = expected;

        }

        @Parameterized.Parameters
        public static Collection<Object[]> getTestParameters()
        {
            return Arrays.asList(new Object[][]{
                    // serializeWriter          Object[]                            Expected
                    {1,     new Object[]{"a12", "b34"},        "[\"a12\",\"b34\"]"},
                    {1,     new Object[]{},                     "[]"},
                    {1,     new Object[]{null, null},           "[null,null]"},
                    {1,     null,                               "null"}  //aggiunto perchè in  write(Object object) ho: if (object == null)
            });
        }

        @Test
        public void test012() //test 0, 1, 2 parametrizzati.
        {
            JSONSerializer.write(out, obj);
            Assert.assertEquals(this.expected, out.toString());

        }
    }

    @RunWith(Parameterized.class)
    public static class ObjectArraySerializerTest3
    {

        private boolean param;
        private Object[] obj;
        private String expected;


        //costruttore
        public ObjectArraySerializerTest3(boolean param, Object[] obj, String expected) {
            configure(param, obj, expected); //nel costruttore uso configure.
        }

        //configure
        public void configure(boolean param, Object[] obj, String expected) {
            this.param = param;
            this.obj = obj;
            this.expected = expected;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> getTestParameters() {
            return Arrays.asList(new Object[][]{
                    // param                   Object[]                     Expected
                    { false             , new Object[]{null, null},     "[null,null]"},
                    {true,     new Object[]{"a12", "b34"},        "[\n" +
                            "\t\"a12\",\n" +
                            "\t\"b34\"\n" +
                            "]"},

                    });}

        @Test
        public void test3()
            {
                Assert.assertEquals(expected, JSON.toJSONString(obj, param));
            }
    }

}
