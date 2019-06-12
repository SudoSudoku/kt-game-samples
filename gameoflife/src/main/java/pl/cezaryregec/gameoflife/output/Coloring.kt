package pl.cezaryregec.gameoflife.output

import com.github.ajalt.mordant.AnsiColorCode
import com.github.ajalt.mordant.TermColors
import pl.cezaryregec.gameoflife.cell.CellType

object Coloring {
    private val coloring = mapOf<CellType, AnsiColorCode>(
            Pair(CellType.LIVING, TermColors().green),
            Pair(CellType.DEAD, TermColors().red)
    )

    fun get(cellType: CellType) : AnsiColorCode {
        return coloring.getValue(cellType)
    }
}