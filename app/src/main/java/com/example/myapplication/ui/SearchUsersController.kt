package com.example.myapplication.ui

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.bumptech.glide.RequestManager
import com.example.myapplication.UserBindingModel_
import com.example.myapplication.model.User

class SearchUsersController(
    private val requestManager: RequestManager
) : PagedListEpoxyController<User>() {
    override fun buildItemModel(currentPosition: Int, item: User?): EpoxyModel<*> {
        return if (item == null) {
            UserBindingModel_().id("-$currentPosition")
        } else {
            UserBindingModel_()
                .id(currentPosition)
                .userName(item.name)
                .requestManager(requestManager)
                .avatarUrl(item.avatarUrl)
        }
    }
}