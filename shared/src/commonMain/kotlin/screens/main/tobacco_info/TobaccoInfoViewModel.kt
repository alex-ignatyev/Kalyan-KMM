package screens.main.tobacco_info

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.repository.RatingRepository
import kotlin.random.Random
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.main.tobacco_info.TobaccoInfoEvent.ClearActions
import screens.main.tobacco_info.TobaccoInfoEvent.InitTobaccoInfoScreen
import screens.main.tobacco_info.TobaccoInfoEvent.VoteForTobacco
import utils.answer.onFailure
import utils.answer.onSuccess

class TobaccoInfoViewModel : KoinComponent, BaseSharedViewModel<TobaccoInfoState, TobaccoInfoAction, TobaccoInfoEvent>(
    initialState = TobaccoInfoState()
) {

    private val repo: RatingRepository by inject()
    private var tobaccoId: String = ""

    override fun obtainEvent(viewEvent: TobaccoInfoEvent) {
        when (viewEvent) {
            is InitTobaccoInfoScreen -> fetchData(viewEvent.tobaccoId)
            is VoteForTobacco -> voteForTobacco()
            is ClearActions -> clearActions()
        }
    }

    private fun fetchData(tobaccoId: String) {
        this.tobaccoId = tobaccoId
        viewModelScope.launch {
            repo.getTobaccoInfo(tobaccoId).onSuccess {
                viewState = viewState.copy(
                    isLoading = false,

                    id = it.id,
                    taste = it.taste,
                    company = it.company,
                    line = it.line,

                    image = it.image,

                    strengthByCompany = it.strengthByCompany,

                    strengthByUsers = it.strengthByUsers,
                    smokinessByUsers = it.smokinessByUsers,
                    aromaByUsers = it.aromaByUsers,
                    ratingByUsers = it.ratingByUsers,
                    tastePowerByUsers = it.tastePowerByUsers,

                    strengthByUser = it.strengthByUser,
                    smokinessByUser = it.smokinessByUser,
                    aromaByUser = it.aromaByUser,
                    tastePowerByUser = it.tastePowerByUser,
                    ratingByUser = it.ratingByUser,

                    votes = it.votes,
                )
            }.onFailure {
                viewState = viewState.copy(isLoading = false, error = "Ошибка")
            }

        }
    }

    private fun voteForTobacco() {
        viewModelScope.launch {
            repo.postTobaccoVote(
                tobaccoId = tobaccoId,
                strength = Random.nextInt(1, 10),
                smokiness = Random.nextInt(1, 10),
                aroma = Random.nextInt(1, 10),
                tastePower = Random.nextInt(1, 10),
                rating = Random.nextInt(1, 10)
            )
        }
    }

    private fun clearActions() {
        viewAction = null
    }
}