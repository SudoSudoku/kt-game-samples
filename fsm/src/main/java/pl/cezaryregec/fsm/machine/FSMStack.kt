package pl.cezaryregec.fsm.machine

abstract class FSMStack<SUBJECT> {
    private val stack : MutableList<State<SUBJECT>> = mutableListOf<State<SUBJECT>>()
    private var isRunning = false

    fun start() {
        isRunning = true
        activateState()
    }

    fun push(state : State<SUBJECT>) {
        stack.add(state)

        if (isRunning) {
            activateState()
        }
    }

    abstract fun activateState()

    fun pop() : State<SUBJECT> {
        val result = stack.removeAt(stack.lastIndex)

        if (isRunning) {
            activateState()
        }

        return result
    }

    fun isEmpty() : Boolean = stack.isEmpty()

    fun peek() : State<SUBJECT> = stack[stack.lastIndex]
}

interface State<SUBJECT> {
    fun describe(subject: SUBJECT)
}