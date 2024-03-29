package com.flx.covid_19.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.flx.covid_19.R

class MainActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var btnNext: Button? = null

    private val about_title_array = arrayOf(
        "About Covid-19 App",
        "Wear a Mask",
        "Clean your Hands",
        "Keep a safe Distance")

    private val about_description_array = arrayOf(
        "This applications informs you of how to prevent Covid-19 and statistics so far about Corona virus.",
        "Wear a mask to cover your mouth, nose and chin. Avoid touching the mask!",
        "Regularly and thoroughly clean your hands with an alcohol-based hand rub or wash them with soap and water.",
        "Maintain at least 1 metre (3 feet) distance between yourself and others.")

    private val about_images_array = intArrayOf(
        R.drawable.logo,
        R.drawable.wearing_a_mask_,
        R.drawable.washing_hands,
        R.drawable.smiley_face)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        //Tools.setSystemBarColor(this, R.color.grey_5)
       // Tools.setSystemBarLight(this)
    }

    private fun initComponent() {
        viewPager = findViewById<View>(R.id.view_pager) as ViewPager
        btnNext = findViewById<View>(R.id.btn_next) as Button

        // adding bottom dots
        bottomProgressDots(0)
        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
        btnNext!!.setOnClickListener {
            val current = viewPager!!.currentItem + 1
            if (current < MAX_STEP) {
                // move to next screen
                viewPager!!.currentItem = current
            } else {
                val intent = Intent (baseContext, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                overridePendingTransition(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }
        }
        (findViewById<View>(R.id.bt_close) as ImageButton).setOnClickListener {
            val intent = Intent (baseContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
        }
    }

    private fun bottomProgressDots(current_index: Int) {
        val dotsLayout = findViewById<View>(R.id.layoutDots) as LinearLayout
        val dots = arrayOfNulls<ImageView>(MAX_STEP)
        dotsLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val width_height = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(resources.getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN)
            dotsLayout.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[current_index]!!.setImageResource(R.drawable.shape_circle)
            dots[current_index]!!.setColorFilter(resources.getColor(R.color.orange_400), PorterDuff.Mode.SRC_IN)
        }
    }

    //  viewpager change listener
    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object :
        ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            bottomProgressDots(position)
            if (position == about_title_array.size - 1) {
                btnNext!!.text = getString(R.string.GOT_IT)
                btnNext!!.setBackgroundColor(resources.getColor(R.color.orange_400))
                btnNext!!.setTextColor(Color.WHITE)
            } else {
                btnNext!!.text = getString(R.string.next)
                btnNext!!.setBackgroundColor(resources.getColor(R.color.grey_10))
                btnNext!!.setTextColor(resources.getColor(R.color.grey_90))
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater!!.inflate(R.layout.item_stepper_wizard_light, container, false)
            (view.findViewById<View>(R.id.title) as TextView).text = about_title_array[position]
            (view.findViewById<View>(R.id.description) as TextView).text = about_description_array[position]
            (view.findViewById<View>(R.id.image) as ImageView).setImageResource(about_images_array[position])
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return about_title_array.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    companion object {
        private const val MAX_STEP = 4
    }

    override fun onStart() {
        super.onStart()
        val current = viewPager!!.currentItem + 3
        if (current == MAX_STEP){
            val intent = Intent (baseContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
        }
    }
}