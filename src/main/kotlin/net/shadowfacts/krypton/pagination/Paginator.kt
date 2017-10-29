package net.shadowfacts.krypton.pagination

import net.shadowfacts.krypton.Page
import net.shadowfacts.krypton.config.Configuration
import net.shadowfacts.krypton.permalinks.config.permalinkIndex

/**
 * @author shadowfacts
 */
object Paginator {

	fun <T> paginate(page: Page, items: List<T>, perPage: Int = 5): List<Pair<PaginationData, List<T>>> {
		val permalink = page.metadata["permalink"] as String

		val pages = mutableListOf<Pair<PaginationData, List<T>>>()

		val totalPages = Math.ceil(items.size.toDouble() / perPage).toInt()
		for (i in 0.until(totalPages)) {
			val subList = items.subList(i * perPage, Math.min((i + 1) * perPage, items.size))
			val prev = if (i == 0) null else getPermalink(permalink, i - 1)
			val next = if (i == totalPages - 1) null else getPermalink(permalink, i + 1)
			pages += PaginationData(i + 1, prev, next) to subList
		}

		return pages
	}

	fun getPermalink(permalink: String, index: Int): String {
		var permalink = if (index > 0) {
			val dest = if (permalink.endsWith("/")) permalink else permalink.split(".").dropLast(1).joinToString(".")
			dest + (index + 1).toString() + "/"
		} else {
			permalink
		}
		return permalink
	}

	data class PaginationData(val page: Int, val previousPermalink: String?, val nextPermalink: String?)

}