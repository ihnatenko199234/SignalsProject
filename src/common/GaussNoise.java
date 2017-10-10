package common;
import java.util.Random;

public class GaussNoise extends Signal {

  public GaussNoise( double A, int t1, int f, double d ) {
    super( A, t1, f, d );
  }

  @Override
  protected double[][] generateSignal() {
    double[][] tab = new double[(int)(d*f)][2];
    Random rand = new Random();
    for(int i = 0; i < d*f; i++) {
      tab[i][0] = t1 + ((double) 1/f * i);
      tab[i][1] = (double) ( (rand.nextGaussian() * (2 * A)) - A );
    } 
    return tab;
  }

}
