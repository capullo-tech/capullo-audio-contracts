package tech.capullo.audio.contracts

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test

class ContractsTest {

    @Test
    fun nowPlaying_defaults_areLiveSafe() {
        val np = NowPlaying(title = "Radio X")
        assertEquals("", np.artist)
        assertEquals(0L, np.durationMs)
        assertFalse(np.canGoNext)
        assertNull(np.streamUrl)
        assertEquals(emptyMap<String, String>(), np.extras)
        assertEquals("", NowPlaying.EMPTY.title)
    }

    @Test
    fun mediaRequest_carriesUriAndHints() {
        val r = MediaRequest("https://host/stream.m3u8", mimeType = "application/x-mpegURL")
        assertEquals("https://host/stream.m3u8", r.uri)
        assertEquals("application/x-mpegURL", r.mimeType)
        assertEquals(emptyMap<String, String>(), r.headers)
    }

    /** A trivial in-memory source proves the SPI is implementable and internally consistent. */
    private class FakeSource(private val urls: List<String>) : MediaSourceProvider, NowPlayingSource {
        private val _now = MutableStateFlow(NowPlaying.EMPTY)
        override val nowPlaying: StateFlow<NowPlaying> get() = _now
        var lastPrefetch: Int = -1; private set

        override suspend fun mediaRequestFor(id: String) = MediaRequest(uri = id)
        override fun queue() = object : PlaybackQueue {
            override val size = urls.size
            override val currentIndex = 0
            override val isRotating = false
            override fun idAt(index: Int) = urls.getOrNull(index)
        }
        override fun onQueueAdvanced(currentIndex: Int) { lastPrefetch = currentIndex }
    }

    @Test
    fun spi_isImplementable_endToEnd() = runBlocking {
        val src = FakeSource(listOf("a", "b", "c"))
        val q = src.queue()
        assertEquals(3, q.size)
        assertEquals("b", q.idAt(1))
        assertEquals("b", src.mediaRequestFor(q.idAt(1)!!).uri)
        src.onQueueAdvanced(1)
        assertEquals(1, src.lastPrefetch)
    }
}
