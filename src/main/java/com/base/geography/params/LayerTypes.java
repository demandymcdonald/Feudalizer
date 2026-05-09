package com.base.geography.params;

import com.base.component.InstanceType;
import com.base.geography.RawGeoContainer;
import com.google.common.collect.ImmutableMap;

import java.util.*;

public class LayerTypes {

    public static final LayerType ADM0 = new LayerType(InstanceType.HARDCODED, "adm_nation", "ADM", 0);
    public static final LayerType ADM1 = new LayerType(InstanceType.HARDCODED, "adm_region", "ADM", 1);
    public static final LayerType ADM2 = new LayerType(InstanceType.HARDCODED, "adm_subregion", "ADM", 2);
    public static final LayerType ADM3 = new LayerType(InstanceType.HARDCODED, "adm_county", "ADM", 3);
    public static final LayerType ADM4 = new LayerType(InstanceType.HARDCODED, "adm_metro_area", "ADM", 4);
    public static final LayerType ADM5 = new LayerType(InstanceType.HARDCODED, "adm_municipality", "ADM", 5);
    public static final LayerType ADM6 = new LayerType(InstanceType.HARDCODED, "adm_special_economic_zone", "ADM", 6);
    public static final LayerType ADM7 = new LayerType(InstanceType.HARDCODED, "adm_federal_land", "ADM", 7);
    public static final LayerType ADM8 = new LayerType(InstanceType.HARDCODED, "adm_other", "ADM", 8);

    public static final LayerType NAT0 = new LayerType(InstanceType.HARDCODED, "nat_water_fresh", "NAT", 0);
    public static final LayerType NAT1 = new LayerType(InstanceType.HARDCODED, "nat_water_salt", "NAT", 1);
    public static final LayerType NAT2 = new LayerType(InstanceType.HARDCODED, "nat_mountain", "NAT", 2);
    public static final LayerType NAT3 = new LayerType(InstanceType.HARDCODED, "nat_biome", "NAT", 3);
    public static final LayerType NAT4 = new LayerType(InstanceType.HARDCODED, "nat_natural_resource_above_ground", "NAT", 4);
    public static final LayerType NAT5 = new LayerType(InstanceType.HARDCODED, "nat_natural_resource_below_ground", "NAT", 5);



    private static final Map<Integer,LayerType> ADM_MAP = ImmutableMap.of(0,ADM0,1,ADM1,2,ADM2,3,ADM3,4,ADM4,5,ADM5,6,ADM6,7,ADM7,8,ADM8);
    public static LayerType getADMLayer(int current, int numEntries){
        int slot = (Math.clamp(current,0,ADM_MAP.size()) + 1) - Math.clamp(numEntries,1,ADM_MAP.size() + 1);
        if (slot >= 0){
            return ADM_MAP.get(current);
        }
        //This part works because we know the dataset always has all ADM levels < current.
        switch (numEntries){
            case 1 -> {
                return ADM0;
            }
            case 2 -> {
                if (current > 0){
                    return ADM3;
                } else {
                    return ADM0;
                }
            }
            case 3 -> {
                switch (current){
                    case 0 -> {
                        return ADM0;
                    }
                    case 1 -> {
                        return ADM1;
                    }
                    default -> {
                        return ADM3;
                    }
                }
            }
            default -> {
                return ADM_MAP.get(current + slot);
            }
        }
    }
    public static LayerType getADMLayer(RawGeoContainer container, int numEntries){
        return getADMLayer(container.getAdmLevel(), numEntries);
    }

}
