import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TrelloDateParser: JsonAdapter<ZonedDateTime>() {
    override fun fromJson(reader: JsonReader): ZonedDateTime {
        // "date":"2017-03-29T07:46:13.073Z",
        val f = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault())

        return ZonedDateTime.parse(reader.nextString(), f)
    }

    override fun toJson(writer: JsonWriter?, value: ZonedDateTime?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}