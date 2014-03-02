package es.lucasgp.cait.si.practica1.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Condition {

    public final List<Variable> vars = new ArrayList<Variable>();

    public Condition() {
        this(Collections.<Variable> emptyList());
    }

    public Condition(List<Variable> vars) {
        this.vars.addAll(vars);
    }

    public boolean eval(Collection<Variable> actual) {

        boolean result = true;

        for (Variable var : vars) {
            result &= actual.contains(var);
        }

        return result;

    }

    public boolean isEmpty() {
        return vars.isEmpty();
    }

    public String toString() {
        String result = "";
        for (Variable var : vars) {
            result += var + ",";
        }
        return result.length() == 0 ? result : result.substring(0, result.length() - 1);
    }
}
