/*
 *
 * @author Ali Akhtar
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *  *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.github.aliakhtar.clerk.codegen;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class CodeGen
{
    public static final VelocityContext EMPTY_CONTEXT
            = new VelocityContext();

    private static final VelocityEngine v = new VelocityEngine();
    private CodeGen()
    {
        v.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        v.setProperty("classpath.resource.loader.class",
                             ClasspathResourceLoader.class.getName());
        v.init();
    }


    public static Template getTemplate(Class<?> packageNeighbor, String fileName)
    {
        String packagePath = packageNeighbor.getPackage().getName()
                                            .replace(".", "/");
        return v.getTemplate(packagePath + "/" + fileName);
    }

    public static String asString(Template tpl, VelocityContext context)
    {
        StringWriter sw = new StringWriter();
        tpl.merge(context, sw);
        return sw.getBuffer().toString();
    }

    public static String asString(Template tpl)
    {
        return asString(tpl, EMPTY_CONTEXT);
    }

    public static String getParsedTemplate(Class<?> packageNeighbor, String fileName,
                                    VelocityContext context)
    {
        Template tpl = getTemplate(packageNeighbor, fileName);
        return asString(tpl, context);
    }


    public CodeGenResult write(String packageName,
                               String className,
                               String code,
                               ProcessingEnvironment env)
            throws IOException
    {
        String cannonicalName =
                packageName + "." + className;

        JavaFileObject file =
                env.getFiler().createSourceFile(cannonicalName);

        Writer writer = file.openWriter();
        writer.write( code );
        writer.close();

        return new CodeGenResult(className, cannonicalName, file, code);
    }
}
