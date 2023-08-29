package ramble.sokol.myolimp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ramble.sokol.myolimp.feature_authentication.domain.view_models.LoginViewModel
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_profile.domain.view_models.ProfileViewModel

val viewModelsModule = module {

    /*
        Create viewModels here
    */

    /* ProfileScreen */

    viewModel {
        ProfileViewModel()
    }

    /* Calendar */

    viewModel {
        PlansViewModel(get())
    }

    /* Login */

    viewModel {
        LoginViewModel()
    }
}
