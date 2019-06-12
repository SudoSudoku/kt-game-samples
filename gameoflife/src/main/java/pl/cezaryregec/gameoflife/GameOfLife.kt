package pl.cezaryregec.gameoflife

import pl.cezaryregec.gameoflife.cell.Board
import pl.cezaryregec.gameoflife.cell.CellType
import pl.cezaryregec.gameoflife.output.BoardPrinter

fun main() {
    println("Size: ")
    val size = readLine()!!.toInt()

    println("Update interval: ")
    val interval = readLine()!!.toLong()

    val board = Board(size)
    val boardPrinter = BoardPrinter()
    for (i in 0..size) {
        val randomx = (0..(size-1)).random()
        val randomy = (0..(size-1)).random()
        board.set(randomx, randomy, CellType.LIVING)
    }

    for (i in 0..1000) {
        boardPrinter.clear()
        boardPrinter.print(board)
        board.step()
        Thread.sleep(interval)
    }
}