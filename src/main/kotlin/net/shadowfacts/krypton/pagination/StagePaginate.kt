package net.shadowfacts.krypton.pagination

import net.shadowfacts.krypton.Page
import net.shadowfacts.krypton.pagination.config.paginationDelimiter
import net.shadowfacts.krypton.pipeline.stage.Stage

/**
 * @author shadowfacts
 */
class StagePaginate: Stage() {

	override val id = "paginate"

	override fun scan(page: Page) {
	}

	override fun apply(page: Page, input: String): String {
		val paginate = page.metadata["paginate"] as? Boolean
		if (paginate != null && paginate) {
			val pages = input.split(page.krypton.config.paginationDelimiter).map(String::trim).filter(String::isNotEmpty)

			if (System.getProperty("krypton.pagination.debugPages").toBoolean()) {
				println(pages)
			}

			page.metadata["pages"] = pages
			return ""
		}
		return input
	}

}