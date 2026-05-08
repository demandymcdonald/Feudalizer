package com.utilities.number.bound_double;

public class BoundDoubles {
    public static BoundDbl percent(boolean neg){
        if(neg){
            return new PercentBoth(0);
        } else {
            return new PercentPos(0);
        }
    }
    public static BoundDbl percent(boolean neg, double initial){
        if(neg){
            return new PercentBoth(initial);
        } else {
            return new PercentPos(initial);
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
    public static BoundDbl dbl512(boolean neg){
        if(neg){
            return new Dbl512(0);
        } else {
            return new Dbl512Pos(0);
        }
    }
    public static BoundDbl dbl512(boolean neg, double initial){
        if(neg){
            return new Dbl512(initial);
        } else {
            return new Dbl512Pos(initial);
        }
    }
    public static BoundDbl dbl1024(boolean neg){
        if(neg){
            return new Dbl1024(0);
        } else {
            return new Dbl1024Pos(0);
        }
    }
    public static BoundDbl dbl2048(boolean neg, double initial){
        if(neg){
            return new Dbl2048(initial);
        } else {
            return new Dbl2048Pos(initial);
        }
    }
    public static BoundDbl dbl2048(boolean neg){
        if(neg){
            return new Dbl2048(0);
        } else {
            return new Dbl2048Pos(0);
        }
    }
    public static BoundDbl dbl1024(boolean neg, double initial){
        if(neg){
            return new Dbl1024(initial);
        } else {
            return new Dbl1024Pos(initial);
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
            return 99;
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
            return 255;
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
            return 255;
        }
    }
    private static class Dbl512 extends BoundDbl{
        Dbl512(double value) {
            super(value);
        }

        @Override
        public double getMin() {
            return -512;
        }

        @Override
        public double getMax() {
            return 511;
        }
    }
    private static class Dbl512Pos extends BoundDbl{
        Dbl512Pos(double value) {
            super(value);
        }
        @Override
        public double getMin() {
            return 0;
        }

        @Override
        public double getMax() {
            return 511;
        }
    }
    private static class Dbl1024 extends BoundDbl{
        Dbl1024(double value) {
            super(value);
        }

        @Override
        public double getMin() {
            return -1024;
        }

        @Override
        public double getMax() {
            return 1023;
        }
    }
    private static class Dbl1024Pos extends BoundDbl{
        Dbl1024Pos(double value) {
            super(value);
        }
        @Override
        public double getMin() {
            return 0;
        }

        @Override
        public double getMax() {
            return 1023;
        }
    }
    private static class Dbl2048 extends BoundDbl{
        Dbl2048(double value) {
            super(value);
        }

        @Override
        public double getMin() {
            return -2048;
        }

        @Override
        public double getMax() {
            return 2047;
        }
    }
    private static class Dbl2048Pos extends BoundDbl{
        Dbl2048Pos(double value) {
            super(value);
        }
        @Override
        public double getMin() {
            return 0;
        }

        @Override
        public double getMax() {
            return 2048;
        }
    }
}
