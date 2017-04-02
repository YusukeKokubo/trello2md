import java.time.LocalDateTime

data class Card (
    val id: String,
    val name: String,
    val idList: String,
    val url: String,
    val pos: Float,
    val desc: String,
    val members: List<MemberCreator>
)

data class TList (
    val id: String,
    val name: String,
    val pos: Float
)

data class Comment (
    val id: String,
    val data: ActionData,
    val date: LocalDateTime,
    val memberCreator: MemberCreator
)

data class ActionData (
        val list: ActionDataList,
        val board: ActionDataBoard,
        val card: ActionDataCard,
        val text: String?,
        val attachment: TAttachment?
)

data class TAttachment (
        val url: String,
        val name: String,
        val id: String,
        val previewUrl: String,
        val previewUrl2x: String
)

data class ActionDataList (
        val name: String,
        val id: String
)

data class ActionDataBoard (
        val shortLink: String,
        val name: String,
        val id: String
)

data class ActionDataCard (
        val shortLink: String,
        val idShort: String,
        val name: String,
        val id: String
)

data class MemberCreator (
        val id: String,
        val avatarHash: String,
        val fullName: String,
        val initials: String,
        val username: String
)
