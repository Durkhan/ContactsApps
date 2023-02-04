package com.vholodynskyi.assignment.common

import kotlin.reflect.KProperty

class CustomDelegate(initialValue: String) {
    private var value: String = initialValue

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("Retrieving value: $value")
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("Setting value: $value")
        this.value = value
    }
}
