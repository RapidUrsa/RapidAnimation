# Rapid Animation 1.0.0 release steps

1. Run `gradlew build`, then `gradlew runClient`, and cast a standard spellbook teleport. Confirm that the bird appears at the tested timing and the settings show only Replace spell teleports.
2. Create a public GitHub repository named RapidAnimation under RapidUrsa. Upload the contents of this project folder to the repository root, including the Gradle wrapper, LICENSE, and runelite-plugin.properties.
3. Suggested initial commit summary: Release Rapid Animation v1.0.0
4. Copy the full commit SHA from the repository's commit page.
5. In your fork of runelite/plugin-hub, create plugins/rapid-animation containing the two lines below. Replace FULL_COMMIT_SHA with that exact SHA:

```
repository=https://github.com/RapidUrsa/RapidAnimation.git
commit=FULL_COMMIT_SHA
```

6. Open a pull request against runelite/plugin-hub master titled Add Rapid Animation. Suggested description:

Rapid Animation replaces standard spellbook teleport visuals with the second half of the quetzal teleport, including the bird. Settings only, with no sidebar. The effect is local and visual only; teleport timing, destinations, and requirements are unchanged.

Validation: in-game visual timing confirmed in v0.3.2. Add the final local Gradle build and smoke-test results after step 1.

The Plugin Hub review determines acceptance. This package has not been published or submitted automatically.
