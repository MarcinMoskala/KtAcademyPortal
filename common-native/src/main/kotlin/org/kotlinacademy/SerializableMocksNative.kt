package org.kotlinacademy

actual annotation class Serializable

actual interface KSerializer<T> {
    actual val serialClassDesc: KSerialClassDesc
    actual fun save(output: KOutput, obj: T)
    actual fun load(input: KInput): T
}

actual interface KSerialClassDesc
actual open class SerialClassDescImpl: KSerialClassDesc {
    actual constructor(name: String)
}
actual abstract class KOutput {
    actual abstract fun writeStringValue(value: String)
}
actual abstract class KInput {
    actual abstract fun readStringValue(): String
}