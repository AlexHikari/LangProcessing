package maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class MaquinaP {
   private final static String W_ACCESO="**** WARNING: Acceso a memoria sin inicializar"; 
   private final Valor UNKNOWN; 
   private class Valor {
      public int valorInt() {throw new UnsupportedOperationException();}  
      public boolean valorBool() {throw new UnsupportedOperationException();} 
      public double valorReal() {throw new UnsupportedOperationException();}
      public char valorChar(){throw new UnsupportedOperationException();}
      public String valorString(){throw new UnsupportedOperationException();}
   } 
   private class ValorInt extends Valor {
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorReal extends Valor {
      private double valor;
      public ValorReal(double valor) {
          this.valor = valor;
      }
      public double valorReal(){return valor;}
      public String toString(){
          return String.valueOf(valor);
      }
   }
   private class ValorBool extends Valor {
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorChar extends Valor {
      private char valor;
      public ValorChar(char valor) {
          this.valor = valor;
      }
      public char valorChar(){return valor;}
      public String toString(){
          return String.valueOf(valor);
      }
   }
   private class ValorString extends Valor {
      private String valor;
      private int longitud;
      public ValorString(String valor) {
          this.valor = valor;
          this.longitud = valor.length();
      }
      public String valorString(){return valor;}
      public int longitud(){return longitud;}
      public String toString(){
          return valor;
      }
   }
   
   private class ValorUnknown extends Valor {
      public String toString() {
        return "?";
      }
   }
   private List<Instruccion> codigoP;
   private Stack<Valor> pilaEvaluacion;
   private Valor[] datos; 
   private int pc;

   public interface Instruccion {
      void ejecuta();  
   }
   private ISumInts ISUMINTS;
   private class ISumInts implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorInt(opnd1.valorInt()+opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "suma";};
   }
   private ISumReals ISUMREALS;
   private class ISumReals implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorReal(opnd1.valorReal()+opnd2.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "suma";};
   }
   private IConcat ICONCAT;
   private class IConcat implements Instruccion {
    public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorString(opnd1.valorString().concat(opnd2.valorString()));
         pilaEvaluacion.push(resul);
         pc++;
    }
    public String toString() {return "Concatena";};
   }
   private IConvIntToReal ICONVINTTOREAL;
   private class IConvIntToReal implements Instruccion {
       private int posicion;   
       public IConvIntToReal (){
           this.posicion = -1;
       }
       public void setPos(int pos){
            this.posicion = pos;
       }
       public void ejecuta() {
           Valor resul;
           switch(this.posicion){
           
               case -1:{
                   Valor opnd1 = pilaEvaluacion.pop();
                   if (opnd1 == UNKNOWN)
                       resul = UNKNOWN;
                   else
                       resul = new ValorReal(opnd1.valorInt());
                   pilaEvaluacion.push(resul);
       
               }break;
               case 0:{
                   Valor opnd2 = pilaEvaluacion.pop();
                   Valor opnd1 = pilaEvaluacion.pop();
                   if (opnd1 == UNKNOWN)
                        resul = UNKNOWN;
                   else
                        resul = new ValorReal(opnd1.valorInt());
                   pilaEvaluacion.push(resul);
                   pilaEvaluacion.push(opnd2);
                  
               }break;
               case 1:{
                   Valor opnd1 = pilaEvaluacion.pop();
                   if (opnd1 == UNKNOWN)
                        resul = UNKNOWN;
                   else
                        resul = new ValorReal(opnd1.valorInt());
                   pilaEvaluacion.push(resul);
            
               }break;
               default:break;
           }
           this.posicion = -1;
           pc++;
       }
       public String toString() {
       switch(this.posicion){
           
               case -1:{
                   return "Conversion explicita";
               }
               case 0:{
                   return "Conversion implicita primer argumento";
               }
               case 1:{
                   return "Conversion implicita segundo argumento";
               }
               default:break;
           }
       return "";
       }
   }
   private IResInts IRESINTS;
   private class IResInts implements Instruccion{
       public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorInt(opnd1.valorInt()-opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
       }
       public String toString() {return "Resta";};
   }
   private IResReals IRESREALS;
   private class IResReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorReal(opnd1.valorReal()-opnd2.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
       }
    public String toString() {return "Resta";};
   }
   private IMulInts IMULINTS;
   private class IMulInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorInt(opnd1.valorInt()*opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Multiplica";};
   }
   private IMulReals IMULREALS;
   private class IMulReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorReal(opnd1.valorReal()*opnd2.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Multiplica";};
   }
   private IDivInts IDIVINTS;
   private class IDivInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorInt(opnd1.valorInt()/opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Divide";};
   }
   private IDivReals IDIVREALS;
   private class IDivReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorReal(opnd1.valorReal()/opnd2.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Divide";};
   }
   private IRestoEntero IRESTOENTERO;
   private class IRestoEntero implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorInt(opnd1.valorInt()%opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Resto entero";};
   }
   private ICambiaSigno ICAMBIASIGNO;
   private class ICambiaSigno implements Instruccion{
       private String tipo;
       public ICambiaSigno(){
            this.tipo = "INT";
        }
       public void setTipo (String tipo){this.tipo = tipo;}
       public void ejecuta(){ 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else{ 
                if (tipo.equals("INT")) 
                 resul = new ValorInt(-opnd1.valorInt());
                else
                 resul = new ValorReal(-opnd1.valorReal());
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
       public String toString() {return "Cambia signo";};
   }
   private IGetElem IGETELEM;
   private class IGetElem implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop(); 
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if ((opnd1 == UNKNOWN || opnd2 == UNKNOWN) || (opnd1.valorString().length() < opnd2.valorInt())) 
                 resul = UNKNOWN;
            else 
                resul = new ValorChar(opnd1.valorString().charAt(opnd2.valorInt()));
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Get elemento";};
   }
   private IConvIntToInt ICONVINTOINT;
   private class IConvIntToInt implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorInt(opnd1.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte int a int";};
   }
   private IConvRealToInt ICONVREALTOINT;
   private class IConvRealToInt implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else 
                resul = new ValorInt((int)opnd1.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte real a int";};
   }
   private IConvBoolToInt ICONVBOOLTOINT;
   private class IConvBoolToInt implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if (opnd1.valorBool() == true)
                    resul = new ValorInt(1);
                else
                    resul = new ValorInt(0);
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte bool a int";};
   }
   private IConvCharToInt ICONVCHARTOINT;
   private class IConvCharToInt implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else
                resul = new ValorInt(opnd1.valorChar());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte char a int";};
   }
   private IConvRealToReal ICONVREALTOREAL;
   private class IConvRealToReal implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else
                resul = new ValorReal(opnd1.valorReal());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte real a real";};
   }
   private IConvCharToReal ICONVCHARTOREAL;
   private class IConvCharToReal implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else
                resul = new ValorReal(opnd1.valorChar());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte char a real";};
   }
   private IConvBoolToReal ICONVBOOLTOREAL;
   private class IConvBoolToReal implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if (opnd1.valorBool() == true)
                    resul = new ValorReal(1);
                else
                    resul = new ValorReal(0);
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte bool a real";};
   }
   private IConvIntToChar ICONVINTTOCHAR;
   private class IConvIntToChar implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else
                resul = new ValorChar((char) opnd1.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte int a char";};
   }
   private IConvIntToBool ICONVINTTOBOOL;
   private class IConvIntToBool implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorInt() == 0)
                    resul = new ValorBool(false);
                else
                    resul = new ValorBool(true);
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte int a bool";};
   }
   private IConvCharToString ICONVCHARTOSTRING;
   private class IConvCharToString implements Instruccion{
       public void ejecuta(){
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN) 
                 resul = UNKNOWN;
            else
               resul = new ValorString(Character.toString(opnd1.valorChar()));
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "Convierte char a string";};
   }
   private IIgualInts IIGUALINTS;
   private class IIgualInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorInt() == opnd2.valorInt())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "igual int a int";};
   }
   private IIgualReals IIGUALREALS;
   private class IIgualReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                
                if(opnd1.valorReal() == opnd2.valorReal())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
                
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "igual real a real";};
   }
   private IIgualBools IIGUALBOOLS;
   private class IIgualBools implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorBool() == opnd2.valorBool())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "igual bool a bool";};
   }
   private IIgualChars IIGUALCHARS;
   private class IIgualChars implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if((int) opnd1.valorChar() == (int) opnd2.valorChar())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "igual char a char";};
   }
   private IIgualStrings IIGUALSTRINGS;
   private class IIgualStrings implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorString().equals(opnd2.valorString()))
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "igual string a string";};
   }
   private IMenorInts IMENORINTS;
   private class IMenorInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorInt() < opnd2.valorInt())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor int a int";};
   }
   private IMenorReals IMENORREALS;
   private class IMenorReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorReal() < opnd2.valorReal())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor real a real";};
   }
   private IMenorBools IMENORBOOLS;
   private class IMenorBools implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorBool() == false && opnd2.valorBool() == true)
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor bool a bool";};
   }
   private IMenorChars IMENORCHARS;
   private class IMenorChars implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
                 resul = UNKNOWN;
            else{
                if((int) opnd1.valorChar() < (int) opnd2.valorChar())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor char a char";};
   }
   private IMenorStrings IMENORSTRINGS;
   private class IMenorStrings implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            Valor aux;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                 aux = new ValorInt(opnd1.valorString().compareTo(opnd2.valorString()));
                    if (aux.valorInt() < 0)
                        resul = new ValorBool(true);
                    else
                        resul = new ValorBool(false);
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor string a string";};
   }
   private IMayorInts IMAYORINTS;
   private class IMayorInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorInt() > opnd2.valorInt())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor int a int";};
   }
   private IMayorReals IMAYORREALS;
   private class IMayorReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
                 resul = UNKNOWN;
            else{
                if(opnd1.valorReal() > opnd2.valorReal())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor real a real";};
   }
   private IMayorBools IMAYORBOOLS;
   private class IMayorBools implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorBool() == true && opnd2.valorBool() == false)
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor bool a bool";};
   }
   private IMayorChars IMAYORCHARS;
   private class IMayorChars implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if((int) opnd1.valorChar() > (int) opnd2.valorChar())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor char a char";};
   }
   private IMayorStrings IMAYORSTRINGS;
   private class IMayorStrings implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            Valor aux;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                 aux = new ValorInt(opnd1.valorString().compareTo(opnd2.valorString()));
                    if (aux.valorInt() > 0)
                        resul = new ValorBool(true);
                    else
                        resul = new ValorBool(false);
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor string a string";};
   }
   private IMenorIgualInts IMENORIGUALINTS;
   private class IMenorIgualInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorInt() <= opnd2.valorInt())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor igual int a int";};
   }
   private IMenorIgualReals IMENORIGUALREALS;
   private class IMenorIgualReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorReal() <= opnd2.valorReal())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor igual real a real";};
   }
   private IMenorIgualBools IMENORIGUALBOOLS;
   private class IMenorIgualBools implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if((opnd1.valorBool() == false && opnd2.valorBool() == true) ||opnd1.valorBool() == opnd2.valorBool())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor igual bool a bool";};
   }
   private IMenorIgualChars IMENORIGUALCHARS;
   private class IMenorIgualChars implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if((int) opnd1.valorChar() <= (int) opnd2.valorChar())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor igual char a char";};
   }
   private IMenorIgualStrings IMENORIGUALSTRINGS;
   private class IMenorIgualStrings implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            Valor aux;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                 aux = new ValorInt(opnd1.valorString().compareTo(opnd2.valorString()));
                    if (aux.valorInt() <= 0)
                        resul = new ValorBool(true);
                    else
                        resul = new ValorBool(false);
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "menor igual string a string";};
   }
   private IMayorIgualInts IMAYORIGUALINTS;
   private class IMayorIgualInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorInt() >= opnd2.valorInt())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor igual int a int";};
   }
   private IMayorIgualReals IMAYORIGUALREALS;
   private class IMayorIgualReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorReal() >= opnd2.valorReal())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor igual real a real";};
   }
   private IMayorIgualBools IMAYORIGUALBOOLS;
   private class IMayorIgualBools implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if((opnd1.valorBool() == true && opnd2.valorBool() == false) ||opnd1.valorBool() == opnd2.valorBool() )
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor igual bool a bool";};
   }
   private IMayorIgualChars IMAYORIGUALCHARS;
   private class IMayorIgualChars implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if((int) opnd1.valorChar() >= (int) opnd2.valorChar())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor igual char a char";};
   }
   private IMayorIgualStrings IMAYORIGUALSTRINGS;
   private class IMayorIgualStrings implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            Valor aux;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                 aux = new ValorInt(opnd1.valorString().compareTo(opnd2.valorString()));
                    if (aux.valorInt() >= 0)
                        resul = new ValorBool(true);
                    else
                        resul = new ValorBool(false);
            }
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "mayor igual string a string";};
   }
      private IDistintoInts IDISTINTOINTS;
   private class IDistintoInts implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorInt() != opnd2.valorInt())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "distinto int a int";};
   }
   private IDistintoReals IDISTINTOREALS;
   private class IDistintoReals implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                
                if(opnd1.valorReal() != opnd2.valorReal())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
                
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "distinto real a real";};
   }
   private IDistintoBools IDISTINTOBOOLS;
   private class IDistintoBools implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(opnd1.valorBool() != opnd2.valorBool())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "distinto bool a bool";};
   }
   private IDistintoChars IDISTINTOCHARS;
   private class IDistintoChars implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if((int) opnd1.valorChar() != (int) opnd2.valorChar())
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "distinto char a char";};
   }
   private IDistintoStrings IDISTINTOSTRINGS;
   private class IDistintoStrings implements Instruccion{
       public void ejecuta(){
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            Valor resul;
            if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
                 resul = UNKNOWN;
            else{
                if(!opnd1.valorString().equals(opnd2.valorString()))
                    resul = new ValorBool(true);
                else
                    resul = new ValorBool(false);
            }
              
         pilaEvaluacion.push(resul);
         pc++;
       }
           public String toString() {return "distinto string a string";};
   }
   private IAnd IAND;
   private class IAnd implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorBool(opnd1.valorBool()&&opnd2.valorBool());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "and";};
   }
   private IOr IOR;
   private class IOr implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorBool(opnd1.valorBool() || opnd2.valorBool());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "or";};
   }
   private INot INOT;
   private class INot implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorBool(!opnd1.valorBool());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "not";};
   }
   private ILeeInt ILEEINT;
   private class ILeeInt implements Instruccion {
      public void ejecuta() {
         Scanner in = new Scanner(System.in);
         String lee;
         Valor resul;
         lee = in.next();
         resul = new ValorInt(Integer.parseInt(lee));
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "lee int";};
   }
   private ILeeReal ILEEREAL;
   private class ILeeReal implements Instruccion {
      public void ejecuta() {
         Scanner in = new Scanner(System.in);
         String lee;
         Valor resul;
         lee = in.next();
         resul = new ValorReal(Double.parseDouble(lee));
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "lee real";};
   }
      private ILeeBool ILEEBOOL;
   private class ILeeBool implements Instruccion {
      public void ejecuta() {
         Scanner in = new Scanner(System.in);
         String lee;
         Valor resul;
         lee = in.next();
         resul = new ValorBool(Boolean.parseBoolean(lee));
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "lee bool";};
   }
      private ILeeChar ILEECHAR;
   private class ILeeChar implements Instruccion {
      public void ejecuta() {
         Scanner in = new Scanner(System.in);
         String lee;
         Valor resul;
         lee = in.next();
         resul = new ValorChar(lee.charAt(0));
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "lee char";};
   }
      private ILeeString ILEESTRING;
   private class ILeeString implements Instruccion {
      public void ejecuta() {
         Scanner in = new Scanner(System.in);
         String lee;
         Valor resul;
         lee = in.next();
         resul = new ValorString(lee);
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "lee string";};
   }
   
   
   
   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apilaInt("+valor+")";};
   }
   
   private class IApilaReal implements Instruccion {
      private double valor;
      public IApilaReal(double valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorReal(valor)); 
         pc++;
      } 
      public String toString() {return "apilaReal("+valor+")";};
   }
   private class IApilaChar implements Instruccion {
      private char valor;
      public IApilaChar(char valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorChar(valor)); 
         pc++;
      } 
      public String toString() {return "apilaChar("+valor+")";};
   }
   private class IApilaString implements Instruccion {
      private String valor;
      public IApilaString(String valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorString(valor)); 
         pc++;
      } 
      public String toString() {return "apilaString("+valor+")";};
   }
   private class IDesapilaDir implements Instruccion {
      private int dir;
      public IDesapilaDir(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         datos[dir] = pilaEvaluacion.pop();
         pc++;
      } 
      public String toString() {return "desapilaDir("+dir+")";};
   }
   private class IApilaDir implements Instruccion {
      private int dir;
      private String enlaceFuente;
      public IApilaDir(int dir) {
        this(dir,null);  
      }
      public IApilaDir(int dir, String enlaceFuente) {
        this.enlaceFuente = enlaceFuente;  
        this.dir = dir;  
      }
      public void ejecuta() {
         if(datos[dir] == null) { 
             System.err.println(enlaceFuente+":"+W_ACCESO); 
             pilaEvaluacion.push(UNKNOWN); 
         }     
         else 
              pilaEvaluacion.push(datos[dir]);
         pc++;
      } 
      public String toString() {return "apilaDir("+dir+","+enlaceFuente+")";};
   }

   private class IApilaBool implements Instruccion {
      private boolean valor;
      public IApilaBool(boolean valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorBool(valor)); 
         pc++;
      } 
      public String toString() {return "apilaBool("+valor+")";};
   }
   
   public Instruccion sumInts() {return ISUMINTS;}
   public Instruccion sumReals() {return ISUMREALS;}
   public Instruccion concat() {return ICONCAT;}
   public Instruccion convIntToReal(){return ICONVINTTOREAL;}
   public Instruccion convIntToReal(int pos){
       ICONVINTTOREAL.setPos(pos);
       return ICONVINTTOREAL;
   }
   public Instruccion resInts(){return IRESINTS;}
   public Instruccion resReals(){return IRESREALS;}
   public Instruccion mulInts(){return IMULINTS;}
   public Instruccion mulReals(){return IMULREALS;}
   public Instruccion divInts(){return IDIVINTS;}
   public Instruccion divReals(){return IDIVREALS;}
   public Instruccion restoInt(){return IRESTOENTERO;}
   public Instruccion cambiaSigno(String tipo){
       ICAMBIASIGNO.setTipo(tipo);
       return ICAMBIASIGNO;
   }
   public Instruccion getElem(){return IGETELEM;}
   public Instruccion and() {return IAND;}
   public Instruccion or() {return IOR;}
   public Instruccion not() {return INOT;}
   public Instruccion convIntToInt() {return ICONVINTOINT;}
   public Instruccion convRealToInt() {return ICONVREALTOINT;}
   public Instruccion convBoolToInt() {return ICONVBOOLTOINT;}
   public Instruccion convCharToInt() {return ICONVCHARTOINT;}
   public Instruccion convRealToReal() {return ICONVREALTOREAL;}
   public Instruccion convCharToReal() {return ICONVCHARTOREAL;}
   public Instruccion convBoolToReal() {return ICONVBOOLTOREAL;}
   public Instruccion convIntToChar() {return ICONVINTTOCHAR;}
   public Instruccion convIntToBool() {return ICONVINTTOBOOL;}
   public Instruccion convCharToString() {return ICONVCHARTOSTRING;}
   public Instruccion igualInts() {return IIGUALINTS;}
   public Instruccion igualReals() {return IIGUALREALS;}
   public Instruccion igualBools() {return IIGUALBOOLS;}
   public Instruccion igualChars() {return IIGUALCHARS;}
   public Instruccion igualStrings() {return IIGUALSTRINGS;}
   public Instruccion menorInts() {return IMENORINTS;}
   public Instruccion menorReals() {return IMENORREALS;}
   public Instruccion menorBools() {return IMENORBOOLS;}
   public Instruccion menorChars() {return IMENORCHARS;}
   public Instruccion menorStrings() {return IMENORSTRINGS;}
   public Instruccion mayorInts() {return IMAYORINTS;}
   public Instruccion mayorReals() {return IMAYORREALS;}
   public Instruccion mayorBools() {return IMAYORBOOLS;}
   public Instruccion mayorChars() {return IMAYORCHARS;}
   public Instruccion mayorStrings() {return IMAYORSTRINGS;}
   public Instruccion menorIgualInts() {return IMENORIGUALINTS;}
   public Instruccion menorIgualReals() {return IMENORIGUALREALS;}
   public Instruccion menorIgualBools() {return IMENORIGUALBOOLS;}
   public Instruccion menorIgualChars() {return IMENORIGUALCHARS;}
   public Instruccion menorIgualStrings() {return IMENORIGUALSTRINGS;}
   public Instruccion mayorIgualInts() {return IMAYORIGUALINTS;}
   public Instruccion mayorIgualReals() {return IMAYORIGUALREALS;}
   public Instruccion mayorIgualBools() {return IMAYORIGUALBOOLS;}
   public Instruccion mayorIgualChars() {return IMAYORIGUALCHARS;}
   public Instruccion mayorIgualStrings() {return IMAYORIGUALSTRINGS;}
   public Instruccion distintoInts() {return IDISTINTOINTS;}
   public Instruccion distintoReals() {return IDISTINTOREALS;}
   public Instruccion distintoBools() {return IDISTINTOBOOLS;}
   public Instruccion distintoChars() {return IDISTINTOCHARS;}
   public Instruccion distintoStrings() {return IDISTINTOSTRINGS;} 
   public Instruccion apilaInt(int val) {return new IApilaInt(val);}
   public Instruccion apilaReal(double val) {return new IApilaReal(val);}
   public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilaChar(char val) {return new IApilaChar(val);}
   public Instruccion apilaString(String val) {return new IApilaString(val);}
   public Instruccion desapilaDir(int dir) {return new IDesapilaDir(dir);}
   public Instruccion apilaDir(int dir) {return new IApilaDir(dir);}
   public Instruccion apilaDir(int dir,String dinfo) {return new IApilaDir(dir,dinfo);}
   public Instruccion leeInt(){return ILEEINT;}
   public Instruccion leeReal(){return ILEEREAL;}
   public Instruccion leeBool(){return ILEEBOOL;}
   public Instruccion leeChar(){return ILEECHAR;}
   public Instruccion leeString(){return ILEESTRING;}
   public void addInstruccion(Instruccion i) {
      codigoP.add(i); 
   }

   public MaquinaP(int tamdatos) {
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos];
      this.pc = 0;
      ISUMINTS = new ISumInts();
      ISUMREALS = new ISumReals();
      IRESINTS = new IResInts();
      IRESREALS = new IResReals();
      IMULINTS = new IMulInts();
      IMULREALS = new IMulReals();
      IDIVINTS = new IDivInts();
      IDIVREALS = new IDivReals();
      IRESTOENTERO = new IRestoEntero();
      ICAMBIASIGNO = new ICambiaSigno();
      IGETELEM = new IGetElem();
      ICONVINTTOREAL = new IConvIntToReal();
      ICONVINTOINT = new IConvIntToInt();
      ICONVREALTOINT = new IConvRealToInt();
      ICONVBOOLTOINT = new IConvBoolToInt();
      ICONVCHARTOINT = new IConvCharToInt();
      ICONVREALTOREAL = new IConvRealToReal();
      ICONVCHARTOREAL = new IConvCharToReal();
      ICONVBOOLTOREAL = new IConvBoolToReal();
      ICONVINTTOCHAR = new IConvIntToChar();
      ICONVINTTOBOOL = new IConvIntToBool();
      ICONVCHARTOSTRING = new IConvCharToString();
      IAND = new IAnd();
      IOR = new IOr();
      INOT = new INot();
      IIGUALINTS = new IIgualInts();
      IIGUALREALS = new IIgualReals();
      IIGUALBOOLS = new IIgualBools();
      IIGUALCHARS = new IIgualChars();
      IIGUALSTRINGS = new IIgualStrings();
      IMENORINTS = new IMenorInts();
      IMENORREALS = new IMenorReals();
      IMENORBOOLS = new IMenorBools();
      IMENORCHARS = new IMenorChars();
      IMENORSTRINGS = new IMenorStrings();
      IMAYORINTS = new IMayorInts();
      IMAYORREALS = new IMayorReals();
      IMAYORBOOLS = new IMayorBools();
      IMAYORCHARS = new IMayorChars();
      IMAYORSTRINGS = new IMayorStrings();
      IMENORIGUALINTS = new IMenorIgualInts();
      IMENORIGUALREALS = new IMenorIgualReals();
      IMENORIGUALBOOLS = new IMenorIgualBools();
      IMENORIGUALCHARS = new IMenorIgualChars();
      IMENORIGUALSTRINGS = new IMenorIgualStrings();
      IMAYORIGUALINTS = new IMayorIgualInts();
      IMAYORIGUALREALS = new IMayorIgualReals();
      IMAYORIGUALBOOLS = new IMayorIgualBools();
      IMAYORIGUALCHARS = new IMayorIgualChars();
      IMAYORIGUALSTRINGS = new IMayorIgualStrings();
      IDISTINTOINTS = new IDistintoInts();
      IDISTINTOREALS = new IDistintoReals();
      IDISTINTOBOOLS = new IDistintoBools();
      IDISTINTOCHARS = new IDistintoChars();
      IDISTINTOSTRINGS = new IDistintoStrings();
      ILEEINT = new ILeeInt();
      ILEEREAL = new ILeeReal();
      ILEECHAR = new ILeeChar();
      ILEEBOOL = new ILeeBool();
      ILEESTRING = new ILeeString();
      
      UNKNOWN = new ValorUnknown();
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
          codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
}
