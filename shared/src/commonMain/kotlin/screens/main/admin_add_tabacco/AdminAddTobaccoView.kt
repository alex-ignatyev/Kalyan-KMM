package screens.main.admin_add_tabacco

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.kalyan.shared.AppRes
import com.kalyan.shared.strings.AppResStrings
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.ime
import com.moriatsushi.insetsx.navigationBars
import com.moriatsushi.insetsx.statusBars
import model.domain.Company
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.AddTobaccoClick
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.ChangeCompany
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.ChangeLine
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.ChangeManual
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.ChangeStrengthByCompany
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.ChangeTaste
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.OnBackClick
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.OnCompanyClick
import screens.main.admin_add_tabacco.AdminAddTobaccoEvent.OnLineClick
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanCircularProgress
import ui.components.KalyanSelect
import ui.components.KalyanTextField
import ui.components.KalyanToolbar

@OptIn(ExperimentalSoftwareKeyboardApi::class)
@Composable
fun AdminAddTobaccoView(state: AdminAddTobaccoState, obtainEvent: (AdminAddTobaccoEvent) -> Unit) {

    Scaffold(
        modifier = Modifier.background(KalyanTheme.colors.primaryBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.ime),
        backgroundColor = KalyanTheme.colors.primaryBackground,
        topBar = {
            KalyanToolbar(
                title = AppResStrings.title_admin_add_tobacco,
                onBackClick = {
                    obtainEvent(OnBackClick())
                })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = AppResStrings.text_admin_manually,
                        style = KalyanTheme.typography.header,
                        color = KalyanTheme.colors.primaryText,
                        modifier = Modifier.weight(1f)
                    )

                    Switch(state.isManual, colors = colors(
                        checkedThumbColor = KalyanTheme.colors.generalColor,
                        checkedTrackColor = KalyanTheme.colors.primaryText,
                        uncheckedTrackColor = KalyanTheme.colors.primaryText
                    ), onCheckedChange = {
                        obtainEvent(ChangeManual(it))
                    })
                }

                if (state.isManual) {
                    KalyanTextField(
                        value = state.company,
                        placeholder = AppResStrings.text_company,
                        enabled = !state.isLoading,
                        isError = state.error.isNotBlank()
                    ) {
                        obtainEvent(ChangeCompany(it))
                    }
                } else {
                    KalyanSelect(title = AppResStrings.text_company, text = state.company) {
                        obtainEvent(OnCompanyClick())
                    }
                }

                KalyanTextField(
                    value = state.taste,
                    placeholder = AppResStrings.text_taste,
                    enabled = !state.isLoading,
                    isError = state.error.isNotBlank(),
                ) {
                    obtainEvent(ChangeTaste(it))
                }

                if (state.isManual) {
                    KalyanTextField(
                        value = state.line,
                        placeholder = AppResStrings.text_line,
                        enabled = !state.isLoading,
                        isError = state.error.isNotBlank()
                    ) {
                        obtainEvent(ChangeLine(it))
                    }
                } else {
                    KalyanSelect(title = AppResStrings.text_line, text = state.line) {
                        val lines = state.companies.findLast { it.company == state.company }?.lines ?: return@KalyanSelect
                        obtainEvent(OnLineClick(lines))
                    }
                }

                KalyanTextField(
                    value = state.strength,
                    placeholder = AppResStrings.text_strength,
                    enabled = !state.isLoading,
                    inputType = KeyboardType.NumberPassword,
                    isError = state.error.isNotBlank()
                ) {
                    obtainEvent(ChangeStrengthByCompany(it))
                }

                Text(
                    text = state.error,
                    color = KalyanTheme.colors.errorColor,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            KalyanButton(
                modifier = Modifier.padding(vertical = 32.dp).align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets.navigationBars)),
                text = if (state.isLoading) null else AppRes.string.title_admin_add_tobacco,
                enabled = !state.isLoading && state.isButtonEnabled,
                content = {
                    KalyanCircularProgress()
                },
                onClick = {
                    obtainEvent(AddTobaccoClick())
                }
            )
        }
    }
}

data class CompanyBottomSheet(val companies: List<Company>, val obtainEvent: (AdminAddTobaccoEvent) -> Unit) : Screen {

    @OptIn(ExperimentalSoftwareKeyboardApi::class)
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        LazyColumn(
            modifier = Modifier.wrapContentHeight()
                .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets.navigationBars).add(WindowInsets(bottom = 8.dp)))
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            items(companies) {
                Text(
                    text = it.company,
                    style = KalyanTheme.typography.body,
                    modifier = Modifier.fillMaxWidth().clickable {
                        obtainEvent(ChangeCompany(it.company))
                        bottomSheetNavigator.hide()
                    })
            }
        }
    }
}

data class LineBottomSheet(val lines: List<String>, val obtainEvent: (AdminAddTobaccoEvent) -> Unit) : Screen {

    @OptIn(ExperimentalSoftwareKeyboardApi::class)
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        LazyColumn(
            modifier = Modifier.wrapContentHeight()
                .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets.navigationBars).add(WindowInsets(bottom = 8.dp)))
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            items(lines) {
                Text(
                    text = it,
                    style = KalyanTheme.typography.body,
                    modifier = Modifier.fillMaxWidth().clickable {
                        obtainEvent(ChangeLine(it))
                        bottomSheetNavigator.hide()
                    })
            }
        }
    }
}
