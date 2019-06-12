package pl.cezaryregec.cellular.simple.output

import com.github.ajalt.mordant.AnsiColorCode
import pl.cezaryregec.cellular.simple.cell.Board
import pl.cezaryregec.cellular.simple.cell.Cell

class BoardPrinter {

    fun print(board: Board) {
        val cells = board.getBoard()
        for (cell in cells) {
            printCell(cell)
        }
        println()
    }

    fun printCell(cell: Cell) {
        val color = resolveColor(cell)
        print((color on color)(" "))
    }

    private fun resolveColor(cell: Cell): AnsiColorCode {
        val cellType = cell.cellType
        return Coloring.get(cellType)
    }

    fun clear() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }
}