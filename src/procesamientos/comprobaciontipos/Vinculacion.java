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
import programa.Programa.ConvIntToInt;
import programa.Programa.ConvIntToReal;
import programa.Programa.ConvBoolToInt;
import programa.Programa.ConvCharToInt;
import programa.Programa.ConvReal;
import programa.Programa.ConvRealToInt;
import programa.Programa.ConvRealToReal;
import programa.Programa.ConvBoolToReal;
import programa.Programa.ConvCharToReal;
import programa.Programa.ConvChar;
import programa.Programa.ConvBool;
import programa.Programa.ConvString;
import programa.Programa.Or;
import programa.Programa.Not;
import programa.Programa.Mayor;
import programa.Programa.MayorBool;
import programa.Programa.MayorChar;
import programa.Programa.MayorNum;
import programa.Programa.MayorString;
import programa.Programa.Menor;
import programa.Programa.MenorBool;
import programa.Programa.MenorChar;
import programa.Programa.MenorNum;
import programa.Programa.MenorString;
import programa.Programa.Igual;
import programa.Programa.IgualBool;
import programa.Programa.IgualChar;
import programa.Programa.IgualNum;
import programa.Programa.IgualString;
import programa.Programa.MayorIgual;
import programa.Programa.MayorIgualBool;
import programa.Programa.MayorIgualChar;
import programa.Programa.MayorIgualNum;
import programa.Programa.MayorIgualString;
import programa.Programa.MenorIgual;
import programa.Programa.MenorIgualBool;
import programa.Programa.MenorIgualChar;
import programa.Programa.MenorIgualNum;
import programa.Programa.MenorIgualString;
import programa.Programa.Distinto;
import programa.Programa.DistintoBool;
import programa.Programa.DistintoChar;
import programa.Programa.DistintoNum;
import programa.Programa.DistintoString;

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
   public void procesa(ConvIntToInt exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvRealToInt exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvBoolToInt exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvCharToInt exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvReal exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvRealToReal exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvIntToReal exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvBoolToReal exp) {
   exp.opnd1().procesaCon(this);
   }
   public void procesa(ConvCharToReal exp) {
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
   public void procesa(MayorNum exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorBool exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorChar exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorString exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Menor exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   
   public void procesa(MenorNum exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorBool exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorChar exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorString exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Igual exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(IgualNum exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(IgualBool exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(IgualChar exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(IgualString exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorIgual exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorIgualNum exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorIgualBool exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorIgualChar exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MayorIgualString exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorIgual exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorIgualNum exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorIgualBool exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorIgualChar exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(MenorIgualString exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(Distinto exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   
   public void procesa(DistintoNum exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(DistintoBool exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(DistintoChar exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
   public void procesa(DistintoString exp) {
   exp.opnd1().procesaCon(this);
     exp.opnd2().procesaCon(this);
   }
}
