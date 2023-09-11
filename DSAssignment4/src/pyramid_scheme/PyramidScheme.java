package pyramid_scheme;

import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
import java.util.ArrayList;

/**
 * A Pyramid Scheme is a particular form of LinkedTree<Person>. It has methods
 * for simulating a pyramid scheme scam.
 *
 * @author pmele
 * @version 3/28/2019
 */
public class PyramidScheme extends LinkedTree<Person> {

    private double recruitPrice;
    private double paidUp;

    /**
     * Initialized-root Default constructor (takes a Person).
     *
     * @param startElem the root of the scheme (Element)
     */
    PyramidScheme(Person startElem) {
        super(startElem);
        recruitPrice = 25.00;
        paidUp = 0.10;
    }

    /**
     * Normal constructor (takes a Person).
     *
     * @param startElem the root of the scheme (Element)
     * @param rPrice Price to recruit in this scheme
     * @param pUp Fraction of earnings paid upwards in this scheme
     */
    PyramidScheme(Person startElem, double rPrice, double pUp) {
        super(startElem);
        recruitPrice = rPrice;
        paidUp = pUp;
        while (paidUp > 1) {
            paidUp *= .01; //This will convert percentages into decimals
        }
    }

    /**
     * Initialized-root Default constructor (takes a MultiTreeNode).
     *
     * @param startNode the root of the scheme (Node)
     */
    PyramidScheme(MultiTreeNode<Person> startNode) {
        super(startNode);
        recruitPrice = 25.00;
        paidUp = 0.10;
    }

    /**
     * Normal constructor (takes a MultiTreeNode).
     *
     * @param startNode the root of the scheme (Node)
     * @param rPrice Price to recruit in this scheme
     * @param pUp Fraction of earnings paid upwards in this scheme
     */
    PyramidScheme(MultiTreeNode<Person> startNode, double rPrice, double pUp) {
        super(startNode);
        recruitPrice = rPrice;
        paidUp = pUp;
        while (paidUp > 1) {
            paidUp *= .01; //This will convert percentages into decimals
        }
    }

    /**
     * Initiates the recursive findNodeChain method to return a list of all
     * nodes that are above the node containing the target. This overload takes
     * a Person.
     *
     * @param sucker the individual from whom others above are benefiting
     * @return an ArrayList of individuals higher up in the hierarchy
     */
    public ArrayList<MultiTreeNode<Person>> whoBenefits(Person sucker) {
        return findNodeChain(root, sucker);
    }

    /**
     * Initiates the recursive findNodeChain method to return a list of all
     * nodes that are above the node containing the target. This overload takes
     * a MultiTreeNode.
     *
     * @param sucker the individual from whom others above are benefitting
     * @return an ArrayList of individuals higher up in the hierarchy
     */
    public ArrayList<MultiTreeNode<Person>>
            whoBenefits(MultiTreeNode<Person> sucker) {
        return findNodeChain(root, sucker.getElement());
    }

    /**
     * Similar to findNode, but rather than returning the node that contains the
     * target, instead returns a list of nodes above the one that contains the
     * target, in low to high level order.
     *
     * @param node the current node being examined
     * @param target the Person being searched for
     * @return an ArrayList of nodes that are directly above the target in the
     * hierarchy
     */
    private ArrayList<MultiTreeNode<Person>>
            findNodeChain(MultiTreeNode<Person> node, Person target) {
        if (!this.contains(target)) {
            return null;
        }
        ArrayList<MultiTreeNode<Person>> ret = new ArrayList<MultiTreeNode<Person>>();
        if (!node.getElement().equals(target)) {
            ret.add(node);
            ArrayList<MultiTreeNode<Person>> list = node.getChildren();

            for (MultiTreeNode<Person> chap : list) {
                ret.addAll(0, findNodeChain(chap, target));
            }

        }

        return ret;

    }

    /**
     * An override of LinkedTree's addChild, which does the same thing, but
     * ensures the appropriate payment between the recruit and the recruiter, as
     * well as those above them in the hierarchy.
     *
     * @param recruiter the person who is recruiting the other into the scheme
     * @param recruit the person being brought into the scheme
     * @throws ElementNotFoundException
     */
    @Override
    public void addChild(Person recruiter, Person recruit)
            throws ElementNotFoundException {
        addChild(findNode(recruiter), recruit);
    }

    /**
     * An override of LinkedTree's addChild, which does the same thing, but
     * ensures the appropriate payment between the recruit and the recruiter, as
     * well as those above them in the hierarchy
     *
     * @param recruiter the person who is recruiting the other into the scheme
     * @param recruit the person being brought into the scheme
     */
    @Override
    public void addChild(MultiTreeNode<Person> recruiter, Person recruit) {
        super.addChild(recruiter, recruit);
        recruit.adjustBalance(-recruitPrice);
        payUp(recruiter, recruitPrice);
    }

    /**
     * Performs all the transactions inherent to the system wherein a percentage
     * of anything earned by an individual is passed upwards in the hierarchy,
     * such that each individual directly above gets an increasingly smaller
     * fraction of the money.
     *
     * @param moneyMaker the initial individual earning the money
     * @param moneyMade the initial money earned
     */
    private void payUp(MultiTreeNode<Person> moneyMaker, double moneyMade) {
        ArrayList<MultiTreeNode<Person>> owed = whoBenefits(moneyMaker);
        if (owed == null || owed.isEmpty()) { //Person at top gets paid all
            moneyMaker.getElement().adjustBalance(moneyMade);
            return;
        }
        //Initial money maker gets payed, with a portion lost to those above
        moneyMaker.getElement().adjustBalance((1.0 - paidUp) * moneyMade);
        //Each step above takes a share, but passes a portion of it up
        for (int i = 0; i < owed.size() - 1; i++) {
            moneyMade = paidUp * moneyMade;
            owed.get(i).getElement().adjustBalance((1.0 - paidUp) * moneyMade);
        }
        //Person at the very top gets his whole share
        owed.get(owed.size() - 1).getElement()
                .adjustBalance(paidUp * moneyMade);
    }

    /**
     * Initiates the collapse of the pyramid scheme
     */
    public void initiateCollapse() {
        collapse(root);
    }

    /**
     * A recursive method that determines the punishment received by different
     * individuals in the pyramid scheme after it is discovered and halted. Also
     * severs each node from its children, such that no hierarchy exists at all
     * when the collapse is complete.
     *
     * @param personNode the individual currently being examined
     * @return
     */
    private int collapse(MultiTreeNode<Person> personNode) {
        int underlings = 1;
        ArrayList<MultiTreeNode<Person>> toSever = new ArrayList<>();
        for (MultiTreeNode<Person> child : personNode.getChildren()) {
            underlings += collapse(child);
            toSever.add(child);
        }
        for (MultiTreeNode<Person> child : toSever) {
            personNode.removeChild(child);
        }
        if (personNode.getElement().getBalance() < 0) {
            System.out.println(personNode.getElement() + " is a victim "
                    + "and has been returned their money.");
            personNode.getElement()
                    .adjustBalance(-personNode.getElement().getBalance());
        } else if (personNode.getElement().getBalance() < 100) {
            System.out.println(personNode.getElement() + " has been "
                    + "appropriately warned, and fined $" + underlings * 5.0);
            personNode.getElement()
                    .adjustBalance(-underlings * 5.0);
        } else if (personNode.getElement().getBalance() < 1000) {
            System.out.println(personNode.getElement() + " has been charged "
                    + "with a misdeamenor, and fined $" + underlings * 5.0);
            personNode.getElement()
                    .adjustBalance(-underlings * 5.0);
        } else {
            System.out.println(personNode.getElement() + " has been charged "
                    + "with a felony, and fined $" + underlings * 5.0);
            personNode.getElement()
                    .adjustBalance(-(underlings * 5.0));
        }
        return underlings;
    }
}
