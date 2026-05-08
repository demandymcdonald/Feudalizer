package com.base.loaders.remote;

import com.Global.*;
import com.base.loaders.ILoader;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class GlobalLoader implements ILoader {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());



        public void blah(){
            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setCRS(DefaultGeographicCRS.WGS84);
        }
        (DefaultGeographicCRS.WGS84);

}
