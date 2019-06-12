package pl.cezaryregec.fsm.machine

class MarioFSM : FSMStack<MarioFSM>() {
    var horizontalVelocity = 0.0
    var verticalVelocity = 0.0
    var lastJump = 0L
    var height = 1.0

    override fun activateState() {
        if (!isEmpty()) {
            val state = peek()
            state.describe(this)
        }
    }
}

class StandStillState : State<MarioFSM> {
    override fun describe(subject: MarioFSM) {
        println("Mario is standing still")
        subject.horizontalVelocity = 0.0
        subject.height = 1.0
    }
}

class RunState : State<MarioFSM> {
    override fun describe(subject: MarioFSM) {
        println("Mario is running!")
        subject.horizontalVelocity = 11.0
        subject.height = 1.0
    }
}

class JumpState : State<MarioFSM> {
    override fun describe(subject: MarioFSM) {
        if (System.currentTimeMillis() - subject.lastJump > 60000) {
            println("Mario is jumping!")
            subject.verticalVelocity = 3.0
            subject.height = 1.0
            subject.lastJump = System.currentTimeMillis()
        }

        subject.pop()
    }
}

class DuckState : State<MarioFSM> {
    override fun describe(subject: MarioFSM) {
        println("Mario is ducking")
        subject.verticalVelocity = 0.0
        subject.horizontalVelocity = 0.0
        subject.height = 0.5
    }
}