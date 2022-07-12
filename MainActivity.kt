package com.example.caculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,View.OnClickListener {
    private var numCount: Int = 0
    private var operateCount: Int = 0
    private val mixList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickEvent()
        clean()
    }

    override fun onClick(p: View?) {
        Log.d("try", "click")
        when (p?.id) {
            R.id.zero_id -> addNum(0)
            R.id.one_id -> addNum(1)
            R.id.two_id -> addNum(2)
            R.id.three_id -> addNum(3)
            R.id.four_id -> addNum(4)
            R.id.five_id -> addNum(5)
            R.id.six_id -> addNum(6)
            R.id.seven_id -> addNum(7)
            R.id.eight_id -> addNum(8)
            R.id.nine_id -> addNum(9)
            R.id.dengyu_id -> equal()
            R.id.jia_id -> addOperate('+')
            R.id.jian_id -> addOperate('-')
            R.id.cheng_id -> addOperate('*')
            R.id.chu_id -> addOperate('/')
            R.id.delete_id -> clean()
        }
    }

    fun setClickEvent() {
        dengyu_id.setOnClickListener(this)
        jia_id.setOnClickListener(this)
        jian_id.setOnClickListener(this)
        cheng_id.setOnClickListener(this)
        chu_id.setOnClickListener(this)
        one_id.setOnClickListener(this)
        two_id.setOnClickListener(this)
        three_id.setOnClickListener(this)
        four_id.setOnClickListener(this)
        five_id.setOnClickListener(this)
        six_id.setOnClickListener(this)
        seven_id.setOnClickListener(this)
        eight_id.setOnClickListener(this)
        nine_id.setOnClickListener(this)
        delete_id.setOnClickListener(this)
    }

    fun addNum(num: Long) {
        if (operateCount == numCount) {
            // 当前为1位的数字
            numCount++
            mixList.add(num.toString())
        } else {
            // 当前为多位的数字
            val numPlus: Long = mixList.get(mixList.size - 1).toLong() * 10 + num
            mixList.set(mixList.size - 1, numPlus.toString())
        }
        // 实时展示界面变化
        show(mixList)
    }

    fun addOperate(operate: Char) {
        if (numCount == 0) return
        if (operateCount < numCount) {
            operateCount++
            mixList.add(operate.toString())
        } else if (operateCount == numCount) {
            mixList.set(mixList.size - 1, operate.toString())
        }
        show(mixList)
    }

    fun calculate() {
        // 优先进行乘除运算
        for (i in mixList.indices) {
            // 兜底判断，防止后续的删除操作导致List长度减小，导致下标溢出
            if (i > mixList.size - 1) break;
            if (mixList.get(i).equals("*")) {
                // 计算“乘”
                val tmp: Long = mixList.get(i - 1).toLong() * mixList.get(i + 1).toLong()
                // 更新值并删除相关操作数
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            } else if (mixList.get(i).equals("/")) {
                // 对非法输入“除0”进行判断
                if (mixList.get(i + 1).equals("0")) break;
                val tmp: Long = mixList.get(i - 1).toLong() / mixList.get(i + 1).toLong()
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            }
        }

        // 进行加减运算
        for(i in mixList.indices) {
            // 兜底判断，防止后续的删除操作导致List长度减小，导致下标溢出
            if (i > mixList.size - 1) break;
            if (mixList.get(i).equals("+")) {
                val tmp: Long = mixList.get(i - 1).toLong() + mixList.get(i + 1).toLong()
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            } else if (mixList.get(i).equals("-")) {
                val tmp: Long = mixList.get(i - 1).toLong() - mixList.get(i + 1).toLong()
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            }
        }
    }


    fun equal() {
        // 当前只输入数字，不需要计算，直接返回
        if (operateCount == 0) return
        // 当前最后一位为运算符，不合法，对最后一位运算符进行删除
        if (operateCount == numCount) {
            mixList.removeAt(mixList.size - 1)
        }
        // 计算表达式的值
        calculate()
        // 进行界面展示
        show(mixList)
    }


    fun clean() {
        operateCount = 0
        numCount = 0
        mixList.clear()
        shuru_id.setText("0")
    }

    fun show(list: ArrayList<String>) {
        if (list.size == 0) shuru_id.setText("0")
        var sb = StringBuilder()
        for (item in list) {
            sb.append(item)
        }
        shuru_id.setText(sb)
    }


}
