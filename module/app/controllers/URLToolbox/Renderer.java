package controllers.URLToolbox;

import play.exceptions.TemplateNotFoundException;
import play.mvc.*;

public class Renderer extends Controller {

    public static void render(String path) {
        try { renderTemplate(path); }
        catch (TemplateNotFoundException ex) { notFound(); }
    }
}