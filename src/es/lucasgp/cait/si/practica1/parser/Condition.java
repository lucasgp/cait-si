package es.lucasgp.cait.si.practica1.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Condition {

    enum Operation {
        AND, OR;
    }

    public final Operation op;
    public final List<Variable> vars = new ArrayList<Variable>();

    public Condition() {
        this(Collections.<Variable> emptyList());
    }

    public Condition(List<Variable> vars) {
        this(vars, Operation.AND);
    }

    public Condition(List<Variable> vars, Operation operation) {
        this.vars.addAll(vars);
        this.op = operation;
    }

    public boolean eval(Collection<Variable> actual) {

        boolean result = vars.isEmpty() || op == Operation.AND ? true : false;

        for (Variable var : vars) {
            switch (op) {
            case AND:
                result &= actual.contains(var);
                break;
            case OR:
                result |= actual.contains(var);
                break;
            default:
                throw new IllegalArgumentException("Operation not recognized");
            }
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
