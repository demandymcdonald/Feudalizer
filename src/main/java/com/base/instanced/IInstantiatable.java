package com.base.instanced;

import com.utilities.id.Identifiable;

public interface IInstantiatable<T extends IInstantiatable<T,I,ID>,I extends IInstance<T,I,ID>,ID> extends Identifiable<ID> {


}
