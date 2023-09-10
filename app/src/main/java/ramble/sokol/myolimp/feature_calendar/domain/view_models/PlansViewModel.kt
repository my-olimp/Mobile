package ramble.sokol.myolimp.feature_calendar.domain.view_models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.CalendarScreenDestination
import ramble.sokol.myolimp.feature_calendar.data.Constants.IS_CREATED
import ramble.sokol.myolimp.feature_calendar.data.Constants.PREVIOUS_DATE
import ramble.sokol.myolimp.feature_calendar.data.database.PlansDatabase
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.repositories.CalendarDataRepository
import ramble.sokol.myolimp.feature_calendar.domain.repositories.PlansRepository
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.Status

class PlansViewModel (
    private val context: Context
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

    private val dataStoreRepository = CalendarDataRepository(context = context)

    fun onEvent(
        event: Event
    ) {
        when (event) {
            is Event.CreatePlan -> {

                when (checkData()) {
                    1 -> {
                        Log.i(TAG, "title is not valid")

                        Toast.makeText(context,
                            context.getString(R.string.name_error), Toast.LENGTH_SHORT).show()

                        return
                    }
                    2 -> {
                        Log.i(TAG, "date is not valid")

                        Toast.makeText(context,
                            context.getString(R.string.data_error), Toast.LENGTH_SHORT).show()

                        return
                    }
                    3 -> {
                        Log.i(TAG, "color is not valid")

                        Toast.makeText(context,
                            context.getString(R.string.color_error), Toast.LENGTH_SHORT).show()

                        return
                    }
                    4 -> {
                        Log.i(TAG, "subject is not valid")

                        Toast.makeText(context,
                            context.getString(R.string.subject_error), Toast.LENGTH_SHORT).show()

                        return
                    }
                    5 -> {
                        Log.i(TAG, "time is not valid")

                        Toast.makeText(context,
                            context.getString(R.string.time_error), Toast.LENGTH_SHORT).show()

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
                            startMinute = state.value.startMinute,
                            endMinute = state.value.endMinute,
                            startHour = state.value.startHour,
                            endHour = state.value.endHour,
                            isCompleted = false
                        )

                        viewModelScope.launch {
                            repository.addPlan(plan = plan)

                            // save date in data store
                            dataStoreRepository.setPreviousDate(
                                key = PREVIOUS_DATE,
                                value = plan.date
                            )

                            dataStoreRepository.isCreatedPlan(
                                key = IS_CREATED,
                                value = true
                            )

                            setDefaultData()

                            // update destination
                            event.navController.navigate(
                                CalendarScreenDestination()
                            ) {
                                popUpTo(NavGraphs.root) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            // navigate to calendar screen
                            event.navController.navigate(
                                CalendarScreenDestination()
                            )
                        }
                    }
                }

            }
            is Event.OnDateUpdated -> {

                _state.update {
                    it.copy(
                        date = event.date,
                        isSearching = false
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

            is Event.OnEndHourUpdated -> {
                _state.update {
                    it.copy(
                        endHour = event.endHour
                    )
                }
            }

            is Event.OnEndMinUpdated -> {
                _state.update {
                    it.copy(
                        endMinute = event.endMin
                    )
                }
            }

            is Event.OnStartHourUpdated -> {
                _state.update {
                    it.copy(
                        startHour = event.startHour
                    )
                }
            }

            is Event.OnStartMinUpdated -> {
                _state.update {
                    it.copy(
                        startMinute = event.startMin
                    )
                }
            }

            is Event.OnCompletedPlan -> {
                viewModelScope.launch {
                    repository.updatePlan(
                        plan = event.plan.copy(
                            isCompleted = event.isCompleted
                        )
                    )

                    Log.i(TAG, "plan - ${event.plan}")
                }
            }

            is Event.UpdatePlan -> {

                when (checkData()) {
                    1 -> {
                        Log.i(TAG, "title is not valid")

                        Toast.makeText(
                            context,
                            context.getString(R.string.name_error), Toast.LENGTH_SHORT
                        ).show()

                        return
                    }

                    2 -> {
                        Log.i(TAG, "date is not valid")

                        Toast.makeText(
                            context,
                            context.getString(R.string.data_error), Toast.LENGTH_SHORT
                        ).show()

                        return
                    }

                    3 -> {
                        Log.i(TAG, "color is not valid")

                        Toast.makeText(
                            context,
                            context.getString(R.string.color_error), Toast.LENGTH_SHORT
                        ).show()

                        return
                    }

                    4 -> {
                        Log.i(TAG, "subject is not valid")

                        Toast.makeText(
                            context,
                            context.getString(R.string.subject_error), Toast.LENGTH_SHORT
                        ).show()

                        return
                    }

                    5 -> {
                        Log.i(TAG, "time is not valid")

                        Toast.makeText(
                            context,
                            context.getString(R.string.time_error), Toast.LENGTH_SHORT
                        ).show()

                        return
                    }

                    else -> {

                        val plan = PlanModel(
                            id = event.id,
                            title = state.value.title,
                            date = state.value.date,
                            color = state.value.color,
                            subject = state.value.subject,
                            type = state.value.type,
                            isFavourite = state.value.isFavourite,
                            startMinute = state.value.startMinute,
                            endMinute = state.value.endMinute,
                            startHour = state.value.startHour,
                            endHour = state.value.endHour,
                            isCompleted = false
                        )

                        Log.i(TAG, "Update plan - $plan")

                        viewModelScope.launch {
                            repository.updatePlan(plan = plan)


                            // save date in data store
                            dataStoreRepository.setPreviousDate(
                                key = PREVIOUS_DATE,
                                value = plan.date
                            )

                            dataStoreRepository.isCreatedPlan(
                                key = IS_CREATED,
                                value = true
                            )
                        }

                        setDefaultData()

                        // navigate to calendar screen

                        event.navController.navigate(
                            CalendarScreenDestination()
                        )
                    }
                }
            }

            is Event.OnDatePickerShowing -> {
                _state.update {
                    it.copy(
                        isShowingCalendar = event.isShowing
                    )
                }
            }

            is Event.IsShowingCreatedPlan -> {
                _state.update {
                    it.copy(
                        isShowingCreatedPlan = event.isShowing
                    )
                }
            }

            is Event.SaveDate -> {

                viewModelScope.launch {

                    // save date in data store
                    dataStoreRepository.setPreviousDate(
                        key = PREVIOUS_DATE,
                        value = event.date
                    )

                    dataStoreRepository.isCreatedPlan(
                        key = IS_CREATED,
                        value = true
                    )
                }
            }

            Event.GetPreviousDate -> {

                viewModelScope.launch {

                    val status = dataStoreRepository.getStatusCreating(
                        key = IS_CREATED
                    )

                    Log.i(TAG, "is created plan - $status")

                    if (status) {
                        _state.update {
                            it.copy(
                                date = dataStoreRepository
                                    .getPreviousDate(
                                        key = PREVIOUS_DATE,
                                    ),
                                isShowingCreatedPlan = true
                            )
                        }

                        Log.i(TAG, "date plan - ${_state.value.date}")

                        dataStoreRepository.isCreatedPlan(
                            key = IS_CREATED,
                            value = false
                        )

                        Log.i(TAG, "is created plan after - ${dataStoreRepository.getStatusCreating(key = IS_CREATED)}")

                    }
                }

            }
        }
    }

    private fun checkData() : Int {

        val startHour = if (state.value.startHour == 0) 12 else if (state.value.startHour < 24) state.value.startHour - 12 else state.value.startHour
        val startMin = state.value.startMinute
        val endHour = if (state.value.endHour == 0) 12 else if (state.value.endHour < 24) state.value.endHour - 12 else state.value.endHour
        val endMin = state.value.endMinute

        Log.i(TAG, "$startHour:$startMin - $endHour:$endMin - ${state.value.startHour}")

        return if (state.value.title.isBlank()) Status.ERROR_TITLE
        else if (state.value.date.isBlank()) Status.ERROR_DATE
        else if (state.value.color.isBlank()) Status.ERROR_COLOR
        else if (startHour > endHour && startHour != 12) {
            Log.i(TAG, "1 time")
            Status.ERROR_TIME
        }
        else if (startHour == endHour && startMin >= endMin) {
            Log.i(TAG, "2 time")

            Status.ERROR_TIME
        }
        else if (endHour == 12 && endMin != 0 && startHour != 12) {
            Log.i(TAG, "3 time")

            Status.ERROR_TIME
        }
        else if (state.value.subject.isBlank()) Status.ERROR_SUBJECT
        else Status.SUCCESS
    }

    private fun setDefaultData() {
        _state.update {
            it.copy(
                title = "",
                color = "#FF7E50FF",
                subject = "",
                type = "Событие",

                startHour = 0,
                startMinute = 0,
                endHour = 0,
                endMinute = 0,

                isFavourite = false,
            )
        }
    }

}
