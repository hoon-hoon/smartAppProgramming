package kr.ac.kumoh.s17w04carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s17w04carddealer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding;
    private lateinit var model: CardDealerViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        model = ViewModelProvider(this)[CardDealerViewModel::class.java]
        model.cards.observe(this, Observer {
            val res = IntArray(5)
            for (i in res.indices) {
                res[i] = resources.getIdentifier(
                    getCardName(model.cards.value!![i]), //  == getCardName(it[i])
                    "drawable",
                    packageName
                )
            }
            main.card1.setImageResource(res[0])
            main.card2?.setImageResource(res[1])
            main.card3?.setImageResource(res[2])
            main.card4?.setImageResource(res[3])
            main.card5?.setImageResource(res[4])

        })

        main.btnShuffle.setOnClickListener {
            model.shuffle()
            val hand: List<Int> = model.cards.value?.toList() ?: emptyList()
            val result = evaluateHand(hand)
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Poker Hand Result")
            alertDialog.setMessage("Result: $result")
            alertDialog.setPositiveButton("OK", null)
            alertDialog.show()
        }

        main.btnSimul.setOnClickListener {
            runSimulation(10000)
        }
    }

    private fun runSimulation(simulationCount: Int) {
        val model = CardDealerViewModel()
        val handOccurrences = mutableMapOf<String, Int>()

        repeat(simulationCount) {
            model.shuffle()
            val hand: List<Int> = model.cards.value?.toList() ?: emptyList()
            val result = evaluateHand(hand)
            handOccurrences[result] = handOccurrences.getOrDefault(result, 0) + 1
        }

        val sortedOccurrences = handOccurrences.entries.sortedByDescending { it.value }
        val resultStringBuilder = StringBuilder()
        resultStringBuilder.append("족보별 출현 횟수 및 확률\n")

        for ((hand, count) in sortedOccurrences) {
            val probability = count.toDouble() / simulationCount * 100
            resultStringBuilder.append("$hand: $count 회, 확률: $probability%\n")
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("시뮬레이션 결과")
        alertDialogBuilder.setMessage(resultStringBuilder.toString())
        alertDialogBuilder.setPositiveButton("확인", null)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun getCardName(c: Int): String {
        if (c == -1) {
            return "joker_card"
        }
        var shape = when (c / 13) {
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }

        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> {
                shape = shape.plus("2")
                "jack"
            }

            11 -> {
                shape = shape.plus("2")
                "queen"
            }

            12 -> {
                shape = shape.plus("2")
                "king"
            }

            else -> "error"
        }
//        return if (number in arrayOf("jack", "queen", "king"))
//            "c_${number}_of_${shape}2"
//        else
//            "c_${number}_of_${shape}"
        return "c_${number}_of_${shape}"
    }

    //과제부분
    private fun isFlush(cards: List<Int>): Boolean {
        val firstSuit = cards[0] / 13
        return cards.all { it / 13 == firstSuit }
    }

    private fun isStraight(cards: List<Int>): Boolean {
        for (i in 1 until cards.size) {
            if (cards[i] % 13 != cards[i - 1] % 13 - 1) {
                return false
            }
        }
        return true
    }

    private fun isFourOfAKind(cards: List<Int>): Boolean {
        val counts = mutableMapOf<Int, Int>()
        for (card in cards) {
            val rank = card % 13
            counts[rank] = counts.getOrDefault(rank, 0) + 1
            if (counts[rank] == 4) {
                return true
            }
        }
        return false
    }

    private fun isFullHouse(cards: List<Int>): Boolean {
        val counts = mutableMapOf<Int, Int>()
        for (card in cards) {
            val rank = card % 13
            counts[rank] = counts.getOrDefault(rank, 0) + 1
        }
        val values = counts.values.toList()
        return values.contains(3) && values.contains(2)
    }

    private fun isThreeOfAKind(cards: List<Int>): Boolean {
        val counts = mutableMapOf<Int, Int>()
        for (card in cards) {
            val rank = card % 13
            counts[rank] = counts.getOrDefault(rank, 0) + 1
            if (counts[rank] == 3) {
                return true
            }
        }
        return false
    }

    private fun isTwoPair(cards: List<Int>): Boolean {
        val counts = mutableMapOf<Int, Int>()
        var pairCount = 0
        for (card in cards) {
            val rank = card % 13
            counts[rank] = counts.getOrDefault(rank, 0) + 1
            if (counts[rank] == 2) {
                pairCount++
            }
        }
        return pairCount == 2
    }

    private fun isOnePair(cards: List<Int>): Boolean {
        val counts = mutableMapOf<Int, Int>()
        for (card in cards) {
            val rank = card % 13
            counts[rank] = counts.getOrDefault(rank, 0) + 1
            if (counts[rank] == 2) {
                return true
            }
        }
        return false
    }



    private fun evaluateHand(cards: List<Int>): String {
        if (cards.size != 5) {
            return "Invalid hand"
        }

        val sortedCards = cards.sortedByDescending { if (it % 13 == 0) 13 else it % 13 }

        val isFlush = isFlush(sortedCards)
        val isStraight = isStraight(sortedCards)

        if (isFlush && isStraight) {
            if (sortedCards[0] % 13 == 12) {
                return "Royal Straight Flush"
            }
            return "Straight Flush"
        }
        if (isFourOfAKind(sortedCards)) {
            return "Four of a Kind"
        }
        if (isFullHouse(sortedCards)) {
            return "Full House"
        }
        if (isFlush) {
            return "Flush"
        }
        if (isStraight) {
            return "Straight"
        }
        if (isThreeOfAKind(sortedCards)) {
            return "Three of a Kind"
        }
        if (isTwoPair(sortedCards)) {
            return "Two Pair"
        }
        if (isOnePair(sortedCards)) {
            return "One Pair"
        }

        val highCardValue = when (val highCardRank = sortedCards[0] % 13) {
            10 -> "Jack"
            11 -> "Queen"
            12 -> "King"
            0 -> "ACE"
            else -> (highCardRank + 1).toString()
        }

        return "$highCardValue High Card"
    }

}

