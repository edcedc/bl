package com.bl.xd.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.bl.xd.R
import com.bl.xd.bean.DataBean


class AlertJournalAdapter (data: ArrayList<DataBean>) :
    BaseQuickAdapter<DataBean, BaseViewHolder>(
        R.layout.i_alert_journal, data) {


    init {
//        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: DataBean) {
        //赋值
        item.run {
            holder.setText(R.id.tv_trigger_location, item.LoginID)
        }
    }

}