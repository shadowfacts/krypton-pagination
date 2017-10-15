package net.shadowfacts.krypton.pagination.config

import net.shadowfacts.krypton.config.Configuration
import net.shadowfacts.krypton.config.config

/**
 * @author shadowfacts
 */
var Configuration.paginationDelimiter: String by config({ it }, fallback = { "===" })