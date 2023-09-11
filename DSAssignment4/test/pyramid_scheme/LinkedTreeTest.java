package pyramid_scheme;

import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Solution Test file for LinkedTree
 *
 * @author pmele
 * @version 3/28/2019
 */
public class LinkedTreeTest {

    private LinkedTree<String> instance;
    private MultiTreeNode<String> root;
    private String s01;
    private String s02;
    private String s03;
    private String s04;
    private String s05;

    /**
     * Sets up the later tests.
     */
    @Before
    public void setUp() {
        s01 = "Elem 1";
        s02 = "Elem 2";
        s03 = "Elem 3";
        s04 = "Elem 4";
        s05 = "Elem 5";
        root = new MultiTreeNode<>(s01);
        instance = new LinkedTree<>(root);
    }

    /**
     * Test of getRootElement method, of class LinkedTree.
     */
    @Test
    public void testGetRootElement() {
        System.out.println("testGetRootElement");
        assertEquals(instance.getRootElement(), s01);
        
    }

    /**
     * Test of addChild method, of class LinkedTree.
     */
    @Test
    public void testAddChild() {
        System.out.println("testAddChild");
        try{
            instance.addChild(s03, s04);
        }
        catch(Exception e){
            new ElementNotFoundException ("this element does not exist");
        }
        
        try{
            assertEquals(instance.size(), 1);
            instance.addChild(s01, s02);
            assertEquals(instance.findNode(s02).getElement(), s02);
            assertEquals(instance.size(), 2);
        }
        catch(Exception e){
            fail("should not throw exception");
        }
        
        
        
        
    }

    /**
     * Test of findNode method, of class LinkedTree.
     */
    @Test
    public void testFindNode() {
        System.out.println("testFindNode");
        try {
            //Can find root
            assertEquals(s01, instance.findNode(s01).getElement());
            instance.addChild(s01, s02);
            //System.out.println("Successfully added " + s02 + " to " + s01);
            //Can find a child node
            assertEquals(s02, instance.findNode(s02).getElement());
            instance.addChild(s01, s03); //Add several deep
            instance.addChild(s02, s04);
            instance.addChild(s04, s05);
            //Can find a child node deep within tree
            assertEquals(s05, instance.findNode(s05).getElement());
            //Trying to find things not in the tree returns null
            assertEquals(null, instance.findNode("Unreal element"));
        } catch (Exception ex) {
            fail("Unexpected Exception");
        }
    }

    /**
     * Test of contains method, of class LinkedTree.
     */
    @Test
    public void testContains() {
        try{
          assertEquals(instance.size(), 1);
          instance.addChild(s01, s02);
          assertEquals(instance.size(), 2);
          assertTrue(instance.contains(s02));
          assertFalse(instance.contains(s03));
        }
       catch(Exception e){
           fail("should not throw exception");
       }
    }

    /**
     * Test of size method, of class LinkedTree.
     */
    @Test
    public void testSize() {
        try{
          assertEquals(instance.size(), 1);
          instance.addChild(s01, s02);
          assertEquals(instance.size(), 2);
        }
       catch(Exception e){
           fail("should not throw exception");
       }
    }

}
