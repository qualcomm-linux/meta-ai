# Contributing to meta-ai

Thank you for contributing to meta-ai.

For baseline OpenEmbedded / Yocto Project patch expectations, refer to
[Preparing Changes for Submission](https://docs.yoctoproject.org/dev/contributor-guide/submit-changes.html#preparing-changes-for-submission).

## Branching strategy

Develop changes on branches based on `main` and open pull requests against
`main`.

## Submitting a pull request

1. Read our [code of conduct](CODE-OF-CONDUCT.md) and [license](LICENSE.txt).
1. [Fork](https://github.com/qualcomm-linux/meta-ai/fork) and clone the
   repository.
1. Create a branch from `main`.
1. Add an `upstream` remote to keep your branch current with
   `qualcomm-linux/meta-ai`.
1. Make your changes and run the checks listed below.
1. Commit with DCO sign-off (`git commit -s`).
1. Rebase on top of latest upstream `main`.
1. Push your branch and open a pull request.

Keep each pull request focused on one logical change. If you have independent
changes, send them as separate pull requests. For large or architectural
changes, align with maintainers early so review can focus on implementation
details.

## Local checks before opening or updating a pull request

This repository runs pull-request workflows automatically. The workflow YAML is
not a local executable. Before opening or updating a PR, run the baseline
OpenEmbedded build with `kas-container`:

```bash
kas-container build kas/base.yml:kas/qemuarm64.yml
```

For changes involving virtualization, include the virtualization overlay:

```bash
kas-container build kas/base.yml:kas/qemuarm64.yml:kas/virt.yml
```

Run the smallest relevant targeted validation in addition to the baseline
build when your change affects a specific recipe or component.

## Commit message requirements

Each commit must be atomic: one logical change per commit, with the tree kept
functional after every commit.

Use a clear commit subject in the form:

```text
component: summary of the change
```

Write the commit body in plain English and focus on:

- why the change is needed;
- what approach is taken to address the issue;
- any behavior impact or migration guidance when applicable.

Use imperative mood ("add", "fix", "drop", "update") and wrap body lines for
readability (about 72 columns).

## Sign-off and attribution trailers

Every commit must include a `Signed-off-by` trailer matching your local git
identity (use `git commit -s`).

If an AI assistant was used, add an `Assisted-by` trailer:

```text
Assisted-by: AGENT_NAME:MODEL_VERSION [TOOL1] [TOOL2]
```

Only include specialized analysis tools in bracketed fields. Do not include
basic tools such as git or editors.

## Security analysis of pull requests

External pull requests are automatically scanned with
[Semgrep](https://github.com/semgrep/semgrep) to detect insecure coding
patterns and potential security flaws.

Contributors are expected to resolve findings before merge.

## Pull request checklist

- Follow the existing style where possible.
- Run the relevant local validation.
- Keep the change focused; submit independent changes as separate PRs.
- Use well-formed commit messages with the required trailers.
- Discuss large features, architecture changes, and other core changes early.
