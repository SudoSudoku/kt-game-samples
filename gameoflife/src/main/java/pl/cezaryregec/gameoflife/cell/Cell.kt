package pl.cezaryregec.gameoflife.cell

enum class CellType {
    LIVING,
    DEAD
}

class Cell(var cellType: CellType)

class Board(val size: Int = 10) {
    private var board: MutableList<MutableList<Cell>>

    init {
        board = MutableList(size) { _ -> MutableList(size) { _ -> Cell(CellType.DEAD) } }
    }

    fun getBoard(): List<List<Cell>> = board.toList()

    fun set(x: Int, y: Int, cellType: CellType) {
        board[y][x] = Cell(cellType)
    }

    fun step() {
        val newBoard = mutableListOf<MutableList<Cell>>()

        for (i in 0..(board.size - 1)) {
            newBoard.add(lineStep(i))
        }

        board = newBoard
    }

    private fun lineStep(lineIndex: Int): MutableList<Cell> {
        val line = mutableListOf<Cell>()

        for (cellIndex in 0..(board[lineIndex].size - 1)) {
            val currentCell = board[lineIndex][cellIndex]
            var resultType = CellType.DEAD
            val livingNeighborsCount = countNeighbors(CellType.LIVING, lineIndex, cellIndex)

            val isAlive = currentCell.cellType == CellType.LIVING
            val isBorn = livingNeighborsCount == 3

            val isLonely = livingNeighborsCount < 2
            val isOvercrowded = livingNeighborsCount > 3

            if ((!isAlive && isBorn) || (isAlive && (!isLonely && !isOvercrowded))) {
                resultType = CellType.LIVING
            }

            line.add(Cell(resultType))
        }

        return line
    }

    private fun countNeighbors(cellType: CellType, line: Int, cell: Int) : Int {
        var result = 0

        for (currentLineIndex in (line - 1)..(line + 1)) {
            if (currentLineIndex >= 0 && currentLineIndex < board.size) {
                result += countNeighborsInLine(cellType, currentLineIndex, cell)
            }
        }

        if (board[line][cell].cellType == cellType) {
            result -= 1
        }

        return result
    }

    private fun countNeighborsInLine(cellType: CellType, lineIndex: Int, cellIndex: Int) : Int {
        var result = 0

        for (currentCellIndex in (cellIndex - 1)..(cellIndex + 1)) {
            if (currentCellIndex >= 0 && currentCellIndex < board[lineIndex].size) {
                val cell = board[lineIndex][currentCellIndex]
                if (cell.cellType == cellType) {
                    result += 1
                }
            }
        }

        return result
    }
}