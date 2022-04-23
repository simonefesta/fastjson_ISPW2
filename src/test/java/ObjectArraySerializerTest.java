import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ObjectArraySerializerTest extends TestCase {

 private SerializeWriter out;
 private Object[] obj;
 private String expected;

//costruttore
public ObjectArraySerializerTest(SerializeWriter out, Object[] obj, String expected) {
    configure(out, obj,expected); //nel costruttore uso configure.
}

//configure
    private void configure(SerializeWriter out, Object[] obj,String expected)
    {
        this.out = out;
        this.obj = obj;
        this.expected = expected;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{
                {new SerializeWriter(1), new Object[] { "a12", "b34" },"[\"a12\",\"b34\"]"},
                {new SerializeWriter(1), new Object[] {},"[]"},
                {new SerializeWriter(1), new Object[] {null,null},"[null,null]"}

        });
    }
    //{JSON.toJSONString(new Object[] { null, null }, false),"[null,null]"} andrebbe tra i test, ma ha una config diversa.

        @Test
        public void test() throws Exception {

            JSONSerializer.write(out, obj);

            Assert.assertEquals(this.expected, out.toString());

        }

/*
    public void test_0() throws Exception {
        SerializeWriter out = new SerializeWriter(1);

        JSONSerializer.write(out, new Object[] { "a12", "b34" });

        Assert.assertEquals("[\"a12\",\"b34\"]", out.toString());
    }

    public void test_1() throws Exception {
        SerializeWriter out = new SerializeWriter(1);

        JSONSerializer.write(out, new Object[] {});

        Assert.assertEquals("[]", out.toString());
    }

    public void test_2() throws Exception {
        SerializeWriter out = new SerializeWriter(1);

        JSONSerializer.write(out, new Object[] { null, null });

        Assert.assertEquals("[null,null]", out.toString());
    }

    public void test_3() throws Exception {
        Assert.assertEquals("[null,null]", JSON.toJSONString(new Object[] { null, null }, false));
    }


 */
}