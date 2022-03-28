package com.example.had;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Label;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class HelloController {


    //Settings
    int rada = 10;
    int maxScore = rada * rada;
    int velKostky = 6;
    int timeDelay = 50;
    int pocetHracu = 2;   // 1 / 3
    int pocetPrekazek = 5; //0 / 10
    int obtiznost = 0;

    //Default
    int kostkaNumRN = 0;
    boolean autoActive = false;
    int tah = 1;
    int Score1 = 1;
    int Score2 = 1;
    int Score3 = 1;
    int Score4 = 1;

    Label scoreLabel1 = new Label();
    Label scoreLabel2 = new Label();


    int row = 0;
    int[] arr;
    int naTahu = 0;
    int[] posPrekazky;
    int[] hodnotaPrekazky;

    Circle hrac1 = new Circle(8);
    Circle hrac2 = new Circle(8);
    Circle hrac3 = new Circle(8);



    @FXML
    private VBox scoreBox;

    @FXML
    private Label cisloHoduLab;
    @FXML
    private Label kontrolaLab;

    @FXML
    private GridPane pole;


    @FXML
    private Label tahLab;

    @FXML
    public void roll() throws InterruptedException {
        Random r = new Random();
        int delkaKutaleniKostky = r.nextInt(6);
        int kontrolaKostky = 0;
        System.out.println("(kostka aktivována)");

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

            //cisloHoduLab.setText(String.valueOf(kostkaNumRN + 1));
            System.out.println((kostkaNumRN + 1));
            TimeUnit.MILLISECONDS.sleep(timeDelay);
        }
        /**  vizualni zmena cisla na kostce   */
        System.out.println("Kostka se zastavila na: " + (kostkaNumRN + 1));
        cisloHoduLab.setText(String.valueOf(kostkaNumRN + 1));
        kontrolaLab.setText(String.valueOf(kontrolaKostky + 1));

        znic();
        afterRoll(kostkaNumRN);
        //System.out.println(naTahu);


    }


    @FXML
    protected void printText() {
        System.out.println("press");
    }


    @FXML
    public void test() {
        hadiZebriky();


    }

    @FXML
    public void playerHandler() {
        int columnTest = arr[naTahu];
        int testScore = arr[naTahu];


        /** urcuje radu hrace   */
        while (testScore > 10) {
            testScore -= 10;
            row++;
        }
        row = rada - 1 - row;
        //System.out.println("row is: " + row);


        /** urcuje sloupec hrace   */
        while (columnTest > 10) {
            columnTest -= 10;
        }
        if (row % 2 == 0) {
            columnTest = (rada - 1) - (columnTest - 1);
        } else columnTest = columnTest - 1;
        //System.out.println("column is: " + columnTest);


        /**  vytvari hrace a upravuje ho  */
        if (naTahu == 0) {

            pole.add(hrac1, columnTest, row);
            hrac1.setFill(Color.DEEPSKYBLUE);
            pole.setValignment(hrac1, VPos.CENTER);
            pole.setHalignment(hrac1, HPos.CENTER);
        }
        if (naTahu == 1) {

            pole.add(hrac2, columnTest, row);
            hrac2.setFill(Color.DARKRED);
            pole.setValignment(hrac2, VPos.CENTER);
            pole.setHalignment(hrac2, HPos.CENTER);
        }
        row = 0;

        System.out.println("score: " + arr[naTahu]);


        /**  zmena hrace po odehrani tahu  */
        naTahu++;
        if ((naTahu + 1) > pocetHracu) {
            naTahu = 0;
        }


    }

    void afterRoll(int x) {


        /**  Secteni skore daneho hrace    */
        arr[naTahu] = arr[naTahu] + x + 1;

        /**  zmena skore v textu   */
        if (naTahu == 0) {
            scoreLabel1.setText("hrac 1 skore: " + arr[naTahu]);
        }
        if (naTahu == 1) {
            scoreLabel2.setText("hrac 2 skore: " + arr[naTahu]);
        }


        /**  Dosazeni maximalniho poctu bodu   */
        if (arr[naTahu] > maxScore) {
            arr[naTahu] = arr[naTahu] - (x + 1);


        }

        /**  Cislo tahu   */
        if (naTahu + 1 == pocetHracu) {
            tahLab.setText("Cislo tahu: " + (tah++));
        }
        System.out.println(naTahu);
        playerHandler();
    }


    @FXML
    protected void AutoPlay() throws InterruptedException {
        autoActive = !autoActive;
        timeDelay = 1;

        while (autoActive && !((arr[naTahu] == 100) || (Score2 == 100))) {
            System.out.println("Score1 = " + arr[naTahu] + " Score2 = " + Score2);
            roll();
        }

    }

    @FXML
    protected void scoreDebug() {
        Score2 = Score2 + 1;
        arr[naTahu] = arr[naTahu] + 1;
        scoreLabel1.setText("hrac 1 skore: " + arr[naTahu]);
        scoreLabel2.setText("hrac 2 skore: " + arr[naTahu]);

    }

    @FXML
    protected void onStartLogic() {
        arr = new int[pocetHracu + 4];
        arr[0] = Score1;
        arr[1] = Score2;
        arr[2] = Score3;
        arr[3] = Score4;


        scoreBox.getChildren().add(scoreLabel1);
        scoreBox.getChildren().add(scoreLabel2);
        scoreLabel1.setText("Score for player 1");
        scoreLabel2.setText("Score for player 2");
        hrac1.setStroke(Color.BLACK);
        hrac2.setStroke(Color.BLACK);

        /**  Vytvoreni cislic na desce  */

        for (int a = 1; a < rada * rada + 1; a++) {
            int aRow = a;
            int aCol = a;

            /**  Cisla rady   */
            while (aRow > 10) {
                aRow -= 10;
                row++;
            }
            row = rada - 1 - row;

            /**  Cisla sloupce   */
            while (aCol > 10) {
                aCol -= 10;
            }
            if (row % 2 == 0) {
                aCol = (rada - 1) - (aCol - 1);
            } else aCol = aCol - 1;

            /**  prirazeni cisla   */

            Label boxNum = new Label();
            boxNum.setAlignment(Pos.TOP_LEFT);
            boxNum.setText(String.valueOf(a));

            pole.setValignment(boxNum, VPos.TOP);
            pole.setHalignment(boxNum, HPos.LEFT);
            pole.add(boxNum, aCol, row);
            //System.out.println(aCol + " " + row + " " + a);

            row = 0;
        }

        /**  vytvoreni pocatecnich hracu   */
        pole.add(hrac1,0, 9);
        hrac1.setFill(Color.DEEPSKYBLUE);
        pole.add(hrac2, 0, 9);
        hrac2.setFill(Color.DARKRED);


    }


    protected void znic() {
        if (naTahu == 0) {
            pole.getChildren().remove(hrac1);

        }
        if (naTahu == 1) {
            pole.getChildren().remove(hrac2);
        }
    }


    protected void hadiZebriky(){



        posPrekazky = new int[pocetPrekazek * 2];
        hodnotaPrekazky = new int[pocetPrekazek * 2];
        for (int b = 0; b < pocetPrekazek * 2; b++ )
        {
            Random r = new Random();
            int bRan = r.nextInt(rada * rada -2 );
            int bRan2 = r.nextInt(15);

            Rectangle had = new Rectangle(20, 20);
            pole.setValignment(had, VPos.CENTER);
            pole.setHalignment(had, HPos.CENTER);


            int bCol = bRan + 2;
            int bRow = bRan + 2;


            /**  Cisla rady   */
            while (bRow > 10) {
                bRow -= 10;
                row++;
            }
            row = rada - 1 - row;

            /**  Cisla sloupce   */
            while (bCol > 10) {
                bCol -= 10;
            }
            if (row % 2 == 0) {
                bCol = (rada - 1) - (bCol - 1);
            } else bCol = bCol - 1;

            System.out.println("sloupec: " + bCol + ", rada: " + row);
            pole.add(had, bCol, row);

            if(bRan2 - 8 == 0){bRan2 = bRan2 + 1;}
            hodnotaPrekazky[b] = bRan2 - 8;
            System.out.println(bRan+" "+bRan2 + " "+ hodnotaPrekazky[b]);
            posPrekazky[b] = bRan;


            row = 0;





        }





    }
}

