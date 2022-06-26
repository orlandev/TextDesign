package com.orlandev.textdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orlandev.textdesign.ui.theme.TextDesignTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextDesignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        TextDesign()
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextDesign() {
    data class DesignTypo(
        val name: String,
        val textStyle: androidx.compose.ui.text.TextStyle
    )

    val typography = listOf<DesignTypo>(
        DesignTypo("BL", MaterialTheme.typography.bodyLarge),
        DesignTypo("BM", MaterialTheme.typography.bodyMedium),
        DesignTypo("BS", MaterialTheme.typography.bodySmall),
        DesignTypo("DL", MaterialTheme.typography.displayLarge),
        DesignTypo("DM", MaterialTheme.typography.displayMedium),
        DesignTypo("DS", MaterialTheme.typography.displayLarge),
    )

    val currentTypography = remember {
        mutableStateOf(typography[0])
    }

    var chipsState by rememberSaveable {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier) {
        androidx.compose.material.Text(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .clickable {
                    chipsState = !chipsState
                },
            text = "Text Design",
            style = currentTypography.value.textStyle
        )
        AnimatedVisibility(visible = chipsState) {
            Row(
                modifier = Modifier.horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (i in typography) {
                    FilterChip(
                        selected = currentTypography.value.name != i.name,
                        onClick = {
                            currentTypography.value = i
                        },
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        Text(text = i.name, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
