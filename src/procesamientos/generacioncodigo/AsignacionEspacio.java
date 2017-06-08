package procesamientos.generacioncodigo;

import procesamientos.Procesamiento;
import programa.Programa;
import programa.Programa.Bool;
import programa.Programa.DecTipo;
import programa.Programa.Prog;
import programa.Programa.DecVar;
import programa.Programa.Int;
import programa.Programa.TPointer;
import programa.Programa.TRef;


public class AsignacionEspacio extends Procesamiento {
   private int dir; 
   public AsignacionEspacio() {
       dir = 0;
   }
   public void procesa(Prog p) {
      for(Programa.Dec d: p.decs()) 
          d.procesaCon(this);
   }     
   public void procesa(DecVar d) {
       d.tipoDec().procesaCon(this);
       d.ponDir(dir);
       dir += d.tipoDec().tamanio();
   }     
   public void procesa(DecTipo d) {
       if (d.tipoDec().tamanio() == 0) {
          d.tipoDec().procesaCon(this);
       }   
   }
   public void procesa(Int t) {
       if (t.tamanio() == 0)
           t.ponTamanio(1); 
   }
   public void procesa(Bool t) {
       if (t.tamanio() == 0)
           t.ponTamanio(1); 
   }
   public void procesa(TPointer t) {
       if (t.tamanio() == 0) {
           t.ponTamanio(1); 
           t.tbase().procesaCon(this);
       }    
   }
   public void procesa(TRef r) {
      if (r.tamanio() == 0) { 
        r.declaracion().tipoDec().procesaCon(this);
        r.ponTamanio(r.declaracion().tipoDec().tamanio());
      }  
   }
   public int tamanioDatos() {return dir;}
}
