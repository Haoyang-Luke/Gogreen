package com.example.gogreen.Data;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModelUserTest {


    @Test
    public void getEmail() {
        ModelUser test=new ModelUser("nike","shi","cc","aaa","bb");
        String email=test.getEmail();
        assertEquals("bb",email);
    }

    @Test
    public void getName() {
        ModelUser test=new ModelUser("nike","shi","cc","aaa","bb");
        String name=test.getName();
        assertEquals("nike",name);
    }

    @Test
    public void getPoints() {
        ModelUser test=new ModelUser("nike","shi","cc","aaa","bb");
        String Points=test.getPoints();
        assertEquals("cc",Points);
    }
}