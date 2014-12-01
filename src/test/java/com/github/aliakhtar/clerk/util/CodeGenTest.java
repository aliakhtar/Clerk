package com.github.aliakhtar.clerk.util;

import com.github.aliakhtar.annoTest.util.AnnoTest;
import com.github.aliakhtar.clerk.processor.MainProcessor;
import com.github.aliakhtar.clerk.shared.ValidateMe;
import org.junit.Test;

import javax.validation.constraints.NotNull;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CodeGenTest extends AnnoTest
{
    public CodeGenTest() throws Exception
    {
        super(new MainProcessor(), NotNull.class, ValidateMe.class);
    }

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
