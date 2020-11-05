package practica5f;

/**
 *
 * @author molec
 */
public class Interrupcion {
    private boolean inter;
    
    Interrupcion(){
        inter=true; //true; activa la Interrupcion
    }
    
    public boolean isInter() {
        return inter;
    }
    public void setInter(boolean inter) {
        this.inter = inter;
    }
}
