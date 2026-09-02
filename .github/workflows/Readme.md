# Workflows and actions

The repository runs policy checks, standalone OpenEmbedded builds and one hardware integration build. Large hardware artifacts are uploaded through the shared Qualcomm private-artifact action; GitHub artifacts contain only coordination files and compact diagnostics.

## Build flow

`pr.yml` and `push.yml` call `build-openembedded.yml`. Build jobs use the `[self-hosted, qcom-u2404, amd64]` project runner pool and reuse `/efs/qli/meta-qcom/downloads` and `/efs/qli/meta-qcom/sstate-cache`. Each job has an isolated temporary build directory; only the OpenEmbedded downloads and sstate caches are shared.

The standalone matrix builds every recipe in this layer for `qemuarm`, `qemuarm64` and `qemux86-64` with `DISTRO = "nodistro"`. The RB3 Gen 2 job combines the checked-out meta-ai revision with meta-qcom and meta-qcom-distro, installs the AI runtime packages into `qcom-multimedia-image`, and reuses meta-qcom's compile and private artifact actions.

Kas and cross-repository actions are pinned to reviewed commits. Kas lockfiles pin the OpenEmbedded metadata revisions used by each workflow run.

`build_successful` is the required aggregate result. It fails if setup, layer checks, any standalone build, the hardware image or result publication did not succeed.

## LAVA flow

`test-pr.yml` runs from the default branch after `Build on PR` completes. The pull request build has no LAVA or reporting credentials; the trusted `workflow_run` downloads the original event and build URL artifacts without executing pull request code. It passes only `LAVATOKEN` to `test.yml` and only `TEST_REPORTING_APP_TOKEN` to `publish-results.yml`.

`test.yml` reuses meta-qcom's pinned LAVA-plan and result-summary actions. It boots the RB3 Gen 2 image first, then runs the pinned meta-qcom-distro pre-merge plan. Matrix failures are recorded individually and collapsed into explicit required results, so a skipped or incomplete test cannot appear successful.

The current shared LAVA plans do not contain a meta-ai-specific runtime suite. This workflow proves the hardware image, private artifact and trusted LAVA path; deterministic inference coverage must be added to `qualcomm-linux/lava-test-plans` and `qualcomm-linux/qcom-linux-testkit` before this becomes the final meta-ai gate.

`push.yml` runs the same LAVA path for trusted branches. The temporary `koenkooi-ci-lava-proposal` trigger exists only to exercise the complete workflow before it reaches `main` and must be removed before merging.

LAVA results are published as a check and one updated pull request comment. Automated comments identify their origin so they cannot be confused with maintainer-authored text.

## Other workflows

`qcom-preflight-checks.yml` runs Qualcomm's policy, licensing and security checks. See [qualcomm/qcom-actions](https://github.com/qualcomm/qcom-actions).

`stale-issues.yaml` reports inactive issues and pull requests.
