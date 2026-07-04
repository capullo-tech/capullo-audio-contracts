package tech.capullo.audio.contracts

/**
 * Immutable snapshot of what is currently playing.
 *
 * The delivery engine's Snapcast control plugin reads this to publish
 * `Plugin.Stream.Player.Properties` to web players and remote snapclients. It is the union
 * of both apps' needs; app-specific fields (country, codec, bitrate, youtubeUrl, chat name…)
 * go in [extras] so the contract stays stable as apps evolve.
 */
public data class NowPlaying(
    public val title: String,
    public val artist: String = "",
    /** Radio station name OR audio album. */
    public val album: String = "",
    /** Base64-encoded artwork (embedded `artData`); `null` when unavailable. */
    public val artworkBase64: String? = null,
    /** `0` = live/unknown (radio). */
    public val durationMs: Long = 0,
    public val positionMs: Long = 0,
    public val isPlaying: Boolean = false,
    public val canGoNext: Boolean = false,
    public val canGoPrevious: Boolean = false,
    /** Radio origin URL; `null` for file-based playback. */
    public val streamUrl: String? = null,
    /** App-specific metadata: country, countryCode, codec, bitrate, youtubeUrl, … */
    public val extras: Map<String, String> = emptyMap(),
) {
    public companion object {
        /** Neutral "nothing playing" state. */
        public val EMPTY: NowPlaying = NowPlaying(title = "")
    }
}
