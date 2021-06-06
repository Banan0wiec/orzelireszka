package pl.janek.orzeireszka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate invoked");

        final ImageView coinView = findViewById(R.id.moneta);

        View button = findViewById(R.id.machinalosującaxd);

        MojListener pierwszy = new MojListener(coinView);
        button.setOnClickListener(pierwszy);

    }


}


class MojListener implements View.OnClickListener {
    ImageView widokMonety;


    volatile boolean monetaSieKreci = false;

    public MojListener(ImageView view) {
        this.widokMonety = view;
    }

    private void nextimage(int imageid, long milisecondsnumber) {
        widokMonety.setImageResource(imageid);
        widokMonety.invalidate();
        waiting(milisecondsnumber);
    }


    @Override
    public void onClick(View v) {

        final int maxSpinsNumber = (int) (10 + 10 * Math.random());
        System.out.println("wylosowałem: " + maxSpinsNumber);

        final int resztaZDzielenia = maxSpinsNumber % 2;
        final int slowingDownSpining1 = (int) (maxSpinsNumber * 2 / 4);
        final int slowingDownSpining2 = (int) (maxSpinsNumber * 4 / 5);
        System.out.println("ułamek z maksa: " + slowingDownSpining1);
        System.out.println("działa przycisk");

        Thread t = new Thread() {
            public void run() {
                spinning(maxSpinsNumber, resztaZDzielenia, slowingDownSpining1, slowingDownSpining2);
                System.out.println();
            }
        };

        t.start();

    }

    private void spinning(int maksymalnaLiczbaObrotów, int reszta, double ułamekspowolnienia1, double ułamekspwolnienia2) {

        boolean jestreszka = false;

        if (monetaSieKreci) {
            return;
        }
        monetaSieKreci = true;

        long iloraz = 0;

        for (int n = 1; n < maksymalnaLiczbaObrotów; n++) {

            double czynnik = 2.5;
            if (n > ułamekspowolnienia1) {
                czynnik = 4;
            }
            if (n > ułamekspwolnienia2) {
                czynnik = 6;
            }

            iloraz = (long) (czynnik * n);

            obracanieOrzel(iloraz);

            obracaniereszka2(iloraz);

        }


        if (reszta == 1) {
            obracanieOrzel(iloraz);
            nextimage(R.drawable.reszka4, iloraz);
            jestreszka = true;
        } else
            nextimage(R.drawable.orzel1, iloraz);
        jestreszka = false;


        monetaSieKreci = false;


    }


    private void obracanieOrzel(long liczbamilisekund) {

        nextimage(R.drawable.orzel1, liczbamilisekund);
        nextimage(R.drawable.orzel2, liczbamilisekund);
        nextimage(R.drawable.orzel3, liczbamilisekund);
        nextimage(R.drawable.orzel4, liczbamilisekund);
//        waiting(50);
        nextimage(R.drawable.reszka1, liczbamilisekund);
        nextimage(R.drawable.reszka2, liczbamilisekund);
        nextimage(R.drawable.reszka3, liczbamilisekund);
//        nextimage(R.drawable.reszka4);

    }

    private void obracaniereszka2(long liczbamilisekund) {
        nextimage(R.drawable.reszka4, liczbamilisekund);
        nextimage(R.drawable.reszka3, liczbamilisekund);
        nextimage(R.drawable.reszka2, liczbamilisekund);
        nextimage(R.drawable.reszka1, liczbamilisekund);
//        waiting(50);
        nextimage(R.drawable.orzel4, liczbamilisekund);
        nextimage(R.drawable.orzel3, liczbamilisekund);
        nextimage(R.drawable.orzel2, liczbamilisekund);
//        nextimage(R.drawable.orzel1);

    }


    private void waiting(long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

