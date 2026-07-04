package tech.capullo.audio.contracts

/**
 * THE INTEGRATOR SEAM - the only structural difference between the apps.
 *
 * A source library implements this to feed the shared delivery engine. The engine owns the
 * player (ExoPlayer + FIFO sink + Snapserver); the source only *resolves* items and *observes*
 * playback to drive prefetch. This keeps the dependency direction one-way (source → contracts ←
 * engine) with a single feedback signal: [onQueueAdvanced].
 */
public interface MediaSourceProvider {
    /**
     * Resolve a logical id (from [PlaybackQueue.idAt]) to a playable [MediaRequest].
     *
     * May suspend: Telecloud awaits the on-demand TDLib download here before returning the
     * local file URI; QuantumCast returns the stream URL immediately.
     */
    public suspend fun mediaRequestFor(id: String): MediaRequest

    /** The current queue/ordering. */
    public fun queue(): PlaybackQueue

    /**
     * Called by the engine when playback advances to [currentIndex], so the source can pull
     * N+1/N+2 ahead (Telecloud's 2-track lookahead prefetch). Default no-op for sources that
     * don't prefetch (e.g. live radio). This is the one player→source feedback path.
     */
    public fun onQueueAdvanced(currentIndex: Int) {}
}
