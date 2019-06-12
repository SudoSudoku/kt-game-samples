package pl.cezaryregec.cellular.simple.cell

enum class CellType {
    LIVING,
    DEAD
}

class Cell(var cellType: CellType)

class Board(val size: Int = 10) {
    private var board: MutableList<Cell>

    init {
        board = MutableList(size) { _ -> Cell(CellType.DEAD) }
    }

    fun getBoard(): List<Cell> = board.toList()

    fun set(x: Int, cellType: CellType) {
        board[x] = Cell(cellType)
    }

    fun step() {
        val newBoard = mutableListOf<Cell>()

        for (cellIndex in 0..(board.size - 1)) {
            val currentCell = board[cellIndex]
            var resultType = CellType.DEAD
            var livingNeighborsCount = countNeighbors(CellType.LIVING, cellIndex)
            val isAlive = currentCell.cellType == CellType.LIVING

            if (isAlive) {
                livingNeighborsCount -= 1
            }

            val isBorn = livingNeighborsCount == 2

            val isLonely = livingNeighborsCount < 1
            val isOvercrowded = isAlive && livingNeighborsCount == 2

            if ((!isAlive && isBorn) || (isAlive && !isLonely && !isOvercrowded)) {
                resultType = CellType.LIVING
            }

            newBoard.add(Cell(resultType))
        }

        board = newBoard
    }

    private fun countNeighbors(cellType: CellType, cellIndex: Int) : Int {
        var result = 0

        for (currentCellIndex in (cellIndex - 1)..(cellIndex + 1)) {
            if (currentCellIndex >= 0 && currentCellIndex < board.size) {
                val cell = board[currentCellIndex]
                if (cell.cellType == cellType) {
                    result += 1
                }
            }
        }
        return result
    }
}