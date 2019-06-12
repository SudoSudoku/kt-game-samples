package pl.cezaryregec.cellular.simple

import pl.cezaryregec.cellular.simple.cell.Board
import pl.cezaryregec.cellular.simple.cell.CellType
import pl.cezaryregec.cellular.simple.output.BoardPrinter

fun main() {
    println("Size: ")
    val size = readLine()!!.toInt()

    println("Update interval: ")
    val interval = readLine()!!.toLong()

    val board = Board(size)
    val boardPrinter = BoardPrinter()
    for (i in 0..size) {
        val randomx = (0..(size-1)).random()
        board.set(randomx, CellType.LIVING)
    }

    for (i in 0..1000) {
        boardPrinter.clear()
        boardPrinter.print(board)
        board.step()
        Thread.sleep(interval)
    }
}