package com.example.myapplication

const val HEADER_LINK = "link"
private const val DELIM_LINKS = ","
private const val DELIM_LINK_PARAM = ";"
private const val META_REL = "rel"
private const val META_NEXT = "next"

fun String.parseNextLink(): String? {
    val links = this.split(DELIM_LINKS)
    links.forEach { link ->
        val segments = link.split(DELIM_LINK_PARAM)
        if (segments.size >= 2) {
            val linkPart = segments[0].trim()
            if (linkPart.startsWith("<") && linkPart.endsWith(">")) {
                val pageLink = linkPart.substring(1, linkPart.length - 1)

                for (i in 1 until segments.size) {
                    val rel = segments[i].split("=")
                    val e = rel[0]
                    if (rel.size >= 2 && META_REL == rel[0].trim()) {
                        if (rel[1].startsWith("\"") && rel[1].endsWith("\"")) {
                            val relValue = rel[1].substring(1, rel[1].length - 1)
                            if (relValue == META_NEXT) {
                                return pageLink
                            }
                        }
                    }
                }
            }
        }
    }

    return null
}