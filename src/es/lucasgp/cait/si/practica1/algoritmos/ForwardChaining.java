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

        Program program = new Program(args[0]);
        Set<Variable> result = new TreeSet<>();

        System.out.println(String.format("------ Looking for asignations ------"));

        for (Iterator<Sentence> programIterator = program.iterator(); programIterator.hasNext();) {
            Sentence sentence = programIterator.next();
            Variable var = sentence.eval(Collections.<Variable> emptyList());
            if (var != null) {
                result.add(var);
                programIterator.remove();
                System.out.println(String.format("Found %s", sentence));
            }
        }

        System.out.println(String.format("------ Asignations ------\n%s", result));

        int iter = 0;
        Collection<Variable> previous = null;
        do {

            previous = new ArrayList<>(result);

            System.out.println(String.format("------ Removing variables already in the result ------"));

            for (Iterator<Sentence> sentenceIt = program.iterator(); sentenceIt.hasNext();) {

                Sentence sentence = sentenceIt.next();

                if (result.contains(sentence.ls)) {
                    sentenceIt.remove();
                    System.out.println(sentence + " deleted");
                } else if (!sentence.rs.isEmpty()) {
                    for (Iterator<Variable> varIt = sentence.rs.vars.iterator(); varIt.hasNext();) {
                        Variable variable = varIt.next();
                        if (result.contains(variable)) {
                            System.out.println(variable + " removed from " + sentence);
                            varIt.remove();
                        }
                    }
                }
            }

            System.out.println(String.format("------ Execution %d ------", ++iter));

            for (Sentence sentence : program) {

                System.out.println("Sentence: " + sentence);

                Variable sentenceResult = sentence.eval(result);

                if (sentenceResult != null)
                    result.add(sentenceResult);

                System.out.println(String.format("Execution %d result: %s", iter, result));
            }

            System.out.println(String.format("---- Finished execution %d ----\n", iter));

        } while (!program.isEmpty() && !previous.containsAll(result));

        System.out.println(String.format("\nFinal result: %s\n", result));

    }

}
