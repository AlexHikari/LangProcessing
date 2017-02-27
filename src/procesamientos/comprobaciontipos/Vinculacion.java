package procesamientos.comprobaciontipos;

import errores.Errores;
import java.util.HashMap;
import java.util.Map;
import procesamientos.Procesamiento;
import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.Suma;
import programa.Programa.Prog;
import programa.Programa.DecVar;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.And;
import programa.Programa.Int;
import programa.Programa.Bool;
import programa.Programa.Dec;
import programa.Programa.Error;
import programa.Programa.Inst;
import programa.Programa.Var;

public class Vinculacion extends Procesamiento {
   private final static String ERROR_ID_DUPLICADO="Identificador ya declarado";
   private final static String ERROR_ID_NO_DECLARADO="Identificador no declarado";
   private Map<String,DecVar> tablaDeSimbolos;
   private boolean error;
   private Errores errores;
   public Vinculacion(Errores errores) {
      tablaDeSimbolos = new HashMap<>();
      this.errores = errores;
      error = false;
   }
   public void procesa(Prog p) {
     for (Dec d: p.decs())
         d.procesaCon(this);
     p.inst().procesaCon(this);
   }     
   public void procesa(DecVar d) {
     if(tablaDeSimbolos.containsKey(d.var())) {
       error = true;
       errores.msg(d.enlaceFuente()+":"+ERROR_ID_DUPLICADO+"("+d.var()+")");
     }
     else {
       tablaDeSimbolos.put(d.var(), d);
     }
   }     
   public void procesa(IAsig i) {
     DecVar decVar = tablaDeSimbolos.get(i.var());
     if (decVar == null) {
        error = true; 
        errores.msg(i.enlaceFuente()+":"+ERROR_ID_NO_DECLARADO+"("+i.var()+")");
     }
     else {
        i.ponDeclaracion(decVar); 
     }
     i.exp().procesaCon(this);
   }     
   public void procesa(IBloque b) {
     for (Inst i: b.is())
         i.procesaCon(this);
   }    
   public boolean error() {
      return error; 
   }
   public void procesa(And exp) {
     exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Suma exp) {
     exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Var exp) {
     DecVar decVar = tablaDeSimbolos.get(exp.var());
     if (decVar == null) {
        error = true; 
        errores.msg(exp.enlaceFuente()+":"+ERROR_ID_NO_DECLARADO+"("+exp.var()+")");
     }
     else {
        exp.ponDeclaracion(decVar); 
     }
       
   } 

}
