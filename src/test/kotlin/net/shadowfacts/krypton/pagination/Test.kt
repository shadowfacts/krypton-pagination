package net.shadowfacts.krypton.pagination

import net.shadowfacts.krypton.Krypton
import net.shadowfacts.krypton.collections.pipeline.stage.StageLoadCollections
import net.shadowfacts.krypton.ekt.config.EKTConfig
import net.shadowfacts.krypton.ekt.config.ekt
import net.shadowfacts.krypton.ekt.pipeline.PipelineVoidIncludesLayouts
import net.shadowfacts.krypton.ekt.pipeline.stage.StageLayoutEKT
import net.shadowfacts.krypton.ekt.pipeline.stage.StageRenderEKT
import net.shadowfacts.krypton.pipeline.selector.PipelineSelectorExtension
import net.shadowfacts.krypton.util.dependencies.Dependencies
import java.io.File

/**
 * @author shadowfacts
 */
fun main(args: Array<String>) {
	val krypton = Krypton {
		source = File("source")
		output = File("output")

		ekt = EKTConfig().apply {
			layoutsDir = File("source/_layouts")
		}
	}

	krypton.createPipeline {
		selector = PipelineSelectorExtension("html")

		addStage(StageLoadCollections(), Dependencies {
		})
		addStage(StageRenderEKT(), Dependencies {
			after += "loadCollections"
		})
		addStage(StagePaginate(), Dependencies {
			after += "ekt"
		})
		addStage(StageLayoutEKT(), Dependencies {
			after += "paginate"
		})

		final = FinalStageOutputPaginated()
	}

	krypton.addPipeline(PipelineVoidIncludesLayouts, 100)

	krypton.generate()
}