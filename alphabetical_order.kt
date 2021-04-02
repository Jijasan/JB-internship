val g = List<MutableSet<Int>>(26, { i -> mutableSetOf() })
val u = MutableList(26, { i -> -1 })
var cnt = 0

fun dfs(v: Int): Boolean {
    if (u[v] == -2)
        return true
    if (u[v] != -1)
        return false
    u[v] = -2
    g[v].forEach { to ->
        if (dfs(to))
            return true
    }
    u[v] = cnt
    ++cnt
    return false
}

fun main() {
    val n = readLine()!!.toInt()
    val names = List<String>(n, { i -> readLine()!! })
    for (i in 1 until n) {
        var it = 0
        while (it < names[i - 1].length && it < names[i].length && names[i - 1][it] == names[i][it])
            ++it
        if (it == names[i - 1].length || it == names[i].length)
            continue
        g[names[i][it] - 'a'].add(names[i - 1][it] - 'a')
    }
    for (i in 0 until 26) {
        if (dfs(i)) {
            print("Impossible")
            return
        }
    }
    var ans = CharArray(26)
    u.forEachIndexed { index, i ->
        ans[i] = 'a' + index
    }
    print(String(ans))
}
