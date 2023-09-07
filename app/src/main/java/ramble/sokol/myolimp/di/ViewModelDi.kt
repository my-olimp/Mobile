package ramble.sokol.myolimp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ramble.sokol.myolimp.feature_authentication.domain.view_models.LoginViewModel
import ramble.sokol.myolimp.feature_authentication.domain.view_models.RegisterEducationViewModel
import ramble.sokol.myolimp.feature_authentication.domain.view_models.RegisterInfoViewModel
import ramble.sokol.myolimp.feature_authentication.domain.view_models.SignUpViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.view_models.RegisterImageViewModel
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_library.domain.view_models.LibraryViewModel
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel

val viewModelsModule = module {

    /*
        Create viewModels here
    */

    /* ProfileScreen */
    viewModel {
        ProfileViewModel(get())
    }

    /* Calendar */
    viewModel {
        PlansViewModel(get())
    }

    /* Login */
    viewModel {
        LoginViewModel(get())
    }

    /* SignUp */
    viewModel {
        SignUpViewModel(get())
    }

    /* Register Info */
    viewModel {
        RegisterInfoViewModel(get())
    }

    /* Register Image */
    viewModel {
        RegisterImageViewModel(get())
    }

    /* Register Education */
    viewModel {
        RegisterEducationViewModel(get())
    }

    /* Library */
    viewModel {
        LibraryViewModel(get())
    }

}
