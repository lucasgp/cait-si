package es.lucasgp.cait.si.practica1.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program extends ArrayList<Sentence> {

    private static final long serialVersionUID = 1L;

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
}
