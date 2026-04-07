package com.objects.culture.term;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.objects.culture.Culture;

public interface CulturalObject<T extends DateMutableEntity<T>> {

    DMEReference<Culture> getCulture();
}
