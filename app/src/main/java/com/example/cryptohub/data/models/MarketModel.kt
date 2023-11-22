package com.example.cryptohub.data.models

import java.io.Serializable

data class MarketModel(
	val data: Data? = null,
	val status: Status? = null
)

data class Data(
	val cryptoCurrencyList: List<CryptoCurrencyListItem?>? = null,
	val totalCount: String? = null
)

data class QuotesItem(
	val marketCap: Any? = null,
	val volume24h: Any? = null,
	val percentChange1y: Double? = null,
	val marketCapByTotalSupply: Any? = null,
	val percentChange30d: Double? = null,
	val lastUpdated: String? = null,
	val percentChange24h: Double? = null,
	val percentChange7d: Double? = null,
	val price: Any? = null,
	val ytdPriceChangePercentage: Any? = null,
	val percentChange60d: Double? = null,
	val name: String? = null,
	val percentChange90d: Double? = null,
	val dominance: Any? = null,
	val fullyDilluttedMarketCap: Any? = null,
	val percentChange1h: Double? = null,
	val turnover: Any? = null,
	val tvl: Any? = null
)

data class Platform(
	val symbol: String? = null,
	val name: String? = null,
	val tokenAddress: String? = null,
	val id: Int? = null,
	val slug: String? = null
)

data class AuditInfoListItem(
	val coinId: String? = null,
	val auditor: String? = null,
	val auditStatus: Int? = null,
	val reportUrl: String? = null,
	val auditTime: String? = null,
	val score: String? = null,
	val contractAddress: String? = null,
	val contractPlatform: String? = null
)

data class Status(
	val errorMessage: String? = null,
	val elapsed: String? = null,
	val creditCount: Int? = null,
	val errorCode: String? = null,
	val timestamp: String? = null
)

data class CryptoCurrencyListItem(
	val symbol: String? = null,
	val selfReportedCirculatingSupply: Double? = null,
	val totalSupply: Any? = null,
	val cmcRank: Int? = null,
	val isActive: Double? = null,
	val circulatingSupply: Any? = null,
	val dateAdded: String? = null,
	val platform: Platform? = null,
	val tags: List<String?>? = null,
	val quotes: List<QuotesItem?>? = null,
	val badges: List<Int?>? = null,
	val lastUpdated: String? = null,
	val isAudited: Boolean? = null,
	val name: String? = null,
	val marketPairCount: Int? = null,
	val id: Int? = null,
	val maxSupply: Any? = null,
	val slug: String? = null,
	val auditInfoList: List<Any?>? = null
) : Serializable{
	override fun equals(other: Any?): Boolean {
		return super.equals(other)
	}
	override fun hashCode(): Int {
		return super.hashCode()
	}
	override fun toString(): String {
		return super.toString()
	}
}

