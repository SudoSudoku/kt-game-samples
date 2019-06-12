package pl.cezaryregec.gameoflife.output

import com.github.ajalt.mordant.AnsiColorCode
import com.github.ajalt.mordant.TermColors
import pl.cezaryregec.gameoflife.cell.Board
import pl.cezaryregec.gameoflife.cell.Cell

class BoardPrinter {

    fun print(board: Board) {
        val cells = board.getBoard()
        for (line in cells) {
            printLine(line)
        }
    }

    fun printLine(line: List<Cell>) {
        for (cell in line) {
            val color = resolveColor(cell)
            print((color on color)(" "))
        }
        println()
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