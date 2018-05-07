package com.letshout.util

import org.slf4j.LoggerFactory

trait Logging {

    protected[this] val log = LoggerFactory.getLogger(this.getClass)

    def singleLine(value: Any): String = value.toString.replaceAll("\n"," ")
}