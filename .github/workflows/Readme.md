# Workflows and actions

The repository runs policy checks and standalone OpenEmbedded builds.

## Build flow

`pr.yml` and `push.yml` call `build-openembedded.yml`. Metadata setup and layer checks use GitHub-hosted runners. Compilation uses the `[self-hosted, qcom-u2404, amd64]` project runner pool and reuses `/efs/qli/meta-qcom/downloads` and `/efs/qli/meta-qcom/sstate-cache`. Each compilation job has an isolated temporary build directory; only the OpenEmbedded downloads and sstate caches are shared. Pull requests from forks skip the OpenEmbedded build because untrusted fork code must not execute on credential-bearing self-hosted runners or write to shared caches; a maintainer must reproduce the change on a repository branch.

The standalone matrix builds every recipe in this layer for `qemuarm`, `qemuarm64` and `qemux86-64` with `DISTRO = "nodistro"`.

Kas and cross-repository actions are pinned to reviewed commits. Kas lockfiles pin the OpenEmbedded metadata revisions used by each workflow run.

`build_successful` is the required aggregate result. It fails if setup, layer checks, or any standalone build does not succeed.

`push.yml` includes the temporary `koenkooi-ci-lava-proposal` trigger to exercise the standalone build loop before it reaches `main`; remove it before merging.

## Other workflows

`qcom-preflight-checks.yml` runs Qualcomm's policy, licensing and security checks. See [qualcomm/qcom-actions](https://github.com/qualcomm/qcom-actions).

`stale-issues.yaml` reports inactive issues and pull requests.
