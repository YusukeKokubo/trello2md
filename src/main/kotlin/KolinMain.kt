import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.*

object KotlinMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val boardId = args[0]
        val trello = Trello(key = args[1], token = args[2])
        val list_filter =
                if (args.size > 3 && args[3].isNotEmpty())
                    Regex(args[3])
                else
                    Regex(".*")
        val list_with_card = trello.getListsWithCard(boardId)
        val md = Markdown()
        val hl = 1 // header level

        list_with_card.filter { (list, _) ->
            list.name.matches(list_filter)
        }.forEach { (list, cards) ->
            md.h(hl, list.name)
            cards.forEach { (card, comments) ->
                val card_members = card.members.map { "${avatarUrl(it)}" }.joinToString(" ")
                md.h(hl + 1, "[:link:](${card.url}) ${card.name} $card_members")
                md.quote(card.desc)
                comments.forEach { (id, data, date, memberCreator) ->
                    md.quote("----")
                    md.quote("${avatarUrl((memberCreator))} ${showDate(date)}")
                    if (data.text != null) {
                        md.quote(data.text)
                    } else if (data.attachment != null) {
                        md.quote("![${data.attachment.name}](${data.attachment.url})")
                    }
                }
            }
        }

        println(md.toString())
    }

    private fun showDate(date: LocalDateTime): String {
        val d = date.plusHours(9) // FIXME どうやったらデフォルトのoffsetを柔軟に設定できるの…
        val p = DateTimeFormatter.ofPattern("yyyy/MM/dd (E) HH:mm:ss")
        return d.atZone(ZoneId.systemDefault()).format(p)
    }

    private fun avatarUrl(member: MemberCreator): String {
        return "![${member.username}](https://trello-avatars.s3.amazonaws.com/${member.avatarHash}/30.png)"
    }
}
