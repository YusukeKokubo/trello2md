import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TrelloDateParser: JsonAdapter<LocalDateTime>() {
    companion object {
        val FACTORY: Factory = object: Factory {
            override fun create(type: Type?, annotations: MutableSet<out Annotation>?, moshi: Moshi?): JsonAdapter<*>? {
                return if (type == LocalDateTime::class.java) {
                    TrelloDateParser()
                } else {
                    null
                }
            }
        }
    }

    override fun fromJson(reader: JsonReader): LocalDateTime {
        // "date":"2017-03-29T07:46:13.073Z",
        val f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        return LocalDateTime.parse(reader.nextString(), f)
    }

    override fun toJson(writer: JsonWriter?, value: LocalDateTime?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}