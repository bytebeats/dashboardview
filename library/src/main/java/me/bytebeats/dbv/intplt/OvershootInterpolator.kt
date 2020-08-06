package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:12
 */
class OvershootInterpolator : BaseTimeInterpolator() {
    var tension = 2.0F
    override fun getInterpolation(input: Float): Float {
        val newInput = input - 1.0F
        return newInput * newInput * ((tension + 1F) * newInput + tension) + 1.0F
    }
}