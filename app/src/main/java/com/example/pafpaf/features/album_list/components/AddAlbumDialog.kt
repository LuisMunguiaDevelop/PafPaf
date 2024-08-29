package com.example.pafpaf.features.album_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddAlbumDialog(
    shouldShowDialog: MutableState<Boolean>,
    onConfirm: (String) -> Unit,
) {
    var albumName by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()

    if (shouldShowDialog.value) {
        Dialog(
            onDismissRequest = { shouldShowDialog.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true
            )
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Crear un álbum", fontWeight = FontWeight.Bold)

                    OutlinedTextField(
                        value = albumName,
                        onValueChange = { albumName = it },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Blue
                        ),
                        modifier = Modifier.focusRequester(focusRequester),
                        shape = RoundedCornerShape(20.dp),
                        placeholder = { Text(text = "Nombre del álbum") }
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Button(onClick = { shouldShowDialog.value = false }) {
                            Text(text = "Cancelar")
                        }

                        Button(
                            enabled = albumName.isNotEmpty(),
                            onClick = {
                                onConfirm.invoke(albumName)
                                shouldShowDialog.value = false
                            }
                        ) {
                            Text(text = "Aceptar")
                        }
                    }


                }
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

    }
}


@Composable
@Preview
fun PreviewAddAlbumDialog() {
    val shouldShowDialog = remember { mutableStateOf(true) }
    AddAlbumDialog(
        shouldShowDialog = shouldShowDialog,
        onConfirm = {  })
}