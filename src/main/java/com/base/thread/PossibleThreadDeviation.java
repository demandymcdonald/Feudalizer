package com.base.thread;

import com.Global.*;
import com.base.thread.flight.ThreadFlight;

import java.util.*;

public class PossibleThreadDeviation extends Exception{
    //# to call is basically the error code, ideally formatted as a 555 number
    public PossibleThreadDeviation(ThreadFlight flight, String numberToCall, String message){
        super(build(flight,numberToCall,message));

    }

    private static String build(ThreadFlight flight, String numberToCall, String message){
        return "Tower to " + flight.getCallsign() + "Possible Thread Deviation, prepare to copy: " + numberToCall + "... Message: " + message;
    }
}
