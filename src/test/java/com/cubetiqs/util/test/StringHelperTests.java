package com.cubetiqs.util.test;

import com.cubetiqs.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringHelperTests {
    @Test
    public void getOrEmptyString() {
        Assert.assertEquals("Hello", StringUtils.getOrEmpty("Hello"));
        Assert.assertEquals("", StringUtils.getOrEmpty(null));
    }
}
