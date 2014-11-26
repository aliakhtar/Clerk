package com.github.aliakhtar.clerk.util;

import com.github.aliakhtar.annoTest.annotation.PrintMe;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

@PrintMe
public class VelocityTest
{
    private VelocityEngine v;

    @Before
    public void setUp() throws Exception
    {
        v = new VelocityEngine();

        v.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        v.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        v.init();
    }

    @Test
    public void simpleBean()
            throws Exception
    {
        Template t = v.getTemplate("SimpleBean.vm");
        StringWriter sw = new StringWriter();
        t.merge(new VelocityContext(), sw);

        String result = sw.getBuffer().toString();
        System.out.println( result );

        System.out.println( VelocityTest.class.getPackage().getName() );
    }
}
