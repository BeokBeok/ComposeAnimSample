package com.beok.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.beok.sample.ui.theme.ComposeplaygorundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeplaygorundTheme {
                var tabPage by remember { mutableStateOf(TabPage.Spring) }
                Scaffold(
                    topBar = {
                        TabRow(
                            selectedTabIndex = tabPage.ordinal
                        ) {
                            TabPage.entries.forEach {
                                Text(
                                    modifier = Modifier.clickable { tabPage = it },
                                    text = it.name
                                )
                            }
                        }
                    },
                    content = { paddingValues ->
                        when (tabPage) {
                            TabPage.Spring -> {
                                SpringContent(paddingValues)
                            }
                            TabPage.Scale -> {
                                ScaleContent(paddingValues)
                            }
                            TabPage.Curve -> {

                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    @OptIn(ExperimentalComposeUiApi::class)
    private fun ScaleContent(paddingValues: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var scaled by remember { mutableStateOf(false) }
            var durationMillis by remember { mutableIntStateOf(AnimationConstants.DefaultDurationMillis) }
            var delayMillis by remember { mutableIntStateOf(0) }
            var easing: Easing by remember { mutableStateOf(CubicBezierEasing(0f, 0f, 0f, 0f)) }
            var a: Float by remember { mutableFloatStateOf(0f) }
            var b: Float by remember { mutableFloatStateOf(0f) }
            var c: Float by remember { mutableFloatStateOf(0f) }
            var d: Float by remember { mutableFloatStateOf(0f) }

            var durationMillisInput by remember { mutableStateOf(durationMillis.toString()) }
            var delayMillisInput by remember { mutableStateOf(delayMillis.toString()) }
            var aInput: String by remember { mutableStateOf(a.toString()) }
            var bInput: String by remember { mutableStateOf(b.toString()) }
            var cInput: String by remember { mutableStateOf(c.toString()) }
            var dInput: String by remember { mutableStateOf(d.toString()) }

            val keyboard = LocalSoftwareKeyboardController.current

            val transition = updateTransition(
                targetState = scaled,
                label = ""
            )
            val scale by transition.animateFloat(
                label = "",
                transitionSpec = {
                    tween(
                        durationMillis = durationMillis,
                        delayMillis = delayMillis,
                        easing = easing
                    )
                }
            ) {
                if (it) 1f else 0.5f
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .scale(scale)
                        .size(size = 100.dp)
                        .background(color = Color.Red)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            scaled = scaled.not()
                        }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Easing")
                Button(onClick = {
                    easing = LinearEasing
                    aInput = "0.0"
                    bInput = "0.0"
                    cInput = "0.0"
                    dInput = "0.0"
                }) {
                    Text(text = "Linear")
                }
                Button(onClick = {
                    easing = FastOutLinearInEasing
                    aInput = "0.4"
                    bInput = "0.0"
                    cInput = "1.0"
                    dInput = "1.0"
                }) {
                    Text(text = "FastOutLinearIn")
                }
                Button(onClick = {
                    easing = LinearOutSlowInEasing
                    aInput = "0.0"
                    bInput = "0.0"
                    cInput = "0.2"
                    dInput = "1.0"
                }) {
                    Text(text = "LinearOutSlowIn")
                }
                Button(onClick = {
                    easing = FastOutSlowInEasing
                    aInput = "0.4"
                    bInput = "0.0"
                    cInput = "0.2"
                    dInput = "1.0"
                }) {
                    Text(text = "FastOutSlowIn")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "DurationMillis")
                    TextField(
                        value = durationMillisInput,
                        onValueChange = {
                            durationMillisInput = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions {
                            durationMillis =
                                durationMillisInput.toIntOrNull() ?: 0
                            keyboard?.hide()
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "DelayMillis")
                    TextField(
                        value = delayMillisInput,
                        onValueChange = {
                            delayMillisInput = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions {
                            delayMillis = delayMillisInput.toIntOrNull() ?: 0
                            keyboard?.hide()
                        }
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Easing")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = aInput,
                            onValueChange = {
                                aInput = it
                            },
                            placeholder = {
                                Text(text = "a")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions {
                                a = aInput.toFloatOrNull() ?: 0f
                                easing = CubicBezierEasing(a, b, c, d)
                                keyboard?.hide()
                            }
                        )
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = bInput,
                            onValueChange = {
                                bInput = it
                            },
                            placeholder = {
                                Text(text = "b")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions {
                                b = bInput.toFloatOrNull() ?: 0f
                                easing = CubicBezierEasing(a, b, c, d)
                                keyboard?.hide()
                            }
                        )
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = cInput,
                            onValueChange = {
                                cInput = it
                            },
                            placeholder = {
                                Text(text = "c")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions {
                                c = cInput.toFloatOrNull() ?: 0f
                                easing = CubicBezierEasing(a, b, c, d)
                                keyboard?.hide()
                            }
                        )
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = dInput,
                            onValueChange = {
                                dInput = it
                            },
                            placeholder = {
                                Text(text = "d")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions {
                                d = dInput.toFloatOrNull() ?: 0f
                                easing = CubicBezierEasing(a, b, c, d)
                                keyboard?.hide()
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalComposeUiApi::class)
    private fun SpringContent(paddingValues: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var moved by remember { mutableStateOf(false) }
            var dampingRatio by remember { mutableFloatStateOf(Spring.DampingRatioNoBouncy) }
            var stiffness by remember { mutableFloatStateOf(Spring.StiffnessVeryLow) }
            var visibilityThreshold by remember { mutableStateOf(1.dp) }

            var dampingRatioInput by remember { mutableStateOf(dampingRatio.toString()) }
            var stiffnessInput by remember { mutableStateOf(stiffness.toString()) }
            var visibilityThresholdInput by remember { mutableStateOf("1") }

            val keyboard = LocalSoftwareKeyboardController.current

            val transition = updateTransition(
                targetState = moved,
                label = ""
            )
            val offset by transition.animateDp(
                label = "",
                transitionSpec = {
                    spring(
                        dampingRatio = dampingRatio,
                        stiffness = stiffness,
                        visibilityThreshold = visibilityThreshold
                    )
                }
            ) {
                if (it) 300.dp else 0.dp
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .offset(x = offset)
                    .size(size = 100.dp)
                    .background(color = Color.Red)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        moved = moved.not()
                    }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column {
                    Text(text = "DampingRatio")
                    Button(onClick = {
                        dampingRatio = Spring.DampingRatioHighBouncy
                        dampingRatioInput = dampingRatio.toString()
                    }) {
                        Text(text = "HighBouncy")
                    }
                    Button(onClick = {
                        dampingRatio = Spring.DampingRatioMediumBouncy
                        dampingRatioInput = dampingRatio.toString()
                    }) {
                        Text(text = "MediumBouncy")
                    }
                    Button(onClick = {
                        dampingRatio = Spring.DampingRatioLowBouncy
                        dampingRatioInput = dampingRatio.toString()
                    }) {
                        Text(text = "LowBouncy")
                    }
                    Button(onClick = {
                        dampingRatio = Spring.DampingRatioNoBouncy
                        dampingRatioInput = dampingRatio.toString()
                    }) {
                        Text(text = "NoBouncy")
                    }
                }
                Column {
                    Text(text = "Stiffness")
                    Button(onClick = {
                        stiffness = Spring.StiffnessHigh
                        stiffnessInput = stiffness.toString()
                    }) {
                        Text(text = "High")
                    }
                    Button(onClick = {
                        stiffness = Spring.StiffnessMedium
                        stiffnessInput = stiffness.toString()
                    }) {
                        Text(text = "Medium")
                    }
                    Button(onClick = {
                        stiffness = Spring.StiffnessMediumLow
                        stiffnessInput = stiffness.toString()
                    }) {
                        Text(text = "MediumLow")
                    }
                    Button(onClick = {
                        stiffness = Spring.StiffnessLow
                        stiffnessInput = stiffness.toString()
                    }) {
                        Text(text = "Low")
                    }
                    Button(onClick = {
                        stiffness = Spring.StiffnessVeryLow
                        stiffnessInput = stiffness.toString()
                    }) {
                        Text(text = "VeryLow")
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "DampingRatio")
                    TextField(
                        value = dampingRatioInput,
                        onValueChange = {
                            dampingRatioInput = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions {
                            dampingRatio =
                                dampingRatioInput.toFloatOrNull() ?: Spring.DampingRatioNoBouncy
                            keyboard?.hide()
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Stiffness")
                    TextField(
                        value = stiffnessInput,
                        onValueChange = {
                            stiffnessInput = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions {
                            stiffness = stiffnessInput.toFloatOrNull() ?: Spring.StiffnessVeryLow
                            keyboard?.hide()
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "VisibilityThreshold")
                    TextField(
                        value = visibilityThresholdInput,
                        onValueChange = {
                            visibilityThresholdInput = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions {
                            visibilityThreshold = visibilityThresholdInput.toFloatOrNull()?.dp ?: 1.dp
                            keyboard?.hide()
                        }
                    )
                }
            }
        }
    }

    private enum class TabPage {
        Spring, Scale, Curve
    }
}
