package com.victorlh.android.framework.logger

enum class NivelLogImpl(override val nivelLog: Int) : NivelLog {
    OFF(Integer.MAX_VALUE),
    ERROR(1000),
    WARN(800),
    INFO(600),
    DEBUG(500),
    TRACE(400),
    ALL(Integer.MIN_VALUE);
}