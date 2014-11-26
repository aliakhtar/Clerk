package com.github.aliakhtar.clerk.util;

import com.github.aliakhtar.annoTest.annotation.PrintMe;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@PrintMe
public class CodeGenTest
{
    @Test
    public void testGetTemplate()
            throws Exception
    {
        String template =
                CodeGen.get().getTemplate(this.getClass(), "SimpleBean.vm");

        assertNotNull(template, template);
        assertTrue(template, template.contains("@NotNull") );
    }
}
