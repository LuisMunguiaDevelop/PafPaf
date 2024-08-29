package com.example.pafpaf.features.album_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pafpaf.R

@Composable
fun AlbumListBottomMenu(
    onDeleteAction: () -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onDeleteAction.invoke() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "delete"
                )
                Text(text = "Eliminar")
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun AlbumListBottomMenuPreview() {
    AlbumListBottomMenu(
        onDeleteAction = {}
    )
}