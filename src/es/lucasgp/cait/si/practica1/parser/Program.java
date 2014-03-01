package es.lucasgp.cait.si.practica1.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Program {

    private final Set<Variable> variables = new TreeSet<>();
    private final List<Sentence> sentences = new ArrayList<>();
    private final Map<Variable, List<Sentence>> sentencesByVariable = new HashMap<>();

    public Program(String path) {
        loadProgram(path);
    }

    private void loadProgram(String programPath) {

        System.out.println("------ Loading program ------");

        try (Scanner scanner = new Scanner(new File(programPath))) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] sentenceSides = line.replace(".", "").replace(" ", "").split("<-");

                List<Variable> conditionVars = new ArrayList<>();
                if (sentenceSides.length > 1) {
                    for (String varName : sentenceSides[1].split(",")) {
                        conditionVars.add(new Variable(varName));
                    }
                }

                Sentence sentence = new Sentence(new Variable(sentenceSides[0]), new Condition(conditionVars));
                add(sentence);

                System.out.println(sentence);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("---- Program loaded ----\n");
    }

    private void add(Sentence sentence) {
        sentences.add(sentence);
        variables.add(sentence.ls);
        variables.addAll(sentence.rs.vars);
        List<Sentence> variableSentences = sentencesByVariable.get(sentence.ls);
        if (variableSentences == null) {
            variableSentences = new ArrayList<>();
            sentencesByVariable.put(sentence.ls, variableSentences);
        }
        variableSentences.add(sentence);
    }

    public boolean isEmpty() {
        return sentences.isEmpty();
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public List<Sentence> getSentences(Variable var) {
        List<Sentence> sentences = sentencesByVariable.get(var);
        return sentences != null ? sentences : Collections.<Sentence>emptyList();
    }
}
