package screens.auth.account_forgot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.auth.account_forgot.AccountForgotAction.OpenLoginScreen
import screens.auth.account_forgot.AccountForgotEvent.ClearActions

@Composable
internal fun AccountForgotScreen() {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { AccountForgotViewModel() }) { viewModel ->
        val state by viewModel.viewStates().collectAsState()
        val action by viewModel.viewActions().collectAsState(null)

        AccountForgotView(state) { event ->
            viewModel.obtainEvent(event)
        }

        when (action) {
            is OpenLoginScreen -> {
                rootController.push("account_login")
                viewModel.obtainEvent(ClearActions())
            }

            null -> {}
        }
    }
}