/**
 * Created by 18012666 on 27/11/17.
 */
public class Train extends Thread {
    int capacity;
    int free_seats;
    private int etat;
    private int vitesse ;
    private int engare_time = 5000 ;
    private int quai_time = 2000 ;
    private int raill_time = 15000 ;
    public static int QUAI_TIME = 5000 ;
    private EspaceQuai espaceQuai  ;
    private String flag = "TR";


    public Train(int capacity) {
        this.capacity = capacity;
        this.espaceQuai = EspaceQuai.getInstance();
        this.etat = Constantes.EN_GARE;
    }

    public Train(int capacity, String flag) {
        this.flag = flag;
        this.capacity = capacity;
        this.espaceQuai = EspaceQuai.getInstance();
        this.etat = Constantes.EN_GARE;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFree_seats() {
        return free_seats;
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
        free_seats--;
    }

    public void descendeVoyageur(){
        free_seats--;
    }

    public void viderTrain(){
        free_seats = 0 ;
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

        while (true) {
            if (this.etat == Constantes.SUR_QUAI) {
                try {
                    System.out.println(ConsoleColors.RED_BACKGROUND + "Train: sur le quai " + this.getFlag() + ConsoleColors.RESET);
                    sleep(quai_time);
                    this.setEtat(Constantes.DEPART_IMMINANT);
                    System.out.println(ConsoleColors.CYAN + "Train: depart imminant " + this.getFlag() + ConsoleColors.RESET);
                    espaceQuai.departTrain(this);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if (this.etat == Constantes.SUR_RAILL) {
                try {
                    System.out.println(ConsoleColors.GREEN_BACKGROUND + "Train: sur raill " + this.getFlag() + ConsoleColors.RESET);
                    sleep(raill_time);
                    this.setEtat(Constantes.EN_GARE);
                    espaceQuai.garerTrain(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if (this.etat == Constantes.EN_GARE) {
                try {
                    this.free_seats = capacity;
                    System.out.println("Train: en gare "+ this.getFlag());
                    sleep(engare_time);
                    espaceQuai.garerTrain(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getFlag() {
        return flag;
    }
    public String toString(){
        return "{name:"+flag+", seats:"+ capacity +", free:"+ free_seats +"}";
    }
}
