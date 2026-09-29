# Rapid Animation

Rapid Animation replaces the local player's standard spellbook teleport visuals with the second half of the quetzal teleport, including the bird.

Enable the plugin and switch on **Replace spell teleports** in its settings. No sidebar, whistle, recording, or manual animation IDs are needed.

**Visual only:** this changes what you see on your own client. It does not change teleport speed, destination, requirements, or what other players see.

## Supported teleports

Version 1.0.0 supports standard spellbook teleports using player animation 714. Other animation styles, such as ancient teleports, home teleports, and jewellery teleports, are not covered in this release.

## Development

Use Java 11 or later. Run `gradlew runClient` on Windows or `./gradlew runClient` on Linux/macOS to launch the development client. Run `gradlew build` (or `./gradlew build`) to compile and package.

The working timing is fixed at player animation 10944 / frame 25, attached effect 2735 / frame 1, bird delay 38 client ticks, and maximum preview duration 120 client ticks. The diagnostic controls and chat recording messages have been removed.

The visual timing was confirmed in-game during v0.3.2 testing. The final source cleanup still needs a local build and smoke test before Plugin Hub submission; the authoring environment could not download Gradle dependencies.

Licensed under the BSD 2-Clause license.
