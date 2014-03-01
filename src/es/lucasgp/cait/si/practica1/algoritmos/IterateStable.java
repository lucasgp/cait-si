package es.lucasgp.cait.si.practica1.algoritmos;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.TreeSet;

import es.lucasgp.cait.si.practica1.parser.Program;
import es.lucasgp.cait.si.practica1.parser.Sentence;
import es.lucasgp.cait.si.practica1.parser.Variable;

public class IterateStable {

    public static void main(String... args) throws FileNotFoundException {

        Program program = new Program(args[0]);
        Set<Variable> previous = new TreeSet<>();
        Set<Variable> actual = new TreeSet<>();

        int iter = 0;
        do {

            System.out.println(String.format("------ Execution %d ------", ++iter));

            previous.addAll(actual);

            for (Sentence sentence : program.getSentences()) {

                System.out.println("Sentence: " + sentence);

                if (sentence.eval(actual)) {
                    actual.add(sentence.ls);
                }

                System.out.println(String.format("Execution %d result: %s", iter, actual));
            }

            System.out.println(String.format("---- Finished execution %d ----", iter));

        } while (!previous.containsAll(actual));

        System.out.println(String.format("\nFinal result: %s\n", actual));

    }
}
