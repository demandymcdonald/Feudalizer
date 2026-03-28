//package com;
//
//import com.base.DMRegistry;
//import com.utilities.LoadingManager;
//import com.utilities.ThreadSpecific;
//
//import java.nio.file.Path;
//import java.time.LocalDate;
//import java.util.function.Supplier;
//
//public class GlobalVariableContainer  {
//    @Override
//    public Type specificTypeName() {
//        return Type.GLOBAL_VARIABLE_CONTAINER;
//    }
//
//    @Override
//    public void onThreadInit() {
//
//    }
//
//    private static final ThreadLocal<LocalDate> CURRENT_DATE =  ThreadLocal.withInitial(new Supplier<LocalDate>() {
//        @Override
//        public LocalDate get() {
//            return DEFAULT_DATE;
//        }
//    });
//
//    public static final LocalDate DEFAULT_DATE = LocalDate.of(2415,12,24);
//
//
//    private static final LoadingManager LOADING_MANAGER = new LoadingManager();
//
//
//
//
//}
