package pl.cezaryregec.fsm

import pl.cezaryregec.fsm.machine.*

fun main() {
    val mario = MarioFSM()
    mario.push(StandStillState())
    mario.push(JumpState())
    mario.push(RunState())
    mario.start()
    mario.pop()
    mario.push(DuckState())
}