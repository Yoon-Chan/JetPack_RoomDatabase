package com.example.chap43_roomdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ProductRepository(private val productDao: ProductDao) {
    val allProducts : LiveData<List<Product>> = productDao.getAllProducts()
    val searchResult = MutableLiveData<List<Product>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertProduct(newprodect : Product){
        coroutineScope.launch(Dispatchers.IO) {
            productDao.insertProduct(newprodect)
        }
    }

    fun deleteProduct(name : String){
        coroutineScope.launch(Dispatchers.IO){
            productDao.deleteProduct(name)
        }
    }

    fun findProduct(name : String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name : String) : Deferred<List<Product>?> = coroutineScope.async(Dispatchers.IO){
        return@async productDao.findProduct(name)
    }

}