# capullo-audio-contracts

The **SPI (contract layer)** of the [Capullo Audio Platform](https://github.com/capullo-tech).
A tiny, **pure-Kotlin/JVM** library of interfaces that decouple *who produces audio + metadata*
(source libraries) from *who delivers it* (`capullo-audio`, the engine).

No Android types. No Media3. No DI framework. Only `kotlinx-coroutines-core` (for `StateFlow`).
This is the stability anchor of the platform - it changes rarely and deliberately (SemVer).

## What's in here

| Type | Role |
|---|---|
| `MediaSourceProvider` | **the integrator seam** - resolve a logical id → `MediaRequest`; expose the queue; receive prefetch signals |
| `MediaRequest` | neutral playable descriptor (uri + mime hint + headers); the engine turns it into a Media3 `MediaItem` |
| `NowPlaying` | immutable now-playing snapshot the engine publishes to web players / snapclients |
| `NowPlayingSource` | exposes `StateFlow<NowPlaying>` |
| `PlaybackController` | transport commands (play/pause/next/previous/seek) issued back to the app |
| `PlaybackQueue` | ordering; `isRotating` (radio) vs finite playlist |

## Consumers

- `capullo-audio` (engine) depends on this and adapts `MediaRequest` ⇄ Media3.
- `capullo-source-radiobrowser`, `capullo-source-telegram` implement `MediaSourceProvider`.

## Build

```bash
./gradlew build          # compile + unit tests (pure JVM - no Android SDK needed)
./gradlew publishToMavenLocal
```

Toolchain: Gradle 9.6.1 · Kotlin 2.3.10 · JDK 17. `explicitApi()` is on, so the public
surface is deliberate.

## Coordinates

`tech.capullo.audio:capullo-audio-contracts` (published via jitpack as
`com.github.capullo-tech.capullo-audio-contracts:<tag>` for releases; use a Gradle composite
build for local co-development).

## License

Copyright 2026 capullo-tech. Licensed under GPLv3 - see [`LICENSE`](LICENSE).
