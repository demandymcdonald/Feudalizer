package com.objects.culture.term;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.objects.culture.Culture;
import com.objects.culture.object.ICultureObject;

public interface CulturalObject<T extends DateMutableEntity<T>> extends ICultureObject {

    DMEReference<Culture> getCulture();
}
