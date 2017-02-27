package procesamientos;

import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.Var;
import programa.Programa.Suma;
import programa.Programa.Prog;
import programa.Programa.DecVar;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.And;
import programa.Programa.Int;
import programa.Programa.Bool;
import programa.Programa.Error;
import programa.Programa.Ok;

public class Procesamiento {
   public void procesa(CteInt exp) {} 
   public void procesa(CteBool exp) {} 
   public void procesa(Var exp) {} 
   public void procesa(Suma exp) {} 
   public void procesa(And exp) {}     
   public void procesa(Int t) {}     
   public void procesa(Bool t) {}     
   public void procesa(Ok t) {}     
   public void procesa(Error t) {}     
   public void procesa(Prog p) {}     
   public void procesa(DecVar d) {}     
   public void procesa(IAsig i) {}     
   public void procesa(IBloque i) {}     
}
