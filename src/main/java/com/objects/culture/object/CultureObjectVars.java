package com.objects.culture.object;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;

public interface CultureObjectVars {
    static final double b = .23; //apathy peak as percent from start
    static final double c = 0.00022; //apathy decay
    static final double z = .125; // zealotry peak as percent from end. Should hit right as the they pass the Fanatic mark
    static final double f = .69; // zealotry drop-off  target
    static final double g = 2.2; //zealotry drop-off steepness
    static final double floor = .02; //kernal floor
    static final int pf = 2; //crushing power for normalization
    static final int oc = MAX_VALUE; // upper and lower bound for opinion values

}
