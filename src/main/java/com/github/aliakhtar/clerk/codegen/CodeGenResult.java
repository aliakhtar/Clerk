/*
 * @author Ali Akhtar
 * Licensed under the Apache License, Version 2.0 (the "License"); youmay not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.github.aliakhtar.clerk.codegen;

import javax.tools.JavaFileObject;
import java.io.File;

public class CodeGenResult
{
    private final String simpleName;
    private final String cannonicalName;
    private final String path;
    private final String content;
    private final JavaFileObject jfo;

    public CodeGenResult(String simpleName,
                         String cannonicalName,
                         JavaFileObject jfo,
                         String content)
    {
        this.simpleName = simpleName;
        this.cannonicalName = cannonicalName;
        this.path = jfo.getName();
        this.content = content;
        this.jfo = jfo;
    }


    public String getSimpleName()
    {
        return simpleName;
    }

    public String getCannonicalName()
    {
        return cannonicalName;
    }

    public String getPath()
    {
        return path;
    }

    public JavaFileObject getJfo()
    {
        return jfo;
    }

    public boolean exists()
    {
        File f = new File( getPath() );
        return f.exists() && f.isFile();
    }

    public String getContent()
    {
        return content;
    }

    @Override
    public String toString()
    {
        return "CodeGenResult{" +
               "simpleName='" + simpleName + '\'' +
               ", cannonicalName='" + cannonicalName + '\'' +
               ", path='" + path + '\'' +
               ", content='" + content + '\'' +
               ", jfo=" + jfo +
               '}';
    }
}
