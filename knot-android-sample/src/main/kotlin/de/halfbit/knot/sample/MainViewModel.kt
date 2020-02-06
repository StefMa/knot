package de.halfbit.knot.sample

import androidx.lifecycle.ViewModel
import de.halfbit.knot.knot
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val knot = knot<State, Change, Action> {
        state {
            initial = State.Initial

            // observe state on Main thread
            observeOn = AndroidSchedulers.mainThread()
        }
        changes {
            reduce { change ->
                // this is the current state
                when (change) {
                    Change.Load -> when (this) {
                        State.Initial -> State.Loading + Action.Load

                        // By returning "only" (the same state we received) from
                        // here we ignore every follow up events coming from the
                        // button.

                        // If we used "unexpected(change)" here, the app would
                        // crash if the user succeeded to press "Load Movies"
                        // button multiple times quick enough.
                        else -> only
                    }
                    is Change.Load.Success -> when (this) {
                        State.Loading -> State.Ready(change.movies).only
                        else -> unexpected(change)
                    }
                    Change.Load.Fail -> when (this) {
                        State.Loading -> State.Error.only
                        else -> unexpected(change)
                    }
                }
            }
        }
        actions {
            perform<Action.Load> {
                this
                    .delay(5, TimeUnit.SECONDS) // To fake the loading
                    .map {
                        // Do an operation to load the movies
                        listOf(
                            Movie("The day after tomorrow"),
                            Movie("Joker"),
                            Movie("Avatar")
                        )
                    }
                    .map { movies -> Change.Load.Success(movies) as Change }
                    .onErrorReturn { Change.Load.Fail }
            }
        }
    }

    val showButton: Observable<Boolean> = knot.state
        .map { it == State.Initial }
        .distinctUntilChanged()

    val showLoading: Observable<Boolean> = knot.state
        .map { it == State.Loading }
        .distinctUntilChanged()

    val showMovies: Observable<List<Movie>> = knot.state
        .ofType(State.Ready::class.java)
        .map { it.movies }
        .distinctUntilChanged()

    fun onButtonClick() {
        knot.change.accept(Change.Load)
    }

    override fun onCleared() {
        super.onCleared()
        knot.dispose()
    }
}

private sealed class State {
    object Initial : State()
    object Loading : State()
    data class Ready(val movies: List<Movie>) : State()
    object Error : State()
}

private sealed class Change {
    object Load : Change() {
        data class Success(val movies: List<Movie>) : Change()
        object Fail : Change()
    }
}

private sealed class Action {
    object Load : Action()
}