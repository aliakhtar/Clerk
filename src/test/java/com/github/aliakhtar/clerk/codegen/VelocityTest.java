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

package com.github.aliakhtar.clerk.codegen;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VelocityTest
{
    public VelocityTest() throws Exception
    {
    }

    @Test
    public void testGetTemplate()
            throws Exception
    {
        Template tpl  = CodeGen.getTemplate(this.getClass(), "VariablePrintTest.vm");
        String parsed = CodeGen.asString(tpl);

        assertNotNull(parsed, parsed);
        assertEquals(parsed, "$fooBar", parsed);

        VelocityContext context = new VelocityContext();
        context.put("fooBar","test");

        parsed = CodeGen.asString(tpl, context);
        assertNotNull(parsed, parsed);
        assertEquals(parsed, "test", parsed);
    }
}
