# Java Refresher: CSV Player Stats Analyzer

Oct 28, 2026 · @Sachin

## Goal

Build a command-line app that loads soccer player stats from a CSV and answers questions about them, in about 6 phases of 1–2 hours each. It's a warm-up for the Swing soccer stats app: the `Player` model and query logic can carry straight over.

What you'll practice:

- Records, classes and enums
- File I/O with `java.nio.file`
- Collections and generics (`List`, `Map`, `Optional`)
- Streams and `Collectors`
- Exceptions (checked vs. unchecked)
- Maven or Gradle project layout, and JUnit 5

## Setup (Phase 0, \~30 min)

- [ ] Install a current LTS JDK (21 or newer) and confirm with `java -version`
- [ ] Create a Maven project in IntelliJ (Community is fine) or VS Code with the Java extension pack
- [ ] Set the package to something like `com.sachin.stats`
- [ ] Create `src/main/resources/players.csv` with 15–20 rows, including 2–3 deliberately broken ones
- [ ] Commit to a new GitHub repo

Sample CSV to start from:

```csv
name,team,position,age,appearances,minutes,goals,assists
Erling Haaland,Man City,FW,26,31,2600,27,5
Bukayo Saka,Arsenal,FW,25,35,2900,14,11
Rodri,Man City,MF,30,34,3000,8,9
Virgil van Dijk,Liverpool,DF,35,36,3240,3,2
Broken Row,Chelsea,MF,abc,10,,1,0
```

## Phases

Work through these in order; each one leaves you with something that runs.

### Phase 1: Model the data

- [ ] Create a `Position` enum (GK, DF, MF, FW)
- [ ] Create a `record Player(String name, String team, Position position, int age, int appearances, int minutes, int goals, int assists)`
- [ ] Add a method `goalsPer90()` on the record (watch for division by zero)
- [ ] In `main`, build 3 players by hand and print them

Done when: printing a `Player` shows all its fields (records give you `toString` for free).

### Phase 2: Load the CSV

- [ ] Write a `PlayerLoader` class with `List<Player> load(Path path)`
- [ ] Read lines with `Files.readAllLines`, skip the header, split on commas
- [ ] Parse each row into a `Player` with `Integer.parseInt` and `Position.valueOf`

Done when: all valid rows load and you print the count.

### Phase 3: Handle bad data

- [ ] Wrap row parsing in try/catch for `NumberFormatException` and `IllegalArgumentException`
- [ ] Skip bad rows and print a warning with the line number
- [ ] Handle the file not existing (`IOException`) with a clear message
- [ ] Optional: create your own `InvalidRowException`

Done when: the broken rows are reported and skipped, and the app doesn't crash.

### Phase 4: Query with streams

Put these in a `StatsService` class, one method each:

- [ ] Top N scorers (`sorted` + `limit`)
- [ ] Players on a given team (`filter`)
- [ ] Total goals per team (`groupingBy` + `summingInt`) returning `Map<String, Integer>`
- [ ] Best goals-per-90 with a minimum minutes cutoff
- [ ] Average age by position (`groupingBy` + `averagingInt`)
- [ ] Find a player by name, returning `Optional<Player>`

Done when: each method prints sensible output from `main`.

### Phase 5: Add a menu

- [ ] Build a `Scanner` loop with numbered options for each query, plus Quit
- [ ] Prompt for inputs like team name or N
- [ ] Handle invalid menu input without crashing
- [ ] Format output as aligned columns with `String.format` or `printf`

Done when: you can run every query from the menu and quit cleanly.

### Phase 6: Test it

- [ ] Add the JUnit 5 dependency to `pom.xml`
- [ ] Test `goalsPer90` including the zero-minutes case
- [ ] Test each `StatsService` method against a small hand-built list
- [ ] Test that the loader skips bad rows

Done when: `mvn test` passes.

## Stretch goals

- [ ] Replace manual splitting with a library like OpenCSV or Apache Commons CSV (handles quoted names with commas)
- [ ] Export a query result to a new CSV or JSON file (Gson or Jackson)
- [ ] Load a real season export with a few hundred rows and check performance
- [ ] Move the `Player` model and `StatsService` into the Swing soccer app

## Quick reference: Java vs. JS/TS and C#

| Concept | Java | JS/TS or C# equivalent |
| --- | --- | --- |
| String equality | `a.equals(b)` (`==` compares references) | `===` in JS; `==` works on strings in C# |
| Immutable data class | `record Player(...)` | TS interface / C# `record` |
| Lambda | `p -> p.goals()` | `p => p.goals` |
| Filter/map/sort | `list.stream().filter(...).toList()` | `.filter()` in JS, LINQ `.Where()` in C# |
| Group by | `Collectors.groupingBy(...)` | lodash `groupBy`, LINQ `GroupBy` |
| Maybe-null value | `Optional<T>` | `T \| undefined`, `T?` |
| Checked exceptions | Must catch or declare `throws` | No equivalent |
| Primitive vs. boxed | `int` vs. `Integer` (generics need `Integer`) | No split in JS; C# generics take `int` |
| Type inference | `var x = ...;` (local variables only) | `let`/`const`, C# `var` |
