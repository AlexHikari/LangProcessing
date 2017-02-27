package procesamientos.comprobaciontipos;

import errores.Errores;
import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.Suma;
import programa.Programa.And;
import programa.Programa.DecVar;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.Inst;
import programa.Programa.Prog;
import programa.Programa.Var;


public class ComprobacionTipos extends Procesamiento { 
   private final static String ERROR_TIPO_OPERANDOS="Los tipos de los operandos no son correctos";
   private final static String ERROR_ASIG="Tipos no compatibles en asignacion";
   private Programa programa;
   private Errores errores;
   public ComprobacionTipos(Programa programa, Errores errores) {
     this.programa = programa;  
     this.errores = errores;
   }

   public void procesa(Var exp) {
      exp.ponTipo(exp.declaracion().tipoDec());
   } 
   public void procesa(CteInt exp) {
      exp.ponTipo(programa.tipoInt());
   } 
   public void procesa(CteBool exp) {
      exp.ponTipo(programa.tipoBool());
   } 
   public void procesa(Suma exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoInt()) &&
         exp.opnd2().tipo().equals(programa.tipoInt())) {
         exp.ponTipo(programa.tipoInt()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   } 
   public void procesa(And exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoBool()) &&
         exp.opnd2().tipo().equals(programa.tipoBool())) {
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Prog p) {
     p.inst().procesaCon(this);
     p.ponTipo(p.inst().tipo());
   }     
   public void procesa(IAsig i) {
     i.exp().procesaCon(this);  
     if(! i.declaracion().tipoDec().equals(i.exp().tipo())) {
         if (! i.exp().tipo().equals(programa.tipoError()))
             errores.msg(i.enlaceFuente()+":"+ERROR_ASIG);
        i.ponTipo(programa.tipoError()); 
     }
     else {
        i.ponTipo(programa.tipoOk());  
     }
   }     
   public void procesa(IBloque b) {
      boolean ok=true;
      for (Inst i: b.is()) {
         i.procesaCon(this);
         ok = ok && i.tipo().equals(programa.tipoOk());
      }
      if (ok) 
        b.ponTipo(programa.tipoOk());
      else
       b.ponTipo(programa.tipoError());   
   }     
}
