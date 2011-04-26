package functional;

import java.util.Random;
import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

public class RendererTest extends FunctionalTest {

    @Test
    public void testRender() {
        Response response = GET("/render/render_test.html");
        assertIsOk(response);
        assertContentEquals("This is the response from render_test.html", response);

        response = GET("/render/test/render_test.html");
        assertIsOk(response);
        assertContentEquals("This is the response from test/render_test.html", response);

    }

    @Test
    public void testInjectParams() {
        Random rand=new Random(System.currentTimeMillis());
        long val1 = rand.nextLong(); long val2 = rand.nextLong();
        Response response = GET("/render/render_params_test.html?key1="+val1+"&key2="+val2);
        assertIsOk(response);
        assertContentEquals("key1="+val1+" | key2:="+val2, response);
    }

    @Test
    public void testRenderNotFound() {
        Response response = GET("/render/render_notfound.html");
        assertIsNotFound(response);
    }
}
