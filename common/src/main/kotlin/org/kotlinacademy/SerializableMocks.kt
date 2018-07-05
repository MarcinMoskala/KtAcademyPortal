package org.kotlinacademy

expect annotation class Serializable()

expect interface KSerializer<T> {
    val serialClassDesc: KSerialClassDesc
    fun save(output: KOutput, obj: T)
    fun load(input: KInput): T
}

expect interface KSerialClassDesc
expect open class SerialClassDescImpl: KSerialClassDesc {
    constructor(name: String)
}
expect abstract class KOutput {
    abstract fun writeStringValue(value: String)
}
expect abstract class KInput {
    abstract fun readStringValue(): String
}