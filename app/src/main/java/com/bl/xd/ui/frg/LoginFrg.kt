package com.bl.xd.ui.frg

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.bl.xd.api.UIHelper
import com.bl.xd.base.BaseFragment
import com.bl.xd.databinding.FLoginBinding
import com.bl.xd.util.CacheUtil
import com.bl.xd.viewmodel.LoginModel
import me.hgj.jetpackmvvm.ext.nav


class LoginFrg: BaseFragment<LoginModel, FLoginBinding>() {

    val loginModel: LoginModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(loginModel)
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()

        val user = CacheUtil.getUser()
        if (user != null){
            mViewModel.username.set(user.LoginID)
            mViewModel.password.set(user.Password)
        }
    }

    override fun createObserver() {

    }

    inner class ProxyClick() {
        fun clear(){
            mViewModel.username.set("")
        }

        var onCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowPwd.set(isChecked)
            }
        fun login(){
//            when {
//                mViewModel.username.get().isEmpty() -> showMessage(getString(R.string.error_phone))
//                mViewModel.password.get().isEmpty() -> showMessage(getString(R.string.error_phone))
//                else -> loginModel.login(
//                    mViewModel.username.get(),
//                    mViewModel.password.get()
//                )
//            }
            UIHelper.startMainAct()
        }

        fun toSet(){
            UIHelper.startSettingFrg(nav())
        }

    }

}
