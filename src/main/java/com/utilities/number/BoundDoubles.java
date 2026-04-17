package com.utilities.number;

public class BoundDoubles {
    public static BoundDbl percent(boolean neg){
        if(neg){
            return new PercentBoth(0);
        } else {
            return new PercentPos(0);
        }
    }
    public static BoundDbl percent(boolean neg, int integer){
        if(neg){
            return new PercentBoth(integer);
        } else {
            return new PercentPos(integer);
        }
    }
    public static BoundDbl dbl256(boolean neg){
        if(neg){
            return new Dbl256(0);
        } else {
            return new Dbl256Pos(0);
        }
    }
    public static BoundDbl dbl256(boolean neg, double initial){
        if(neg){
            return new Dbl256(initial);
        } else {
            return new Dbl256Pos(initial);
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
    private static class Dbl256 extends BoundDbl{
        Dbl256(double value) {
            super(value);
        }

        @Override
        public double getMin() {
            return -256;
        }

        @Override
        public double getMax() {
            return 256;
        }

    }
    private static class Dbl256Pos extends BoundDbl{
        Dbl256Pos(double value) {
            super(value);
        }
        @Override
        public double getMin() {
            return 0;
        }

        @Override
        public double getMax() {
            return 256;
        }

    }
}
