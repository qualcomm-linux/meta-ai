# Workflows and actions

The repository runs policy checks, standalone OpenEmbedded builds and one hardware integration build. Large hardware artifacts are uploaded through the shared Qualcomm private-artifact action; GitHub artifacts contain only coordination files and compact diagnostics.

## Build flow

`pr.yml` and `push.yml` call `build-openembedded.yml`. Build jobs use the `[self-hosted, qcom-u2404, amd64]` project runner pool and reuse `/efs/qli/meta-qcom/downloads` and `/efs/qli/meta-qcom/sstate-cache`. Each job has an isolated temporary build directory; only the OpenEmbedded downloads and sstate caches are shared.

The standalone matrix builds every recipe in this layer for `qemuarm`, `qemuarm64` and `qemux86-64` with `DISTRO = "nodistro"`. The RB3 Gen 2 job combines the checked-out meta-ai revision with meta-qcom and meta-qcom-distro, installs the AI runtime packages into `qcom-multimedia-image`, and reuses meta-qcom's compile and private artifact actions.

Kas and cross-repository actions are pinned to reviewed commits. Kas lockfiles pin the OpenEmbedded metadata revisions used by each workflow run.

`build_successful` is the required aggregate result. It fails if setup, layer checks, any standalone build, the hardware image or result publication did not succeed.

## Other workflows

`qcom-preflight-checks.yml` runs Qualcomm's policy, licensing and security checks. See [qualcomm/qcom-actions](https://github.com/qualcomm/qcom-actions).

`stale-issues.yaml` reports inactive issues and pull requests.
