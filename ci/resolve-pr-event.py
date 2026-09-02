#!/usr/bin/env python3
# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

import json
import os
from pathlib import Path
import sys


def main() -> int:
    if len(sys.argv) != 3:
        print(f"Usage: {sys.argv[0]} PULLS_FILE TRUSTED_EVENT_FILE", file=sys.stderr)
        return 1

    output_path = os.environ.get("GITHUB_OUTPUT")
    if not output_path:
        print("GITHUB_OUTPUT is empty.", file=sys.stderr)
        return 1

    expected = {
        "base_repository": os.environ.get("EXPECTED_BASE_REPOSITORY", ""),
        "head_ref": os.environ.get("EXPECTED_HEAD_REF", ""),
        "head_repository": os.environ.get("EXPECTED_HEAD_REPOSITORY", ""),
        "head_sha": os.environ.get("EXPECTED_HEAD_SHA", ""),
    }
    if not all(expected.values()):
        print("Expected workflow_run metadata is incomplete.", file=sys.stderr)
        return 1

    with open(sys.argv[1], encoding="utf-8") as pulls_file:
        pulls = json.load(pulls_file)

    matching_pulls = [
        pull
        for pull in pulls
        if pull["base"]["repo"]["full_name"] == expected["base_repository"]
        and pull["head"]["ref"] == expected["head_ref"]
        and pull["head"]["repo"]["full_name"] == expected["head_repository"]
        and pull["head"]["sha"] == expected["head_sha"]
    ]
    if len(matching_pulls) != 1:
        print(
            f"Expected one pull request matching the workflow run, found {len(matching_pulls)}.",
            file=sys.stderr,
        )
        return 1

    pull_request = matching_pulls[0]
    values = {
        "branch": pull_request["base"]["ref"],
        "head_sha": pull_request["head"]["sha"],
        "pr_number": str(pull_request["number"]),
        "pr_url": pull_request["html_url"],
    }

    with Path(output_path).open("a", encoding="utf-8") as output:
        for name, value in values.items():
            if "\n" in value:
                print(f"Event value '{name}' contains a newline.", file=sys.stderr)
                return 1
            output.write(f"{name}={value}\n")

    trusted_event_path = Path(sys.argv[2])
    trusted_event_path.parent.mkdir(parents=True, exist_ok=True)
    trusted_event_path.write_text(
        json.dumps(
            {"number": pull_request["number"], "pull_request": pull_request},
            separators=(",", ":"),
        ),
        encoding="utf-8",
    )

    return 0


if __name__ == "__main__":
    sys.exit(main())
