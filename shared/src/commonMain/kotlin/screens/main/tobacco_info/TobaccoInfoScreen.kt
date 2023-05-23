package screens.main.tobacco_info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.tobacco_info.TobaccoInfoAction.OpenVoteBottomSheet
import screens.main.tobacco_info.TobaccoInfoAction.ReturnBack
import screens.main.tobacco_info.TobaccoInfoEvent.InitTobaccoInfoScreen

data class TobaccoInfoScreen(val tobaccoId: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        ViewModel(factory = { TobaccoInfoViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            TobaccoInfoView(state) {
                viewModel.obtainEvent(it)
            }

            viewModel.obtainEvent(InitTobaccoInfoScreen(tobaccoId))

            when (action) {
                is ReturnBack -> navigator.pop()
                is OpenVoteBottomSheet -> bottomSheetNavigator.show(VoteBottomSheet(state, viewModel::obtainEvent))
                else -> {}
            }
        }
    }
}
