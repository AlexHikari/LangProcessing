package procesamientos.generacioncodigo;

import programa.Programa;
import procesamientos.Procesamiento;
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
import programa.Programa.IWhile;


public class Etiquetado extends Procesamiento {
   private int etq; 
   public Etiquetado() {
       etq = 0;
   }
   public void procesa(Var exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaDir(...dir variable...)
      exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(CteInt exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaInt(...)
      exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(CteBool exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaBool(...)
      exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(CteReal exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaReal(...)
      exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(CteChar exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaChar(...)
      exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(CteString exp) {
      exp.ponDirPrimeraInstruccion(etq);
      // apilaString(...)
      exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Suma exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // suma
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(Resta exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // resta
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(Multi exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // multiplica
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(Div exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // divide
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(RestoEntero exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // resto entero
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(CambiaSigno exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       // cambia signo
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(Elem exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // elem <- string [a]
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(ConvBool exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       // conv to bool
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(ConvChar exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       // conv to char
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(ConvInt exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       // conv to int
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(ConvReal exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       // conv to real
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(ConvString exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       // conv to string
       exp.ponDirInstruccionSiguiente(++etq);
   } 
   public void procesa(And exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // and
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Or exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // or
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Not exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       // not
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Igual exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // igual
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Menor exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // menor
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Mayor exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // mayor
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(MenorIgual exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // menor igual
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(MayorIgual exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // mayor igual
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Distinto exp) {
       exp.ponDirPrimeraInstruccion(etq);
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       // distinto
       exp.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(Prog p) {
      p.inst().procesaCon(this);
   }     
   public void procesa(IAsig i) {
      i.ponDirPrimeraInstruccion(etq);
      i.exp().procesaCon(this);
      // desapilaDir
      i.ponDirInstruccionSiguiente(++etq);
   }     
   public void procesa(IBloque b) {
      b.ponDirPrimeraInstruccion(etq);
      for(Programa.Inst i: b.is())
          i.procesaCon(this);
      b.ponDirInstruccionSiguiente(etq);
   }     
   public void procesa(IWhile i) {
      i.ponDirPrimeraInstruccion(etq);
      i.exp().procesaCon(this);
      // ir_f(...)
      etq++;
      i.cuerpo().procesaCon(this);
      // ir_a(...)
      etq++;
      i.ponDirInstruccionSiguiente(etq);
   }
   public void procesa(ILee i) {
       i.ponDirPrimeraInstruccion(etq);
       i.declaracion().procesaCon(this);
       // lee
       i.ponDirInstruccionSiguiente(++etq);
   }
   public void procesa(IEscribe i) {
       i.ponDirPrimeraInstruccion(etq);
       i.exp().procesaCon(this);
       // Escribe
       i.ponDirInstruccionSiguiente(++etq);
   }
}

