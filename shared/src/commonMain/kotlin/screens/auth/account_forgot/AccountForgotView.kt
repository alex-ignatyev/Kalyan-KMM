package screens.auth.account_forgot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Sharp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kalyan.shared.AppRes
import com.kalyan.shared.strings.AppResStrings
import screens.auth.account_forgot.AccountForgotEvent.ChangeLogin
import screens.auth.account_forgot.AccountForgotEvent.ChangePassword
import screens.auth.account_forgot.AccountForgotEvent.ChangePasswordRepeat
import screens.auth.account_forgot.AccountForgotEvent.OnBackClick
import screens.auth.account_forgot.AccountForgotEvent.ResetPasswordClick
import screens.auth.account_forgot.AccountForgotEvent.ShowPasswordClick
import screens.auth.account_forgot.AccountForgotEvent.ShowPasswordRepeatClick
import ui.themes.KalyanTheme
import ui.themes.components.KalyanButton
import ui.themes.components.KalyanCircularProgress
import ui.themes.components.KalyanTextField
import ui.themes.components.KalyanToolbar
import ui.themes.components.TextFieldType

@Composable
fun AccountForgotView(state: AccountForgotState, obtainEvent: (AccountForgotEvent) -> Unit) {
    Scaffold(
        topBar = {
            KalyanToolbar() {
                obtainEvent.invoke(OnBackClick())
            }
        },
        backgroundColor = KalyanTheme.colors.primaryBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = AppResStrings.title_forgot,
                style = KalyanTheme.typography.header,
                color = KalyanTheme.colors.primaryText
            )

            Text(
                text = AppResStrings.subtitle_forgot,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                style = KalyanTheme.typography.body,
                textAlign = TextAlign.Center,
                color = KalyanTheme.colors.secondaryText
            )

            Spacer(modifier = Modifier.height(20.dp))

            KalyanTextField(
                value = state.login,
                placeholder = AppResStrings.text_login,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
            ) {
                obtainEvent(ChangeLogin(it))
            }

            KalyanTextField(
                value = state.password,
                placeholder = AppResStrings.text_password,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
                fieldType = TextFieldType.Password(state.isPasswordHidden),
                endIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            obtainEvent.invoke(ShowPasswordClick())
                        },
                        imageVector = if (state.isPasswordHidden) {
                            Sharp.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        tint = KalyanTheme.colors.secondaryText
                    )
                }
            ) {
                obtainEvent(ChangePassword(it))
            }

            KalyanTextField(
                value = state.passwordRepeat,
                placeholder = AppResStrings.text_password_repeat,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
                fieldType = TextFieldType.Password(state.isPasswordRepeatHidden),
                endIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            obtainEvent.invoke(ShowPasswordRepeatClick())
                        },
                        imageVector = if (state.isPasswordRepeatHidden) {
                            Sharp.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = null,
                        tint = KalyanTheme.colors.secondaryText
                    )
                }
            ) {
                obtainEvent(ChangePasswordRepeat(it))
            }

            KalyanButton(
                modifier = Modifier.padding(vertical = 32.dp),
                text = if (state.isLoading) null else AppRes.string.text_forgot_reset,
                enabled = !state.isLoading,
                content = {
                    KalyanCircularProgress()
                },
                onClick = {
                    obtainEvent(ResetPasswordClick())
                })

            Text(
                text = state.error,
                color = KalyanTheme.colors.errorColor,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
