package com.bl.xd.ui.act

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.bl.xd.R
import com.bl.xd.databinding.AZxingBinding
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.bl.xd.base.BaseActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class ZxingAct  : BaseActivity<BaseViewModel, AZxingBinding>(), OnClickListener {

    /**
     * 扫描跳转Activity RequestCode
     */
    val REQUEST_CODE = 111

    /**
     * 选择系统图片Request Code
     */
    val REQUEST_IMAGE = 112

    var isOpen = false

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.ivOpen.setOnClickListener(this)
        mDatabind.ivPhoto.setOnClickListener(this)

        val captureFragment = CaptureFragment()
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.f_zxing);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    inner class ProxyClick {

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_open ->{
                if (!isOpen) {
                    mDatabind.ivOpen.background = ContextCompat.getDrawable(this, R.mipmap.dl_loc)
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    mDatabind.ivOpen.background = ContextCompat.getDrawable(this, R.mipmap.dl_filters)
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }
            }
            R.id.iv_photo ->{
                val intent = Intent()
                intent.action = Intent.ACTION_PICK
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_IMAGE)
            }
        }
    }

    /**
     * 二维码解析回调函数
     */
    var analyzeCallback: CodeUtils.AnalyzeCallback = object : CodeUtils.AnalyzeCallback {
        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS)
            bundle.putString(CodeUtils.RESULT_STRING, result)
            resultIntent.putExtras(bundle)
            LogUtils.e("onAnalyzeSuccess；" + result)
//            EventBus.getDefault().post(StockTakeListEvent(result, "0", true, SCAN_STATUS_QRCODE))
            finish()
//            this@SecondActivity.setResult(RESULT_OK, resultIntent)
//            this@SecondActivity.finish()
        }

        override fun onAnalyzeFailed() {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED)
            bundle.putString(CodeUtils.RESULT_STRING, "")
            resultIntent.putExtras(bundle)
            LogUtils.e("onAnalyzeFailed")
//            this@SecondActivity.setResult(RESULT_OK, resultIntent)
//            this@SecondActivity.finish()
        }
    }

}