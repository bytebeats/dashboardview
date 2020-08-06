package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:00
 */
class BounceInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float = when {
        input < 0.3535F -> bounce(input)
        input < 0.7408F -> bounce(input - 0.54719F) + 0.7F
        input < 0.9644F -> bounce(input - 0.8526F) + 0.9F
        else -> bounce(input - 1.0435F) + 0.95F
    }

    private fun bounce(input: Float): Float = input * input * 8F
}