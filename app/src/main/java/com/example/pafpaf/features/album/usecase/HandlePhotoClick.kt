package com.example.pafpaf.features.album.usecase


enum class PhotoClickEvents {
    ADD_SELECTED_ITEM,
    REMOVE_SELECTED_ITEM,
    SHOW_PHOTO
}

fun handlePhotoClick(
    photoIsSelected: Boolean,
    isInSelectionMode: Boolean
): PhotoClickEvents {
    return when (isInSelectionMode) {
        true -> {
            if (photoIsSelected) PhotoClickEvents.REMOVE_SELECTED_ITEM else PhotoClickEvents.ADD_SELECTED_ITEM
        }

        false -> {
            PhotoClickEvents.SHOW_PHOTO
        }
    }
}