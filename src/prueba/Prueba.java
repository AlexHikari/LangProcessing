
package prueba;

import errores.Errores;
import maquinaP.MaquinaP;
import procesamientos.comprobaciontipos.ComprobacionTipos;
import procesamientos.comprobaciontipos.Vinculacion;
import procesamientos.generacioncodigo.AsignacionDirecciones;
import procesamientos.generacioncodigo.GeneracionDeCodigo;
import procesamientos.impresion.Impresion;
import programa.Programa;


public class Prueba extends Programa {
   private Prog programa;
   public Prueba() {
     programa = prog(new Dec[]{decvar(tipoInt(),"x","linea1"),
                               decvar(tipoInt(),"y","linea2"),
                               decvar(tipoBool(),"z","linea3")
                              }, 
                     ibloque(
                          new Inst[]{
                                     iasig("x",  
                                         suma(suma(cteint(5),cteint(6),"linea 5"),
                                              cteint(25),"linea 5"),"linea 5"),
                                     iasig("y",  
                                         suma(suma(var("y","linea 6"),cteint(6),"linea 6"),
                                              cteint(25),"linea 6"), "linea 6")
                                   }));  
   }
   public Prog raiz() {
      return programa; 
   } 
   public static void main(String[] args) {
      Prueba programa = new Prueba();
      Impresion impresion = new Impresion();
      programa.raiz().procesaCon(impresion);
      Errores errores = new Errores();
      Vinculacion vinculacion = new Vinculacion(errores);
      programa.raiz().procesaCon(vinculacion);
      if (! vinculacion.error()) {
        ComprobacionTipos ctipos = new ComprobacionTipos(programa,errores); 
        programa.raiz().procesaCon(ctipos);
        if (programa.raiz().tipo().equals(programa.tipoOk())) {
           AsignacionDirecciones asignaciondirs = new AsignacionDirecciones();
           programa.raiz().procesaCon(asignaciondirs);
           MaquinaP maquina = new MaquinaP(asignaciondirs.tamanioDatos());
           GeneracionDeCodigo generacioncod = new GeneracionDeCodigo(maquina);
           programa.raiz().procesaCon(generacioncod);
           maquina.muestraCodigo(); 
           maquina.ejecuta();
           maquina.muestraEstado();
           maquina.ejecuta();
        }
        
      }
      
   }
}
  