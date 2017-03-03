package programa;

import procesamientos.Procesamiento;

public abstract class Programa {

    private final Tipo TENT;
    private final Tipo TBOOL;
    private final Tipo TOK;
    private final Tipo TERROR;
    //nuevos tipos para practica 1
    private final Tipo TREAL;
    private final Tipo TSTRING;
    private final Tipo TCHAR;
    private final Tipo TUNKNOWN;

    public Programa() {
        TENT = new Int();
        TBOOL = new Bool();
        TOK = new Ok();
        TERROR = new Error();
        TREAL = new Real();
        TSTRING = new Stringg();
        TCHAR = new Char();
        TUNKNOWN = new Unknown();
    }

    public interface Tipo {

        void acepta(Procesamiento p);
    }

    public class Int implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "INT";
        }
    }

    public class Bool implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "BOOL";
        }
    }

    public class Ok implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "OK";
        }
    }

    public class Error implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "ERROR";
        }
    }

    //declaracion de tipos para variables practica 1
    public class Real implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "REAL";
        }
    }

    public class Stringg implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "STRING";
        }
    }

    public class Char implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "CHAR";
        }
    }

    public class Unknown implements Tipo {

        public void acepta(Procesamiento p) {
            p.procesa(this);
        }

        public String toString() {
            return "UNKNOWN";
        }
    }

    public class Prog {

        private Dec[] decs;
        private Inst i;
        private Tipo tipo;

        public Prog(Dec[] decs, Inst i) {
            this.decs = decs;
            this.i = i;
            this.tipo = null;
        }

        public Dec[] decs() {
            return decs;
        }

        public Inst inst() {
            return i;
        }

        public Tipo tipo() {
            return tipo;
        }

        public void ponTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public abstract class Dec {

        public abstract void procesaCon(Procesamiento p);
    }

    public class DecVar extends Dec {

        private String enlaceFuente;
        private String var;
        private Tipo tipoDec;
        private int dir;

        public DecVar(Tipo tipo, String var) {
            this(tipo, var, null);
        }

        public DecVar(Tipo tipo, String var, String enlaceFuente) {
            this.tipoDec = tipo;
            this.enlaceFuente = enlaceFuente;
            this.var = var;
        }

        public Tipo tipoDec() {
            return tipoDec;
        }

        public String var() {
            return var;
        }

        public int dir() {
            return dir;
        }

        public void ponDir(int dir) {
            this.dir = dir;
        }

        public String enlaceFuente() {
            return enlaceFuente;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public abstract class Inst {

        private Tipo tipo;

        public Inst() {
            tipo = null;
        }

        public Tipo tipo() {
            return tipo;
        }

        public void ponTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public abstract void procesaCon(Procesamiento p);
    }

    public class IAsig extends Inst {

        private String var;
        private Exp exp;
        private String enlaceFuente;
        private DecVar declaracion;

        public IAsig(String var, Exp exp, String enlaceFuente) {
            this.var = var;
            this.exp = exp;
            this.declaracion = null;
            this.enlaceFuente = enlaceFuente;
        }

        public IAsig(String var, Exp exp) {
            this(var, exp, null);
        }

        public String var() {
            return var;
        }

        public Exp exp() {
            return exp;
        }

        public DecVar declaracion() {
            return declaracion;
        }

        public String enlaceFuente() {
            return enlaceFuente;
        }

        public void ponDeclaracion(DecVar d) {
            declaracion = d;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class IBloque extends Inst {

        private Inst[] is;

        public IBloque(Inst[] is) {
            this.is = is;
        }

        public Inst[] is() {
            return is;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public abstract class Exp {

        private Tipo tipo;

        public Exp() {
            tipo = null;
        }

        public void ponTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public Tipo tipo() {
            return tipo;
        }

        public abstract void procesaCon(Procesamiento p);
    }

    public class Var extends Exp {

        private String var;
        private DecVar declaracion;
        private String enlaceFuente;

        public Var(String var) {
            this(var, null);
        }

        public Var(String var, String enlaceFuente) {
            this.var = var;
            declaracion = null;
            this.enlaceFuente = enlaceFuente;
        }

        public String var() {
            return var;
        }

        public DecVar declaracion() {
            return declaracion;
        }

        public void ponDeclaracion(DecVar dec) {
            declaracion = dec;
        }

        public String enlaceFuente() {
            return enlaceFuente;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class CteInt extends Exp {

        private int val;

        public CteInt(int val) {
            this.val = val;
        }

        public int valEntero() {
            return val;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class CteBool extends Exp {

        private boolean val;

        public CteBool(boolean val) {
            this.val = val;
        }

        public boolean valBool() {
            return val;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }
    // declaracion cte practica 1

    public class CteChar extends Exp {

        private char val;

        public CteChar(char val) {
            this.val = val;
        }

        public char valChar() {
            return val;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class CteReal extends Exp {

        private double val;

        public CteReal(double val) {
            this.val = val;
        }

        public double valChar() {
            return val;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }
    //*************************************************************************************************************

    public class CteUnknown extends Exp {

        private Unknown val;

        public CteUnknown(Unknown val) {
            this.val = val;
        }

        public Unknown valUnknown() {
            return val;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class CteStringg extends Exp {

        private String val;

        public CteStringg(String val) {
            this.val = val;
        }

        public String valChar() {
            return val;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    private abstract class ExpBin extends Exp {

        private Exp opnd1;
        private Exp opnd2;
        private String enlaceFuente;

        public ExpBin(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public ExpBin(Exp opnd1, Exp opnd2, String enlaceFuente) {
            this.enlaceFuente = enlaceFuente;
            this.opnd1 = opnd1;
            this.opnd2 = opnd2;
        }

        public Exp opnd1() {
            return opnd1;
        }

        public Exp opnd2() {
            return opnd2;
        }

        public String enlaceFuente() {
            return enlaceFuente;
        }
    }

    //expresion unaria cambio de signo practica 1
    private abstract class ExpUna extends Exp {

        private Exp opnd1;
        private String enlaceFuente;

        public ExpUna(Exp opnd1) {
            this(opnd1, null);
        }

        public ExpUna(Exp opnd1, String enlaceFuente) {
            this.opnd1 = opnd1;
            this.enlaceFuente = enlaceFuente;
        }

        public Exp opnd1() {
            return opnd1;
        }

        public String enlaceFuente() {
            return enlaceFuente;
        }
    }

    public class Suma extends ExpBin {

        public Suma(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Suma(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Resta extends ExpBin {

        public Resta(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Resta(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Multi extends ExpBin {

        public Multi(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Multi(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Div extends ExpBin {

        public Div(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Div(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Concat extends ExpBin {

        public Concat(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Concat(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class RestoEntero extends ExpBin {

        public RestoEntero(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public RestoEntero(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class CambiaSigno extends ExpUna {

        public CambiaSigno(Exp opnd1) {
            this(opnd1, null);
        }

        public CambiaSigno(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Elem extends ExpBin {

        public Elem(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Elem(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvInt extends ExpUna {

        public ConvInt(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvInt(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvIntToInt extends ExpUna {

        public ConvIntToInt(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvIntToInt(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvRealToInt extends ExpUna {

        public ConvRealToInt(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvRealToInt(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvBoolToInt extends ExpUna {

        public ConvBoolToInt(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvBoolToInt(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvCharToInt extends ExpUna {

        public ConvCharToInt(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvCharToInt(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvReal extends ExpUna {

        public ConvReal(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvReal(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvRealToReal extends ExpUna {

        public ConvRealToReal(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvRealToReal(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvIntToReal extends ExpUna {

        public ConvIntToReal(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvIntToReal(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvBoolToReal extends ExpUna {

        public ConvBoolToReal(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvBoolToReal(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvCharToReal extends ExpUna {

        public ConvCharToReal(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvCharToReal(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvChar extends ExpUna {

        public ConvChar(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvChar(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvBool extends ExpUna {

        public ConvBool(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvBool(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class ConvString extends ExpUna {

        public ConvString(Exp opnd1) {
            this(opnd1, null);
        }

        public ConvString(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class And extends ExpBin {

        public And(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public And(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Or extends ExpBin {

        public Or(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Or(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Not extends ExpUna {

        public Not(Exp opnd1) {
            this(opnd1, null);
        }

        public Not(Exp opnd1, String enlaceFuente) {
            super(opnd1, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    //*********************************************************************
    public class Mayor extends ExpBin {

        public Mayor(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Mayor(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorNum extends ExpBin {

        public MayorNum(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorNum(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorChar extends ExpBin {

        public MayorChar(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorChar(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorBool extends ExpBin {

        public MayorBool(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorBool(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorString extends ExpBin {

        public MayorString(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorString(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Menor extends ExpBin {

        public Menor(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Menor(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorNum extends ExpBin {

        public MenorNum(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorNum(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorChar extends ExpBin {

        public MenorChar(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorChar(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorBool extends ExpBin {

        public MenorBool(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorBool(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorString extends ExpBin {

        public MenorString(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorString(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Igual extends ExpBin {

        public Igual(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Igual(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class IgualNum extends ExpBin {

        public IgualNum(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public IgualNum(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class IgualChar extends ExpBin {

        public IgualChar(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public IgualChar(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class IgualBool extends ExpBin {

        public IgualBool(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public IgualBool(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class IgualString extends ExpBin {

        public IgualString(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public IgualString(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorIgual extends ExpBin {

        public MayorIgual(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorIgual(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorIgualNum extends ExpBin {

        public MayorIgualNum(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorIgualNum(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorIgualChar extends ExpBin {

        public MayorIgualChar(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorIgualChar(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorIgualBool extends ExpBin {

        public MayorIgualBool(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorIgualBool(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MayorIgualString extends ExpBin {

        public MayorIgualString(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MayorIgualString(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorIgual extends ExpBin {

        public MenorIgual(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorIgual(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorIgualNum extends ExpBin {

        public MenorIgualNum(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorIgualNum(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorIgualChar extends ExpBin {

        public MenorIgualChar(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorIgualChar(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorIgualBool extends ExpBin {

        public MenorIgualBool(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorIgualBool(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class MenorIgualString extends ExpBin {

        public MenorIgualString(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public MenorIgualString(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class Distinto extends ExpBin {

        public Distinto(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public Distinto(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class DistintoNum extends ExpBin {

        public DistintoNum(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public DistintoNum(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class DistintoChar extends ExpBin {

        public DistintoChar(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public DistintoChar(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class DistintoBool extends ExpBin {

        public DistintoBool(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public DistintoBool(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public class DistintoString extends ExpBin {

        public DistintoString(Exp opnd1, Exp opnd2) {
            this(opnd1, opnd2, null);
        }

        public DistintoString(Exp opnd1, Exp opnd2, String enlaceFuente) {
            super(opnd1, opnd2, enlaceFuente);
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }

    public Prog prog(Dec[] decs, Inst i) {
        return new Prog(decs, i);
    }

    public Dec decvar(Tipo t, String v) {
        return new DecVar(t, v);
    }

    public Dec decvar(Tipo t, String v, String enlaceFuente) {
        return new DecVar(t, v, enlaceFuente);
    }

    public Inst iasig(String v, Exp e) {
        return new IAsig(v, e);
    }

    public Inst iasig(String v, Exp e, String enlaceFuente) {
        return new IAsig(v, e, enlaceFuente);
    }

    public Inst ibloque(Inst[] is) {
        return new IBloque(is);
    }

    public Exp var(String id) {
        return new Var(id);
    }

    public Exp var(String id, String enlaceFuente) {
        return new Var(id, enlaceFuente);
    }

    public Exp cteint(int val) {
        return new CteInt(val);
    }

    public Exp ctebool(boolean val) {
        return new CteBool(val);
    }

    public Exp suma(Exp exp1, Exp exp2) {
        return new Suma(exp1, exp2);
    }

    public Exp and(Exp exp1, Exp exp2) {
        return new And(exp1, exp2);
    }

    public Exp suma(Exp exp1, Exp exp2, String enlaceFuente) {
        return new Suma(exp1, exp2, enlaceFuente);
    }

    public Exp and(Exp exp1, Exp exp2, String enlaceFuente) {
        return new And(exp1, exp2, enlaceFuente);
    }

    public Tipo tipoInt() {
        return TENT;
    }

    public Tipo tipoBool() {
        return TBOOL;
    }

    public Tipo tipoOk() {
        return TOK;
    }

    public Tipo tipoError() {
        return TERROR;
    }

    public Tipo tipoChar() {
        return TCHAR;
    }

    public Tipo tipoReal() {
        return TREAL;
    }

    public Tipo tipoStringg() {
        return TSTRING;
    }

    public Tipo tipoUnknown() {
        return TUNKNOWN;
    }
    public abstract Prog raiz();

}
