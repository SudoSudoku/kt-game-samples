package pl.cezaryregec.cellular.forest.output

import com.github.ajalt.mordant.AnsiColorCode
import com.github.ajalt.mordant.TermColors
import pl.cezaryregec.cellular.forest.cell.CellType

object Coloring {
    private val coloring = mapOf<CellType, AnsiColorCode>(
            Pair(CellType.LIVING_TREE, TermColors().green),
            Pair(CellType.BURNING_TREE, TermColors().red),
            Pair(CellType.DEAD_TREE, TermColors().gray)
    )

    fun get(cellType: CellType) : AnsiColorCode {
        return coloring.getValue(cellType)
    }
}