import java.io.File

fun LCS(a: List<Int>, b: List<Int>): List<Int> {
    val d = List(a.size) { MutableList(b.size) { Pair(0, Pair(-1, -1)) } }
    var x = 0
    var y = 0
    for (i in a.indices) {
        for (j in b.indices) {
            if (a[i] == b[j]) {
                d[i][j] =
                    if (i == 0 || j == 0) Pair(1, Pair(-1, -1)) else Pair(d[i - 1][j - 1].first + 1,
                        if (a[i - 1] == b[j - 1]) Pair(i - 1, j - 1) else d[i - 1][j - 1].second)
            } else {
                if ((if (i == 0) 0 else d[i - 1][j].first) > (if (j == 0) 0 else d[i][j - 1].first))
                    d[i][j] = if (i == 0) Pair(0, Pair(-1, -1)) else Pair(d[i - 1][j].first,
                        if (a[i - 1] == b[j]) Pair(i - 1, j) else d[i - 1][j].second)
                else
                    d[i][j] = if (j == 0) Pair(0, Pair(-1, -1)) else Pair(d[i][j - 1].first,
                        if (a[i] == b[j - 1]) Pair(i, j - 1) else d[i][j - 1].second)
            }
            if (d[i][j].first > d[x][y].first) {
                x = i
                y = j
            }
        }
    }
    var ans = listOf<Int>()
    while (x != -1) {
        ans += a[x]
        val x1 = d[x][y].second.first
        val y1 = d[x][y].second.second
        x = x1
        y = y1
    }
    return ans.reversed()
}

fun main() {
    val path1 = readLine()!!
    val path2 = readLine()!!
    val file1 = File(path1)
    val file2 = File(path2)
    val lines1 = file1.readLines()
    val lines2 = file2.readLines()
    val a = lines1.map { s -> s.hashCode() }
    val b = lines2.map { s -> s.hashCode() }
    val c = LCS(a, b)
    val ans = File("diff.html")
    if (!ans.exists())
        ans.createNewFile()
    var htmlText = "<head>\n<style type=\"text/css\">\n" +
            ".added{background-color: #49FF1C;}\n" +
            ".deleted{background-color: #BEBEBE;}\n" +
            ".changed{background-color: #37C2FF;}\n" +
            "</style>\n</head><body><table>"
    var it1 = 0
    var it2 = 0
    for (hash in c) {
        val flag1 = a[it1] == hash
        val flag2 = b[it2] == hash
        htmlText += "<tr><td valign=\"top\">"
        while (a[it1] != hash) {
            htmlText += "<div class=\" ${if (flag2) "changed" else "deleted"}\"><xmp>${lines1[it1]}\n</xmp></div>"
            ++it1
        }
        htmlText += "</td><td valign=\"top\">"
        while (b[it2] != hash) {
            htmlText += "<div class=\" ${if (flag1) "changed" else "added"}\"><xmp>${lines2[it2]}\n</xmp></div>"
            ++it2
        }
        htmlText += "</td></tr><tr><td valign=\"top\">"
        htmlText += "<xmp>${lines1[it1]}\n</xmp>"
        htmlText += "</td><td valign=\"top\">"
        htmlText += "<xmp>${lines2[it2]}\n</xmp>"
        htmlText += "</td></tr>"
        ++it1
        ++it2
    }
    val flag1 = it1 == a.size
    val flag2 = it2 == b.size
    htmlText += "<tr><td valign=\"top\">"
    while (it1 < a.size) {
        htmlText += "<div class=\" ${if (flag2) "changed" else "deleted"}\"><xmp>${lines1[it1]}\n</xmp></div>"
        ++it1
    }
    htmlText += "</td><td valign=\"top\">"
    while (it2 < b.size) {
        htmlText += "<div class=\" ${if (flag1) "changed" else "added"}\"><xmp>${lines2[it2]}\n</xmp></div>"
        ++it2
    }
    htmlText += "</td></tr></table></body>"
    ans.writeText(htmlText)
}
