package practica5f;

import java.util.ArrayList;

/**
 *
 * @author José Alfredo Moreno Suárez
 */
public class RCompartido {
    private String datoCompartido;
    private ArrayList<Interrupcion> interrupciones;
    private VCerradura VC;
    private int TurnoHilo;
    
    
    RCompartido(){
        datoCompartido = "";
        interrupciones = new ArrayList<Interrupcion>();
        for(int i=0;i<4;i++){
            interrupciones.add(new Interrupcion());
        }
        VC = new VCerradura();
        TurnoHilo = 0;
    }
    
    public String getDatoCompartido(){
        return datoCompartido;
    }
    public void setDatoCompartido(String datoCompartido){
        this.datoCompartido=datoCompartido;
    }
    
    //DI
    public ArrayList<Interrupcion> getInterrupciones(){
        return interrupciones;
    }
    public void setInterrupciones(ArrayList<Interrupcion> interrupciones){
        this.interrupciones = interrupciones;
    }
    public void bloquea(){
        for(Interrupcion i: interrupciones)
            i.setInter(false);
    }
    public void desbloquea(){
        for(Interrupcion i: interrupciones)
            i.setInter(true);
    }
    
    public boolean isEntra(){
        boolean ban = false;
        for(Interrupcion i: interrupciones)
            if(i.isInter())
                ban=true;
            else return false;
        return ban;
    }
    
    //VC
    public boolean getCerradura(){
        return VC.isCerradura();
    }
    public void setCerradura(boolean c){
        VC.setCerradura(c);
    }
    
    //DK
    public int getTurnoHilo() {
        return TurnoHilo;
    }
    public void setTurnoHilo(int i) {
        this.TurnoHilo = i;
    }
}