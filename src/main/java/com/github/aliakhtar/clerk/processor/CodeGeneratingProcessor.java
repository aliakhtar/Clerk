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

package com.github.aliakhtar.clerk.processor;

import com.github.aliakhtar.clerk.codegen.CodeGen;
import com.github.aliakhtar.clerk.codegen.CodeGenResult;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CodeGeneratingProcessor extends BaseProcessor
{
    protected final String tplName;
    protected final Class<?> tplPackageNeighbor;

    protected final String genClassName;
    protected final String genPackageName;

    protected final Template tpl;

    protected final List<CodeGenResult> generatedFiles
            = new ArrayList<>();

    protected final static CodeGen cg = CodeGen.get();

    public CodeGeneratingProcessor(String tplName,
                                   Class<?> tplPackageNeighbor,
                                   String genClassName,
                                   Class<?> genPackageNeighbor)
    {
        this(tplName, tplPackageNeighbor,
             genClassName,
             genPackageNeighbor.getPackage().getName() );
    }

    public CodeGeneratingProcessor(String tplName,
                                   Class<?> tplPackageNeighbor,
                                   String genClassName,
                                   String genPackageName)
    {
        this.tplName = tplName;
        this.tplPackageNeighbor = tplPackageNeighbor;
        this.genClassName = genClassName;
        this.genPackageName = genPackageName;
        tpl = cg.getTemplate(tplPackageNeighbor, tplName);
    }

    protected CodeGenResult generate(VelocityContext context)
            throws IOException
    {
        String code = cg.asString(tpl, context);
        CodeGenResult result =
                cg.write(genPackageName, genClassName, code, processingEnv );
        generatedFiles.add(result);

        return result;
    }

    public List<CodeGenResult> getGeneratedFiles()
    {
        return generatedFiles;
    }
}
