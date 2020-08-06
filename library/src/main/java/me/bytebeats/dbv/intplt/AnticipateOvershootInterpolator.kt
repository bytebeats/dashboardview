package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:23
 */
class AnticipateOvershootInterpolator : BaseTimeInterpolator() {
    var tension = 2.0F * 1.5F

    override fun getInterpolation(input: Float): Float {
        return if (input < 0.5F) {
            0.5F * anticipate(input * 2.0F, tension)
        } else {
            0.5F * (overshoot(input * 2.0F - 2.0F, tension) + 2.0F)
        }
    }

    private fun anticipate(input: Float, tension: Float): Float {
        return input * input * ((tension + 1.0F) * input - tension)
    }

    private fun overshoot(input: Float, tension: Float): Float {
        return input * input * ((tension + 1.0F) * input + tension)
    }
}