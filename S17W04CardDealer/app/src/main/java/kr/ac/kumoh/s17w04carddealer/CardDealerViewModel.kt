package kr.ac.kumoh.s17w04carddealer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CardDealerViewModel : ViewModel() {
    private var _cards = MutableLiveData<IntArray>(IntArray(5) { -1 })
    val cards: LiveData<IntArray> get() = _cards

    fun shuffle() {
        var num = 0
        val newCards = IntArray( 5 ) { -1 }

        for ( i in newCards.indices) {
            do {
                num = Random.nextInt(52)
            } while (num in newCards)
            newCards[i] = num
            Log.d("newCards", num.toString())
        }
        newCards.sort()
        _cards.value = newCards // 제일 중요한 부분
    }
}