#!/usr/bin/env python3
# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

import json
import os
from pathlib import Path
import sys


def main() -> int:
    if len(sys.argv) != 2:
        print(f"Usage: {sys.argv[0]} EVENT_FILE", file=sys.stderr)
        return 1

    output_path = os.environ.get("GITHUB_OUTPUT")
    if not output_path:
        print("GITHUB_OUTPUT is empty.", file=sys.stderr)
        return 1

    with open(sys.argv[1], encoding="utf-8") as event_file:
        event = json.load(event_file)

    pull_request = event.get("pull_request")
    if not isinstance(pull_request, dict):
        print("The event does not contain a pull_request object.", file=sys.stderr)
        return 1

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

    return 0


if __name__ == "__main__":
    sys.exit(main())
