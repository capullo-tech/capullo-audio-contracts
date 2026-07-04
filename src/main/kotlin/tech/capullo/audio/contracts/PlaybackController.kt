package tech.capullo.audio.contracts

/**
 * Transport commands the delivery engine (and remote snapclients / web players via the
 * Snapcast control plugin) issue back to the app.
 */
public interface PlaybackController {
    public fun play()
    public fun pause()
    public fun next()
    public fun previous()
    public fun seekTo(positionMs: Long)
}
