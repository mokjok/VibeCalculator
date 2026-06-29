# Vibe Calculator (App 1 — NAN Calculator)

## What this satisfies (Assessment 2, App 1)
- **Single Activity app** — everything happens in `MainActivity` / `activity_main.xml`. No second screen.
- **Creative calculator, not numbers** — instead of arithmetic, it "calculates" by combining two moods (e.g. *Calm*, *Bold*, *Mysterious*) with an operation (*Blend*, *Clash*, *Amplify*, *Balance*) to deterministically produce a named "Vibe" result, a colour, and a description. Same inputs always give the same result (it's a real calculation, on non-numeric data), satisfying the "NAN calculator" requirement.
- **Informative modal pop-up view** — `dialog_vibe_result.xml` is a custom `Dialog` (not `AlertDialog.Builder` text box, not a `Toast`) shown via `showResultDialog()` in `MainActivity.kt`. It displays a result name, emoji, colour swatch, and explanation, with its own close button.
- Uses `Spinner` + `ArrayAdapter` (course week 7 content) instead of free text input.
- Includes Reset button and an in-app history list (extra polish, not required but harmless).

## How to open in Android Studio
1. Open Android Studio → **File > Open** → select the `VibeCalculator` folder.
2. Let Gradle sync (it will download dependencies — needs internet).
3. Run on an emulator or device (minSdk 23).

## Files of interest
- `app/src/main/java/com/example/vibecalculator/MainActivity.kt` — all logic
- `app/src/main/res/layout/activity_main.xml` — the single view
- `app/src/main/res/layout/dialog_vibe_result.xml` — the modal popup
- `app/src/main/res/drawable/dialog_background.xml` — rounded dialog background

## Before you submit
- Open the project once in Android Studio so it regenerates the `.gradle` cache and `local.properties` (point it at your SDK path).
- Test on the emulator: pick two different moods + an operation + tap **Calculate Vibe** → modal should appear.
- Rename the package/app name if you want it to feel more personal — search-replace `vibecalculator` / `Vibe Calculator`.
- Push to the GitHub Classroom repo from the assignment brief, then fill in the Google Form.
- Record yourself walking through the design rationale for the formative submission (mood-combination logic = your "concept").
