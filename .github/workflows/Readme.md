# List of workflows and actions
This folder contains workflows that are helpful for maintaining a smooth and secure development process. The workflows should be enabled for open-source projects.

Workflows:
1. `qcom-preflight-checks.yml` - This workflow runs several preflight checks, including copyight, email, repolinter, and security checks.  See [qualcomm/qcom-actions](https://github.com/qualcomm/qcom-actions)
2. `stale-issues.yaml` - This workflow will periodically run every 30 days to check for stalled issues and PRs. If the workflow detects any stalled issues and/or PRs, it will automatically leave just a comment to draw attention.
3. `build-yocto.yml` - Reusable build workflow. Runs a kas build matrix (`nodistro` × `qemuarm`/`qemuarm64`/`qemux86-64`) on the shared `qcom-u2404` self-hosted runner pool, reusing `qualcomm-linux/meta-qcom`'s `compile` action directly rather than a local copy. Called by `pr.yml` and `push.yml`.
4. `pr.yml` / `push.yml` - Trigger `build-yocto.yml` on pull requests to `main` and on pushes to `main`/`wrynose`.
5. `lava-test.yml` - Disabled placeholder for future on-device LAVA testing (see the TODO block in that file). `workflow_dispatch`-only; not wired into `pr.yml`/`push.yml` yet.
