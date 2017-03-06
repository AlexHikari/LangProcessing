package procesamientos.comprobaciontipos;

import errores.Errores;
import java.util.HashMap;
import java.util.Map;
import procesamientos.Procesamiento;
import programa.Programa.Dec;
import programa.Programa.DecVar;
import programa.Programa;
import programa.Programa.Var;
import programa.Programa.Suma;
import programa.Programa.Prog;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.And;
import programa.Programa.Resta;
import programa.Programa.Multi;
import programa.Programa.Div;
import programa.Programa.Concat;
import programa.Programa.RestoEntero;
import programa.Programa.CambiaSigno;
import programa.Programa.Elem;
import programa.Programa.ConvInt;
import programa.Programa.ConvReal;
import programa.Programa.ConvChar;
import programa.Programa.ConvBool;
import programa.Programa.ConvString;
import programa.Programa.Or;
import programa.Programa.Not;
import programa.Programa.Mayor;
import programa.Programa.Menor;
import programa.Programa.Igual;
import programa.Programa.MayorIgual;
import programa.Programa.MenorIgual;
import programa.Programa.Distinto;
import programa.Programa.ILee;
import programa.Programa.IEscribe;
import programa.Programa.IIfThen;
import programa.Programa.IIfThenElse;
import programa.Programa.IWhile;

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
   public void procesa(ILee i){
    DecVar decVar = tablaDeSimbolos.get(i.var());
    if(decVar == null){
        error = true; 
        errores.msg(i.enlaceFuente()+":"+ERROR_ID_NO_DECLARADO+"("+i.var()+")");
    }
    else{
        i.ponDeclaracion(decVar);
    }
   }
   
   public void procesa(IEscribe i){
        i.exp().procesaCon(this);
   }
   
   public void procesa(IBloque b) {
     for (Programa.Inst i: b.is())
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
   public void procesa(Resta exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Multi exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Div exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Concat exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(RestoEntero exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(CambiaSigno exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(Elem exp) {
   exp.opnd1().procesaCon(this);
   exp.opnd2().procesaCon(this);
   }
   public void procesa(ConvInt exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvReal exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvChar exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvBool exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvString exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(Or exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Not exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(Mayor exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Menor exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Igual exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorIgual exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorIgual exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Distinto exp) {
     exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   
   public void procesa(IWhile i) {
     i.exp().procesaCon(this);
     i.cuerpo().procesaCon(this);
   }
   public void procesa(IIfThen i) {
     i.exp().procesaCon(this);
     i.cuerpo().procesaCon(this);
   } 
   public void procesa(IIfThenElse i) {
     i.exp().procesaCon(this);
     i.cuerpo0().procesaCon(this);
     i.cuerpo1().procesaCon(this);
   } 
}
