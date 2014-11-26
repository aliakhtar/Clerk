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

import java.io.StringWriter;

public class CodeGen
{
    private static final CodeGen instance = new CodeGen();

    private final VelocityContext emptyContext
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

    public String getTemplate(Class packageNeighbor,
                              String fileName)
    {
        return getTemplate(packageNeighbor, fileName, emptyContext);
    }


    public String getTemplate(Class packageNeighbor, String fileName,
                              VelocityContext context)
    {
        String packagePath = packageNeighbor.getPackage().getName()
                                            .replace(".", "/");
        StringWriter sw = new StringWriter();
        Velocity.mergeTemplate(packagePath + "/" + fileName, "UTF-8", context, sw);

        return sw.getBuffer().toString();
    }

}
