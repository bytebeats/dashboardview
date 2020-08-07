package me.bytebeats.dbv.tplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:06
 */
class CubicHermiteInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float = cubicHermite(input, 0F, 1F, 4F, 4F)

    private fun cubicHermite(t: Float, start: Float, end: Float, tangent0: Float, tangent1: Float): Float {
        val t2 = t * t
        val t3 = t2 * t
        return (2 * t3 - 3 * t2 + 1) * start
        +(t3 - 2 * t2 + t) * tangent0
        +(-2 * t3 + 3 * t2) * end
        +(t3 - t2) * tangent1
    }
}