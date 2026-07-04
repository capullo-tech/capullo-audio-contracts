package tech.capullo.audio.contracts

/**
 * The ordering the source presents to the engine.
 *
 * - QuantumCast: a single-station "rotation" ([isRotating] = true) where next/previous are
 *   always meaningful.
 * - Telecloud: a finite (optionally shuffled) playlist where next/previous depend on position.
 */
public interface PlaybackQueue {
    public val size: Int
    public val currentIndex: Int

    /** `true` when next/previous are always available (endless rotation) regardless of position. */
    public val isRotating: Boolean

    /** Logical id at [index], consumed by [MediaSourceProvider.mediaRequestFor]; `null` if out of range. */
    public fun idAt(index: Int): String?
}
