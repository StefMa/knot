package de.halfbit.knot.detekt.rules

import de.halfbit.knot.knot
import io.reactivex.subjects.PublishSubject

class Demo {

    private data class State(val value: String)
    private object Change
    private object Action

    private val actions = PublishSubject.create<Unit>()

    private val knot = knot<State, Change, Action> {
        state { initial = State("empty") }
        changes { reduce { only } }
        actions {
            perform<Action> {
                actions
                    .map { Change }
            }
        }
    }

}