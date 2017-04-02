package trello

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class TrelloAvatarParser: JsonAdapter<AvatarUrl>() {
    override fun fromJson(reader: JsonReader): AvatarUrl {
        return AvatarUrl("https://trello-avatars.s3.amazonaws.com/${reader.nextString()}/30.png)")
    }

    override fun toJson(writer: JsonWriter?, value: AvatarUrl) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}