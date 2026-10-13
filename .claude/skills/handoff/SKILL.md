---
name: handoff
description: Resume work on the Ben 10 mod with full context, or record progress for the next session. Use at the start of a session, after a context compaction or resume, when someone says "continue", "where were we", "pick up", "handoff", "catch up", or when finishing a milestone (step done, PR opened, decision made) so the log stays current.
---

# Handoff

`docs/HANDOFF.md` is the running log: current state, work done, decisions, open questions, next step, gotchas. A SessionStart hook (`.claude/settings.json`) prints it into context on startup, resume and after compaction; this skill says what to do with it.

## Resuming

1. Read `docs/HANDOFF.md` (fully) and `CLAUDE.md`.
2. Check the live state against the log:
   ```bash
   git status --short | head; git branch --show-current; git log --oneline -5
   gh pr list --state open
   ```
   If git or the PR differs from the log (new commits, merged PR, failing checks), trust git and fix the log.
3. Tell the user in two or three sentences where things stand and what you're about to do next. Don't redo finished work, and don't reopen decisions in the Decisions table.
4. Before claiming anything works, run the verification ladder in `CLAUDE.md`.

## Recording progress

Update `docs/HANDOFF.md` whenever a milestone lands, and before a long-running step that could outlast the context:
- Add a dated entry at the top of **Done**: what changed, paths, how it was verified (commands and results, commit hashes, PR numbers), and anything not verified.
- Update **Current state**, **Decisions**, **Open questions** and **Next**.
- Keep it factual and under about 250 lines; compress old entries once merged.
- Stable rules go in `CLAUDE.md`, data formats in `docs/`, and the moving state stays here.

Commit the log together with the work it describes, only when the user has asked for commits.
