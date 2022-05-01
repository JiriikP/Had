package com.example.had;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class HelloController implements Initializable {


    /** Nastavení dané uživatelem */
    boolean solo = false;
    int velKostky = 6; //
    double timeDelay = 0.5; //0 -0.5- 10
    int pocetHracu = 4 ;   // 1 / 4
    int pocetPrekazek = 15; //0 -15- 30
    int obtiznost = 10;  // 3  -8- 20

    //Default
    int rada = 10;
    boolean gameRun = true;
    boolean boolRulesActive = false;
    boolean boolInfoActive = false;
    boolean boolDokuActive = false;
    int maxScore = rada * rada;
    int kostkaNumRN = 0;
    boolean autoActive = false;
    int tah = 1;
    int Score1 = 1;
    int Score2 = 1;
    int Score3 = 1;
    int Score4 = 1;

    Label scoreLabel1 = new Label();
    Label scoreLabel2 = new Label();
    Label scoreLabel3 = new Label();
    Label scoreLabel4 = new Label();

    static int Rada;
    static int Sloupec;


    int[] arr;
    int naTahu = 0;
    int[] posPrekazky;
    int[] hodnotaPrekazky;

    Circle hrac1 = new Circle(8);
    Circle hrac2 = new Circle(8);
    Circle hrac3 = new Circle(8);
    Circle hrac4 = new Circle(8);

    @FXML
    private Pane mainPane;
    @FXML
    private Pane settingsPane;

    @FXML
    private Pane gamePane;
    @FXML
    private Slider diffSlider;
    @FXML
    private VBox scoreBox;

    @FXML
    private Label cisloHoduLab;
    @FXML
    private Label kontrolaLab;

    @FXML
    private GridPane pole;

    @FXML
    private Label hracLab;

    @FXML
    private Label pravidlaLab;
    
    @FXML
    private Label infoLab;
    
    @FXML
    private Label dokulaLab;

    
    
    @FXML
    private Label tahLab;

    @FXML
    private Slider prekazkySlider;

    @FXML
    private Slider kostkaSlider;
    @FXML
    private Slider timeSlider;




    /** Initializ app */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        diffSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                obtiznost = (int) diffSlider.getValue();
            }
        });

        kostkaSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                velKostky = (int) kostkaSlider.getValue();
            }
        });

        prekazkySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                pocetPrekazek = (int) prekazkySlider.getValue();
            }
        });

        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                timeDelay = (double) timeSlider.getValue();
            }
        });

    }

    @FXML
    protected void soloMode()
    {
     pocetHracu = 2;
     solo = true;
     onStartLogic();
    }

    @FXML
    protected void duoMode()
    {
        pocetHracu = 2;
        solo = false;
        onStartLogic();
    }
    @FXML
    protected void trioMode()
    {
        pocetHracu = 3;
        solo = false;
        onStartLogic();
    }
    @FXML
    protected void quatroMode()
    {
        pocetHracu = 4;
        solo = false;
        onStartLogic();
    }



    @FXML
    protected void onStartLogic() {

        System.out.println("time delay " + timeDelay);
        System.out.println("kostka " + velKostky);
        System.out.println("prekazky " + pocetPrekazek);
        System.out.println("obtiznost " + obtiznost);


        pole.setVisible(true);
        scoreBox.setVisible(true);
        gamePane.setVisible(true);

        settingsPane.setVisible(false);


        arr = new int[pocetHracu + 5];
        arr[0] = Score1;
        arr[1] = Score2;
        arr[2] = Score3;
        arr[3] = Score4;


        scoreBox.getChildren().add(scoreLabel1);
        scoreBox.getChildren().add(scoreLabel2);
        scoreBox.getChildren().add(scoreLabel3);
        scoreBox.getChildren().add(scoreLabel4);
        scoreLabel1.setStyle("-fx-text-fill: deepskyblue; -fx-font-size: 30px;");
        scoreLabel2.setStyle("-fx-text-fill: red; -fx-font-size: 30px;");
        scoreLabel3.setStyle("-fx-text-fill: green; -fx-font-size: 30px;");
        scoreLabel4.setStyle("-fx-text-fill: orangered; -fx-font-size: 30px;");




        scoreLabel1.setText("Score for player 1");
        scoreLabel2.setText("Score for player 2");
        if(pocetHracu >= 3){scoreLabel3.setText("Score for player 3");}
        if(pocetHracu >= 4){scoreLabel4.setText("Score for player 4");}


        hrac1.setStroke(Color.BLACK);
        hrac2.setStroke(Color.BLACK);
        hrac3.setStroke(Color.BLACK);
        hrac4.setStroke(Color.BLACK);

        /**  Vytvoreni cislic na desce  */

        for (int a = 1; a < rada * rada + 1; a++) {

            toCoords(a);

            Label boxNum = new Label();
            boxNum.setAlignment(Pos.TOP_LEFT);
            boxNum.setText(String.valueOf(a));

            GridPane.setValignment(boxNum, VPos.TOP);
            GridPane.setHalignment(boxNum, HPos.LEFT);
            pole.add(boxNum, Sloupec, Rada);
            //System.out.println(aCol + " " + row + " " + a);


        }


        /**  vytvoreni pocatecnich hracu   */
        pole.add(hrac1, 0, 9);
        hrac1.setFill(Color.DEEPSKYBLUE);
        pole.setValignment(hrac1, VPos.CENTER);
        pole.setHalignment(hrac1, HPos.CENTER);

        pole.add(hrac2, 0, 9);
        hrac2.setFill(Color.DARKRED);
        pole.setValignment(hrac2, VPos.CENTER);
        pole.setHalignment(hrac2, HPos.RIGHT);

        if(pocetHracu >= 3){
            pole.add(hrac3, 0, 9);
            hrac3.setFill(Color.GREEN);
            pole.setValignment(hrac3, VPos.BOTTOM);
            pole.setHalignment(hrac3, HPos.CENTER);
        }


        if(pocetHracu == 4){
            pole.add(hrac4, 0, 9);
            hrac4.setFill(Color.ORANGE);
            pole.setValignment(hrac4, VPos.BOTTOM);
            pole.setHalignment(hrac4, HPos.RIGHT);
        }







        hadiZebriky();
        //startButton.setDisable(true);

    }


    protected void hadiZebriky() {

        posPrekazky = new int[pocetPrekazek];
        hodnotaPrekazky = new int[pocetPrekazek];



        for (int b = 0; b < pocetPrekazek; b++) {
            Random r = new Random();
            int bRan = r.nextInt(rada * rada - 2);
            int bRan2 = r.nextInt(15);
            bRan += 1;

            Rectangle had = new Rectangle(20, 20);
            Label prekazkaLab = new Label();

            GridPane.setValignment(had, VPos.CENTER);
            GridPane.setHalignment(had, HPos.CENTER);


            /** Pozice prekazky */
            posPrekazky[b] = bRan;

            for (int c = 0; c < pocetPrekazek; c++) {
                //System.out.println("PRED b " + posPrekazky[b] + " c " + posPrekazky[c] + " hodnota " + hodnotaPrekazky[b]);
                if ((posPrekazky[b] == posPrekazky[c]) && (b != c)) {
                    posPrekazky[b] = (posPrekazky[b] + 1);
                    if (posPrekazky[b] == 100) {
                        posPrekazky[b] = 2;
                        System.out.println("100!");
                    }

                    c = 0;
                    //System.out.println("PO b " + posPrekazky[b] + " c " + posPrekazky[c]);
                }
                if (posPrekazky[b] == 1) {
                    posPrekazky[b] = 2;
                    //System.out.println("zmena prekazky z pole 0");
                    c = 0;
                }
            }

            toCoords(posPrekazky[b]);

            /** Hodnota prekazky */
            if (bRan2 - obtiznost == 0) {
                bRan2 = bRan2 + 1;
            }
            hodnotaPrekazky[b] = bRan2 - obtiznost;
            for (int j = 0; j < 15; j++) {
                if (posPrekazky[b] + hodnotaPrekazky[b] < 1) {
                    hodnotaPrekazky[b] = hodnotaPrekazky[b] + 1;
                    j = 0;
                }

            }
            for (int j = 0; j < 15; j++) {
                if (posPrekazky[b] + hodnotaPrekazky[b] > 100) {
                    hodnotaPrekazky[b] = hodnotaPrekazky[b] - 1;
                    j = 0;
                }
            }

            //System.out.println(bRan+" "+bRan2 + " "+ hodnotaPrekazky[b]);

            prekazkaLab.setText(String.valueOf(hodnotaPrekazky[b]));

            /** Barva prekazek */
            if (hodnotaPrekazky[b] < 0) {
                had.setFill(Color.GREEN);
            } else had.setFill(Color.SADDLEBROWN);


            /** Pridani prekazky na desku */
            pole.add(had, Sloupec, Rada);
            pole.add(prekazkaLab, Sloupec, Rada);


        }


    }


    @FXML
    public void roll() throws InterruptedException {
        if(gameRun == true)
        {
            hracLab.setText("Odehrál hráč "+ (naTahu + 1));

            Random r = new Random();
            int delkaKutaleniKostky = r.nextInt(6);
            int kontrolaKostky = 0;
            //System.out.println("(kostka aktivována)");


            /**  Hod kostkou    */
            for (int loop = 1; loop < 4 + delkaKutaleniKostky; loop++) {
                kontrolaKostky = kostkaNumRN;
                kostkaNumRN = r.nextInt(velKostky);

                /**  test aby se kostka nekutalela po stejnych cislicich   */
                if (kontrolaKostky == kostkaNumRN) {
                    kostkaNumRN = (velKostky - 1) - kontrolaKostky;
                    if (kontrolaKostky == kostkaNumRN) {
                        kostkaNumRN = kostkaNumRN - 1;

                    }

                }


                TimeUnit.MILLISECONDS.sleep((long) (timeDelay * 1000 / 6));
                cisloHoduLab.setText(String.valueOf(kostkaNumRN + 1));
                //System.out.println((kostkaNumRN + 1));


            }
            /**  vizualni zmena cisla na kostce   */
            System.out.println("Kostka se zastavila na: " + (kostkaNumRN + 1));
            cisloHoduLab.setText(String.valueOf(kostkaNumRN + 1));
            kontrolaLab.setText(String.valueOf(kontrolaKostky + 1));

            System.out.println("Hrac na tahu:    " + naTahu);


            playerHandler();
        }
    }



    @FXML
    public void playerHandler() throws InterruptedException {

        /**  Secteni skore daneho hrace    */
        arr[naTahu] = arr[naTahu] + kostkaNumRN + 1;

        /**Kontrola pro prehozeni maximalniho pole */
        if (arr[naTahu] > maxScore) {
            arr[naTahu] = arr[naTahu] - (kostkaNumRN + 1);
        }



        /** Zničí Hráče na desce*/
        if (naTahu == 0) {
            pole.getChildren().remove(hrac1);

        }
        if (naTahu == 1) {
            pole.getChildren().remove(hrac2);
        }

        if (naTahu == 2) {
            pole.getChildren().remove(hrac3);
        }

        if (naTahu == 3) {
            pole.getChildren().remove(hrac4);
        }

        hrac1.setFill(Color.DEEPSKYBLUE);
        pole.setValignment(hrac1, VPos.CENTER);
        pole.setHalignment(hrac1, HPos.CENTER);

        hrac2.setFill(Color.DARKRED);
        GridPane.setValignment(hrac2, VPos.CENTER);
        GridPane.setHalignment(hrac2, HPos.RIGHT);


        if(pocetHracu >= 3){
            hrac3.setFill(Color.GREEN);
            pole.setValignment(hrac3, VPos.BOTTOM);
            pole.setHalignment(hrac3, HPos.CENTER);
        }


        if(pocetHracu == 4){
            hrac4.setFill(Color.ORANGE);
            pole.setValignment(hrac4, VPos.BOTTOM);
            pole.setHalignment(hrac4, HPos.RIGHT);
        }


        int h = 0;

        /** Kontrola hrace kdyz stoji na prekazce */
        while (h < 100) {
            for (int g = 0; g < pocetPrekazek; g++) {
                if (arr[naTahu] == posPrekazky[g]) {
                    //System.out.println("na prekazce");
                    arr[naTahu] = arr[naTahu] + hodnotaPrekazky[g];
                }
            }
            h++;
        }

        toCoords(arr[naTahu]);


        /** upravuje hrace  */
        if (naTahu == 0) {
            pole.add(hrac1, Sloupec, Rada);

        }
        if (naTahu == 1) {
            pole.add(hrac2, Sloupec, Rada);

        }
        if (naTahu == 2) {
            pole.add(hrac3, Sloupec, Rada);

        }
        if (naTahu == 3) {

            pole.add(hrac4, Sloupec, Rada);

        }

        //System.out.println("score: " + arr[naTahu]);





        recalculateScore(kostkaNumRN);
    }


    void recalculateScore(int x) throws InterruptedException {




        /**  zmena skore v textu   */
        if (naTahu == 0) {
            scoreLabel1.setText("Hráč 1 je na poli: " + arr[naTahu]);
        }
        if (naTahu == 1) {
            scoreLabel2.setText("Hráč 2 je na poli: " + arr[naTahu]);
        }
        if (naTahu == 2) {
            scoreLabel3.setText("Hráč 3 je na poli: " + arr[naTahu]);
        }
        if (naTahu == 3) {
            scoreLabel4.setText("Hráč 4 je na poli: " + arr[naTahu]);
        }





        /**  Dosazeni maximalniho poctu bodu   */
        if(naTahu + 1 == pocetHracu) {
            for (int y = 0; y <= pocetHracu; y++) {
                if (arr[y] == 100) {
                    if(y == 0){ scoreLabel1.setText("Hráč " + (y+1) + " se dostal na pole 100!" );}
                    if(y == 1){ scoreLabel2.setText("Hráč " + (y+1) + " se dostal na pole 100!" );}
                    if(y == 2){ scoreLabel3.setText("Hráč " + (y+1) + " se dostal na pole 100!" );}
                    if(y == 3){ scoreLabel4.setText("Hráč " + (y+1) + " se dostal na pole 100!" );}
                    System.out.println("player " + (y+1) + " won!!!");
                    autoActive = false;
                    gameRun = false;
                }
            }
        }

        /**  Cislo tahu   */
        if (naTahu + 1 == pocetHracu) {
            tahLab.setText("Cislo tahu: " + (tah++));
        }


        //System.out.println("hrac na tahu: "naTahu);

        /**  zmena hrace na tahu po odehrani tahu  */
        naTahu++;
        if ((naTahu + 1) > pocetHracu) {
            naTahu = 0;




        }

        if (naTahu == 1 && solo){roll();

            System.out.println("bot move started ");}

    }

    @FXML
    protected void AutoPlay() throws InterruptedException {
        autoActive = !autoActive;
        timeDelay = 0;

        while (autoActive && gameRun) {


         roll();
        }
    }

    @FXML
    protected void scoreDebug() {
        Score2 = Score2 + 1;
        arr[naTahu] = arr[naTahu] + 1;
        scoreLabel1.setText("hrac 1 skore: " + arr[naTahu]);
        scoreLabel2.setText("hrac 2 skore: " + arr[naTahu]);
        scoreLabel3.setText("hrac 1 skore: " + arr[naTahu]);
        scoreLabel4.setText("hrac 2 skore: " + arr[naTahu]);

    }


    void toCoords(int req) {
        int row = 0;
        int bRow = req;
        int col = req;

        /**  Cisla rady   */
        while (bRow > 10) {
            bRow -= 10;
            row++;
        }
        row = rada - 1 - row;

        /**  Cisla sloupce   */
        while (col > 10) {
            col -= 10;
        }
        if (row % 2 == 0) {
            col = (rada - 1) - (col - 1);
        } else col = col - 1;
        Rada = row;
        Sloupec = col;
    }



    @FXML
    protected void pravidla() {
        pravidlaLab.setText("Příprava: Určí se hráč, který začne jako první s hodem.\n \nCíl hry: Vyhrává ten z hráčů, který jako první dostane z pole č. 1 na cílového pole 100. \n \nHra: Hráči se střídají v hodech kostkou a posouvají své figurky o počet polí \nodpovídající číslu na kostce.  Směr je naznačen čísly.\n Pokud se figurka zastaví na poli s dolním koncem žebříku (hnědý čtvereček), \n vystoupá na jeho horní konec (o počet polí určený číslem) a zkrátí si tak cestu. \n Pokud se figurka zastaví na poli s hadí hlavou (zelený čtvereček),\n sestoupí na pole, kde končí hadův ocas(o počet polí určený číslem).\n Aby hráč vyhrál musí hodit přesným hodem který ho dostane na cílové pole. \n Hodí‑li příliš mnoho, vrátí se zpět na pole ze kterého házel.");

        boolRulesActive = !boolRulesActive;
        if (boolRulesActive) {
            pravidlaLab.setVisible(true);
        } else pravidlaLab.setVisible(false);

    }
    protected void info() {
        boolInfoActive = !boolInfoActive;
        if (boolInfoActive) {
            infoLab.setVisible(true);
        } else infoLab.setVisible(false);
    }
    
    protected void doku() {
        boolDokuActive = !boolDokuActive;
        if (boolDokuActive) {
            dokulaLab.setVisible(true);
        } else dokulaLab.setVisible(false);
    }
    
    
    @FXML
    protected void printText() {
        System.out.println("press");
    }


    @FXML
    public void test() {

    }

    @FXML
    protected void click() {
        mainPane.setVisible(false);
    }
}
