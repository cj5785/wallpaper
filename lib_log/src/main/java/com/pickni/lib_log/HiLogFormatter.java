package com.pickni.lib_log;

public interface HiLogFormatter<T> {

    String format(T data);
}