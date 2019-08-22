package com.example.cityfun.util;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiaohan
 * @since 2019-08-21 16:47
 */
public class JsonUtilTest {

    @Test
    public void testJson(){
        String js = "{\n"
            + "  \"name\": \"cxh\",\n"
            + "  \"gender\": \"male\",\n"
            + "  \"age\":16\n"
            + "}";

        String js2 = "{\n"
            + "  \"name\": \"cxh\"\n"
            + "}";

        People people = JsonUtils.string2Obj(js, People.class);
        People people2 = JsonUtils.string2Obj(js2, People.class);

        Assert.assertNotNull(people);
        Assert.assertNotNull(people2);
        Assert.assertNull(people2.getGender());
    }

    @Data
    static class People{
        String name;

        String gender;
    }



}

