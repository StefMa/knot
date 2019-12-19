package de.halfbit.knot.detekt.rules

import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtExpression

class RequireOnErrorReturn : Rule() {

    override val issue: Issue = Issue(
        javaClass.simpleName,
        Severity.Warning,
        "This rule reports missing onErrorReturn operators in actions {} section",
        Debt.FIVE_MINS
    )

    override fun visitExpression(expression: KtExpression) {
        println("--- expression")
        println(expression)
        println(expression.text)

        if (expression is KtCallExpression) {
            if (expression.calleeExpression?.text == "actions") {
                println(">>>>>")
                println("           Calling knot function")
                println(">>>>>")
            }
        }

        println()
        super.visitExpression(expression)
    }

    /*
    override fun visitElement(element: PsiElement) {
        println("--- element")
        println(element)
        println(element.text)
        println()
        super.visitElement(element)
    }
     */

}
