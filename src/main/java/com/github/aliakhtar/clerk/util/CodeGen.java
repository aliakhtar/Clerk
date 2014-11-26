/*
 *
 *  * @author Ali Akhtar
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  * use this file except in compliance with the License. You may obtain a copy of
 *  * the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package com.github.aliakhtar.clerk.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class CodeGen
{
    private static final CodeGen instance = new CodeGen();

    public static final VelocityContext EMPTY_CONTEXT
            = new VelocityContext();

    private CodeGen()
    {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class",
                             ClasspathResourceLoader.class.getName());
        Velocity.init();
    }


    public static CodeGen get()
    {
        return instance;
    }

    public String getTemplate(Class<?> packageNeighbor,
                              String fileName)
    {
        return getTemplate(packageNeighbor, fileName, EMPTY_CONTEXT);
    }


    public String getTemplate(Class<?> packageNeighbor, String fileName,
                              VelocityContext context)
    {
        String packagePath = packageNeighbor.getPackage().getName()
                                            .replace(".", "/");
        StringWriter sw = new StringWriter();
        Velocity.mergeTemplate(packagePath + "/" + fileName, "UTF-8", context, sw);

        return sw.getBuffer().toString();
    }


    public void write(String className, String code,
                      Class<?> packageNeighbor, ProcessingEnvironment env)
            throws IOException
    {
        String cannonicalName =
                packageNeighbor.getPackage().getName() + "." + className;

        System.out.println("Cannonical: " + cannonicalName);
        JavaFileObject file =
                env.getFiler().createSourceFile(cannonicalName);

        Writer writer = file.openWriter();

        System.out.println(file.getName());

        writer.write( code );
        writer.close();

        File f = new File( file.getName() );
        System.out.println("Exists: " + f.exists());
    }

}
