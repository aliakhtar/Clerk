/*
 * @author Ali Akhtar
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.aliakhtar.clerk.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;

abstract class BaseProcessor extends AbstractProcessor
{
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        if( SourceVersion.latestSupported() == SourceVersion.RELEASE_8 )
            return SourceVersion.RELEASE_8;

        return SourceVersion.RELEASE_7;
    }


    protected void log(String msg)
    {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    protected void error(String msg)
    {
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }

    protected void error(Throwable e)
    {
        messager.printMessage(Diagnostic.Kind.ERROR, e.toString() );
    }
}
