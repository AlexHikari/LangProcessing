package procesamientos.impresion;

import procesamientos.Procesamiento;

import programa.Programa.CteInt;
import programa.Programa.CteBool;
import programa.Programa.Suma;
import programa.Programa.And;
import programa.Programa.Dec;
import programa.Programa.DecVar;
import programa.Programa.Exp;
import programa.Programa.IAsig;
import programa.Programa.IBloque;
import programa.Programa.Inst;
import programa.Programa.Prog;
import programa.Programa.Var;
import programa.Programa.CteString;
import programa.Programa.CteChar;
import programa.Programa.CteReal;
import programa.Programa.Resta;
import programa.Programa.Multi;
import programa.Programa.Div;
import programa.Programa.Concat;
import programa.Programa.RestoEntero;
import programa.Programa.CambiaSigno;
import programa.Programa.Elem;
import programa.Programa.ConvInt;
import programa.Programa.ConvReal;
import programa.Programa.ConvChar;
import programa.Programa.ConvBool;
import programa.Programa.ConvString;
import programa.Programa.Or;
import programa.Programa.Not;
import programa.Programa.Mayor;
import programa.Programa.Menor;
import programa.Programa.Igual;
import programa.Programa.MayorIgual;
import programa.Programa.MenorIgual;
import programa.Programa.Distinto;
import programa.Programa.ILee;
import programa.Programa.IEscribe;
import programa.Programa.IWhile;
import programa.Programa.IDoWhile;
import programa.Programa.IIfThen;
import programa.Programa.IIfThenElse;
import programa.Programa.ISwitchCase;
import java.util.Map;


public class Impresion extends Procesamiento {
   private boolean atributos;
   private int identacion;
   public Impresion(boolean atributos) {
     this.atributos = atributos; 
     identacion = 0;
   }
   public Impresion() {
     this(false);
   }
    
   private void imprimeAtributos(Exp exp) {
       if(atributos) {
          System.out.print("@{t:"+exp.tipo()+"}"); 
       }
   }
   private void imprimeAtributos(Prog prog) {
       if(atributos) {
          System.out.print("@{t:"+prog.tipo()+"}"); 
       }
   }
   private void imprimeAtributos(Inst i) {
       if(atributos) {
          System.out.print("@{t:"+i.tipo()+"}"); 
       }
   }
   
   private void identa() {
      for (int i=0; i < identacion; i++)
          System.out.print(" ");
   }
     
   public void procesa(CteInt exp) {
     System.out.print(exp.valEntero());
     imprimeAtributos(exp);
   } 
   public void procesa(CteBool exp) {
     System.out.print(exp.valBool());
     imprimeAtributos(exp);
   } 
   public void procesa(Var exp) {
     System.out.print(exp.var());
     imprimeAtributos(exp);
   } 
   public void procesa(Suma exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('+');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   public void procesa(And exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("&&");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   }
   public void procesa(Prog p) {
      for(Dec d: p.decs()) 
          d.procesaCon(this);
      p.inst().procesaCon(this);
      imprimeAtributos(p);
      System.out.println();
   }     
   public void procesa(DecVar t) {
      System.out.print(t.tipoDec()+" "+t.var());    
      System.out.println();
   }     
   public void procesa(IAsig i) {
      identa(); 
      System.out.print(i.var()+"=");
      i.exp().procesaCon(this);
      imprimeAtributos(i);
      System.out.println(); 
   }     
   public void procesa(IBloque b) {
      identa(); 
      System.out.println("{");
      identacion += 3;
      for(Inst i: b.is())
          i.procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("}");
      imprimeAtributos(b);
      System.out.println();
   }   
   
   public void procesa(CteReal exp) {
     System.out.print(exp.valReal());
     imprimeAtributos(exp);
   }
   
   public void procesa(CteChar exp) {
     System.out.print(exp.valChar());
     imprimeAtributos(exp);
   }
   
   public void procesa(CteString exp) {
     System.out.print(exp.valString());
     imprimeAtributos(exp);
   }
   
   public void procesa(Resta exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('-');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Multi exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('*');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Div exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('/');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Concat exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("++");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(RestoEntero exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('%');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(CambiaSigno exp) {
     System.out.print('(');  
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);   
     System.out.print(')'); 
   } 
   
   public void procesa(Elem exp) {
     System.out.print('('); 
     System.out.print("Elem");
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Or exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("||");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Mayor exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('>');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Menor exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('<');
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(MayorIgual exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print(">=");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(MenorIgual exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("<=");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Igual exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("==");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   
   public void procesa(Distinto exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("!=");
     imprimeAtributos(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 

   public void procesa(Not exp) {
     System.out.print('('); 
     System.out.print('¬');
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);
     System.out.print(')'); 
   } 
   
   public void procesa(ConvInt exp) {
     System.out.print('('); 
     System.out.print("ConvInt");
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);
     System.out.print(')'); 
   } 
   
   public void procesa(ConvChar exp) {
     System.out.print('('); 
     System.out.print("ConvChar");
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);
     System.out.print(')'); 
   } 
   
   public void procesa(ConvReal exp) {
     System.out.print('('); 
     System.out.print("ConvReal");
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);
     System.out.print(')'); 
   } 
   
   public void procesa(ConvBool exp) {
     System.out.print('('); 
     System.out.print("ConvBool");
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);
     System.out.print(')'); 
   } 
   
   public void procesa(ConvString exp) {
     System.out.print('('); 
     System.out.print("ConvString");
     exp.opnd1().procesaCon(this);     
     imprimeAtributos(exp);
     System.out.print(')'); 
   }
   
   public void procesa(ILee i){
     System.out.print("(");
     System.out.print("lee valor");
     i.declaracion().procesaCon(this);
     imprimeAtributos(i);
     System.out.print(")");
   }
   
     public void procesa(IEscribe i){
     System.out.print("(");
     System.out.print("escribe expresion ");
     i.exp().procesaCon(this);
     imprimeAtributos(i.exp());
     System.out.print(")");
   }
     
     public void procesa(IWhile b) {
      identa(); 
      System.out.print("while ");
      b.exp().procesaCon(this);
      System.out.println(" do {");
      identacion += 3;
      b.cuerpo().procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("}");
      imprimeAtributos(b);
      System.out.println();
   }  

     public void procesa(IDoWhile b) {
      identa(); 
      System.out.println("do {");
      identacion += 3;
      b.cuerpo().procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("} ");
      System.out.print("while ");
      b.exp().procesaCon(this);
      imprimeAtributos(b);
      System.out.println();
   }  
     
     public void procesa(IIfThen b) {
      identa(); 
      System.out.print("if ");
      b.exp().procesaCon(this);
      System.out.println(" then {");
      identacion += 3;
      b.cuerpo().procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("}");
      imprimeAtributos(b);
      System.out.println();
   }
   public void procesa(IIfThenElse b) {
      identa(); 
      System.out.print("if ");
      b.exp().procesaCon(this);
      System.out.println(" then {");
      identacion += 3;
      b.cuerpo0().procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("}");
      System.out.println("else {");
      identacion +=3;
      b.cuerpo1().procesaCon(this);
      identacion -=3;
      identa();
      System.out.print("}");
      imprimeAtributos(b);
      System.out.println();
   } 
   //Revisar !!!!!!!!!!!!!!!!!!!!!!!
   public void procesa(ISwitchCase b){
   identa();
   System.out.print("Switch");
   //**********************
    for (Map.Entry<Exp,Inst> pair : b.cases().entrySet()) {
           System.out.print("Case");
           pair.getKey().procesaCon(this);
           System.out.print(":");
           pair.getValue().procesaCon(this);
           System.out.println();
    }
   }
   
}   