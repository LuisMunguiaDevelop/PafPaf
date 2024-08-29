package com.example.pafpaf.features.album.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pafpaf.R
import com.example.pafpaf.features.album.model.Photo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoItem(
    photo: Photo,
    isInSelectionMode: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Box {
        AsyncImage(
            model = photo.path,
            contentDescription = "Saved Image",
            modifier = Modifier
                .fillMaxSize()
                .size(100.dp)
                .padding(0.8.dp)
                .combinedClickable(
                    onClick = {
                        onClick.invoke()
                    },
                    onLongClick = {
                        onLongClick.invoke()
                    }
                ),
            contentScale = ContentScale.FillBounds
        )
        if (isInSelectionMode) {
            val selectedImg =
                if (isSelected)
                    R.drawable.baseline_check_circle_24
                else
                    R.drawable.baseline_radio_button_unchecked_24
            Image(
                painter = painterResource(selectedImg),
                contentDescription = "checked",
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(5.dp),
                colorFilter = ColorFilter.tint(Color.Cyan)
            )
        }
    }
}