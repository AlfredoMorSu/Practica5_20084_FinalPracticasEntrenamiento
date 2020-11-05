/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica5f;

/**
 *
 * @author molec
 */
public class Mutex {
                                                        //private boolean pase = false;
    Mutex(){
    
    }
    public void lock(){
        try{
            wait();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void unlock(){
        notify();
    }
    
    
    
    /*public boolean trylock(){
        try{
            wait();
        }catch(Exception e){}
        finally{
            unlock();
            return false;
        }
    }*/
}
