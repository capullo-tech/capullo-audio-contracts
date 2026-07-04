package tech.capullo.audio.contracts

/**
 * A neutral, engine-agnostic description of one playable item.
 *
 * The source library produces a [MediaRequest]; `capullo-audio` (the delivery engine)
 * converts it into a Media3 `MediaItem`. Keeping Media3 out of this contract is deliberate:
 * the source seam must not depend on the engine's playback types .
 *
 * - QuantumCast (radiobrowser): [uri] is a stream URL (often HLS → set [mimeType]).
 * - Telecloud (telegram): [uri] is a local file path/URI, produced *after* the on-demand
 *   download completes.
 */
public data class MediaRequest(
    /** Stream URL or local file URI/path. */
    public val uri: String,
    /** Optional MIME hint, e.g. `application/x-mpegURL` for HLS. `null` = let the engine infer. */
    public val mimeType: String? = null,
    /** Optional request headers (auth tokens, user-agent) for network streams. */
    public val headers: Map<String, String> = emptyMap(),
)
