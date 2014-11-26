package com.github.aliakhtar.clerk.util;

import com.github.aliakhtar.annoTest.annotation.PrintMe;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@PrintMe
public class CodeGenTest
{
    @Test
    public void readWorks()
            throws Exception
    {
        String template =
                CodeGen.get().getTemplate(this.getClass(), "SimpleBean.vm");

        assertNotNull(template, template);
        System.out.println(template);
    }
}
