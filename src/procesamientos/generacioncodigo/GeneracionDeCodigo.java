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
import programa.Programa.Prog;
import programa.Programa.IBloque;
import programa.Programa.IAsig;
import programa.Programa.Var;


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
           if(exp.opnd1().tipo().equals(programa.tipoReal())){ maquina.addInstruccion(maquina.sumReals());}
           else {maquina.addInstruccion(maquina.concat());}
       }
       //sino solamente pueden ser de tipo in real o real int
       else{
               maquina.addInstruccion(maquina.convIntToReal());
               maquina.addInstruccion(maquina.sumaReal());
           
       }      
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
       else{
               maquina.addInstruccion(maquina.convIntToReal());
               maquina.addInstruccion(maquina.resReals());
           
       }      
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
       else{
               maquina.addInstruccion(maquina.convIntToReal());
               maquina.addInstruccion(maquina.mulReals());    
       }      
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
       else{
               maquina.addInstruccion(maquina.convIntToReal());
               maquina.addInstruccion(maquina.divReals());    
       }      
   }
   public void procesa(And exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.and());         
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
}
