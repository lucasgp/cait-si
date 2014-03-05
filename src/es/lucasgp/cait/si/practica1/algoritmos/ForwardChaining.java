package es.lucasgp.cait.si.practica1.algoritmos;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import es.lucasgp.cait.si.practica1.parser.Program;
import es.lucasgp.cait.si.practica1.parser.Sentence;
import es.lucasgp.cait.si.practica1.parser.Variable;

public class ForwardChaining {

    public static void main(String... args) throws FileNotFoundException {

        Program program = new Program("programs/programaOrTypes");
        Set<Variable> result = new TreeSet<>();

        System.out.println(String.format("------ Looking for assignments ------"));

        for (Iterator<Sentence> programIterator = program.getSentences().iterator(); programIterator.hasNext();) {
            Sentence sentence = programIterator.next();
            if (sentence.eval(Collections.<Variable> emptyList())) {
                result.add(sentence.ls);
                programIterator.remove();
            }
        }

        System.out.println(String.format("------ Assignments %s ------\n", result));

        int iter = 0;
        Collection<Variable> previous = null;
        do {

            previous = new ArrayList<>(result);

            System.out.println(String.format("------ Removing variables already in the result ------"));

            for (Iterator<Sentence> sentenceIt = program.getSentences().iterator(); sentenceIt.hasNext();) {

                Sentence sentence = sentenceIt.next();

                if (result.contains(sentence.ls)) {
                    sentenceIt.remove();
                    System.out.println("\t" + sentence + " deleted");
                } else if (!sentence.rs.isEmpty()) {
                    for (Iterator<Variable> varIt = sentence.rs.vars.iterator(); varIt.hasNext();) {
                        Variable variable = varIt.next();
                        if (result.contains(variable)) {
                            System.out.println("\t" + variable + " removed from " + sentence);
                            varIt.remove();
                        }
                    }
                }
            }

            System.out.println(String.format("------ Execution %d ------", ++iter));

            for (Sentence sentence : program.getSentences()) {

                if (sentence.eval(result)) {
                    result.add(sentence.ls);
                }

                System.out.println(String.format("\t%s\t%s", sentence, result));
            }

            System.out.println(String.format("---- Finished execution %d %s ----\n", iter, result));

        } while (!program.isEmpty() && !previous.containsAll(result));

        System.out.println(String.format("\nFinal result: %s\n", result));

    }

}
