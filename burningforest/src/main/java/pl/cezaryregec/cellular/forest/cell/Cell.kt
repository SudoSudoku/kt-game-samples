package pl.cezaryregec.cellular.forest.cell

enum class CellType {
    LIVING_TREE,
    BURNING_TREE,
    DEAD_TREE
}

class Cell(var cellType: CellType)

class Board(
        val spreadProbability: Float = 0.25f,
        val autoignitionProbabilitiy: Float = 0.25f,
        val rebirthProbability: Float = 0.25f,
        val size: Int = 10,
        val randomResolution: Int = 100
) {
    private var board: MutableList<MutableList<Cell>>

    init {
        board = MutableList(size) { _ -> MutableList(size) { _ -> Cell(CellType.LIVING_TREE) } }
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
            var resultType = CellType.DEAD_TREE

            if (currentCell.cellType != CellType.BURNING_TREE) {
                val burningNeighbors = countNeighbors(CellType.BURNING_TREE, lineIndex, cellIndex)
                val isAlive = currentCell.cellType == CellType.LIVING_TREE
                val isDead = currentCell.cellType == CellType.DEAD_TREE

                val willBurn = burningNeighbors.toFloat() / 8.0 > (1.0 - spreadProbability)
                val willAutoignite = (0..randomResolution).random().toFloat() / randomResolution > (1.0 - autoignitionProbabilitiy)
                val willBeReborn = (0..randomResolution).random().toFloat() / randomResolution > (1.0 - rebirthProbability)

                if (isAlive && (willBurn || willAutoignite)) {
                    resultType = CellType.BURNING_TREE
                } else if (isAlive || (isDead && willBeReborn)) {
                    resultType = CellType.LIVING_TREE
                }
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