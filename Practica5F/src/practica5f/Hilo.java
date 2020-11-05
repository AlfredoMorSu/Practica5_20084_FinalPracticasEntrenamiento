package practica5f;
/**
 *
 * @author José Alfredo Moreno Suárez
 */
import javax.swing.JTextArea;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hilo extends Thread{
    private JTextArea area;
    private RCompartido rc;
    private final static int ini = 500;
    private final static int fin = 200;
    private boolean dead=false;
    private String aux;
    private Lock mutex;
    private int nAlgoritmo = 0;//Algoritmo a utilizar
    private boolean D1[] = {false, false};
    private boolean D2[] = {false, false};
    private boolean D3[] = {false, false};
    private boolean Tn1,Tn2,Tn3,Tn4;
    
    public Hilo(JTextArea area, RCompartido rc){
        this.area=area;
        this.rc=rc;
        mutex = new ReentrantLock();
    }
    
    public void run(){
        switch(getAlgoritmo()){
            case 0:
                System.out.println("Algoritmo: Condiciones Competencias (CC)");
                try{
                    while(true){
                        rc.setDatoCompartido(this.getName());
                        area.append(rc.getDatoCompartido() + "\n");
                        Thread.sleep(1000);
                    }
                }catch(Exception e){System.out.println(e.getMessage());}
                break;
            case 1:
                System.out.println("Algoritmo: Desactivacion por Interrupcioens (DI)");
                try{
                    while(true){
                        aux = "En espera...";
                        if(rc.isEntra()){
                            rc.bloquea();
                            rc.setDatoCompartido(this.getName());
                            area.append(rc.getDatoCompartido() + "\n");
                            if(isDead())
                                stop();
                            rc.desbloquea();
                        }else area.append(aux+"\n");
                        Thread.sleep((int)(ini + Math.random()*fin));
                    }
                }catch(Exception e){System.out.println(e.getMessage());}
                break;
            case 2:
                System.out.println("Algoritmo: Variable Cerradura (VC)");
                try{
                    while(true){
                        aux = "En espera...";
                        if(rc.getCerradura()){
                            rc.setCerradura(false);
                            rc.setDatoCompartido(this.getName());
                            area.append(rc.getDatoCompartido()+"\n");
                            if(isDead())//Aqui debe detenerse
                                this.stop();
                            rc.setCerradura(true);
                        }else area.append(aux+"\n");
                        Thread.sleep((int)(ini + Math.random()*fin));
                    }
                }catch(Exception e){System.out.println(e.getMessage());}
                break;
            case 3:
                System.out.println("Algoritmo: Alternancia - Dekker (DK)");
                try{
                    while(true){
                        Tn1 = Tn2 = Tn3 = Tn4 =false;
                        if(rc.getTurnoHilo() == 0 || rc.getTurnoHilo() == 1){
                            D1[0] = true;
                        }
                        if(rc.getTurnoHilo() == 2 || rc.getTurnoHilo() == 3){
                            D1[1] = true;
                        }
                        if(rc.getTurnoHilo() == 0 || rc.getTurnoHilo() == 1){
                            while(D1[1]){
                                if(rc.getTurnoHilo() == 2 || rc.getTurnoHilo() == 3){
                                    D1[0] = false;
                                    while(rc.getTurnoHilo() == 2 || rc.getTurnoHilo() == 3){} //Espera turno de 0 o 1
                                    D1[0] = true;
                                }
                            }
                            while(!Tn1 && !Tn2){
                                if(rc.getTurnoHilo()==0){
                                    D2[0] = true;
                                }
                                if(rc.getTurnoHilo()==1){
                                    D2[1] = true;
                                }
                                if(rc.getTurnoHilo()==0 && !Tn1){
                                    while(D2[1]){
                                        if(rc.getTurnoHilo() != 0){
                                            D2[0]=false;
                                            while(rc.getTurnoHilo() != 0){}
                                            D2[0]=true;
                                        }
                                    }
                                    rc.setDatoCompartido(this.getName());
                                    area.append(rc.getDatoCompartido()+"\n");
                                    if(isDead())
                                        stop();
                                    Tn1 = true;
                                }else{
                                    if(rc.getTurnoHilo() == 1 && !Tn2){
                                        while(D2[0]){
                                            if(rc.getTurnoHilo() != 1){
                                                D2[1]= false;
                                                while(rc.getTurnoHilo() != 1){}
                                                D2[1]= true;
                                            }
                                        }
                                        rc.setDatoCompartido(this.getName());
                                        area.append(rc.getDatoCompartido()+"\n");
                                        if(isDead())
                                            stop();
                                        Tn2=true;
                                    }
                                }
                                if(rc.getTurnoHilo()==0){
                                    D2[0]=false;
                                    rc.setTurnoHilo(1);
                                }
                                if(rc.getTurnoHilo()==1){
                                    D2[1]=false;
                                    rc.setTurnoHilo(2);
                                }
                            }
                        }else{
                            if(rc.getTurnoHilo() == 2 || rc.getTurnoHilo() == 3){
                                while(D1[0]){
                                    if(rc.getTurnoHilo() == 0 || rc.getTurnoHilo() == 1){
                                        D1[1]=false;
                                        while(rc.getTurnoHilo() == 0 || rc.getTurnoHilo() == 1){}
                                        D1[1]=true;
                                    }
                                }
                                while(!Tn3 && !Tn4){
                                    if(rc.getTurnoHilo() == 2){
                                        D3[0]=true;
                                    }
                                    if(rc.getTurnoHilo() == 3){
                                        D3[1]=true;
                                    }
                                    if(rc.getTurnoHilo() == 2 && !Tn3){
                                        while(D3[1]){
                                            if(rc.getTurnoHilo() != 2){
                                                D3[0]=false;
                                                while(rc.getTurnoHilo() != 2){}
                                                D3[0]=true;
                                            }
                                        }
                                        rc.setDatoCompartido(this.getName());
                                        area.append(rc.getDatoCompartido()+"\n");
                                        if(isDead())
                                            stop();
                                        Tn3 = true;
                                    }else{
                                        if(rc.getTurnoHilo() == 3 && !Tn4){
                                            while(D3[0]){
                                                if(rc.getTurnoHilo() != 3){
                                                    D3[1]=false;
                                                    while(rc.getTurnoHilo() != 3){}
                                                    D3[1]=true;
                                                }
                                            }
                                            rc.setDatoCompartido(this.getName());
                                            area.append(rc.getDatoCompartido()+"\n");
                                            if(isDead())
                                                stop();
                                            Tn4 = true;
                                        }
                                    }
                                    if(rc.getTurnoHilo() == 2){
                                        D3[0]=false;
                                        rc.setTurnoHilo(3);
                                    }
                                    if(rc.getTurnoHilo() == 3){
                                        D3[1] = false;
                                        rc.setTurnoHilo(0);
                                    }
                                }
                            }
                        }
                        if(rc.getTurnoHilo() == 1 || rc.getTurnoHilo() == 2)
                            D1[0]=false;
                        else
                            D1[1]=false;
                        Thread.sleep((int)(ini + Math.random() * fin));
                    }
                }catch(Exception e){System.out.println(e.getMessage());}
                break;
            case 4:
                System.out.println("Algoritmo: Dijkstra (DJ)");
                break;
            case 5:
                System.out.println("Algoritmo: Mutex (MX)");
                try{
                    while(true){
                        mutex.lock();
                        rc.setDatoCompartido(this.getName());
                        area.append(rc.getDatoCompartido()+"\n");
                        if(isDead())
                            stop();
                        mutex.unlock();
                        Thread.sleep((int)(ini + Math.random() * fin));
                    }
                }catch(Exception e){System.out.println(e.getMessage());}
                break;
        }
    }
    
    public boolean isDead(){
        return dead;
    }
    public void setDead(boolean d){
        this.dead=d;
    }
    
    public int getAlgoritmo() {
        return nAlgoritmo;
    }
    public void setAlgoritmo(int a) {
        this.nAlgoritmo = a;
    } 
}
