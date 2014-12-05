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
import java.util.ArrayList;
import java.util.List;

public class CodeGen
{
    public static final VelocityContext EMPTY_CONTEXT
            = new VelocityContext();

    private static final VelocityEngine v = new VelocityEngine();

    protected final String genClassName;
    protected final String genPackageName;
    protected final Template tpl;

    protected final List<CodeGenResult> generatedFiles
            = new ArrayList<>();

    static
    {
        v.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        v.setProperty("classpath.resource.loader.class",
                      ClasspathResourceLoader.class.getName());
        v.init();
    }



    public CodeGen(String tplName,
                   Class<?> tplPackageNeighbor,
                   String genClassName,
                   String genPackageName)
    {
        this.genClassName = genClassName;
        this.genPackageName = genPackageName;
        tpl = getTemplate(tplPackageNeighbor, tplName);
    }

    public static class Builder
    {
        private String tplName;
        private Class<?> tplPackageNeighbor;

        private String genClassName;
        private String genPackageName;


        public Builder tplName(String tplName)
        {
            this.tplName = tplName;
            return this;
        }

        public Builder tplNeighbor(Class<?> tplPackageNeighbor)
        {
            this.tplPackageNeighbor = tplPackageNeighbor;
            return this;
        }

        public Builder className(String genClassName)
        {
            this.genClassName = genClassName;
            return this;
        }


        public Builder packageNeighbor(Class<?> genPackageNeighbor)
        {
            return genPackageName( genPackageNeighbor.getPackage().getName() );
        }

        public Builder genPackageName(String genPackageName)
        {
            this.genPackageName = genPackageName;
            return this;
        }

        public CodeGen build()
        {
            Object[] values = {tplName, tplPackageNeighbor, genClassName, genPackageName};
            int i = 0;
            for (Object value : values)
            {
                if (value == null)
                    throw new IllegalStateException("Field # " + i + " not set");
                i++;
            }

            return new CodeGen(tplName, tplPackageNeighbor, genClassName, genPackageName);
        }
    }

    public static Builder builder()
    {
        return new Builder();
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


    public static CodeGenResult write(String packageName,
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

    public CodeGenResult generate(VelocityContext context,
                                  ProcessingEnvironment processingEnv)
            throws IOException
    {
        String code = asString(tpl, context);
        CodeGenResult result =
                write(genPackageName, genClassName, code, processingEnv);
        generatedFiles.add(result);

        return result;
    }


    public List<CodeGenResult> getGeneratedFiles()
    {
        return generatedFiles;
    }
}
