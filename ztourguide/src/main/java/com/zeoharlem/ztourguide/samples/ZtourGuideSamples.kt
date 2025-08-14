package com.zeoharlem.ztourguide.samples

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeoharlem.ztourguide.ZtourGuidePlayManager
import com.zeoharlem.ztourguide.models.DisplayProperty
import com.zeoharlem.ztourguide.models.ZTourGuideStep
import com.zeoharlem.ztourguide.models.ZtourGuideConfig

@Composable
fun CreateZtourGuideConfigExample() {
    val ztourGuideConfig = ZtourGuideConfig(
        titleText = { Text(it, fontWeight = Bold) },
        descriptionText = { Text(it, fontSize = 12.sp) },
        nextBtn = {
            Button(
                onClick = {},
                colors = buttonColors(containerColor = Color.Black)
            ) { Text("Next") }
        },
    )

    Card(
        elevation = cardElevation(5.dp),
        shape = RoundedCornerShape(15.dp),

        colors = ztourGuideConfig.cardColors?.invoke()
            ?: cardColors(containerColor = Color.White),

        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 10.dp)
    ) {
        Box(modifier = Modifier.padding(15.dp)) {
            Icon(
                contentDescription = "close",
                imageVector = ztourGuideConfig.tourGuideCloseIcon,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                ztourGuideConfig.titleText?.invoke("displayProperty.title")
                    ?: Text("displayProperty.title")

                ztourGuideConfig.descriptionText?.invoke("displayProperty.subTitle")
                    ?: Text("displayProperty.subTitle")
                //.....................................................
                //.....................................................
                //.................
            }
        }
    }
}

@Composable
fun CreateZtourGuideStepConfigExample() {
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
            "player8",
            DisplayProperty(
                title = "Title 8",
                subTitle = "Subtitle 8",
            ), onNextClick = {
                println("Do something")
            }
        ),
    )

    val manager = remember { ZtourGuidePlayManager(zTourGuideSteps) }
    //.....................................................
    //.....................................................
    //......................
}