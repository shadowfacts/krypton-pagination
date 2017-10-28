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
			val pages = page.metadata["pages"] as List<String>
			val permalink = page.metadata["permalink"] as String
			pages.forEachIndexed { index, s ->
				val dest = File(page.krypton.config.output, Paginator.getPermalink(page.krypton.config, permalink, index))
				dest.parentFile.mkdirs()
				dest.writeText(s, Charsets.UTF_8)
			}
		} else {
			page.output.parentFile.mkdirs()
			page.output.writeText(input, Charsets.UTF_8)
		}
	}

}