/**
 * Created by 18012666 on 27/11/17.
 */
public class Train extends Thread {
    private int capacite_train;
    private int nb_libre ;
    private int eta ;
    private int vitesse ;

    public Train(int capacite_train, int eta) {
        this.capacite_train = capacite_train;
        this.nb_libre = capacite_train;
        this.eta = eta;
    }

    public int getCapacite_train() {
        return capacite_train;
    }


    public int getNb_libre() {
        return nb_libre;
    }



    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void addVoyageur(){
        nb_libre--;
    }

    public void descendeVoyageur(){
        nb_libre--;
    }

    public void viderTrain(){
        nb_libre = 0 ;
    }


}
