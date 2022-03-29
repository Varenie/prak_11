package com.varenie.prak_11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setObserver()
        setClickListeners()
    }

    private fun setClickListeners() {
        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        val btnPay = findViewById<Button>(R.id.btn_pay)

        fabAdd.setOnClickListener {
            openAddDialog()
        }

        btnPay.setOnClickListener {
            openPaymentDialog()
        }
    }

    private fun openAddDialog() {
        val dialog = AlertDialog.Builder(this)

        val inflater = LayoutInflater.from(this)
        val myWindow = inflater.inflate(R.layout.layout_catalog, null)
        dialog.setView(myWindow)

        val catalogRecycler = myWindow.findViewById<RecyclerView>(R.id.rv_catalog)

        catalogRecycler.layoutManager = LinearLayoutManager(this)
        catalogRecycler.setHasFixedSize(true)
        catalogRecycler.adapter = CatalogAdapter(getProductList())

        dialog.setNegativeButton("Закончить") { dialogInterface, which ->
            dialogInterface.dismiss()
        }

        dialog.show()
    }

    private fun openPaymentDialog() {
        val dialog = AlertDialog.Builder(this)

        val inflater = LayoutInflater.from(this)
        val myWindow = inflater.inflate(R.layout.layout_payment, null)
        dialog.setView(myWindow)

        val ad = dialog.show()
        val bankBtn = myWindow.findViewById<Button>(R.id.btn_bank)
        val cardBtn = myWindow.findViewById<Button>(R.id.btn_card)
        val cashBtn = myWindow.findViewById<Button>(R.id.btn_cash)

        bankBtn.setOnClickListener {
            Singleton.productBasket.value?.clear()
            Singleton.productBasket.apply {
                postValue(value)
            }
            calculateBonus()
            ad.dismiss()
        }

        cardBtn.setOnClickListener {
            Singleton.productBasket.value?.clear()
            Singleton.productBasket.apply {
                postValue(value)
            }
            calculateBonus()
            ad.dismiss()
        }

        cashBtn.setOnClickListener {
            Singleton.productBasket.value?.clear()
            Singleton.productBasket.apply {
                postValue(value)
            }
            calculateBonus()
            ad.dismiss()
        }
    }

    private fun calculateBonus() {
        val bonusCount = (Singleton.totalPrice.value?.times(0.1))?.toInt()
        Singleton.totalPrice.value = 0
        Singleton.countBonus.value = bonusCount
    }

    private fun setObserver() {
        val totalPrice = findViewById<TextView>(R.id.tv_total_price)
        val bonusCount = findViewById<TextView>(R.id.tv_bonus)

        Singleton.productBasket.observe(this) {

            setRecycler(it)
        }

        Singleton.totalPrice.observe(this) {
            totalPrice.text = it.toString()
        }

        Singleton.countBonus.observe(this) {
            bonusCount.text = it.toString()
        }
    }

    private fun getProductList(): List<Product> {
        return listOf(
            Product("Огурец", 12),
            Product("Помидор", 11),
            Product("Хлеб", 31),
            Product("Сухарики", 56),
            Product("Чипсы", 97),
            Product("Кола 0,5л", 68),
            Product("Вода 1л", 34),
            Product("Гусь", 1236),
            Product("Доширак", 48),
            Product("Кетчуп", 89),
            Product("Кофе 0,4", 120),
            Product("Круасан", 128),
            Product("Шоколадка", 65),
            Product("Семечки", 276),
            Product("Липтон", 147),
            Product("Мороженное", 55),
            Product("Чебупели", 147),
        )
    }

    private fun setRecycler(productBasket: ArrayList<Product>) {
        val myRecycler = findViewById<RecyclerView>(R.id.recyclerView)

        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)
        myRecycler.adapter = MyAdapter(productBasket)
    }
}