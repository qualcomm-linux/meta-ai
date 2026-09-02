# Agent Guide for meta-ai

This file guides automation agents to run checks the same way project CI does.

Use a reproducible, isolated execution environment, keep caches outside the
repository when possible, and prioritize PR preflight parity.

## Project overview

meta-ai is an OpenEmbedded layer in the Qualcomm Linux organization.

## 1) Prerequisites

1. A working shell environment with standard build tooling available.
1. Access to the validation toolchain used by this repository.
1. Writable directories outside the repository for reusable caches and build
   outputs.

## 2) Recommended environment behavior

If cache/work variables are already defined, reuse them and do not override
them. Only set defaults when absent.

```sh
export REPO_DIR="$(pwd)"
export BUILD_WORK_DIR="${BUILD_WORK_DIR:-/path/to/build-work}"
export DL_DIR="${DL_DIR:-/path/to/shared-cache/downloads}"
export SSTATE_DIR="${SSTATE_DIR:-/path/to/shared-cache/sstate-cache}"
mkdir -p "${BUILD_WORK_DIR}" "${DL_DIR}" "${SSTATE_DIR}"
```

## 3) KAS build workflow

Use `kas-container` for builds to match the isolated CI-style workflow. Compose
configuration files in this order: base configuration, machine configuration,
then optional feature overlays.

```sh
kas-container build kas/base.yml:kas/qemuarm64.yml
```

`kas/base.yml` selects the `nodistro` distribution and common layer set.
`kas/qemuarm64.yml` selects the `qemuarm64` machine. For virtualization-related
changes, add `kas/virt.yml` as the final overlay:

```sh
kas-container build kas/base.yml:kas/qemuarm64.yml:kas/virt.yml
```

Do not execute `.github/workflows/qcom-preflight-checks.yml` locally; GitHub
Actions runs that workflow for pull requests. Run the smallest relevant
targeted validation in addition to the baseline build when a change affects a
specific recipe or component.

## 4) Contribution workflow

Follow [CONTRIBUTING.md](CONTRIBUTING.md):

1. Base branch: `main`.
1. Use a topic branch with a focused scope.
1. Rebase on latest upstream `main`.
1. Open a GitHub pull request.
1. Iterate in PR discussion.

## 5) Commit requirements for agent-produced changes

Use atomic commits with a `component: summary` subject style and imperative,
plain-English body text that explains why the change is needed.

Every commit must carry:

- `Signed-off-by: <human contributor identity>`
- `Assisted-by: AGENT_NAME:MODEL_VERSION [TOOL1] [TOOL2]` when AI-assisted

Never fabricate contributor identity. Read author name/email from local git
configuration for sign-off identity.
