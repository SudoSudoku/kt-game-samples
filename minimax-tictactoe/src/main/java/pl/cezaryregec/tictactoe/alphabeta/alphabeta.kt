package pl.cezaryregec.tictactoe.alphabeta

import kotlin.math.max
import kotlin.math.min

enum class GameState(val score: Int = 0) {
    TIE,
    WIN(100),
    LOSE(-100)
}

enum class Players(val character: Char = '-') {
    PLAYER('x'),
    COMPUTER('o'),
    EMPTY(' ')
}

class Move(var x: Int = -1, var y: Int = -1, var score: Int = 0)

fun isMoveLeft(board: MutableList<MutableList<Char>>): Boolean {
    for (i in 0..2) {
        for (j in 0..2) {
            if (board[i][j] == Players.EMPTY.character) return true
        }
    }

    return false
}

fun resolveState(board: MutableList<MutableList<Char>>, player: Players): GameState {
    for (row in 0..2) {
        if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
            if (board[row][0] == player.character) return GameState.WIN
            else if (board[row][0] != Players.EMPTY.character) return GameState.LOSE
        }
    }

    for (col in 0..2) {
        if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
            if (board[0][col] == player.character) return GameState.WIN
            else if (board[0][col] != Players.EMPTY.character) return GameState.LOSE
        }
    }

    var diagonally = true
    for (i in 1..2) {
        if (board[i - 1][i - 1] != board[i][i]) {
            diagonally = false
        }
    }

    if (diagonally) {
        if (board[0][0] == player.character) return GameState.WIN
        else if (board[0][0] != Players.EMPTY.character) return GameState.LOSE
    }

    diagonally = true
    for (i in 1..2) {
        if (board[i - 1][-i + 3] != board[i][-i + 2]) {
            diagonally = false
        }
    }

    if (diagonally) {
        if (board[2][0] == player.character) return GameState.WIN
        else if (board[2][0] != Players.EMPTY.character) return GameState.LOSE
    }

    return GameState.TIE
}

fun resolveBestMove(board: MutableList<MutableList<Char>>, isHuman: Boolean = false, depth: Int = 0, alpha: Int = Int.MIN_VALUE, beta: Int = Int.MAX_VALUE): Move {
//fun resolveBestMove(board: MutableList<MutableList<Char>>, isHuman: Boolean = false, depth: Int = 0): Move {
    val player = if (isHuman) Players.PLAYER else Players.COMPUTER
    val state = resolveState(board, player)

    if (state != GameState.TIE) return Move(score = if (isHuman) state.score + depth else state.score - depth)
    else if (!isMoveLeft(board)) return Move(score = 0)

    var best = Move(score = if (isHuman) 1000 else -1000)

    var newAlpha = alpha
    var newBeta = beta

    var isSettled = false

    for (i in 0..2) {
        if (isSettled) break
        for (j in 0..2) {
            if (board[i][j] == Players.EMPTY.character) {
                board[i][j] = player.character
                val extreme = { a: Move, b: Move ->
                    if (isHuman)
                        if (a.score < b.score) a else b
                    else
                        if (a.score > b.score) a else b
                }
                val current = Move(i, j, resolveBestMove(board, !isHuman, depth + 1, newAlpha, newBeta).score)
                best = extreme(best, current)
                board[i][j] = Players.EMPTY.character

                if (isHuman) {
                    newAlpha = max(newAlpha, best.score)
                } else {
                    newBeta = max(newBeta, best.score)
                }

                if (newBeta <= newAlpha) {
                    isSettled = true
                    break
                }
            }
        }
    }

    return best
}

fun isGameFinished(board: MutableList<MutableList<Char>>): Boolean =
        resolveState(board, Players.PLAYER) == GameState.WIN ||
                resolveState(board, Players.COMPUTER) == GameState.WIN ||
                !isMoveLeft(board)

fun main() {
    printHeader()

    val board = MutableList(3) { _ -> MutableList(3) { _ -> Players.EMPTY.character } }

    while (!isGameFinished(board)) {
        printBoard(board)
        print("\n")
        print("Place " + Players.PLAYER.character + " on: (x, y) ")
        val (y, x) = readLine()!!.replace(" ", "")
                .split(',')
                .map { element -> element.toInt() }

        if (x < 0 || x > 2 || y < 0 || y > 2 || board[x][y] != Players.EMPTY.character) {
            print("Taken!\n")
            continue
        }

        board[x][y] = Players.PLAYER.character

        val bestMove = resolveBestMove(board)
        if (bestMove.x != -1 && bestMove.y != -1) {
            board[bestMove.x][bestMove.y] = Players.COMPUTER.character
        }
    }

    val finishedState = resolveState(board, Players.PLAYER)
    when (finishedState) {
        GameState.WIN -> print("== You won! ==\n")
        GameState.LOSE -> {
            printBoard(board)
            print("== You lose! ==\n")
        }
        else -> print("== Tie! ==")
    }
}

fun printHeader() {
    print("\n")
    print("=   TIC TAC TOE    =\n")
    print(" alpha beta edition \n")
}

fun printBoard(board: MutableList<MutableList<Char>>) {
    print("\n")
    for (i in 0..2) {
        for (j in 0..2) {
            print(board[i][j])
            if (j != 2) {
                print("|")
            }
        }
        print("\n")
        if (i != 2) {
            for (j in 0..4) {
                if ((j + 1) % 2 == 0) {
                    print("+")
                } else {
                    print("-")
                }
            }
            print("\n")
        }
    }
}

fun minimax(depth: Int = 0, nodeIndex: Int = 0, isMax: Boolean = true, values: List<Int>, alpha: Int = Int.MIN_VALUE, beta: Int = Int.MAX_VALUE): Int {
    if (depth == 3) return values[nodeIndex]
    var best = if (isMax) Int.MIN_VALUE else Int.MAX_VALUE
    var newAlpha = alpha
    var newBeta = beta

    for (i in 0..1) {
        val value = minimax(depth + 1, 2 * nodeIndex + i, !isMax, values, newAlpha, newBeta)
        best = if (isMax) max(best, value) else min(best, value)

        if (isMax)
            newAlpha = max(newAlpha, best)
        else
            newBeta = min(newBeta, best)

        if (beta <= alpha) return best
    }

    return best
}