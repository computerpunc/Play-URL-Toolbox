package functional;

import org.junit.*;
import play.libs.WS;
import play.libs.WS.HttpResponse;
//import play.libs.WS.WSRequest;
import play.mvc.Http;
import play.test.*;
//import play.mvc.*;
import play.mvc.Http.*;
import play.mvc.Router;
//import models.*;

public class ProxyTest extends FunctionalTest {

    @Test
    public void testHttp() {
        Response response=GET("/proxy/http/htmlref.com/ch2/helloworld.html");
        assertIsOk(response);
        //assertContentType("text/html", response);
        //assertCharset("utf-8", response);
        HttpResponse directResponse=WS.url("http://htmlref.com/ch2/helloworld.html").get();
        String st=directResponse.getString();
        assertContentEquals( st, response);
    }

    @Test
    public void testHttps() {
        Response response=GET("/proxy/https/www.rfc-editor.org/rfc/rfc2606.txt");
        assertIsOk(response);
        HttpResponse directResponse=WS.url("https://www.rfc-editor.org/rfc/rfc2606.txt").get();
        String st=directResponse.getString();
        assertContentEquals( st, response);
    }

    @Test
    public void testQueryStrings() {
        Response response=GET("/proxy/http/ws.geonames.org/findNearbyWikipedia?lat=47&lng=9");
        assertIsOk(response);
        HttpResponse directResponse=WS.url("http://ws.geonames.org/findNearbyWikipedia?lat=47&lng=9").get();
        String st=directResponse.getString();
        assertContentEquals( st, response);
    }

    @Test
    public void testStatusCode() {
        Response response=GET("/proxy/https/www.googleapis.com/latitude/v1/currentLocation");
        assertStatus(Http.StatusCode.UNAUTHORIZED, response);
    }

    @Test
    public void testContentType() {
        Response response=GET("/proxy/http/www.google.com");
        assertIsOk(response);
        assertContentType("text/html; charset=windows-1255", response);
    }

    @Test
    public void testPost() {
        Response response=POST("/proxy/http/www.basic4ppc.com/print.php","application/x-www-form-urlencoded", "userid=joe&password=guessme");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertContentMatch("POST variables:\r\narray\\(2\\) \\{\n  \\[\"userid\"\\]=>\n  string\\(3\\) \"joe\"\n  \\[\"password\"\\]=>\n  string\\(7\\) \"guessme\"\n\\}\n\r\nGET variables:\r\narray\\(0\\) \\{\n\\}\n", response);
    }

    @Test
    public void testDelete() {
        Response response=DELETE("/proxy/http/test.webdav.org/dav/kdjfsi32SDK.txt");
        assertIsNotFound(response);
    }

    @Test
    public void testBlockWhenNotPermited() {
        String canProxyPropName = "urltoolbox.can-proxy";
        String canProxyProp=play.Play.configuration.getProperty(canProxyPropName);
        play.Play.configuration.remove(canProxyPropName);

        Response response=GET("/proxy/http/htmlref.com/ch2/helloworld.html");
        play.Play.configuration.put(canProxyPropName, canProxyProp);
        assertStatus(Http.StatusCode.FORBIDDEN, response);

        response=GET("/proxy/http/htmlref.com/ch2/helloworld.html");
        assertIsOk(response);

    }
}