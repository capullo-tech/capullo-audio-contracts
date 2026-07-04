package tech.capullo.audio.contracts

import kotlinx.coroutines.flow.StateFlow

/**
 * Exposes the app/source's live now-playing state as an observable flow.
 *
 * The delivery engine subscribes to this and forwards changes to the Snapcast control plugin.
 */
public interface NowPlayingSource {
    public val nowPlaying: StateFlow<NowPlaying>
}
