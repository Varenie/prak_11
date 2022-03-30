package com.varenie.prak_11

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val productBasket: ArrayList<Product>): RecyclerView.Adapter<MyAdapter.MyHolder>() {
    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val price = itemView.findViewById<TextView>(R.id.tv_price)

        val delete = itemView.findViewById<ImageView>(R.id.iv_delete)
        val plus = itemView.findViewById<ImageView>(R.id.tv_plus)
        val countTxt = itemView.findViewById<TextView>(R.id.tv_count)
        val minus = itemView.findViewById<ImageView>(R.id.tv_minus)

        fun bind(product: Product, position: Int) {
            name.text = product.name
            price.text = product.price.toString()

            var count = 1
            countTxt.text = count.toString()

            plus.setOnClickListener {
                count++
                countTxt.text = count.toString()
                Singleton.totalPrice.value = Singleton.totalPrice.value?.plus(product.price)
            }

            minus.setOnClickListener {
                count--
                if (count == 0) {
                    Singleton.totalPrice.value = Singleton.totalPrice.value?.minus(product.price)
                    Singleton.productBasket.value?.remove(product)
                    Singleton.productBasket.apply {
                        postValue(value)
                    }
                } else {
                    countTxt.text = count.toString()
                    Singleton.totalPrice.value = Singleton.totalPrice.value?.minus(product.price)
                }
            }

            delete.setOnClickListener {
                Singleton.totalPrice.value = Singleton.totalPrice.value?.minus(product.price * count)
                Singleton.productBasket.value?.remove(product)
                Singleton.productBasket.apply {
                    postValue(value)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)

        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(productBasket[position], position)
    }

    override fun getItemCount(): Int {
        return productBasket.size
    }
}