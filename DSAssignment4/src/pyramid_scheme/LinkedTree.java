package pyramid_scheme;

import ADTs.TreeADT;
import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
import java.util.ArrayList;

/**
 * Generic LinkedTree class for ITSC 2214.
 * 
 * @author nblong
 * @param <T>
 * @version Oct. 2021
 * 
 * Adapted from Perrin Mele's excellent work in the spring semester of 2019
 */
public class LinkedTree<T> implements TreeADT<T> {

    /**
     * The root of the tree is a generic MultiTreeNode.
     */
    protected MultiTreeNode<T> root;

    /**
     * Constructor with element provided.
     *
     * @param startElem - The starting element of this tree.
     */
    public LinkedTree(T startElem) {
        root = new MultiTreeNode<>(startElem);
    }

    /**
     * Constructor with node provided.
     *
     * @param startNode - The starting node of this tree.
     */
    public LinkedTree(MultiTreeNode<T> startNode) {
        root = startNode;
    }

    /**
     * Returns the element stored at the root.
     *
     * @return root element
     */
    @Override
    public T getRootElement() {
        return root.getElement();
    }

    /**
     * @param parent An element contained by a particular node in the tree,
     * which will ultimately be given a child node
     * @param child The element to be packaged into a node and added to the tree
     * @throws ElementNotFoundException
     */
    //checks to see if the parent exists and calls second addChild class
    public void addChild(T parent, T child) throws ElementNotFoundException {
        MultiTreeNode<T> parentNode = findNode(parent);
        if(parentNode == null)
            throw new ElementNotFoundException("There is no Parent node");
        else
            addChild(parentNode, child);
            
            
    }

    /**
     * @param parentNode the node receiving a child node
     * @param child the element to be packaged and added as a child node
     */
    //adds child to parent node
    protected void addChild(MultiTreeNode<T> parentNode, T child) {
        
        parentNode.addChild(new MultiTreeNode<T>(child));
        
    }

    /**
     * Finds the node that contains the target element by calling the recursive
     * overload to search for the target starting at the root of the tree.
     *
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target or null if not found
     */
    public MultiTreeNode<T> findNode(T target) {
        return findNode(root, target);
    }

    /**
     * Finds the node that contains a target element.  
     * @param node the node currently being examined
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target, or null if not found
     */
    private MultiTreeNode<T> findNode(MultiTreeNode<T> node, T target) {
        // If this node is the one containing the target...
        if (node.getElement().equals(target)) {
            return node; // Return this node
        } else { //Otherwise...
            MultiTreeNode<T> temp;
            // For each child of this node...
            for (MultiTreeNode<T> child : node.getChildren()) {
                // Call this method to see if the target is in a child node
                temp = findNode(child, target);
                // If findNode doesn't return null then the target was found
                if (temp != null) {
                    return temp; // Return node where target was found
                }
            }
            // If none of the children contained the target return null
            return null; //The target wasn't found
        }
    }

    /**
     * Tries to find a node that contains the target element and returns true
     * if so, false otherwise
     *
     * @param target the element being searched for.
     * @return a boolean representing whether or not the tree has a node
     * containing the target element.
     */
    @Override
    public boolean contains(T target) {
       MultiTreeNode<T> parentNode = findNode(target);
       return parentNode != null;
    }

    /**
     * Returns the size of the tree measured by the number of nodes in it.  
     * Calls the recursive countDown method to determine this.
     *
     * @return the size of the tree.
     */
    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return countDown(root);
    }

    /**
     * Recursively determines the number of nodes in the tree.
     * @param node the node currently being examined.
     * @return the amount of nodes from the starting node down.
     */
    private int countDown(MultiTreeNode<T> node) {
       int count = 1;
       if (node == null)
           return 0;
       else{
        for (MultiTreeNode<T> child : node.getChildren()) {
                count += countDown(child);
        }
                return count;    
       }
    }

    /**
     * String representation of a LinkedTree.
     * 
     * @return a series of Strings illustrating the elements in each level of
     * the tree.
     */
    @Override
    public String toString() {
        String print = "Linked Tree: \nLayer 1 (Root): " + root.getElement();
        ArrayList<MultiTreeNode<T>> layer = root.getChildren();
        ArrayList<MultiTreeNode<T>> nextLayer;
        int layerNum = 2;
        while (!layer.isEmpty()) {
            print += "\nLayer " + layerNum + ":";
            nextLayer = new ArrayList<>();
            for (MultiTreeNode<T> node : layer) {
                nextLayer.addAll(node.getChildren());
                print += " " + node;
            }
            layer = nextLayer;
            layerNum++;
        }
        return print;
    }
}
