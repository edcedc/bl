package com.bl.xd.viewmodel

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class SettingModel: BaseViewModel() {

    val language = StringObservableField()

    val host = StringObservableField()

    val companyId = StringObservableField()

}