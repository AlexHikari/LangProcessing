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
import programa.Programa.CteChar;
import programa.Programa.CteReal;
import programa.Programa.CteString;
import programa.Programa.SumaInt;
import programa.Programa.SumaReal;
import programa.Programa.Resta;
import programa.Programa.RestaInt;
import programa.Programa.RestaReal;
import programa.Programa.Multi;
import programa.Programa.MulInt;
import programa.Programa.MulReal;
import programa.Programa.Div;
import programa.Programa.DivInt;
import programa.Programa.DivReal;
import programa.Programa.Concat;
import programa.Programa.RestoEntero;
import programa.Programa.CambiaSigno;
import programa.Programa.Elem;
import programa.Programa.ConvInt;
import programa.Programa.ConvIntToInt;
import programa.Programa.ConvRealToInt;
import programa.Programa.ConvBoolToInt;
import programa.Programa.ConvCharToInt;
import programa.Programa.ConvReal;
import programa.Programa.ConvIntToReal;
import programa.Programa.ConvRealToReal;
import programa.Programa.ConvBoolToReal;
import programa.Programa.ConvCharToReal;
import programa.Programa.ConvChar;
import programa.Programa.ConvBool;
import programa.Programa.ConvString;
import programa.Programa.Or;
import programa.Programa.Not;
import programa.Programa.Menor;
import programa.Programa.Mayor;
import programa.Programa.MenorIgual;
import programa.Programa.MayorIgual;
import programa.Programa.Igual;
import programa.Programa.Distinto;


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
   //modificar para que segun sea el operando llame a uno o a otro
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
   
   //Fase 1
   public void procesa(CteChar exp) {
       exp.ponTipo(programa.tipoChar());
   }
   
   public void procesa(CteReal exp) {
       exp.ponTipo(programa.tipoReal());
   }
   public void procesa(CteString exp) {
       exp.ponTipo(programa.tipoString());
   }
   public void procesa(SumaInt exp) {
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
   public void procesa(SumaReal exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoReal()) &&
         exp.opnd2().tipo().equals(programa.tipoReal())) {
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Resta exp) {}
   public void procesa(RestaInt exp) {
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
   public void procesa(RestaReal exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoReal()) &&
         exp.opnd2().tipo().equals(programa.tipoReal())) {
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Multi exp) {}
   public void procesa(MulInt exp) {
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
   public void procesa(MulReal exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoReal()) &&
         exp.opnd2().tipo().equals(programa.tipoReal())) {
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Div exp) {}
   public void procesa(DivInt exp) {
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
   public void procesa(DivReal exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoReal()) &&
         exp.opnd2().tipo().equals(programa.tipoReal())) {
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Concat exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoString()) &&
         exp.opnd2().tipo().equals(programa.tipoString())) {
         exp.ponTipo(programa.tipoString()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(RestoEntero exp) {
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
   public void procesa(CambiaSigno exp) {
      exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoInt()) ||
         exp.opnd1().tipo().equals(programa.tipoReal())) {
         exp.ponTipo(programa.tipoString()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Elem exp) {}
   public void procesa(ConvInt exp) {}
   public void procesa(ConvIntToInt exp) {
      exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoInt())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(ConvRealToInt exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoReal())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(ConvBoolToInt exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoBool())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(ConvCharToInt exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoChar())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(ConvReal exp) {}
   public void procesa(ConvRealToReal exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoReal())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(ConvIntToReal exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoInt())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }  
   }
   public void procesa(ConvBoolToReal exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoBool())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(ConvCharToReal exp) {
    exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoChar())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(ConvChar exp) {}
   public void procesa(ConvBool exp) {}
   public void procesa(ConvString exp) {}
   public void procesa(Or exp) {
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoBool()) &&
         exp.opnd2().tipo().equals(programa.tipoBool())) {
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Not exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoBool())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Mayor exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if((exp.opnd1().tipo().equals(programa.tipoInt()) ||
          exp.opnd1().tipo().equals(programa.tipoReal())) &&
         (exp.opnd2().tipo().equals(programa.tipoInt()) ||
          exp.opnd2().tipo().equals(programa.tipoReal()))){
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Menor exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if((exp.opnd1().tipo().equals(programa.tipoInt()) ||
          exp.opnd1().tipo().equals(programa.tipoReal())) &&
         (exp.opnd2().tipo().equals(programa.tipoInt()) ||
          exp.opnd2().tipo().equals(programa.tipoReal()))){
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Igual exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if((exp.opnd1().tipo().equals(programa.tipoInt()) ||
          exp.opnd1().tipo().equals(programa.tipoReal())) &&
         (exp.opnd2().tipo().equals(programa.tipoInt()) ||
          exp.opnd2().tipo().equals(programa.tipoReal()))){
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(MayorIgual exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if((exp.opnd1().tipo().equals(programa.tipoInt()) ||
          exp.opnd1().tipo().equals(programa.tipoReal())) &&
         (exp.opnd2().tipo().equals(programa.tipoInt()) ||
          exp.opnd2().tipo().equals(programa.tipoReal()))){
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(MenorIgual exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if((exp.opnd1().tipo().equals(programa.tipoInt()) ||
          exp.opnd1().tipo().equals(programa.tipoReal())) &&
         (exp.opnd2().tipo().equals(programa.tipoInt()) ||
          exp.opnd2().tipo().equals(programa.tipoReal()))){
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   
   //Comprueba solo operandos del mimo tipo es asi?
   //o puede comparar de distintos tipos
   public void procesa(Distinto exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(exp.opnd2().tipo())) {
         exp.ponTipo(programa.tipoInt()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   
}
