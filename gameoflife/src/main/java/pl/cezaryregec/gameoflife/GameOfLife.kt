package pl.cezaryregec.gameoflife

import pl.cezaryregec.gameoflife.cell.Board
import pl.cezaryregec.gameoflife.cell.CellType
import pl.cezaryregec.gameoflife.output.BoardPrinter

fun main() {
    println("Size: ")
    val size = readLine()!!.toInt()

    println("Update interval: ")
    val interval = readLine()!!.toLong()

    println("Living cell count: ")
    val livingCells = readLine()!!.toInt()

    val board = Board(size)
    val boardPrinter = BoardPrinter()
    for (i in 0..livingCells) {
        var randomx = (0..(size-1)).random()
        var randomy = (0..(size-1)).random()
        while (board.getBoard()[randomy][randomx].cellType == CellType.LIVING) {
            randomx = (0..(size-1)).random()
            randomy = (0..(size-1)).random()
        }
        board.set(randomx, randomy, CellType.LIVING)
    }

    for (i in 0..1000) {
        boardPrinter.clear()
        boardPrinter.print(board)
        board.step()
        Thread.sleep(interval)
    }
}