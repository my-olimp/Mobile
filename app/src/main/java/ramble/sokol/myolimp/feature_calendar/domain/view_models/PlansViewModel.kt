package ramble.sokol.myolimp.feature_calendar.domain.view_models

import ramble.sokol.myolimp.feature_calendar.data.database.PlansDatabase
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.repositories.PlansRepository
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.Status
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlansViewModel (
    context: Context
) : ViewModel() {

    companion object {
        const val TAG = "ViewModelPlans"
    }

    private var database : PlansDatabase = PlansDatabase.invoke(context)
    private var repository : PlansRepository = PlansRepository(database = database)

    private val _plans = repository.getPlans()

    private val _state = MutableStateFlow(
        PlanState()
    )
    val state = combine(_state, _plans) { state, plans ->
        state.copy(
            plans = plans
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        PlanState()
    )

    fun onEvent(
        event: Event
    ) {
        when (event) {
            Event.CreatePlan -> {

                when (checkData()) {
                    1 -> {
                        Log.i(TAG, "title is not valid")
                        return
                    }
                    2 -> {
                        Log.i(TAG, "date is not valid")
                        return
                    }
                    3 -> {
                        Log.i(TAG, "color is not valid")
                        return
                    }
                    4 -> {
                        Log.i(TAG, "subject is not valid")
                        return
                    }
                    else -> {
                        val plan = PlanModel(
                            title = state.value.title,
                            date = state.value.date,
                            color = state.value.color,
                            subject = state.value.subject,
                            type = state.value.type,
                            isFavourite = state.value.isFavourite,
                            startTime = "${state.value.startHour}:${state.value.startMinute}",
                            endTime = "${state.value.endHour}:${state.value.endMinute}",
                        )

                        viewModelScope.launch {
                            repository.addPlan(plan = plan)
                        }

                        _state.update {
                            it.copy(
                                title = "",
                                date = "",
                                color = "",
                                subject = "",
                                isFavourite = false,
                                isAddingPlan = false
                            )
                        }
                    }
                }

            }
            is Event.OnDateUpdated -> {
                _state.update {
                    it.copy(
                        date = event.date
                    )
                }
            }
            is Event.OnTitleUpdated -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
            is Event.DeletePlan -> {
                viewModelScope.launch {
                    repository.deletePlan(event.plan)
                }
            }

            Event.HideCreatingSheet -> {
                _state.update {
                    it.copy(
                        isAddingPlan = false
                    )
                }
            }
            Event.ShowCreatingSheet -> {
                _state.update {
                    it.copy(
                        isAddingPlan = true,
                        isSearching = false
                    )
                }

                Log.i(TAG, "isAdding - ${_state.value.isAddingPlan}")

            }

            is Event.OnColorUpdated -> {
                _state.update {
                    it.copy(
                        color = event.color
                    )
                }
            }

            is Event.OnSubjectUpdated -> {
                _state.update {
                    it.copy(
                        subject = event.subject
                    )
                }
            }

            is Event.OnSearchQueryUpdated -> {
                _state.update {
                    it.copy(
                        searchQuery = event.searchQuery,
                        isSearching = true
                    )
                }
            }

            Event.CancelSearching -> {
                _state.update {
                    it.copy(
                        isSearching = false
                    )
                }
            }

            is Event.OnFavouritesShowing -> {
                _state.update {
                    it.copy(
                        isShowingFavourites = event.isShowing,
                        isSearching = false
                    )
                }
            }

            is Event.OnFavouriteClick -> {
                _state.update {
                    it.copy(
                        isFavourite = event.isFavourite
                    )
                }
            }

            is Event.OnTypeUpdated -> {
                _state.update {
                    it.copy(
                        type = event.type
                    )
                }
            }

            is Event.OnEndTimeUpdated -> {
                _state.update {
                    it.copy(
                        endHour = event.endHour,
                        endMinute = event.endMin
                    )
                }
            }

            is Event.OnStartTimeUpdated -> {
                _state.update {
                    it.copy(
                        startHour = event.startHour,
                        startMinute = event.startMin
                    )
                }
            }
        }
    }

    private fun checkData() : Int {
        return if (state.value.title.isBlank()) Status.ERROR_TITLE
        else if (state.value.date.isBlank()) Status.ERROR_DATE
        else if (state.value.color.isBlank()) Status.ERROR_COLOR
        else if (state.value.subject.isBlank()) Status.ERROR_SUBJECT
        else Status.SUCCESS
    }

}
