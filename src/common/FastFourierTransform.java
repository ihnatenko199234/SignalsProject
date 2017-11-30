package common;
import static java.lang.Math.*;
 
public class FastFourierTransform {
 
    public static int bitReverse(int n, int bits) {
        int reversedN = n;
        int count = bits - 1;
 
        n >>= 1;
        while (n > 0) {
            reversedN = (reversedN << 1) | (n & 1);
            count--;
            n >>= 1;
        }
 
        return ((reversedN << count) & ((1 << bits) - 1));
    }
 
    static void fft(Complex2[] buffer) {
 
        int bits = (int) (log(buffer.length) / log(2));
        for (int j = 1; j < buffer.length / 2; j++) {
 
            int swapPos = bitReverse(j, bits);
            Complex2 temp = buffer[j];
            buffer[j] = buffer[swapPos];
            buffer[swapPos] = temp;
        }
 
        for (int N = 2; N <= buffer.length; N <<= 1) {
            for (int i = 0; i < buffer.length; i += N) {
                for (int k = 0; k < N / 2; k++) {
 
                    int evenIndex = i + k;
                    int oddIndex = i + k + (N / 2);
                    Complex2 even = buffer[evenIndex];
                    Complex2 odd = buffer[oddIndex];
 
                    double term = (-2 * PI * k) / (double) N;
                    Complex2 exp = (new Complex2(cos(term), sin(term)).mult(odd));
 
                    buffer[evenIndex] = even.add(exp);
                    buffer[oddIndex] = even.sub(exp);
                }
            }
        }
    }
 
//    public static void main(String[] args) {
//        double[] input = {1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0};
// 
//        Complex[] cinput = new Complex[input.length];
//        for (int i = 0; i < input.length; i++)
//            cinput[i] = new Complex(input[i], 0.0);
// 
//        fft(cinput);
// 
//        System.out.println("Results:");
//        for (Complex c : cinput) {
//            System.out.println(c);
//        }
//    }
}
 
class Complex2 {
    public final double re;
    public final double im;
 
    public Complex2() {
        this(0, 0);
    }
 
    public Complex2(double r, double i) {
        re = r;
        im = i;
    }
 
    public Complex2 add(Complex2 b) {
        return new Complex2(this.re + b.re, this.im + b.im);
    }
 
    public Complex2 sub(Complex2 b) {
        return new Complex2(this.re - b.re, this.im - b.im);
    }
 
    public Complex2 mult(Complex2 b) {
        return new Complex2(this.re * b.re - this.im * b.im,
                this.re * b.im + this.im * b.re);
    }
 
    @Override
    public String toString() {
        return String.format("(%f,%f)", re, im);
    }
}
