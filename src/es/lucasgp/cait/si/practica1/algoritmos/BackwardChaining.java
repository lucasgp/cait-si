package es.lucasgp.cait.si.practica1.algoritmos;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import es.lucasgp.cait.si.practica1.parser.Program;
import es.lucasgp.cait.si.practica1.parser.Sentence;
import es.lucasgp.cait.si.practica1.parser.Variable;

public class BackwardChaining {

    private static Program program;

    public static void main(String... args) throws FileNotFoundException {

        program = new Program("programs/programaOrTypes");
        Set<Variable> result = new TreeSet<>();

        for (Variable variable : program.getVariables()) {
            result.addAll(eval(variable, new HashSet<Variable>(), 0));
        }

        System.out.println(String.format("\nFinal result: %s\n", result));

    }

    private static Collection<Variable> eval(Variable var, Collection<Variable> result, int deep) {

        deep++;

        System.out.println(String.format("%s------ Evaluating sentences for variable '%s'... ------", tabs(deep), var));

        for (Sentence sentence : program.getSentences(var)) {

            System.out.println(String.format("%sSentence: %s", tabs(deep), sentence));

            if (sentence.eval(result)) {
                result.add(sentence.ls);
            } else {

                for (Variable conditionVar : sentence.rs.vars) {
                    result.addAll(eval(conditionVar, result, deep));
                }

                if (sentence.eval(result)) {
                    result.add(sentence.ls);
                }

            }

            System.out.println(String.format("%sResult: %s", tabs(deep), result));

        }

        System.out.println(String.format("%s------ Evaluated sentences for '%s'. Result: %s ------\n", tabs(deep), var,
                result));

        return result;
    }

    private static String tabs(int deep) {
        String tabs = "";
        for (int i = 1; i < deep; i++) {
            tabs += "\t";
        }

        return tabs;
    }
}
