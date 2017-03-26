import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type

object KotlinMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val boardId = args[0]
        val trello = Trello(args[1], args[2])
        val list_filter =
                if (args.size > 3 && args[3].isNotEmpty())
                    Regex(args[3].split(",").joinToString(separator = "|"))
                else
                    Regex(".*")
        val list_with_card = trello.getListsWithCard(boardId)
        var result = StringBuilder()

        list_with_card.filter { (list, cards) ->
            list.name.matches(list_filter)
        }.forEach { (list, cards) ->
            result.append("\n## ${list.name}\n")
            cards.forEach { (card, comments) ->
                result.append("\n### [:link:](${card.url}) ${card.name}")
                card.members.forEach { m ->
                    result.append(" ${avatarUrl(m)}")
                }
                result.append("\n")
                result.append(if (card.desc.isNotEmpty()) "\n> ${card.desc.replace("\n", "\n> ")}\n" else "")
                result.append("\n")
                comments.forEach { c ->
                    result.append("> ${avatarUrl(c.memberCreator)} ${c.data.text.replace("\n", "\n> ")}\n")
                }
            }
        }

        println(result.toString())
    }

    private fun avatarUrl(member: MemberCreator): String {
        return "![${member.username}](https://trello-avatars.s3.amazonaws.com/${member.avatarHash}/30.png)"
    }
}

class Trello(val key: String, val token: String) {

    fun getListsWithCard(boardId: String): List<Pair<TList, List<Pair<Card, List<Comment>>>>> {
        val lists = getLists(boardId).sortedBy { it.pos }
        val cards = getCards(boardId).sortedBy { it.pos }
        val comments = getComments(boardId).sortedBy { it.data.date }.reversed()

        return lists.map { list ->
            val cs = cards.filter { it.idList == list.id }.sortedBy(Card::pos).map { c ->
                val ccs = comments.filter { it.data.card.id == c.id }
                c to ccs
            }
            list to cs
        }
    }

    fun getLists(boardId: String): List<TList> {
        val json = get(boardId, "lists", "fields=name,pos")
        return parse(json, TList::class.java)
    }

    fun getCards(boardId: String): List<Card> {
        val json = get(boardId, "cards", "fields=name,idList,url,pos,desc&members=true&member_fields=avatarHash,fullName,initials,username")
        return parse(json, Card::class.java)
    }

    fun getComments(boardId: String): List<Comment> {
        val json = get(boardId, "actions", "filter=commentCard&fields=data,date")
        return parse(json, Comment::class.java)
    }

    private fun get(boardId: String, path: String, fields: String): String {
        val url = "https://api.trello.com/1/boards/$boardId/$path?$fields&key=$key&token=$token"

        println(url)

        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        return response.body().string()
    }

    private fun <T>parse(body: String, type: Type): List<T> {
        val moshi = Moshi.Builder().build()
        val t = Types.newParameterizedType(List::class.java, type)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(t)

        return adapter.fromJson(body)
    }
}