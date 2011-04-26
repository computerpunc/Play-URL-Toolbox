package controllers.URLToolbox;

import play.mvc.*;

public class Redirector extends Controller {
    static public void redirect(String protocol, String domain, int port, String url) {
        String qs=request.querystring;
        redirect(protocol+"://"+domain+((("https".equals(protocol)&&(443==port))||("http".equals(protocol)&&(80==port)))?"":(":"+port))+((null==url)?"":("/"+url))+(((null==qs)||(0==qs.length()))?"":"?"+qs));
    }
}