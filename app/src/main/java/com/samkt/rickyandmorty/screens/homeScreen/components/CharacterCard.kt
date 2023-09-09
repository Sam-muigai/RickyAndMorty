package com.samkt.rickyandmorty.screens.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    characterInfo: CharacterInfo,
    onCharacterClick: (Int) -> Unit,
) {
    val context = LocalContext.current
    val color = when (characterInfo.status) {
        "Dead" -> Color.Red
        "unknown" -> Color.Blue
        else -> Color.Green
    }
    Surface(
        modifier = modifier.width(120.dp).padding(8.dp).clickable {
            onCharacterClick.invoke(characterInfo.id)
        },
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFF47aec4),
    ) {
        Column {
            CoilImage(
                modifier = Modifier.height(100.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp)),
                imageModel = characterInfo.image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                circularReveal = CircularReveal(duration = 500),
            )
            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                Text(
                    text = characterInfo.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Surface(
                        modifier = Modifier.size(8.dp),
                        shape = CircleShape,
                        color = color,
                    ) {}
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = characterInfo.status,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
