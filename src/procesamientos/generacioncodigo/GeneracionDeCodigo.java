package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.CteReal;
import programa.Programa.CteChar;
import programa.Programa.CteString;
import programa.Programa.Suma;
import programa.Programa.Resta;
import programa.Programa.Multi;
import programa.Programa.Div;
import programa.Programa.And;
import programa.Programa.RestoEntero;
import programa.Programa.CambiaSigno;
import programa.Programa.Elem;
import programa.Programa.ConvBool;
import programa.Programa.ConvChar;
import programa.Programa.ConvInt;
import programa.Programa.ConvReal;
import programa.Programa.ConvString;
import programa.Programa.Or;
import programa.Programa.Not;
import programa.Programa.Igual;
import programa.Programa.Mayor;
import programa.Programa.Menor;
import programa.Programa.MayorIgual;
import programa.Programa.MenorIgual;
import programa.Programa.Distinto;
import programa.Programa.Prog;
import programa.Programa.IBloque;
import programa.Programa.IAsig;
import programa.Programa.Var;
import programa.Programa.ILee;
import programa.Programa.IEscribe;
import programa.Programa.IIfThen;
import programa.Programa.IWhile;


public class GeneracionDeCodigo extends Procesamiento {
   private MaquinaP maquina; 
   private Programa programa;
   public GeneracionDeCodigo(MaquinaP maquina,Programa programa) {
      this.maquina = maquina; 
      this.programa = programa;
   }
   public void procesa(Var exp) {
      maquina.addInstruccion(maquina.apilaDir(exp.declaracion().dir(),exp.enlaceFuente()));         
   } 
   public void procesa(CteInt exp) {
       maquina.addInstruccion(maquina.apilaInt(exp.valEntero()));         
   } 
   public void procesa(CteBool exp) {
       maquina.addInstruccion(maquina.apilaBool(exp.valBool()));         
   }
   public void procesa(CteReal exp) {
       maquina.addInstruccion(maquina.apilaReal(exp.valReal()));
   }
   public void procesa(CteChar exp) {
       maquina.addInstruccion(maquina.apilaChar(exp.valChar()));
   }
   public void procesa(CteString exp) {
       maquina.addInstruccion(maquina.apilaString(exp.valString()));
   }
   
   public void procesa(Suma exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       //si son iguales pueden ser int real o string
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt())){ maquina.addInstruccion(maquina.sumInts());}
           else if(exp.opnd1().tipo().equals(programa.tipoReal())){ maquina.addInstruccion(maquina.sumReals());}
           else {maquina.addInstruccion(maquina.concat());}
       }
       else
           maquina.addInstruccion(maquina.sumReals());
                 
   }
      public void procesa(Resta exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       //si son iguales pueden ser int o real
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt())){ maquina.addInstruccion(maquina.resInts());}
           else{ maquina.addInstruccion(maquina.resReals());}
       }
       //sino solamente pueden ser de tipo int real o real int
       else
           maquina.addInstruccion(maquina.resReals());          
   }
      public void procesa(Multi exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       //si son iguales pueden ser int o real
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt())){ maquina.addInstruccion(maquina.mulInts());}
           else{ maquina.addInstruccion(maquina.mulReals());}
       }
       //sino solamente pueden ser de tipo int real o real int
       else
           maquina.addInstruccion(maquina.mulReals());        
   }
    public void procesa(Div exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       //si son iguales pueden ser int o real
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt())){ maquina.addInstruccion(maquina.divInts());}
           else{ maquina.addInstruccion(maquina.divReals());}
       }
       //sino solamente pueden ser de tipo int real o real int
       else
            maquina.addInstruccion(maquina.divReals());         
   }
    public void procesa(RestoEntero exp){
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.restoInt());
    }
    public void procesa(CambiaSigno exp){
        exp.opnd1().procesaCon(this);
        if(exp.opnd1().tipo().equals(programa.tipoInt()))
            maquina.addInstruccion(maquina.cambiaSigno("INT"));
        else
            maquina.addInstruccion(maquina.cambiaSigno("REAL"));
    }
    public void procesa(Elem exp){
        exp.opnd1().procesaCon(this);
        exp.opnd2().procesaCon(this);
        maquina.addInstruccion(maquina.getElem());
    }
    public void procesa(ConvInt exp){
        exp.opnd1().procesaCon(this);
        if(exp.opnd1().tipo().equals(programa.tipoInt())){ maquina.addInstruccion(maquina.convIntToInt());}
        else if (exp.opnd1().tipo().equals(programa.tipoReal())){maquina.addInstruccion(maquina.convRealToInt());}
        else if (exp.opnd1().tipo().equals(programa.tipoBool())){maquina.addInstruccion(maquina.convBoolToInt());}
        else if (exp.opnd1().tipo().equals(programa.tipoChar())){maquina.addInstruccion(maquina.convCharToInt());}
    }
    public void procesa(ConvReal exp){
        exp.opnd1().procesaCon(this);
        if(exp.opnd1().tipo().equals(programa.tipoInt())){maquina.addInstruccion(maquina.convIntToReal());}
        else if (exp.opnd1().tipo().equals(programa.tipoReal())){maquina.addInstruccion(maquina.convRealToReal());}
        else if (exp.opnd1().tipo().equals(programa.tipoBool())){maquina.addInstruccion(maquina.convBoolToReal());}
        else if (exp.opnd1().tipo().equals(programa.tipoChar())){maquina.addInstruccion(maquina.convCharToReal());}
    }
    public void procesa(ConvChar exp){
        exp.opnd1().procesaCon(this);
        maquina.addInstruccion(maquina.convIntToChar());
    }
    public void procesa(ConvBool exp){
        exp.opnd1().procesaCon(this);
        maquina.addInstruccion(maquina.convIntToBool());
    }
    public void procesa(ConvString exp){
        exp.opnd1().procesaCon(this);
        maquina.addInstruccion(maquina.convCharToString());
    }
   public void procesa(And exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.and());         
   }   
   public void procesa(Or exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.or());         
   }
   public void procesa(Not exp) {
       exp.opnd1().procesaCon(this);
       maquina.addInstruccion(maquina.not());         
   }  
   public void procesa(Igual exp){
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt()))maquina.addInstruccion(maquina.igualInts());
           else if(exp.opnd1().tipo().equals(programa.tipoReal()))maquina.addInstruccion(maquina.igualReals());
           else if(exp.opnd1().tipo().equals(programa.tipoBool()))maquina.addInstruccion(maquina.igualBools());
           else if(exp.opnd1().tipo().equals(programa.tipoChar()))maquina.addInstruccion(maquina.igualChars());
           else {maquina.addInstruccion(maquina.igualStrings());}
       }
       else
           maquina.addInstruccion(maquina.igualReals());
       

   } 
      public void procesa(Menor exp){
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt()))maquina.addInstruccion(maquina.menorInts());
           else if(exp.opnd1().tipo().equals(programa.tipoReal()))maquina.addInstruccion(maquina.menorReals());
           else if(exp.opnd1().tipo().equals(programa.tipoBool()))maquina.addInstruccion(maquina.menorBools());
           else if(exp.opnd1().tipo().equals(programa.tipoChar()))maquina.addInstruccion(maquina.menorChars());
           else {maquina.addInstruccion(maquina.menorStrings());}
       }
       else{
           maquina.addInstruccion(maquina.menorReals());
       }

   } 
   public void procesa(Mayor exp){
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt()))maquina.addInstruccion(maquina.mayorInts());
           else if(exp.opnd1().tipo().equals(programa.tipoReal()))maquina.addInstruccion(maquina.mayorReals());
           else if(exp.opnd1().tipo().equals(programa.tipoBool()))maquina.addInstruccion(maquina.mayorBools());
           else if(exp.opnd1().tipo().equals(programa.tipoChar()))maquina.addInstruccion(maquina.mayorChars());
           else {maquina.addInstruccion(maquina.mayorStrings());}
       }
       else{
           maquina.addInstruccion(maquina.mayorReals());
       }

   } 
    public void procesa(MenorIgual exp){
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt()))maquina.addInstruccion(maquina.menorIgualInts());
           else if(exp.opnd1().tipo().equals(programa.tipoReal()))maquina.addInstruccion(maquina.menorIgualReals());
           else if(exp.opnd1().tipo().equals(programa.tipoBool()))maquina.addInstruccion(maquina.menorIgualBools());
           else if(exp.opnd1().tipo().equals(programa.tipoChar()))maquina.addInstruccion(maquina.menorIgualChars());
           else {maquina.addInstruccion(maquina.menorIgualStrings());}
       }
       else{
           maquina.addInstruccion(maquina.menorIgualReals());
       }

   } 
    public void procesa(MayorIgual exp){
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt()))maquina.addInstruccion(maquina.mayorIgualInts());
           else if(exp.opnd1().tipo().equals(programa.tipoReal()))maquina.addInstruccion(maquina.mayorIgualReals());
           else if(exp.opnd1().tipo().equals(programa.tipoBool()))maquina.addInstruccion(maquina.mayorIgualBools());
           else if(exp.opnd1().tipo().equals(programa.tipoChar()))maquina.addInstruccion(maquina.mayorIgualChars());
           else {maquina.addInstruccion(maquina.mayorIgualStrings());}
       }
       else{
           maquina.addInstruccion(maquina.mayorIgualReals());
       }

   }
       public void procesa(Distinto exp){
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       if(exp.opnd1().tipo().equals(exp.opnd2().tipo())){
           if(exp.opnd1().tipo().equals(programa.tipoInt()))maquina.addInstruccion(maquina.distintoInts());
           else if(exp.opnd1().tipo().equals(programa.tipoReal()))maquina.addInstruccion(maquina.distintoReals());
           else if(exp.opnd1().tipo().equals(programa.tipoBool()))maquina.addInstruccion(maquina.distintoBools());
           else if(exp.opnd1().tipo().equals(programa.tipoChar()))maquina.addInstruccion(maquina.distintoChars());
           else {maquina.addInstruccion(maquina.distintoStrings());}
       }
       else{
           maquina.addInstruccion(maquina.distintoReals());
       }

   } 
    
    
   public void procesa(Prog p) {
      p.inst().procesaCon(this);
   }     
   public void procesa(IAsig i) {
      i.exp().procesaCon(this);
      maquina.addInstruccion(maquina.desapilaDir(i.declaracion().dir()));
   }     
   public void procesa(IBloque b) {
      for(Programa.Inst i: b.is())
          i.procesaCon(this);
   }
   public void procesa(ILee i){
       if(i.declaracion().tipoDec().equals(programa.tipoInt()))
           maquina.addInstruccion(maquina.leeInt());
       else if (i.declaracion().tipoDec().equals(programa.tipoReal()))
           maquina.addInstruccion(maquina.leeReal());
       else if (i.declaracion().tipoDec().equals(programa.tipoBool()))
           maquina.addInstruccion(maquina.leeBool());
       else if (i.declaracion().tipoDec().equals(programa.tipoChar()))
           maquina.addInstruccion(maquina.leeChar());
       else if (i.declaracion().tipoDec().equals(programa.tipoString()))
           maquina.addInstruccion(maquina.leeString());
       
       maquina.addInstruccion(maquina.desapilaDir(i.declaracion().dir()));
   }
   public void procesa(IEscribe i){
       i.exp().procesaCon(this);
       if(i.exp().tipo().equals(programa.tipoInt()))
           maquina.addInstruccion(maquina.EscribeInt());
       else if (i.exp().tipo().equals(programa.tipoReal()))
           maquina.addInstruccion(maquina.EscribeReal());
       else if (i.exp().tipo().equals(programa.tipoBool()))
           maquina.addInstruccion(maquina.EscribeBool());
       else if (i.exp().tipo().equals(programa.tipoChar()))
           maquina.addInstruccion(maquina.EscribeChar());
       else if (i.exp().tipo().equals(programa.tipoString()))
           maquina.addInstruccion(maquina.EscribeString());
       
   }
   public void procesa(IWhile i) {
      i.exp().procesaCon(this);
      maquina.addInstruccion(maquina.irF(i.dirInstruccionSiguiente()));
      i.cuerpo().procesaCon(this);
      maquina.addInstruccion(maquina.irA(i.dirPrimeraInstruccion()));      
   }
   public void procesa(IIfThen i){
       i.exp().procesaCon(this);
       maquina.addInstruccion(maquina.irF(i.dirInstruccionSiguiente()));
       i.cuerpo().procesaCon(this);   
   }
}
