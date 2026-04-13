package com.utilities.number;

public class BoundInts {

    public static BoundInt Percent(boolean negative){
        if(negative){
            return new BothPercent(0);
        } else {
            return new PosPercent(0);
        }
    }
    public static BoundInt Int256(boolean negative){
        if(negative){
            return new Both256(0);
        } else {
            return new Pos256(0);
        }
    }
    private static class BothPercent extends BoundInt {
        public BothPercent(int number) {
            super(number);
        }

        @Override
        public int getMin() {
            return -100;
        }

        @Override
        public int getMax() {
            return 100;
        }
    }
    private static class PosPercent extends BoundInt {
        public PosPercent(int number) {
            super(number);
        }

        @Override
        public int getMin() {
            return 0;
        }

        @Override
        public int getMax() {
            return 100;
        }

    }
    private static class Both256 extends BoundInt {
        public Both256(int number) {
            super(number);
        }

        @Override
        public int getMin() {
            return -256;
        }

        @Override
        public int getMax() {
            return 256;
        }

    }
    private static class Pos256 extends BoundInt {
        public Pos256(int number) {
            super(number);
        }

        @Override
        public int getMin() {
            return 0;
        }

        @Override
        public int getMax() {
            return 256;
        }
    }
}
