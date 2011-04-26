package functional;

import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

public class RedirectorTest extends FunctionalTest {

    @Test
    public void testRedirect() {
        Response response = GET("/redirect/http/www.cnn.com/80/main/index.html");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com/main/index.html", response.headers.get("Location").value());
    }

    @Test
    public void testTrailingSlash() {
        Response response=GET("/redirect/http/www.cnn.com/80");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com", response.headers.get("Location").value());

        response=GET("/redirect/http/www.cnn.com/80/");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com/", response.headers.get("Location").value());

        response=GET("/redirect/http/www.cnn.com/80/main");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com/main", response.headers.get("Location").value());

        response=GET("/redirect/http/www.cnn.com/80/main/");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com/main/", response.headers.get("Location").value());
    }

    @Test
    public void testWithQueryString() {
        Response response=GET("/redirect/http/www.cnn.com/80/info?key1=val1&key2=val2");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com/info?key1=val1&key2=val2", response.headers.get("Location").value());
    }

    @Test
    public void testRedirectWithNoDefaultPort() {
        Response response=GET("/redirect/http/www.cnn.com/8080/main2/index2.html");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com:8080/main2/index2.html", response.headers.get("Location").value());
    }

    @Test
    public void testRedirectHttps() {
        Response response=GET("/redirect/https/www.cnn.com/443/main3/index3.html");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("https://www.cnn.com/main3/index3.html", response.headers.get("Location").value());

        response=GET("/redirect/https/www.cnn.com/4443/main3/index3.html");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("https://www.cnn.com:4443/main3/index3.html", response.headers.get("Location").value());
    }

    @Test
    public void testRedirectOtherMethods() {
        Response response=POST("/redirect/http/www.cnn.com/80/index4.html");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("http://www.cnn.com/index4.html", response.headers.get("Location").value());

        response=DELETE("/redirect/https/www.cnn.com/443/main3/index3.html");
        assertEquals(StatusCode.FOUND, (int)response.status);
        assertEquals("https://www.cnn.com/main3/index3.html", response.headers.get("Location").value());
    }

}
