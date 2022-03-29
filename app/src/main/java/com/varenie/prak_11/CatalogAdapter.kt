package com.varenie.prak_11

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CatalogAdapter(private val productList: List<Product>): RecyclerView.Adapter<CatalogAdapter.CatalogVH>() {
    class CatalogVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val price = itemView.findViewById<TextView>(R.id.tv_price)

        val addBtn = itemView.findViewById<ImageView>(R.id.iv_add)

        fun bind(product: Product) {
            name.text = product.name
            price.text = product.price.toString()

            addBtn.setOnClickListener {
                Singleton.productBasket.value?.add(product)
                Singleton.productBasket.apply {
                    postValue(value)
                }
                Singleton.totalPrice.value = Singleton.totalPrice.value?.plus(product.price)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.catalog_recycler_item, null)

        return CatalogVH(view)
    }

    override fun onBindViewHolder(holder: CatalogVH, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}