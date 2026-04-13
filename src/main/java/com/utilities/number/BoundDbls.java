package com.utilities.number;

public class BoundDbls {
    public static BoundDbl getPercent(boolean neg){
        if(neg){
            return new PercentBoth(0);
        } else {
            return new PercentPos(0);
        }
    }

    private static class PercentBoth extends BoundDbl{

        PercentBoth(double value) {
            super(value);
        }

        @Override
        public double getMin() {
            return -100;
        }

        @Override
        public double getMax() {
            return 100;
        }
    }
    private static class PercentPos extends BoundDbl{

        PercentPos(double value) {
            super(value);
        }

        @Override
        public double getMin() {
            return 0;
        }

        @Override
        public double getMax() {
            return 100;
        }
    }
}
