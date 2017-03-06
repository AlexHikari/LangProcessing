
package prueba;

import errores.Errores;
import maquinaP.MaquinaP;
import procesamientos.comprobaciontipos.ComprobacionTipos;
import procesamientos.comprobaciontipos.Vinculacion;
import procesamientos.generacioncodigo.AsignacionDirecciones;
import procesamientos.generacioncodigo.Etiquetado;
import procesamientos.generacioncodigo.GeneracionDeCodigo;
import procesamientos.impresion.Impresion;
import programa.Programa;

/*
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
*/
public class Prueba extends Programa {
   private Prog programa;
   public Prueba() {
     programa = prog(new Dec[]{decvar(tipoInt(),"x","linea1"),
                               decvar(tipoInt(),"y","linea2"),
                               decvar(tipoBool(),"z","linea3")
                              }, 
                     ibloque(
                          new Inst[]{
                                   iasig("x",cteint(0),"linea 1"),
                                   iasig("y",cteint(15),"linea 2"),
                                   iwhile(menor(var("x"),var("y"),"linea 3"), iasig("x",suma(var("x"),cteint(1)),"linea 4"),"linea 3"),
                                   iifthenelse(menor(var("x"),cteint(10),"linea 5"),iasig("x",suma(var("x"), cteint(1)),"linea 5"),iasig("z",ctebool(true),"linea 6"),"linea 4"),
                                   idowhile(distinto(var("y"),ctereal(0),"linea 9"),iasig("y",resta(var("y"), cteint(1),"linea 7")),"linea 7")
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
           Etiquetado etiquetado = new Etiquetado();
           programa.raiz().procesaCon(etiquetado);
           impresion = new Impresion(true);
           programa.raiz().procesaCon(impresion);
           MaquinaP maquina = new MaquinaP(asignaciondirs.tamanioDatos());
           GeneracionDeCodigo generacioncod = new GeneracionDeCodigo(maquina,programa);
           programa.raiz().procesaCon(generacioncod);
           maquina.muestraCodigo(); 
           maquina.ejecuta();
           maquina.muestraEstado();
           maquina.ejecuta();
        }
        
      }
      
   }
}
  