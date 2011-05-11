package functional;

import org.junit.*;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.Http;
import play.test.*;
import play.mvc.Http.*;

public class LocalhostRedirectTest extends FunctionalTest {

    @Test
    public void testHttp() {
        Response response=GET("/render/localhost_redirect_test.html?url=http://localhost:9000/index.html&using=www.cnn.com/redirect");
        assertIsOk(response);
        assertContentEquals( "http://www.cnn.com/redirect/http/localhost/9000/index.html", response);
    }

    @Test
    public void testHttps() {
        Response response=GET("/render/localhost_redirect_test.html?url=https://localhost:9000/index.html&using=www.cnn.com/redirect");
        assertIsOk(response);
        assertContentEquals( "https://www.cnn.com/redirect/https/localhost/9000/index.html", response);
    }
}
