package com.example.taobaounion.model.bean

class FlashSaleData : IBaseInfo {

    var linkUrl = ""
    var cover = ""
    var titles = ""
    var couponCount: Long = 0
    var list = arrayListOf<FlashSaleData>()
    var startTime: Long = 0
    var ticket: Ticket? = null

    constructor() {

    }

    constructor(flashSaleData: FlashSaleData) {
        this.linkUrl = flashSaleData.linkUrl
        this.cover = flashSaleData.cover
        this.titles = flashSaleData.titles
        this.startTime = flashSaleData.startTime
        this.ticket = flashSaleData.ticket
        this.couponCount = flashSaleData.couponCount
    }


    override fun getLink(): String = linkUrl

    override fun getPict_url(): String = cover

    override fun getTitle(): String = titles

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FlashSaleData) return false

        if (linkUrl != other.linkUrl) return false
        if (cover != other.cover) return false
        if (titles != other.titles) return false
        if (couponCount != other.couponCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = linkUrl.hashCode()
        result = 31 * result + cover.hashCode()
        result = 31 * result + titles.hashCode()
        result = 31 * result + couponCount.hashCode()
        return result
    }


}