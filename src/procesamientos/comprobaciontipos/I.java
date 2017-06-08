package procesamientos.comprobaciontipos;

import programa.Programa.Bool;
import programa.Programa.Int;
import programa.Programa.TPointer;
import programa.Programa.Tipo;
import programa.Programa.Error;
import programa.Programa.Ok;
import programa.Programa.TRef;


public class I {
   public static boolean esPointer(Tipo t) {
      return t instanceof TPointer; 
   } 
   public static TPointer pointer(Tipo t) {
      return (TPointer)t; 
   } 
   public static boolean esRef(Tipo t) {
      return t instanceof TRef; 
   }
   public static TRef ref(Tipo t) {
      return (TRef)t; 
   } 
   public static boolean esInt(Tipo t) {
      return t instanceof Int; 
   } 
   public static boolean esBool(Tipo t) {
      return t instanceof Bool; 
   } 
   public static boolean esError(Tipo t) {
      return t instanceof Error;
   } 
   public static boolean esOk(Tipo t) {
      return t instanceof Ok;
   }
   public static Tipo tipoBase(Tipo t) {
     while(I.esRef(t)) {
       t = I.ref(t).declaracion().tipoDec();  
     }
     return t;
   }

}