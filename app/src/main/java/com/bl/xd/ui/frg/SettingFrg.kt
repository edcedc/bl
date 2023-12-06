package com.bl.xd.ui.frg

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.yc.tea.api.ApiService
import com.bl.xd.R
import com.bl.xd.base.BaseFragment
import com.bl.xd.databinding.FSettingBinding
import com.bl.xd.ext.showMessage
import com.bl.xd.ext.showToast
import com.bl.xd.mar.MyApplication
import com.bl.xd.util.CacheUtil
import com.bl.xd.viewmodel.SettingModel
import com.bl.xd.weight.PopupWindowTool
import me.hgj.jetpackmvvm.ext.nav


class SettingFrg: BaseFragment<SettingModel, FSettingBinding>() {

    val settingModel: SettingModel by viewModels()

    var languagePosition: Int = 0

    var languageChoosePosition: Int = 0

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()


        when(MyApplication.getCurrentLanguage()){
            "zh" -> {
                mViewModel.language.set(requireActivity().getString(R.string.s_chinese))
                languagePosition = 0
            }
            "zh-rHK" -> {
                mViewModel.language.set(requireActivity().getString(R.string.t_chinese))
                languagePosition = 1
            }
            "en" -> {
                mViewModel.language.set(requireActivity().getString(R.string.e_english))
                languagePosition = 2
            }
        }
        languageChoosePosition = languagePosition
        mViewModel.host.set(CacheUtil.getUrl())
        mViewModel.companyId.set(CacheUtil.getCompanyID())

    }

    inner class ProxyClick(){

        fun close(){
            nav().navigateUp()
        }

        fun setlanguage(){
            PopupWindowTool.showListDialog(activity)
                .asCenterList(getString(R.string.please_language),
                    arrayOf(
                        getString(R.string.s_chinese),
                        getString(R.string.t_chinese),
                        getString(R.string.e_english),
                    ),{ position, text ->
                        when(position){
                            0 -> mViewModel.language.set(requireActivity().getString(R.string.s_chinese))
                            1 -> mViewModel.language.set(requireActivity().getString(R.string.t_chinese))
                            2 -> mViewModel.language.set(requireActivity().getString(R.string.e_english))
                        }
                        languagePosition = position
                    }).show()
        }

        fun setSave(){
            when {
                mViewModel.host.get().isEmpty() -> showMessage(getString(R.string.hiht_))
                mViewModel.companyId.get().isEmpty() -> showMessage(getString(R.string.hiht_))
                else -> {
                    if (languageChoosePosition != languagePosition){
                        when(languagePosition){
                            0 -> {
                                MyApplication.setCurrentLanguage("zh")
                            }
                            1 ->{
                                MyApplication.setCurrentLanguage("zh-rHK")
                            }
                            2 -> {
                                MyApplication.setCurrentLanguage("en")
                            }
                        }
                    }
                    if ((mViewModel.host.get().isEmpty() && mViewModel.companyId.get().isEmpty())){
                        showToast(getString(R.string.hiht_))
                        return
                    }
                    if (languageChoosePosition != languagePosition){
                        ActivityCompat.recreate(requireActivity())
                    }
                    ApiService.SERVLET_URL = CacheUtil.getUrl()
                    CacheUtil.setUrl(mViewModel.host.get())
                    CacheUtil.setCompanyID(mViewModel.companyId.get())
                    showToast(getString(R.string.release_success))
                }
            }
        }
    }

}