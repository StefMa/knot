package de.halfbit.knot.detekt

import de.halfbit.knot.detekt.rules.RequireOnErrorReturn
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class KnotDetektProvider : RuleSetProvider {
    override val ruleSetId: String = "knot.detekt"
    override fun instance(config: Config) = RuleSet(
        ruleSetId, listOf(RequireOnErrorReturn())
    )
}