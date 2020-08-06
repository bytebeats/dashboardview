package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:06
 */
class CubicHermiteInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float = cubicHermite(input, 0F, 1F, 4F, 4F)

    private fun cubicHermite(input: Float, start: Float, end: Float, tangent0: Float, tangent1: Float): Float {
        val input1 = input * input
        val input2 = input1 * input
        return (2 * input2 - 3 * input1 + 1) * start
        +(input2 - 2 * input1 + input) * tangent0
        +(-2 * input2 + 3 * input1) * end
        +(input2 - input1) * tangent1
    }
}