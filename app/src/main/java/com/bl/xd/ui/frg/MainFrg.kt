package com.bl.xd.ui.frg

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.bl.xd.R
import com.bl.xd.adapter.AlertAdapter
import com.bl.xd.databinding.FMainBinding
import com.bl.xd.ext.init
import com.bl.xd.ext.loadServiceInit
import com.bl.xd.ext.showLoading
import com.bl.xd.viewmodel.MainModel
import com.bl.xd.weight.recyclerview.SpaceItemDecoration
import com.google.android.material.navigation.NavigationView
import com.kingja.loadsir.core.LoadService
import com.bl.xd.api.UIHelper
import com.bl.xd.base.BaseFragment
import me.hgj.jetpackmvvm.ext.nav


class MainFrg: BaseFragment<MainModel, FMainBinding>(), NavigationView.OnNavigationItemSelectedListener {

    //界面状态管理者
    lateinit var loadsir: LoadService<Any>

    val adapter: AlertAdapter by lazy { AlertAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.navView.setNavigationItemSelectedListener(this)

        setHasOptionsMenu(true)

        mDatabind.includeToolbar.toolbar.run {
            init(getString(R.string.app_name))
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_set -> {
                        onOpenDrawer()
                    }
                }
                true
            }
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mDatabind.drawerLayout.postDelayed({
            when(item.itemId){
                R.id.item_alert->{
                    UIHelper.startAlertJournalFrg(nav())
                }
            }
            mDatabind.drawerLayout.closeDrawer(GravityCompat.END)
        }, 300)
        return true
    }

    //region  抽屉布局
    fun onOpenDrawer() {
        if (!mDatabind.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDatabind.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

}