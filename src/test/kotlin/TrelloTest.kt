import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import okhttp3.mockwebserver.MockWebServer

class TrelloTest {
    @Test
    fun getComments() {
        val trello = Trello("", "")
        val server = MockWebServer()
        trello.baseUrl = server.url("/").toString()

        val json = """
[
  {
   "id":"1000",
   "data": {
       "list":{"name":"aList","id":"2000"},
       "board":{"shortLink":"linkToBoard","name":"nameOfBoard","id":"3000"},
       "card":{"shortLink":"linkToCard","idShort":1,"name":"nameOfCard","id":"4000"},
       "text":"hoge hoge fuga fuga"
   },
   "date":"2017-03-29T07:46:13.073Z",
   "memberCreator":{"id":"5000","avatarHash":"hashToAvatar","fullName":"fullName","initials":"YK","username":"yusuke kokubo"}
  }
]
"""
        server.enqueue(MockResponse().setBody(json))

        val comments = trello.getComments("")

        assertThat(comments.size).isEqualTo(1)
        assertThat(comments[0].id).isEqualTo("1000")
        assertThat(comments[0].data.text).isEqualTo("hoge hoge fuga fuga")
        assertThat(comments[0].date).isEqualTo("2017-03-29T07:46:13.073Z")

        server.shutdown()
    }
}
