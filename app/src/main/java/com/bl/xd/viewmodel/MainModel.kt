package com.bl.xd.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bl.xd.bean.DataBean
import com.bl.xd.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class MainModel: BaseViewModel() {

    var triggerLocation = StringObservableField()

    var username = StringObservableField()

    var time = StringObservableField()

    private var pagerNumber = 0


    var listBean: MutableLiveData<ListDataUiState<DataBean>> = MutableLiveData()

//    var urlDataState: MutableLiveData<DataBean> = MutableLiveData()


    fun onRequest(isRefresh: Boolean) {
        if (isRefresh) {
            pagerNumber = 0
        }

        val arrayListOf = arrayListOf<DataBean>()
        for (i in 0..9) {
            val bean = DataBean()
            bean.LoginID = "xxx" + i
            arrayListOf.add(bean)
        }

        val listDataUiState =
            ListDataUiState(
                isSuccess = false,
                errMessage = "xx",
                isRefresh = isRefresh,
                listData = arrayListOf
            )
        listBean.value = listDataUiState
    }

}