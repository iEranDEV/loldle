package me.narei.loldle.ui.components.shared

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import me.narei.loldle.ui.theme.spacing

data class LazyDropdownMenuOption (
    val key: String,
    val title: String,
    val icon: @Composable (() -> Unit)? = null
)

enum class DropdownDirection {
    UP, DOWN
}

@Composable
fun LazyDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<LazyDropdownMenuOption>,
    onOptionSelect: (String) -> Unit,
    textFieldLabel: String = "Select option",
    direction: DropdownDirection = DropdownDirection.DOWN
) {
    var expanded by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val filteredOptions = options.filter { it.title.contains(inputText, ignoreCase = true) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                expanded = true
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    expanded = false
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size
                }
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    expanded = focusState.isFocused
                },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = 2.dp,
                            color = if (expanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (inputText.isEmpty()) {
                            Text(
                                text = textFieldLabel,
                                color = MaterialTheme.colorScheme.onSecondary,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )

        if (expanded && filteredOptions.isNotEmpty()) {
            val density = LocalDensity.current

            val popupAlignment = if (direction == DropdownDirection.DOWN) Alignment.TopStart else Alignment.BottomStart
            val yOffset = if (direction == DropdownDirection.DOWN) textFieldSize.height else -textFieldSize.height

            BackHandler {
                expanded = false
                focusManager.clearFocus()
            }

            Popup(
                alignment = popupAlignment,
                offset = IntOffset(0, yOffset),
                properties = PopupProperties(
                    focusable = false,
                    dismissOnClickOutside = false,
                    dismissOnBackPress = true,
                    clippingEnabled = false
                ),
                onDismissRequest = {
                    expanded = false
                }
            ) {
                Surface(
                    modifier = Modifier
                        .width(with(density) { textFieldSize.width.toDp() })
                        .heightIn(max = 250.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = MaterialTheme.spacing.small,
                    shadowElevation = MaterialTheme.spacing.small
                ) {
                    LazyColumn {
                        items(
                            items = filteredOptions,
                            key = { it.key }
                        ) { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onOptionSelect(option.key)
                                        inputText = ""
                                        expanded = false
                                        focusManager.clearFocus()
                                    }
                                    .padding(MaterialTheme.spacing.extraSmall),
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (option.icon != null) {
                                    option.icon()
                                }
                                Text(text = option.title)
                            }
                        }
                    }
                }
            }
        }
    }
}