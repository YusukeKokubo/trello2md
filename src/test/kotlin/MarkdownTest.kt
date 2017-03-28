import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MarkdownTest {
    @Test
    fun h2() {
        val md = Markdown()
        md.h2("hoge")

        assertThat(md.toString()).isEqualTo("## hoge")
    }

}