# Meta-ai

The goal of this layer is to host AI/ML related recipes that need compilation as well as the absolute minimum of binary-only items to make testing possible, ideally only a single model.


## Branches

**main**: Primary development branch. Contributors should develop submissions based on this branch, and submit pull requests to this branch.
**wrynose**: LTS branch. Submissions should be backports from the main branch.

## Requirements

List requirements to run the project, how to install them, instructions to use docker container, etc...

## Installation Instructions

How to install the software itself.

## Usage

Describe how to use the project.

## Contributing patches

Base changes on `main` and submit patches as GitHub pull requests against `main`. Keep each pull request focused on one logical change and sign off each commit under the Developer Certificate of Origin.

Before opening or updating a pull request, run:

```sh
kas-container build kas/base.yml:kas/qemuarm64.yml
```

Run additional targeted checks for the affected recipe or component. See [CONTRIBUTING.md](CONTRIBUTING.md) for complete validation, commit message, sign-off, and attribution requirements.

## Getting in Contact

How to contact maintainers. E.g. GitHub Issues, GitHub Discussions could be indicated for many cases. However a mail list or list of Maintainer e-mails could be shared for other types of discussions. E.g.

* [Report an Issue on GitHub](../../issues)
* [Open a Discussion on GitHub](../../discussions)
* Contact the maintainer at <koen.kooi@oss.qualcomm.com>.

## License

MIT

*\<meta-ai\>* is licensed under the [MIT License](https://spdx.org/licenses/MIT.html). See [COPYING.MIT](COPYING.MIT) for the full license text.
