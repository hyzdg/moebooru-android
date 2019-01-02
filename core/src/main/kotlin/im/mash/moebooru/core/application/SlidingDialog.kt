package im.mash.moebooru.core.application

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import android.view.View
import android.view.ViewGroup
import im.mash.moebooru.core.widget.PagerEnabledSlidingPaneLayout
import im.mash.moebooru.core.R

abstract class SlidingDialog(context: Context) : Dialog(context, R.style.MoeTheme_Dialog), SlidingPaneLayout.PanelSlideListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSwipeBackFinish()
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onPanelClosed(panel: View) {

    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {

    }

    override fun onPanelOpened(panel: View) {
        this.dismiss()
    }

    private fun initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            val slidingPaneLayout = PagerEnabledSlidingPaneLayout(context)
            //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认
            //是32dp，现在给它改成0
            try {
                //mOverhangSize属性，意思就是左菜单离右边屏幕边缘的距离
                val fOverHang = SlidingPaneLayout::class.java.getDeclaredField("mOverhangSize")
                fOverHang.isAccessible = true
                //设置左菜单离右边屏幕边缘的距离为0，设置全屏
                fOverHang.set(slidingPaneLayout, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            slidingPaneLayout.setPanelSlideListener(this)
            slidingPaneLayout.sliderFadeColor = ContextCompat.getColor(context, android.R.color.transparent)
            // 左侧的透明视图
            val leftView = View(context)
            leftView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            slidingPaneLayout.addView(leftView, 0)  //添加到SlidingPaneLayout中
            // 右侧的内容视图
            val decor = window!!.decorView as ViewGroup
            val decorChild = decor.getChildAt(0) as ViewGroup
            decorChild.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            decor.removeView(decorChild)
            decor.addView(slidingPaneLayout)
            // 为 SlidingPaneLayout 添加内容视图
            slidingPaneLayout.addView(decorChild, 1)
        }
    }

    private fun isSupportSwipeBack(): Boolean {
        return true
    }
}