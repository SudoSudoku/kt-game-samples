package pl.cezaryregec.cellular.forest

import pl.cezaryregec.cellular.forest.cell.Board
import pl.cezaryregec.cellular.forest.cell.CellType
import pl.cezaryregec.cellular.forest.output.BoardPrinter

fun main() {
    println("Size: ")
    val size = readLine()!!.toInt()

    println("Update interval: ")
    val interval = readLine()!!.toLong()

    println("Spread probability: ")
    val spreadProbability = readLine()!!.toFloat()

    println("Autoignition probability: ")
    val autoignitionProbability = readLine()!!.toFloat()

    println("Rebirth probability: ")
    val rebirthProbability = readLine()!!.toFloat()

    val board = Board(
            spreadProbability = spreadProbability,
            autoignitionProbabilitiy = autoignitionProbability,
            rebirthProbability = rebirthProbability,
            size = size
    )
    val boardPrinter = BoardPrinter()

    println("Burning trees count: ")
    val burningTreesCount = readLine()!!.toInt()

    for (i in 0..burningTreesCount) {
        var randomx = (0..(size-1)).random()
        var randomy = (0..(size-1)).random()
        while (board.getBoard()[randomy][randomx].cellType == CellType.BURNING_TREE) {
            randomx = (0..(size-1)).random()
            randomy = (0..(size-1)).random()
        }
        board.set(randomx, randomy, CellType.BURNING_TREE)
    }

    for (i in 0..1000) {
        boardPrinter.clear()
        boardPrinter.print(board)
        board.step()
        Thread.sleep(interval)
    }
}