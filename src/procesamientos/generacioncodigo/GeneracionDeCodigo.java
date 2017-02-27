package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.Suma;
import programa.Programa.And;
import programa.Programa.Prog;
import programa.Programa.IBloque;
import programa.Programa.IAsig;
import programa.Programa.Var;


public class GeneracionDeCodigo extends Procesamiento {
   private MaquinaP maquina; 
   public GeneracionDeCodigo(MaquinaP maquina) {
      this.maquina = maquina; 
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
   public void procesa(Suma exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.suma());         
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
//añadir aqui la suma o cambiarla