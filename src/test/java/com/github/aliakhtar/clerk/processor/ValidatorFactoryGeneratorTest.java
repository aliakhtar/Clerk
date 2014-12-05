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

import com.github.aliakhtar.annoTest.util.AnnoTest;
import com.github.aliakhtar.annoTest.util.SourceFile;
import com.github.aliakhtar.clerk.codegen.CodeGen;
import com.github.aliakhtar.clerk.codegen.CodeGenResult;
import com.github.aliakhtar.clerk.validate.ValidatorProvider;
import org.apache.velocity.Template;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorFactoryGeneratorTest extends AnnoTest<ValidatorFactoryGenerator>
{
    public ValidatorFactoryGeneratorTest() throws Exception
    {
        super(new ValidatorFactoryGenerator(), ValidatorProvider.class);
    }

    @Test
    public void testProcess() throws Exception
    {
        Template tpl = CodeGen.getTemplate(CodeGen.class, "SimpleBean.vm");
        String code = CodeGen.asString(tpl);

        SourceFile sf = new SourceFile("SimpleBean.java", code);

        assertTrue( compiler.compileWithProcessor(processor, sf) );

        assertEquals(1, processor.getWrapped().getGeneratedFiles().size());

        CodeGenResult result = processor.getWrapped().getGeneratedFiles().get(0);
        assertNotNull(result.toString(), result);

        String simpleExpected = "ValidatorFactory";
        String cannonicalExpected = ValidatorProvider.class.getPackage().getName()
                                    + "." + simpleExpected;
        assertEquals(simpleExpected, result.getSimpleName());
        assertEquals( cannonicalExpected, cannonicalExpected, result.getCannonicalName() );
        assertTrue( result.exists() );
        assertNotNull( result.getPath() );
        assertNotNull( result.getJfo() );
        assertNotNull( result.getContent() );
    }
}