package procesamientos.comprobaciontipos;

import errores.Errores;
import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.Var;
import programa.Programa.Suma;
import programa.Programa.Prog;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.And;
import programa.Programa.CteStringg;
import programa.Programa.CteChar;
import programa.Programa.CteReal;
import programa.Programa.CteUnknown;
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
      if(exp.opnd1().tipo().equals(programa.tipoStringg()) &&
         exp.opnd2().tipo().equals(programa.tipoStringg())) {
         exp.ponTipo(programa.tipoStringg()); 
      }
      else if (exp.opnd1().tipo().equals(programa.tipoInt()) &&
               exp.opnd2().tipo().equals(programa.tipoInt())) {
               exp.ponTipo(programa.tipoInt());
      }
      else if((exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoReal())) || 
              (exp.opnd1().tipo().equals(programa.tipoInt())  && exp.opnd2().tipo().equals(programa.tipoReal())) ||
              (exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoInt()))){
               exp.ponTipo(programa.tipoReal());
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
      for (Programa.Inst i: b.is()) {
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
   public void procesa(CteStringg exp) {
       exp.ponTipo(programa.tipoStringg());
   }
   public void procesa(CteUnknown exp) {
       exp.ponTipo(programa.tipoUnknown());
   }
   
   public void procesa(Resta exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if (exp.opnd1().tipo().equals(programa.tipoInt()) &&
               exp.opnd2().tipo().equals(programa.tipoInt())) {
               exp.ponTipo(programa.tipoInt());
      }
      else if((exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoReal())) || 
              (exp.opnd1().tipo().equals(programa.tipoInt())  && exp.opnd2().tipo().equals(programa.tipoReal())) ||
              (exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoInt()))){
               exp.ponTipo(programa.tipoReal());
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     }
   
   public void procesa(Multi exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
       if (exp.opnd1().tipo().equals(programa.tipoInt()) &&
               exp.opnd2().tipo().equals(programa.tipoInt())) {
               exp.ponTipo(programa.tipoInt());
      }
      else if((exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoReal())) || 
              (exp.opnd1().tipo().equals(programa.tipoInt())  && exp.opnd2().tipo().equals(programa.tipoReal())) ||
              (exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoInt()))){
               exp.ponTipo(programa.tipoReal());
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     }
   
   public void procesa(Div exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if (exp.opnd1().tipo().equals(programa.tipoInt()) &&
               exp.opnd2().tipo().equals(programa.tipoInt())) {
               exp.ponTipo(programa.tipoInt());
      }
      else if((exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoReal())) || 
              (exp.opnd1().tipo().equals(programa.tipoInt())  && exp.opnd2().tipo().equals(programa.tipoReal())) ||
              (exp.opnd1().tipo().equals(programa.tipoReal()) && exp.opnd2().tipo().equals(programa.tipoInt()))){
               exp.ponTipo(programa.tipoReal());
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     }
   
   public void procesa(Concat exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoStringg()) &&
         exp.opnd2().tipo().equals(programa.tipoStringg())) {
         exp.ponTipo(programa.tipoStringg()); 
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
      if(exp.opnd1().tipo().equals(programa.tipoInt())) {
         exp.ponTipo(programa.tipoInt()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoReal())) {
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(Elem exp) {
    exp.opnd1().procesaCon(this);
    exp.opnd2().procesaCon(this);
      if((exp.opnd1().tipo().equals(programa.tipoStringg())) &&
          exp.opnd2().tipo().equals(programa.tipoInt())){
         exp.ponTipo(programa.tipoChar()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   
   public void procesa(ConvInt exp) {
   exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoInt())){
         exp.ponTipo(programa.tipoInt()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoReal())){
         exp.ponTipo(programa.tipoInt()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoBool())){
         exp.ponTipo(programa.tipoInt()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoChar())){
         exp.ponTipo(programa.tipoInt()); 
      }
      else {
          if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }
   }
   public void procesa(ConvIntToInt exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoInt()); 
    }
      
   public void procesa(ConvRealToInt exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoInt()); 
   }
   public void procesa(ConvBoolToInt exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoInt()); 
   }
   public void procesa(ConvCharToInt exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoInt()); 
   }
   public void procesa(ConvReal exp) {
       exp.opnd1().procesaCon(this);
      if(exp.opnd1().tipo().equals(programa.tipoInt())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoReal())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoBool())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else if(exp.opnd1().tipo().equals(programa.tipoChar())){
         exp.ponTipo(programa.tipoReal()); 
      }
      else {
          if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }
   }
   public void procesa(ConvRealToReal exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoReal());
   }
   public void procesa(ConvIntToReal exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoReal());
   }
   public void procesa(ConvBoolToReal exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoReal());
   }
   public void procesa(ConvCharToReal exp) {
      exp.opnd1().procesaCon(this);
      exp.ponTipo(programa.tipoReal());   
   }
   public void procesa(ConvChar exp) {
   if(exp.opnd1().tipo().equals(programa.tipoInt())){
         exp.ponTipo(programa.tipoChar()); 
      }
      else {
          if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }
   }
   public void procesa(ConvBool exp) {
   if(exp.opnd1().tipo().equals(programa.tipoInt())){
         exp.ponTipo(programa.tipoBool()); 
      }
      else {
          if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }
   }
   public void procesa(ConvString exp) {
   if(exp.opnd1().tipo().equals(programa.tipoChar())){
         exp.ponTipo(programa.tipoStringg()); 
      }
      else {
          if (! exp.opnd1().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }}
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
      else if (exp.opnd1().tipo().equals(exp.opnd2().tipo())){
      exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(MayorBool exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MayorChar exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MayorNum exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MayorString exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
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
      else if (exp.opnd1().tipo().equals(exp.opnd2().tipo())){
      exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(MenorBool exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MenorChar exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MenorNum exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MenorString exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
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
      else if (exp.opnd1().tipo().equals(exp.opnd2().tipo())){
      exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(IgualBool exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(IgualChar exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(IgualNum exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(IgualString exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
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
      else if (exp.opnd1().tipo().equals(exp.opnd2().tipo())){
      exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }
   }    
   public void procesa(MayorIgualBool exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MayorIgualChar exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MayorIgualNum exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MayorIgualString exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
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
      else if (exp.opnd1().tipo().equals(exp.opnd2().tipo())){
      exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   
   public void procesa(MenorIgualBool exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MenorIgualChar exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MenorIgualNum exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(MenorIgualString exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   
   //Comprueba solo operandos del mimo tipo es asi?
   //o puede comparar de distintos tipos
   public void procesa(Distinto exp) {
   exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      if((exp.opnd1().tipo().equals(programa.tipoInt()) ||
          exp.opnd1().tipo().equals(programa.tipoReal())) &&
         (exp.opnd2().tipo().equals(programa.tipoInt()) ||
          exp.opnd2().tipo().equals(programa.tipoReal()))){
         exp.ponTipo(programa.tipoBool()); 
      }
      else if (exp.opnd1().tipo().equals(exp.opnd2().tipo())){
      exp.ponTipo(programa.tipoBool()); 
      }
      else {
         if (! exp.opnd1().tipo().equals(programa.tipoError()) &&
             ! exp.opnd2().tipo().equals(programa.tipoError()))
             errores.msg(exp.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
         exp.ponTipo(programa.tipoError());
      }     
   }
   public void procesa(DistintoBool exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(DistintoChar exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(DistintoNum exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
   public void procesa(DistintoString exp){
      exp.opnd1().procesaCon(this);
      exp.opnd2().procesaCon(this);
      exp.ponTipo(programa.tipoBool());
   }
}
