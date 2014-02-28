package es.lucasgp.cait.si.practica1.parser;

import java.util.Collection;


public class Sentence {

    public final Variable ls;
    public final Condition rs;

    public Sentence(Variable ls) {
        this(ls, new Condition());
    }

    public Sentence(Variable ls, Condition rs) {
        super();
        this.ls = ls;
        this.rs = rs;
    }

    @Override
    public String toString() {

        String result = "";
        if (rs.isEmpty())
            result = ls + ".";
        else
            result = ls + "<-" + rs + ".";
        return result;
    }

    public Variable eval(Collection<Variable> actual) {
        return rs.eval(actual) ? ls : null;
    }
}
