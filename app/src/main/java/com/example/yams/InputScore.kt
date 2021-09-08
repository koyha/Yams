package com.example.yams

class InputScore {
    private var diceList = ArrayList<Int>()

    fun setDiceList(diceList: MutableCollection<Int>) {
        this.diceList = ArrayList()
        for (i in diceList)
            this.diceList.add(i)
    }

    fun getNormalScores(dice: Int): Int {
        return diceList.count { it == dice } * dice
    }

    fun getSameKind(sameCount: Int): Int {
        for (i in 1..6)
            if (diceList.count { it == i } >= sameCount)
                return diceList.sum()
        return 0
    }

    fun getFull(): Int {
        var twoSame = false
        var threeSame = false
        for (i in 1..6) {
            if (diceList.count { it == i } == 2)
                twoSame = true
            if (diceList.count { it == i } == 3)
                threeSame = true
        }

        return if (twoSame && threeSame)
            25
        else
            0
    }

    fun getSmallStraight(): Int {
        return if (hasInRow(4))
            30
        else
            0
    }

    fun getLargeStraight(): Int {
        return if (hasInRow(5))
            40
        else
            0
    }

    private fun hasInRow(amountInRow: Int): Boolean {
        val set = diceList.toSortedSet()
        for (i in 1..7 - amountInRow) {
            if (set.subSet(i, i + amountInRow).size == amountInRow)
                return true
        }
        return false
    }

    fun getYahtzee(): Int {
        return if (getSameKind(5) != 0)
            50
        else
            0
    }

    fun getChance(): Int {
        return diceList.sum()
    }
}