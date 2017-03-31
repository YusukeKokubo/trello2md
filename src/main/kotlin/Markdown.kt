class Markdown {
    val body: MutableList<String> = ArrayList()

    fun h(header_level: Int,text: String) {
        var ns = StringBuffer()
        for (i in 1 .. header_level) ns.append("#")
        body.add("$ns $text")
    }

    fun h1(text: String) {
        body.add("# $text")
    }

    fun h2(text: String) {
        body.add("## $text")
    }

    fun h3(text: String) {
        body.add("### $text")
    }

    fun h4(text: String) {
        body.add("#### $text")
    }

    fun quote(text: String) {
        val s = text.replace("\n", "\n> ")
        body.add("> $s")
    }

    override fun toString(): String {
        return body.joinToString(separator = "\n\n", prefix = "\n", postfix = "\n")
    }
}