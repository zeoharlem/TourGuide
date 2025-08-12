package com.zeoharlem.tourguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeoharlem.tourguide.ui.theme.TourGuideTheme
import com.zeoharlem.ztourguide.ZtourGuidePlayManager
import com.zeoharlem.ztourguide.components.ZtourGuideOverlayPlayer
import com.zeoharlem.ztourguide.components.ZtourGuideWrapper
import com.zeoharlem.ztourguide.models.DisplayProperty
import com.zeoharlem.ztourguide.models.ZTourGuideStep
import com.zeoharlem.ztourguide.models.ZtourGuideConfig

class MainActivity : ComponentActivity() {

    val zTourGuideSteps = listOf(
        ZTourGuideStep(
            "player1",
            DisplayProperty(
                title = "Title 1",
                subTitle = "Subtitle 1",
            )
        ),

        ZTourGuideStep(
            "player2",
            DisplayProperty(
                title = "Title 2",
                subTitle = "Subtitle 2",
            )
        ),

        ZTourGuideStep(
            "player3",
            DisplayProperty(
                title = "Title 3",
                subTitle = "Subtitle 3",
                displayShape = DisplayProperty.UsedShape.RECTANGLE
            )
        ),

        ZTourGuideStep(
            "player4",
            DisplayProperty(
                title = "Title 4",
                subTitle = "Subtitle 4",
            )
        ),

        ZTourGuideStep(
            "player5",
            DisplayProperty(
                title = "Title 5",
                subTitle = "Subtitle 5",
                displayShape = DisplayProperty.UsedShape.RECTANGLE
            )
        ),

        ZTourGuideStep(
            "player6",
            DisplayProperty(
                title = "Title 6",
                subTitle = "Subtitle 6",
            )
        ),

        ZTourGuideStep(
            "player7",
            DisplayProperty(
                title = "Title 7",
                subTitle = "Subtitle 7",
            )
        ),

        ZTourGuideStep(
            "player8",
            DisplayProperty(
                title = "Title 8",
                subTitle = "Subtitle 8",
            ), onNextEvent = {
                println("Possible last guide. do something here")
            }
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TourGuideTheme {
                var showZeoTourGuide by remember {
                    mutableStateOf(true)
                }

                val positions = remember { mutableStateMapOf<String, Rect>() }
                val manager = remember { ZtourGuidePlayManager(zTourGuideSteps) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button({
                                showZeoTourGuide = true
                            }) { Text(text = "Display") }

                            ZtourGuideWrapper("player1", positionMap = positions) {
                                Button({}) { Text(text = "Click 1") }
                            }

                            ZtourGuideWrapper("player2", positionMap = positions) {
                                Button({}) { Text(text = "Click 2") }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                        ) {
                            ZtourGuideWrapper("player3", positionMap = positions) {
                                Button({}) { Text(text = "Click 3") }
                            }

                            ZtourGuideWrapper("player4", positionMap = positions) {
                                Button({}) { Text(text = "Click 4") }
                            }

                            ZtourGuideWrapper("player5", positionMap = positions) {
                                Button({}) { Text(text = "Click 5") }
                            }

                            ZtourGuideWrapper("player6", positionMap = positions) {
                                Button({}) { Text(text = "Click 6") }
                            }

                            ZtourGuideWrapper("player8", positionMap = positions) {
                                Badge(
                                    modifier = Modifier.padding(10.dp),
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ) { Text(text = "8", fontSize = 25.sp) }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            ZtourGuideWrapper("player7", positionMap = positions) {
                                Button({}) { Text(text = "Click 7") }
                            }
                        }

                        //<----- Configure ZtourGuide for custom ---->
                        val ztourGuideConfig = ZtourGuideConfig(
                            titleText = { Text(it, fontWeight = Bold) },
                            descriptionText = { Text(it, fontSize = 12.sp) },
                            showPrevBtn = false,
                            nextBtn = {
                                Button(
                                    onClick = {
                                        if (manager.isLastStep()) showZeoTourGuide = false
                                        else manager.nextStep()
                                    },
                                    colors = buttonColors(containerColor = Color.Black)
                                ) { Text(if (manager.isLastStep()) "Finish" else "Next") }
                            },
                        )

                        val targetRect = positions[manager.currentTourStep?.targetKey]

                        if (showZeoTourGuide) {
                            //ZtourGuideOverlayPlayer displays the tour guide
                            ZtourGuideOverlayPlayer(
                                manager = manager,
                                targetCoordinates = targetRect,
                                additionalCountIndicator = { Text("Step $it", fontSize = 13.sp) },
                                ztourGuideConfig = ztourGuideConfig,
                                onDismiss = { showZeoTourGuide = false }
                            ) {
                                showZeoTourGuide = false
                            }
                        }
                    }
                }
            }
        }
    }
}