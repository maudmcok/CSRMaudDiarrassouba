/**
 * Created by 18012666 on 27/11/17.
 */
public class Train extends Thread {
    int capacite_train;
    int nb_libre ;
    private int etat;
    private int vitesse ;
    private int engare_time = 5000 ;
    private int quai_time = 2000 ;
    private int raill_time = 15000 ;
    public static int QUAI_TIME = 5000 ;
    private EspaceQuai espaceQuai  ;

    public Train(int capacite_train) {
        this.capacite_train = capacite_train;
        this.nb_libre = capacite_train;
        this.etat = Constantes.EN_GARE;
    }

    public Train(int capacite_train, EspaceQuai espaceQuai) {
        this.capacite_train = capacite_train;
        this.espaceQuai = espaceQuai;
        this.etat = Constantes.EN_GARE;
    }

    public int getCapacite_train() {
        return capacite_train;
    }

    public int getNb_libre() {
        return nb_libre;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
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

    public void spleepQuai(int time){
        try {
            sleep(time);
            this.setEtat(Constantes.DEPART_IMMINANT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        while (true){
            if (this.etat == Constantes.SUR_QUAI){
                try {
                    System.out.println(ConsoleColors.RED_BACKGROUND +"Train: sur quai" + ConsoleColors.RESET);
                    sleep(quai_time);
                    this.setEtat(Constantes.DEPART_IMMINANT);
                    System.out.println(ConsoleColors.RED_BACKGROUND_BRIGHT + "Train: depart imminant" + ConsoleColors.RESET);
                    espaceQuai.departTrain(this);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if (this.etat == Constantes.SUR_RAILL){
                try {
                    System.out.println(ConsoleColors.GREEN_BACKGROUND + "Train: sur raill" + ConsoleColors.RESET);
                    sleep(raill_time);
                    this.setEtat(Constantes.EN_GARE);
                    espaceQuai.garerTrain(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if (this.etat == Constantes.EN_GARE){
                try {
                    this.nb_libre = capacite_train;
                    System.out.println("Train: en gare");
                    sleep(engare_time);
                    espaceQuai.garerTrain(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
