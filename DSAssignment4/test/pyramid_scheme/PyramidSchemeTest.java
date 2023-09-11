/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyramid_scheme;

import DataStructures.MultiTreeNode;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pmele
 * @version 3/28/2019
 */
public class PyramidSchemeTest {
    
    private PyramidScheme instance;
    private MultiTreeNode<Person> root;
    private Person p1;
    private Person p2;
    private Person p3;
    private Person p4;
    private Person p5;
        
    /**
     * Sets up the later tests.
     */
    @Before
    public void setUp() {
        p1 = new Person("Person 1");
        p2 = new Person("Person 2");
        p3 = new Person("Person 3");
        p4 = new Person("Person 4");
        p5 = new Person("Person 5");
        root = new MultiTreeNode<>(p1);
        //p1 is the leader, $100 recruit price, 20% is passed upwards
        instance = new PyramidScheme(root, 100.0, .20);
    }

    /**
     * Test of whoBenefits method, of class PyramidScheme.
     */
    @Test
    public void testWhoBenefits() {
        try {
            //Create chain of recruits
            instance.addChild(p1, p2);
            instance.addChild(p2, p3);
            instance.addChild(p3, p4);
            instance.addChild(p4, p5);
            ArrayList<MultiTreeNode<Person>> bosses = instance.whoBenefits(p5);
            assertEquals(4, bosses.size()); //4 tiers above p5
            assertEquals(p4, bosses.get(0).getElement()); //first, direct boss
            assertEquals(p3, bosses.get(1).getElement()); //next higher
            assertEquals(p2, bosses.get(2).getElement()); //next higher
            assertEquals(p1, bosses.get(3).getElement()); //top of tree
            
            bosses = instance.whoBenefits(new Person("Not in the scheme"));
            assertEquals(null, bosses); //Returns null when target is not found
        } catch (Exception ex) {
            fail("Unexpected Exception");
        }
    }
    
    /**
     * Test of addChild method, of class PyramidScheme.
     */
    @Test
    public void testAddChild() {
        try {
            assertEquals(0.0, p1.getBalance(), 1); //All people start at 0
            assertEquals(0.0, p2.getBalance(), 1);
            assertEquals(0.0, p3.getBalance(), 1);
            assertEquals(0.0, p4.getBalance(), 1);
            assertEquals(0.0, p5.getBalance(), 1);
            instance.addChild(p1, p2); //p2 recruited by p1
            assertEquals(100.0, p1.getBalance(), 1); //p1 is paid in full
            assertEquals(-100.0, p2.getBalance(), 1); //p2 pays recruitment fee
            instance.addChild(p2, p3); //p3 recruited by p2; p1 will benefit
            assertEquals(120.0, p1.getBalance(), 1);  //p1 gets 20 of the fee
            assertEquals(-20.0, p2.getBalance(), 1); //p2 gets 80 of the fee
            assertEquals(-100.0, p3.getBalance(), 1); //p3 pays the 100 fee
            instance.addChild(p3, p4); //p4 recruited by p3; p1 and p2 benefit
            assertEquals(124.0, p1.getBalance(), 1); //p1 gets 4 of the fee
            assertEquals(-04.0, p2.getBalance(), 1); //p2 gets 16 of the fee
            assertEquals(-20.0, p3.getBalance(), 1); //p3 gets 80 of the fee
            assertEquals(-100.0, p4.getBalance(), 1); //p4 pays the 100 fee
            instance.addChild(p2, p5); //p5 recruited by p2; p1 will benefit
            assertEquals(144.0, p1.getBalance(), 1); //p1 gets 20 of the 100
            assertEquals(76.0, p2.getBalance(), 1); //p2 gets 80 of the 100
            assertEquals(-20.0, p3.getBalance(), 1); //p3 is unchanged
            assertEquals(-100.0, p4.getBalance(), 1); //p4 is unchanged
            assertEquals(-100.0, p5.getBalance(), 1); //p5 pays the 100 fee
        } catch (Exception ex) {
            fail("Unexpected Exception");
        }
    }

    /**
     * Test of initiateCollapse method, of class PyramidScheme.
     */
    @Test
    public void testInitiateCollapse() {
        try {
            //Create populated scheme
            instance.addChild(p1, p2);
            instance.addChild(p2, p3);
            instance.addChild(p2, p4);
            instance.addChild(p4, p5);
            assertEquals(5, instance.size());
            instance.initiateCollapse();
            assertEquals(1, instance.size()); //Scheme has been disbanded
            //No tests provided for the arbitrary money changes,
            //Since they are not particularly relevant to the assignment
        } catch (Exception ex) {
            fail("Unexpected Exception");
        }
    }
}
