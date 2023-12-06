package com.bl.xd.ui.frg

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.bl.xd.R
import com.bl.xd.adapter.AlertJournalAdapter
import com.bl.xd.bean.DataBean
import com.bl.xd.databinding.BTitleRecyclerBinding
import com.bl.xd.ext.init
import com.bl.xd.ext.initClose
import com.bl.xd.ext.loadServiceInit
import com.bl.xd.ext.showLoading
import com.bl.xd.viewmodel.AlertJournalModel
import com.bl.xd.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.bl.xd.base.BaseFragment
import me.hgj.jetpackmvvm.ext.nav


class AlertJournalFrg: BaseFragment<AlertJournalModel, BTitleRecyclerBinding>() {

    //界面状态管理者
    lateinit var loadsir: LoadService<Any>

    val adapter: AlertJournalAdapter by lazy { AlertJournalAdapter(arrayListOf()) }

    private val alertJournalModel: AlertJournalModel by viewModels()


    override fun initView(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        mDatabind.includeToolbar.toolbar.initClose(getString(R.string.alert_journal)) {
            nav().navigateUp()
        }

        //初始化recyclerView
        mDatabind.recyclerView.init(LinearLayoutManager(context), adapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
        }

        //状态页配置
        loadsir = loadServiceInit(mDatabind.swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.onRequest(false)
        }

        //初始化 SwipeRefreshLayout  刷新
        mDatabind.swipeRefresh.init {
            mViewModel.onRequest(false)
        }
    }

    override fun createObserver() {
        super.createObserver()
        val list = ArrayList<DataBean>()
        mViewModel.listBean.observe(viewLifecycleOwner, Observer {
            loadsir.showSuccess()
            val listData = it.listData
            adapter.setList(listData)
        })
    }


    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        mViewModel.onRequest(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity.setSupportActionBar(null)
    }

}