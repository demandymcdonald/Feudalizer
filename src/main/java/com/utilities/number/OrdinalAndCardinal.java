package com.utilities.number;

import com.ibm.icu.text.RuleBasedNumberFormat;

import java.util.Locale;

public class OrdinalAndCardinal {
    private static final RuleBasedNumberFormat ordinal = new RuleBasedNumberFormat(Locale.ENGLISH, RuleBasedNumberFormat.ORDINAL);
    private static final RuleBasedNumberFormat spellout = new RuleBasedNumberFormat(Locale.ENGLISH, RuleBasedNumberFormat.SPELLOUT);
    public static String numeral(int number){
        return ordinal.format(number);
    }
    public static String cardinal(int number){
        return spellout.format(number);
    }
}
