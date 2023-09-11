package pyramid_scheme;

import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author pmele
 * @version 3/28/2019
 */
public class PyramidSchemeSim {

    /**
     * Main method for running the simulation.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Person conMan = new Person("Brynjolf");
        MultiTreeNode<Person> conArtist = new MultiTreeNode<>(conMan);
        PyramidScheme myPyramid = new PyramidScheme(conArtist, 100.00, 0.25);

        Person[] anons = new Person[30000];
        for (int i = 0; i < 30000; i++) {
            anons[i] = new Person("Anon " + i);
        }

        ArrayList<Person> layerReps = new ArrayList<>();
        layerReps.add(conMan);

        ArrayList<Person> people = new ArrayList<>();
        people.add(conMan);
        int currAnon = 0;
        for (int i = 0; i < 6; i++) {
            ArrayList<Person> recruits = new ArrayList<>();
            for (Person recruiter : people) {
                int numRecruits = (int)(Math.random() * 5) + 1;
                for (int n = 0; n < numRecruits; n++) {
                    try {
                        myPyramid.addChild(recruiter, anons[currAnon]);
                    } catch (ElementNotFoundException ex) {
                        //
                    }
                    recruits.add(anons[currAnon]);
                    currAnon++;
                }
            }
            people = recruits;
            layerReps.add(recruits.get(0));
        }
        System.out.println("Size of Pyramid Scheme: " + myPyramid.size());
        System.out.println("Recruits on last layer: " + (people.size()));
        int layer = 1;
        for (Person rep : layerReps) {
            System.out.println("Layer " + layer + ": $" + rep.getBalance());
            layer++;
        }

        myPyramid.initiateCollapse();

        layer = 1;
        for (Person rep : layerReps) {
            System.out.println("Layer " + layer + ": $" + rep.getBalance());
            layer++;
        }

        System.out.println("Size of Pyramid Scheme: " + myPyramid.size());
    }
}
