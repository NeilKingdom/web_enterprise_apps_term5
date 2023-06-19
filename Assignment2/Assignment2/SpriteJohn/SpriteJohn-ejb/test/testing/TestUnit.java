/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


import java.util.List;
import javax.annotation.security.RunAs;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
 */
package testing;



import cst8218.hoan0105.adapter.ColorAdapter;
import cst8218.hoan0105.entity.AppUser;
import cst8218.hoan0105.entity.Sprite;

import java.awt.Color;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author J-PC
 */

public class TestUnit {
    
    Sprite sprite;
    AppUser appUser;
    
    public TestUnit() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    /**
     * Test getX method of Sprite not null
     */
    @Test
    public void testGetX() {
        Sprite instance = new Sprite();
        Integer expResult = instance.getX();
        Integer value = null;
        assertNotSame(expResult, value);
    }

    /**
     * Test setX method of Sprite.
     */
    @Test
    public void testSetX() {
        Sprite instance = new Sprite();
        Integer expResult = 10;
        instance.setX(10);
        Integer result = instance.getX();
        assertEquals(expResult,result);
    }

    /**
     * Test getY method of Sprite not null
     */
    @Test
    public void testGetY() {
        Sprite instance = new Sprite();
        Integer expResult = instance.getY();
        Integer value = null;
        assertNotSame(expResult, value);
    }

    /**
     * Test setY method of Sprite.
     */
    @Test
    public void testSetY() {
        Sprite instance = new Sprite();
        Integer expResult = instance.getY();
        Integer value = null;
        assertNotSame(expResult, value);
    }

    /**
     * Test getDx method of Sprite not null
     */
    @Test
    public void testGetDx() {
        Sprite instance = new Sprite();
        Integer expResult = instance.getDx();
        Integer value = null;
        assertNotSame(expResult, value);
    }

    /**
     * Test of setDx method, of class Sprite.
     */
    @Test
    public void testSetDx() {
        Sprite instance = new Sprite();
        Integer expResult = 10;
        instance.setDx(10);
        Integer result = instance.getDx();
        assertEquals(expResult,result);
    }

    /**
     * Test getDy method class Sprite not null
     */
    @Test
    public void testGetDy() {
        Sprite instance = new Sprite();
        Integer expResult = instance.getDy();
        Integer value = null;
        assertNotSame(expResult, value);
    }

    /**
     * Test setDy method of class Sprite.
     */
    @Test
    public void testSetDy() {
        Sprite instance = new Sprite();
        Integer expResult = 10;
        instance.setDy(10);
        Integer result = instance.getDy();
        assertEquals(expResult,result);
    }
    
}
