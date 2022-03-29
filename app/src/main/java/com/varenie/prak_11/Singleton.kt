package com.varenie.prak_11

import androidx.lifecycle.MutableLiveData

object Singleton {
    var productBasket = MutableLiveData(arrayListOf<Product>())
    var countBonus = MutableLiveData(0)
    var totalPrice = MutableLiveData(0)
}