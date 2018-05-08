package restful;

public class MakeFutureHistory {

    private double gaussianRandomSeed(int lowBound, int highBound ) {
        double r = 0;
        int  loops = 6;
        for (int i = 0; i < loops; i += 1) {
            r += Math.random();
        }
        r /= loops;
        double seed = Math.floor(lowBound + r * ( highBound - lowBound + 1));
        return seed;
    }
    public double getGaussianRandomSeed() {
            // In reality, our speeds tend to run 0.7 sec to 1.5 but 1-in-a-100 will be 5 or more seconds...
            double seed = gaussianRandomSeed(0, 4);
            double r = Math.random();
            double scalar = 999;
            if (seed == 1.0) {
                scalar = 500;
            } else if (seed == 2.0) {
                scalar = 1500;
            } else if (seed == 3.0) {

                if (Math.random() > 0.9) {

                    scalar = 10000;
                } else {
                    scalar = 1000;
                }
            }
            return Math.random() * scalar;

    }


}
