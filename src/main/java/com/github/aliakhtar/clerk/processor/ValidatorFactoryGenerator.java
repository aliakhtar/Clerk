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

import com.github.aliakhtar.clerk.validate.ValidatorProvider;
import org.apache.velocity.VelocityContext;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;


@SupportedAnnotationTypes("*")
public class ValidatorFactoryGenerator extends CodeGeneratingProcessor
{
    private int i = 0;

    public ValidatorFactoryGenerator()
    {
        super("ValidatorFactory.vm", ValidatorProvider.class,
                "ValidatorFactory", ValidatorProvider.class);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv)
    {
        i++;
        if (! roundEnv.processingOver())
        {
            log("Processing not over : " + i);
            return false;
        }

        log("Processing over: " + i + ", generating");
        VelocityContext context = new VelocityContext();
        context.put("name", "test");

        try
        {
            generate(context);
        }
        catch (IOException e)
        {
            error(e);
        }
        return false;
    }
}
