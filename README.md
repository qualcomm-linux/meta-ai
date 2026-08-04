# Meta-ai

The goal of this layer is to host AI/ML related recipes that need compilation as well as the absolute minimum of binary-only items to make testing possible, ideally only a single model.

## Layer requirements: standalone build

**meta-ai must build and package on its own, without depending on any other BSP or hardware-enablement layer.** Concretely:

- Recipes in this layer must not `DEPENDS`/`RDEPENDS`/`require`/`inherit` anything provided only by a BSP or hardware-enablement layer. The only layers meta-ai may assume are present are `openembedded-core` and `meta-openembedded`.
- Any recipe or feature that needs a third-party SDK, firmware, or other externally-provided dependency must make that dependency **opt-in**, off by default, and packaged separately (its own `${PN}-<backend>` package) so that the default build and the core package stay dependency-free.
- Every recipe should build for **all three of these qemu machines**, if possible, with `DISTRO = "nodistro"`: `qemuarm`, `qemuarm64`, `qemux86-64`. These three machines are the target bar for "this builds standalone" — they exercise 32-bit ARM, 64-bit ARM, and x86-64 without requiring any real hardware or BSP layer. Some dependencies are inherently 64-bit-only (or otherwise architecture-restricted), so a recipe that can't support one of the three is not automatically disqualified — but that should be the exception, not the default, and the recipe should still build cleanly (or `COMPATIBLE_MACHINE` itself out) on the machines it can support. CI is intended to build this matrix on every PR and push once the corresponding workflow lands (see #19); a change that only builds against a specific hardware target is not acceptable here — open a PR against the relevant BSP layer instead, or make the hardware-specific piece a clearly separated, opt-in addition.
- Downstream integrators are expected to combine meta-ai with a hardware layer via their own kas configuration when they need a specific SoC or accelerator backend; meta-ai itself doesn't assume any particular target beyond generic OE machines.

If you're unsure whether a change violates this constraint, ask: "would this recipe/config still build and produce a working package on at least one of these three qemu machines, with no BSP layer checked out at all?" If the answer is no, the change needs to be restructured before it can be merged.

## Branches

**main**: Primary development branch. Contributors should develop submissions based on this branch, and submit pull requests to this branch.
**wrynose**: LTS branch. Submissions should be backports from the main branch.

## Requirements

List requirements to run the project, how to install them, instructions to use docker container, etc...

## Installation Instructions

How to install the software itself.

## Usage

Describe how to use the project.

## Development

How to develop new features/fixes for the software. Maybe different than "usage". Also provide details on how to contribute via a [CONTRIBUTING.md file](CONTRIBUTING.md).

## Getting in Contact

How to contact maintainers. E.g. GitHub Issues, GitHub Discussions could be indicated for many cases. However a mail list or list of Maintainer e-mails could be shared for other types of discussions. E.g.

* [Report an Issue on GitHub](../../issues)
* [Open a Discussion on GitHub](../../discussions)

## License

MIT

*\<meta-ai\>* is licensed under the [MIT License](https://spdx.org/licenses/MIT.html). See [COPYING.MIT](COPYING.MIT) for the full license text.
