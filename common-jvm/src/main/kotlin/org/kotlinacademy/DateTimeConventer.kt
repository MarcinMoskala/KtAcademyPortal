package org.kotlinacademy

import com.google.gson.*
import java.lang.reflect.Type

abstract class StringJsonConverter<T> : JsonSerializer<T>, JsonDeserializer<T> {

    abstract fun toString(o: T): String?

    abstract fun fromString(o: String): T?

    override fun serialize(o: T, typeOfSrc: Type, jsonContext: JsonSerializationContext): JsonElement =
            JsonPrimitive(toString(o))

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, jsonContext: JsonDeserializationContext): T? =
            if (json.isJsonNull || json.asString.isEmpty()) null else fromString(json.asString)
}

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class DateTimeConverter : StringJsonConverter<DateTime>() {

    override fun toString(dateTime: DateTime): String? = dateTime.toDateFormatString()

    override fun fromString(str: String): DateTime? = str.parseDateTime()
}