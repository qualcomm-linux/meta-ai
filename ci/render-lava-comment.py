#!/usr/bin/env python3
# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

import argparse
from pathlib import Path
import sys


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--commit", required=True)
    parser.add_argument("--output", required=True, type=Path)
    parser.add_argument("--summary-dir", required=True, type=Path)
    parser.add_argument("--workflow-url", required=True)
    args = parser.parse_args()

    summaries = sorted(path for path in args.summary_dir.rglob("*") if path.is_file())
    if not summaries:
        print(f"No summaries found below '{args.summary_dir}'.", file=sys.stderr)
        return 1

    content = [
        f"## LAVA test run [workflow]({args.workflow_url})",
        "",
        f"Test jobs for commit `{args.commit}`.",
        "",
    ]
    for summary in summaries:
        content.append(summary.read_text(encoding="utf-8").strip())
        content.append("")

    content.extend(
        [
            "---",
            "",
            "_Automated CI comment. Template authored by GitHub Copilot using GPT-5.6 Sol (`gpt-5.6-sol`)._",
            "",
        ]
    )
    args.output.write_text("\n".join(content), encoding="utf-8")
    return 0


if __name__ == "__main__":
    sys.exit(main())
