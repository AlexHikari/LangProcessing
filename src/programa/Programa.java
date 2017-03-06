package programa;

import procesamientos.Procesamiento;

public abstract class Programa {

    private final Tipo TENT;
    private final Tipo TBOOL;
    private final Tipo TOK;
    private final Tipo TERROR;
    private final Tipo TREAL;
    private final Tipo TSTRING;
    private final Tipo TCHAR;


    public Programa() {
        TENT = new Int();
        TBOOL = new Bool();
        TOK = new Ok();
        TERROR = new Error();
        TREAL = new Real();
        TSTRING = new Stringg();
        TCHAR = new Char();
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
        private int dirPrimeraInstruccion;
        private int dirInstruccionSiguiente;

        public Inst() {
            tipo = null;
        }

        public Tipo tipo() {
            return tipo;
        }

        public void ponTipo(Tipo tipo) {
            this.tipo = tipo;
        }
        
        public void ponDirPrimeraInstruccion(int dir){
            dirPrimeraInstruccion = dir;
        }

        public void ponDirInstruccionSiguiente(int dir){
            dirInstruccionSiguiente = dir;
        }
        
        public int dirPrimeraInstruccion(){ return dirPrimeraInstruccion;}
        public int dirInstruccionSiguiente() {return dirInstruccionSiguiente;}
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
    
    public class ILee extends Inst{
        
        private String var;
        private String enlaceFuente;
        private DecVar declaracion;
        public ILee(String var, String enlaceFuente){
            this.var = var;
            this.enlaceFuente = enlaceFuente;
            this.declaracion = null;  
        }
        public ILee(String var){
            this(var,null);
        }
        public String var() {
            return var;
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
    
    public class IEscribe extends Inst{

        private String enlaceFuente;
        private Exp exp;
        
        public IEscribe(Exp exp,String enlaceFuente){
            this.exp = exp;
            this.enlaceFuente = enlaceFuente;
        }
        public IEscribe(Exp exp){
            this(exp, null);
        }
        
        public Exp exp(){
            return exp;
        }
        public String enlaceFuente() {
            return enlaceFuente;
        }

        public void procesaCon(Procesamiento p) {
           p.procesa(this); 
        }
    
    
    
    }
    
       public class IWhile extends Inst {
       private Exp exp;
       private Inst cuerpo;
       private String enlaceFuente;
       public IWhile(Exp exp, Inst cuerpo) {
          this(exp,cuerpo,null); 
       }
       public IWhile(Exp exp, Inst cuerpo, String enlaceFuente) {
          this.exp = exp;
          this.cuerpo = cuerpo;
          this.enlaceFuente = enlaceFuente;
       }
       public Exp exp() {return exp;}
       public Inst cuerpo() {return cuerpo;}
       public String enlaceFuente() {return enlaceFuente;}
      
       public void procesaCon(Procesamiento p) {
         p.procesa(this);
       }
   }
       
       public class IIfThen extends Inst {
       
       private Exp exp;
       private Inst cuerpo;
       private String enlaceFuente;
       public IIfThen(Exp exp, Inst cuerpo) {
           this(exp,cuerpo,null);
       }
       public IIfThen(Exp exp, Inst cuerpo, String enlaceFuente){
            this.exp = exp;
            this.cuerpo = cuerpo;
            this.enlaceFuente = enlaceFuente;
       }
       public Exp exp() {return exp;}
       public Inst cuerpo() {return cuerpo;}
       public String enlaceFuente() {return enlaceFuente;}
      
       public void procesaCon(Procesamiento p) {
         p.procesa(this);
       }
       
       }
       public class IIfThenElse extends Inst {
       
       private Exp exp;
       private Inst cuerpo0;
       private Inst cuerpo1;
       private String enlaceFuente;
       private int dirInstElse;
       public IIfThenElse(Exp exp, Inst cuerpo0, Inst cuerpo1) {
           this(exp,cuerpo0,cuerpo1,null);
       }
       public IIfThenElse(Exp exp, Inst cuerpo0, Inst cuerpo1, String enlaceFuente){
            this.exp = exp;
            this.cuerpo0 = cuerpo0;
            this.cuerpo1 = cuerpo1;
            this.enlaceFuente = enlaceFuente;
       }
       public Exp exp() {return exp;}
       public Inst cuerpo0() {return cuerpo0;}
       public Inst cuerpo1() {return cuerpo1;}
       public int dirInstElse(){return dirInstElse;}
       public void ponDireccionElse(int dir){ dirInstElse = dir;}
       public String enlaceFuente() {return enlaceFuente;}
      
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
        private int dirPrimeraInstruccion;
        private int dirInstruccionSiguiente;

        public Exp() {
            tipo = null;
        }

        public void ponTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public Tipo tipo() {
            return tipo;
        }
         public int dirPrimeraInstruccion() {
         return dirPrimeraInstruccion;
        }
        public void ponDirPrimeraInstruccion(int dir) {
           dirPrimeraInstruccion = dir;
        }
        public int dirInstruccionSiguiente() {
            return dirInstruccionSiguiente;
        }
        public void ponDirInstruccionSiguiente(int dir) {
           dirInstruccionSiguiente = dir;
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

        public double valReal() {
            return val;
        }

        public void procesaCon(Procesamiento p) {
            p.procesa(this);
        }
    }
    
    public class CteString extends Exp {

        private String val;

        public CteString(String val) {
            this.val = val;
        }

        public String valString() {
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
    
    public Inst ilee(String v){
        return new ILee(v);
    }
    
    public Inst ilee(String v,String enlaceFuente){
        return new ILee(v,enlaceFuente);
    }

    public Inst iescribe(Exp e){
        return new IEscribe(e);
    }
    
    public Inst iescribe(Exp e, String enlaceFuente){
        return new IEscribe(e,enlaceFuente);
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
    
    public Exp ctereal(double val){
        return new CteReal(val);
    }
    
    public Exp ctechar(char val){
        return new CteChar(val);
    }
    
    public Exp ctestring(String val){
        return new CteString(val);
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
    
    public Exp resta(Exp exp1, Exp exp2){
        return new Resta(exp1, exp2);
    }
    
    public Exp resta(Exp exp1, Exp exp2,String enlaceFuente){
        return new Resta(exp1, exp2, enlaceFuente);
    }

    public Exp multiplica(Exp exp1, Exp exp2,String enlaceFuente){
        return new Multi(exp1,exp2,enlaceFuente);
    }
    
    public Exp multiplica(Exp exp1, Exp exp2){
        return new Multi(exp1,exp2);
    }
    
    public Exp divide(Exp exp1, Exp exp2){
        return new Div(exp1, exp2);
    }
    
    public Exp divide(Exp exp1, Exp exp2, String enlaceFuente){
        return new Div(exp1, exp2, enlaceFuente);
    }
    
    public Exp concatena (Exp exp1, Exp exp2, String enlaceFuente){
        return new Concat(exp1, exp2, enlaceFuente);
    }
     public Exp concatena (Exp exp1, Exp exp2){
        return new Concat(exp1, exp2);
    }
     
     public Exp restoentero (Exp exp1, Exp exp2, String enlaceFuente){
        return new RestoEntero(exp1, exp2, enlaceFuente);
    }
     public Exp restoentero (Exp exp1, Exp exp2){
        return new RestoEntero(exp1, exp2);
    }
     
     public Exp cambiasigno (Exp exp1, String enlaceFuente){
        return new CambiaSigno(exp1, enlaceFuente);
    }
     public Exp cambiasigno (Exp exp1){
        return new CambiaSigno(exp1);
    }
     
     public Exp elem (Exp exp1, Exp exp2, String enlaceFuente){
        return new Elem(exp1, exp2, enlaceFuente);
    }
     public Exp elem (Exp exp1, Exp exp2){
        return new Elem(exp1, exp2);
    }
     public Exp convint (Exp exp1,String enlaceFuente){
        return new ConvInt(exp1, enlaceFuente);
    }
     public Exp convint (Exp exp1){
        return new ConvInt(exp1);
    }
     
     public Exp convreal (Exp exp1, String enlaceFuente){
        return new ConvReal(exp1, enlaceFuente);
    }
     public Exp convreal (Exp exp1){
        return new ConvReal(exp1);
    }
     
     public Exp convchar (Exp exp1, Exp exp2, String enlaceFuente){
        return new ConvChar(exp1, enlaceFuente);
    }
     public Exp convchar (Exp exp1){
        return new ConvChar(exp1);
    }
     
     public Exp convbool(Exp exp1, String enlaceFuente){
        return new ConvBool(exp1, enlaceFuente);
    }
     public Exp convbool (Exp exp1){
        return new ConvBool(exp1);
    }
     
     public Exp convstring (Exp exp1, String enlaceFuente){
        return new ConvString(exp1,  enlaceFuente);
    }
     public Exp convstring (Exp exp1){
        return new ConvString(exp1);
    }
     
     public Exp And (Exp exp1, Exp exp2, String enlaceFuente){
        return new And(exp1, exp2, enlaceFuente);
    }
     public Exp And (Exp exp1, Exp exp2){
        return new And(exp1, exp2);
    }
     
     public Exp or (Exp exp1, Exp exp2, String enlaceFuente){
        return new Or(exp1, exp2, enlaceFuente);
    }
     public Exp or (Exp exp1, Exp exp2){
        return new Or(exp1, exp2);
    }
     
     public Exp not (Exp exp1, String enlaceFuente){
        return new Not(exp1, enlaceFuente);
    }
     public Exp not (Exp exp1){
        return new Not(exp1);
    }
     
     public Exp mayor (Exp exp1, Exp exp2, String enlaceFuente){
        return new Mayor(exp1, exp2, enlaceFuente);
    }
     public Exp mayor (Exp exp1, Exp exp2){
        return new Mayor(exp1, exp2);
    }
     
     public Exp menor (Exp exp1, Exp exp2, String enlaceFuente){
        return new Menor(exp1, exp2, enlaceFuente);
    }
     public Exp menor (Exp exp1, Exp exp2){
        return new Menor(exp1, exp2);
    }
     
     public Exp mayorigual (Exp exp1, Exp exp2, String enlaceFuente){
        return new MayorIgual(exp1, exp2, enlaceFuente);
    }
     public Exp mayorigual (Exp exp1, Exp exp2){
        return new MayorIgual(exp1, exp2);
    }
     
     public Exp menorigual (Exp exp1, Exp exp2, String enlaceFuente){
        return new MenorIgual(exp1, exp2, enlaceFuente);
    }
     public Exp menorigual (Exp exp1, Exp exp2){
        return new MenorIgual(exp1, exp2);
    }
     public Exp igual (Exp exp1, Exp exp2, String enlaceFuente){
        return new Igual(exp1, exp2, enlaceFuente);
    }
     public Exp igual (Exp exp1, Exp exp2){
        return new Igual(exp1, exp2);
    }
     
     public Exp distinto (Exp exp1, Exp exp2, String enlaceFuente){
        return new Distinto(exp1, exp2, enlaceFuente);
    }
     public Exp distinto (Exp exp1, Exp exp2){
        return new Distinto(exp1, exp2);
    }
   public Inst iwhile(Exp exp, Inst cuerpo) {
      return new IWhile(exp,cuerpo);  
   }
   public Inst iwhile(Exp exp, Inst cuerpo, String enlaceFuente) {
      return new IWhile(exp,cuerpo,enlaceFuente);  
   }
   public Inst iifthen(Exp exp, Inst cuerpo) {
      return new IIfThen(exp,cuerpo);  
   }
   public Inst iifthen(Exp exp, Inst cuerpo, String enlaceFuente) {
      return new IIfThen(exp,cuerpo,enlaceFuente);  
   }
   public Inst iifthenelse(Exp exp, Inst cuerpoif,Inst cuerpoelse) {
      return new IIfThenElse(exp,cuerpoif,cuerpoelse);  
   }
   public Inst iifthenelse(Exp exp, Inst cuerpoif,Inst cuerpoelse, String enlaceFuente) {
      return new IIfThenElse(exp,cuerpoif,cuerpoelse,enlaceFuente);  
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

    public Tipo tipoString() {
        return TSTRING;
    }
    
    public abstract Prog raiz();

}
