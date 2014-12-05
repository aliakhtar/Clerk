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

import java.util.List;

public abstract class CodeGeneratingProcessor extends BaseProcessor
{
    protected final CodeGen generator;

    protected CodeGeneratingProcessor(CodeGen generator)
    {
        this.generator = generator;
    }

    public List<CodeGenResult> getGeneratedFiles()
    {
        return generator.getGeneratedFiles();
    }

    public CodeGen getGenerator()
    {
        return generator;
    }
}
