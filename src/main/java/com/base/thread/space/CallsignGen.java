package com.base.thread.space;

import com.base.thread.ThreadTracon;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

import static com.Global.RANDOM;

public class CallsignGen {
    //Is this all a waste of compute? Yes. Does it make me happy? Yes. Will I assign meaning to the different airlines, to help with bugfixing? possibly!
    public static final String[] IFR()  {
        return new String[]{"Concurrent Pacific::CX::CPA", "ThreadJet::WS::WJA", "Stringwest Airlines::WN::SWA", "Double Airlines::DL::DAL", "Hashwaiian Airlines::HA::HAI", "Arrayska Airlines::AS::ASA", "Synchapore Airlines::SQ::SIA", "Atomic Airlines::AA::AAL", "Floatier Airlines::F9::FFT", "Emumarites Airlines::EK::UAE"};
    };


    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Triple<String,String,String> generateFlightCode(ThreadFlight.FlightType type){
        return switch (type){
            case VFR -> {
                yield generateVFRCallsign(false);
            }
            case VFR_FOLLOWING -> {
                yield generateVFRCallsign(true);
            }
            case IFR -> {
                yield generateIFRCallsign();
            }
        };
    }
    private static Triple<String,String,String> generateIFRCallsign(){
        List<String> ifr = List.of(IFR());
        String[] base = ifr.get(RANDOM.nextInt(ifr.size())).split("::");
        String number = generateNums(3,false);
        final String fullNum = base[0] + " " + number;
        final String shortNum = base[1] + " " + number;
        final String icao = base[2] + " " + number;
        return Triple.of(fullNum,shortNum,icao);
    }
    private static Triple<String,String,String> generateVFRCallsign(boolean withFollowing){
        final String fullNum =  "November " + generateNums(5,false);
        final String shortNum =  "N " + generateNums(4,false);
        String follow;
        if (withFollowing){
             follow = generateFollowing();
        } else {
            follow = " V/1200";
        }
        return Triple.of(fullNum + follow,shortNum + follow,shortNum + follow);
    }
    public static String generateTowerCode(){
        StringBuilder sb = new StringBuilder();
        sb.append("K");
        for (int i = 0; i < 3; i++) {
            int index = RANDOM.nextInt(CHARS.length());
            sb.append(CHARS.charAt(index));
        }
        if (sb.toString().equals("KPDX") || ThreadTracon.connect().getTowers().contains(sb.toString())){
            return generateTowerCode();
        }
        return sb.toString();
    }

    private static String generateFollowing(){
        String oct = generateNums(5,true);
        switch(oct){
            case "7500","7600","7700","1200":
                return generateFollowing();
            default:
                return " V/" + oct;
        }
    }
    private static String generateNums(int qty, boolean octal){
        StringBuilder sb = new StringBuilder();
        int max;
        if(octal){
            max = 7;
        } else {
            max = 9;
        }
        for (int i = 0; i < qty; i++) {
            int index = RANDOM.nextInt(max);
            sb.append(index);
        }
        return sb.toString();
    }
}
