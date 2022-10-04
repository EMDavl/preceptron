package ru.itis;

public class Perceptron2 {
    double[] x;
    double y;
    double[][] wxh;
    double[] why;
    double[] h;
    double[][] pat = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    double[] r = {0, 1, 1, 0};

    public Perceptron2() {
        x = new double[pat[0].length];
        h = new double[2];
        wxh = new double[x.length][h.length];
        why = new double[h.length];
        init();
        study();
        for (int p = 0; p < pat.length; p++) {
            for (int i = 0; i < x.length; i++)
                x[i] = pat[p][i];
            cy();
            System.out.println("y=" + y);
        }
    }

    public void init() {
        System.out.println("Начальные значения весов первого слоя");
        for (int i = 0; i < wxh.length; i++) {
            for (int j = 0; j < wxh[i].length; j++) {
                wxh[i][j] = Math.random() * 0.3 + 0.1;
                System.out.println("wxh[" + i + "][" + j + "]=" + wxh[i][j]);
            }
        }
        System.out.println("Начальные значения весов второго слоя");
        for (int i = 0; i < why.length; i++) {
            why[i] = Math.random() * 0.3 + 0.1;
            System.out.println("why[" + i + "]=" + why[i]);
        }
    }

    public void cy() {
        for (int i = 0; i < h.length; i++) {
            h[i] = 0;
            for (int j = 0; j < x.length; j++) {
                h[i] += x[j] * wxh[j][i];
            }

            if (h[i] > 0.5)
                h[i] = 1;
            else
                h[i] = 0;

        }
        y = 0;
        for (int i = 0; i < h.length; i++) {
            y += h[i] * why[i];
        }
        if (y > 0.5)
            y = 1;
        else
            y = 0;

    }

    public void study() {
        double[] err = new double[h.length];
        double gEr = 0;
        double m = 0;
        do {
            gEr = 0;

            for (int p = 0; p < pat.length; p++) {
                for (int i = 0; i < x.length; i++)
                    x[i] = pat[p][i];
                cy();
                double lEr = r[p] - y;

                gEr += Math.abs(lEr);
                for (int i = 0; i < h.length; i++)
                    err[i] = lEr * why[i];
                for (int i = 0; i < x.length; i++) {
                    for (int j = 0; j < h.length; j++) {
                        wxh[i][j] += 0.1 * err[j] * x[i];
                    }
                }
                for (int i = 0; i < h.length; i++)
                    why[i] += 0.1 * lEr * h[i];

            }
            m++;
        } while (gEr != 0);

        System.out.println("Количество итераций равно " + m);
    }


    public static void main(String[] args) {

        Perceptron2 perceptron2 = new Perceptron2();
    }
}