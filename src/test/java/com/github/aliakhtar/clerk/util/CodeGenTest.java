package com.github.aliakhtar.clerk.util;

import org.apache.velocity.Template;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CodeGenTest
{
    private final static CodeGen cg = CodeGen.get();
    public CodeGenTest() throws Exception
    {
    }

    @Test
    public void testGetTemplate()
            throws Exception
    {
        Template tpl = cg.getTemplate(this.getClass(), "VariablePrintTest.vm");
        String parsed =
                cg.asString(tpl);


        assertNotNull(parsed, parsed);
        assertEquals(parsed, "$fooBar", parsed);
    }
}
