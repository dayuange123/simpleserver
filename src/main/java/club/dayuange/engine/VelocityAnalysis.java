package club.dayuange.engine;

import club.dayuange.mypacket.request.SimpleRequest;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VelocityAnalysis {
    private static VelocityEngine ve;

    static {
        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }

    public static String resloveHtml(String path, SimpleRequest request) {
        Template t = ve.getTemplate(path);
        VelocityContext ctx = new VelocityContext();
        Map<String, Object> attributes = request.getAttributes();
        Map<String, Object> attributes1 = request.getSession().getAttributes();
        attributes.forEach((s, o) ->{
            ctx.put(s,o);
        });
        attributes1.forEach((s, o) ->{
            ctx.put(s,o);
        });

        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        return sw.toString();
    }
}