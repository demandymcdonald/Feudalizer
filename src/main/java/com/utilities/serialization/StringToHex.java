package com.utilities.serialization;

import com.Global.*;
import com.google.common.io.BaseEncoding;
import org.apache.commons.codec.binary.Hex;

import java.util.HexFormat;

public class StringToHex {
    public static String encode(String s){
        return BaseEncoding.base16().lowerCase().encode(s.getBytes());
    }
    public static String decode(String s){
        return new String(BaseEncoding.base16().lowerCase().decode(s));
    }
}
