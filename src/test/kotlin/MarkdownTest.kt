import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MarkdownTest {
    @Test
    fun h2() {
        val md = Markdown()
        md.h2("hoge")

        assertThat(md.toString().trim()).isEqualTo("## hoge")
    }

    @Test
    fun h() {
        var md = Markdown()
        md.h(1, "hoge")
        assertThat(md.toString().trim()).isEqualTo("# hoge")

        md = Markdown()
        md.h(2, "hoge")
        assertThat(md.toString().trim()).isEqualTo("## hoge")

        md = Markdown()
        md.h(3, "hoge")
        assertThat(md.toString().trim()).isEqualTo("### hoge")
    }
}