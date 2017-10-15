package net.shadowfacts.krypton.pagination

import net.shadowfacts.krypton.Page
import net.shadowfacts.krypton.pipeline.stage.finalstage.FinalStage
import java.io.File

/**
 * @author shadowfacts
 */
class FinalStageOutputPaginated: FinalStage {

	override fun apply(page: Page, input: String) {
		if ("pages" in page.metadata) {
			val pages = page.metadata["pages"] as? List<String>
			if (pages != null) {
				val permalink = page.metadata["permalink"] as String
				pages.forEachIndexed { index, s ->
					File(page.krypton.config.output, Paginator.getPermalink(page.krypton.config, permalink, index)).apply {
						parentFile.mkdirs()
						writeText(s, Charsets.UTF_8)
					}
				}
				return

			}
		}
		page.output.apply {
			parentFile.mkdirs()
			writeText(input, Charsets.UTF_8)
		}
	}

}