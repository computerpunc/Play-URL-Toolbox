package controllers.URLToolbox;


import java.io.IOException;
import java.io.InputStream;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.*;
import play.mvc.Http.*;

public class Proxy extends Controller {

    static void handleReponse(HttpResponse directResponse) {
        response.status=directResponse.getStatus();
        String contentType = null;
        for ( Header header:directResponse.getHeaders()) if (0==header.name.compareTo("Content-Type")) { contentType=header.value(); break; }
        //String contentLength = null; try { contentLength = directResponse.getHeader("Content-Length"); } catch (Exception e) {}
        if (null==contentType) renderText("");
        response.contentType=contentType;
        String st=directResponse.getString();
        renderText(st);
    }

    static WSRequest handleURL(String url) {
        if (null!=request.querystring) url+="?"+request.querystring;
        return WS.url(url);
    }

    public static void get(String url) {
        handleGet("http://"+url);
    }

    public static void gets(String url) {
        handleGet("https://"+url);
    }

    static void handleGet(String url) {
        HttpResponse directResponse=handleURL(url).get();
        handleReponse(directResponse);
    }

    public static void delete(String url) {
        handleDelete("http://"+url);
    }

    public static void deletes(String url) {
        handleDelete("https://"+url);
    }

    private static void handleDelete(String url) {
        HttpResponse directResponse=handleURL(url).delete();
        handleReponse(directResponse);
    }

    public static void post(String url) throws IOException {
        handlePost("http://"+url);
    }

    public static void posts(String url) throws IOException {
        handlePost("https://"+url);
    }

    static String slurp (InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    static void handlePost(String url) throws IOException {
        WSRequest directRequest=WS.url(url).body(slurp(request.body)).setHeader("Content-Type", request.contentType);
        for (String headerName:request.headers.keySet()) directRequest.setHeader(headerName, request.headers.get(headerName).value());

        HttpResponse directResponse=directRequest.post();
        handleReponse(directResponse);
    }
}