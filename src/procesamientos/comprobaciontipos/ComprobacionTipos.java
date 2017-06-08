package procesamientos.comprobaciontipos;

import errores.Errores;
import java.util.Iterator;
import java.util.Map;
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
import programa.Programa.CteString;
import programa.Programa.CteChar;
import programa.Programa.CteReal;
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
import programa.Programa.Exp;
import programa.Programa.ILee;
import programa.Programa.IEscribe;
import programa.Programa.IWhile;
import programa.Programa.IDoWhile;
import programa.Programa.IIfThen;
import programa.Programa.IIfThenElse;
import programa.Programa.ISwitchCase;
import programa.Programa.Inst;
import programa.Programa.Casos;
import programa.Programa.DRef;
import programa.Programa.IFree;
import programa.Programa.INew;
import programa.Programa.Tipo;


public class ComprobacionTipos extends Procesamiento { 
    private final static String ERROR_DREF="Se espera un objeto de tipo puntero";
   private final static String ERROR_INDEX="Se espera un objeto de tipo array";
   private final static String ERROR_INDEX_INDICE="La expresion indice debe ser de tipo INT";
   private final static String ERROR_SELECT="Se espera un objeto de tipo registro";
   private final static String ERROR_SELECT_CAMPO="El campo seleccionado no existe en el registro";
   private final static String ERROR_TIPO_OPERANDOS="Los tipos de los operandos no son correctos";
   private final static String ERROR_ASIG="Tipos no compatibles en asignacion";
   private final static String ERROR_COND="Tipo erroneo en condicion";
   private final static String ERROR_NEW="El operando de New debe ser un puntero";
   private final static String ERROR_FREE="El operando de Free debe ser un puntero";
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
      if(exp.opnd1().tipo().equals(programa.tipoString()) &&
         exp.opnd2().tipo().equals(programa.tipoString())) {
         exp.ponTipo(programa.tipoString()); 
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
   public void procesa(CteString exp) {
       exp.ponTipo(programa.tipoString());
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
      if((exp.opnd1().tipo().equals(programa.tipoString())) &&
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
         exp.ponTipo(programa.tipoString()); 
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
   public void procesa(ILee i){
        i.ponTipo(programa.tipoOk());
   }
   public void procesa(IEscribe i){
       i.exp().procesaCon(this);
       if(i.exp().tipo().equals(programa.tipoError())){
           errores.msg(i.enlaceFuente()+":"+ERROR_TIPO_OPERANDOS);
           i.ponTipo(programa.tipoError());
       }
       else
           i.ponTipo(programa.tipoOk());
   }
   
      public void procesa(IWhile i) {
       i.exp().procesaCon(this);
       if (! i.exp().tipo().equals(programa.tipoError()) &&
           ! i.exp().tipo().equals(programa.tipoBool())) {
           errores.msg(i.enlaceFuente()+":"+ERROR_COND);
       
       }   
       i.cuerpo().procesaCon(this);
       if(i.exp().tipo().equals(programa.tipoBool()) &&
          i.cuerpo().tipo().equals(programa.tipoOk())) {
          i.ponTipo(programa.tipoOk()); 
       }
       else {
          i.ponTipo(programa.tipoError()); 
       }
      }
      public void procesa(IDoWhile i) {
       i.exp().procesaCon(this);
       if (! i.exp().tipo().equals(programa.tipoError()) &&
           ! i.exp().tipo().equals(programa.tipoBool())) {
           errores.msg(i.enlaceFuente()+":"+ERROR_COND);
       
       }   
       i.cuerpo().procesaCon(this);
       if(i.exp().tipo().equals(programa.tipoBool()) &&
          i.cuerpo().tipo().equals(programa.tipoOk())) {
          i.ponTipo(programa.tipoOk()); 
       }
       else {
          i.ponTipo(programa.tipoError()); 
       }
      }
      public void procesa(IIfThen i) {
      i.exp().procesaCon(this);
      if (! i.exp().tipo().equals(programa.tipoError()) &&
          ! i.exp().tipo().equals(programa.tipoBool())) {
           errores.msg(i.enlaceFuente()+":"+ERROR_COND);
       } 
      i.cuerpo().procesaCon(this);
       if(i.exp().tipo().equals(programa.tipoBool()) &&
          i.cuerpo().tipo().equals(programa.tipoOk())) {
          i.ponTipo(programa.tipoOk()); 
       }
       else {
          i.ponTipo(programa.tipoError()); 
       }
      
      
      }
      public void procesa(IIfThenElse i) {
      i.exp().procesaCon(this);
      if (! i.exp().tipo().equals(programa.tipoError()) &&
          ! i.exp().tipo().equals(programa.tipoBool())) {
           errores.msg(i.enlaceFuente()+":"+ERROR_COND);
       } 
      i.cuerpo0().procesaCon(this);
      i.cuerpo1().procesaCon(this);
       if(i.exp().tipo().equals(programa.tipoBool()) &&
          i.cuerpo0().tipo().equals(programa.tipoOk()) && i.cuerpo1().tipo().equals(programa.tipoOk())) {
          i.ponTipo(programa.tipoOk()); 
       }
       else {
          i.ponTipo(programa.tipoError()); 
       }
      
      
      }
      public void procesa(ISwitchCase i) {
        i.exp().procesaCon(this);
        if (! i.exp().tipo().equals(programa.tipoError()) &&
          (!i.exp().tipo().equals(programa.tipoBool())) || 
          (!i.exp().tipo().equals(programa.tipoChar())) || 
          (!i.exp().tipo().equals(programa.tipoInt())) ||
          (!i.exp().tipo().equals(programa.tipoReal()))) {
           errores.msg(i.enlaceFuente()+":"+ERROR_COND);
       } 
        
        Iterator<Casos> c = i.casos().iterator();
        while( c.hasNext() ){
            Casos caso = c.next();
            caso.cuerpo().procesaCon(this);
            if(!caso.exp().tipo().equals(i.exp().tipo())){
                errores.msg(caso.enlaceFuente()+":"+ERROR_COND);
            }                 
        }
        i.ponTipo(programa.tipoOk());    
        
        }
    
      public void procesa(DRef p) {
       p.mem().procesaCon(this);
       if(I.esPointer(p.mem().tipo())) {
         p.ponTipo(I.pointer(p.mem().tipo()).tbase());
       }
       else {
           if(! p.mem().tipo().equals(programa.tipoError())) {
              errores.msg(p.enlaceFuente()+":"+ERROR_DREF); 
           }
           p.ponTipo(programa.tipoError());
       }
   } 
      public void procesa(INew i) {
    i.mem().procesaCon(this);
    if (I.esPointer(i.mem().tipo())) {
       i.ponTipo(programa.tipoOk()); 
    }
    else {
       if (! i.mem().tipo().equals(programa.tipoError())) {
             errores.msg(i.enlaceFuente()+":"+ERROR_NEW);
       }
       i.ponTipo(programa.tipoError());
    }
   }
   
   public void procesa(IFree i) {
    i.mem().procesaCon(this);
    if (I.esPointer(i.mem().tipo())) {
       i.ponTipo(programa.tipoOk()); 
    }
    else {
       if (! i.mem().tipo().equals(programa.tipoError())) {
             errores.msg(i.enlaceFuente()+":"+ERROR_FREE);
       }
       i.ponTipo(programa.tipoError());
    }
   }
   private static class Tipox2 {
      private Tipo t1;
      private Tipo t2;
      public Tipox2 (Tipo t1, Tipo t2) {
       this.t1 = t1;
       this.t2 = t2;
      }
      public boolean equals(Object o) {
         return (o instanceof Tipox2) &&
                t1.equals(((Tipox2)o).t1) &&
                t2.equals(((Tipox2)o).t2);                 
      }
      public int hashCode() {
        return t1.hashCode()+t2.hashCode();
      }
   }
   
   private boolean sonCompatibles(Tipo t1, Tipo t2) {
       return sonCompatibles(t1,t2,new HashSet<Tipox2>()); 
   }
   private boolean sonCompatibles(Tipo t1, Tipo t2, Set<Tipox2> considerados) {
      t1 = I.tipoBase(t1);
      t2 = I.tipoBase(t2);
      if(considerados.add(new Tipox2(t1,t2))) {
         if(t1.equals(t2)) return true;
         else if (I.esPointer(t1) && I.esPointer(t2)) {
              return sonCompatibles(I.pointer(t1).tbase(),I.pointer(t2).tbase(),considerados);
         }     
         else return false; 
      }
      else {
        return true;  
      }
   } 
}
