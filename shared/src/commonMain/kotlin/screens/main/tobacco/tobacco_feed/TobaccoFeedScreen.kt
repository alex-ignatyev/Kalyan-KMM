package screens.main.tobacco.tobacco_feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.tobacco.tobacco_feed.TobaccoFeedAction.OpenTobaccoInfoScreen
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.ClearActions
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.InitTobaccoFeedScreen
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.RefreshTobaccoFeedScreen
import screens.main.tobacco.tobacco_info.TobaccoInfoScreen
import ui.components.ErrorScreen

object TobaccoFeedScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { TobaccoFeedViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            if (state.isError) {
                ErrorScreen {
                    viewModel.obtainEvent(RefreshTobaccoFeedScreen())
                }
            } else {
                TobaccoFeedView(state) { event ->
                    viewModel.obtainEvent(event)
                }
            }

            LaunchedEffect(Unit) {
                viewModel.obtainEvent(InitTobaccoFeedScreen())
            }

            when (action) {
                is OpenTobaccoInfoScreen -> {
                    navigator.push(TobaccoInfoScreen((action as OpenTobaccoInfoScreen).tobaccoId))
                    viewModel.obtainEvent(ClearActions())
                }

                else -> Unit
            }
        }
    }
}
