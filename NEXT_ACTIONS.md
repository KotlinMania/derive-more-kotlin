# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 5/10 (50.0%)
- **Function parity:** 4/27 matched (target 11) — 14.8%
- **Class/type parity:** 6/16 matched (target 6) — 37.5%
- **Combined symbol parity:** 10/43 matched (target 17) — 23.3%
- **Average inline-code cosine:** 0.47 (function body across 5 matched files)
- **Average documentation cosine:** 0.66 (doc text across 5 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 4 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. try_unwrap

- **Target:** `derivemore.TryUnwrap`
- **Similarity:** 0.39
- **Dependents:** 1
- **Priority Score:** 1010306.1
- **Functions:** 1/2 matched (target 4)
- **Missing functions:** `fmt`
- **Types:** 1/1 matched
- **Missing types:** _none_

### 2. as_dyn_error

- **Target:** `derivemore.AsDynError`
- **Similarity:** 0.18
- **Dependents:** 1
- **Priority Score:** 1000308.2
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 3. ops

- **Target:** `derivemore.Ops`
- **Similarity:** 0.40
- **Dependents:** 0
- **Priority Score:** 10306.0
- **Functions:** 1/2 matched
- **Missing functions:** `fmt`
- **Types:** 1/1 matched
- **Missing types:** _none_

### 4. str

- **Target:** `derivemore.Str`
- **Similarity:** 0.40
- **Dependents:** 0
- **Priority Score:** 10306.0
- **Functions:** 1/2 matched (target 4)
- **Missing functions:** `fmt`
- **Types:** 1/1 matched
- **Missing types:** _none_

### 5. cmp

- **Target:** `derivemore.Cmp`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/derive_more/src rust ../../src/commonMain/kotlin/io/github/kotlinmania/derivemore kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |
