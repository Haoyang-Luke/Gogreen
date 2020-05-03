package com.example.gogreen.Data;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void getName() {
    Person test=new Person("Luke",10,2,3);
    String name=test.getName();
        assertEquals("Luke",name);
    }

    @Test
    public void getPoint() {
        Person test=new Person("nike",5,3,2);
        int point=test.getPoint();
        assertEquals(5,point);
    }
}