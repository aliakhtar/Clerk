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

package com.github.aliakhtar.clerk.processor;

import com.github.aliakhtar.clerk.Clerk;
import com.github.aliakhtar.clerk.util.CodeGen;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;


@SupportedAnnotationTypes("com.github.aliakhtar.clerk.shared.ValidateMe")
public class MainProcessor extends BaseProcessor
{
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv)
    {
        log("OK " + roundEnv.toString());

        if (roundEnv.processingOver())
            return false;

        String code = CodeGen.get().getTemplate(CodeGen.class, "Output.vm");

        log("Writing");
        try
        {
            CodeGen.get().write("Output", code, Clerk.class, processingEnv);
            log("Written");
            log( Class.forName("com.github.aliakhtar.clerk.Output").toString() );
        }
        catch (Exception e)
        {
            error(e.toString() );
        }
        return false;
    }
}
