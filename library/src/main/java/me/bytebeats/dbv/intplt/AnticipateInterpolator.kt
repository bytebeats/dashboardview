package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:28
 */

class AnticipateInterpolator : BaseTimeInterpolator() {
    var tension = 2.0F
    override fun getInterpolation(input: Float): Float = input * input * ((tension + 1.0F) * input - tension)
}