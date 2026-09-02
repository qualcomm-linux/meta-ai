#!/usr/bin/env python3
# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

from pathlib import Path
import sys


def main() -> int:
    if len(sys.argv) != 3:
        print(f"Usage: {sys.argv[0]} BUILD_URL_FILE EXPECTED_URL", file=sys.stderr)
        return 1

    build_url = Path(sys.argv[1]).read_text(encoding="utf-8").strip().rstrip("/")
    expected_url = sys.argv[2].strip().rstrip("/")
    if not build_url or build_url != expected_url:
        print(
            f"Build URL '{build_url}' does not match expected run URL '{expected_url}'.",
            file=sys.stderr,
        )
        return 1

    print(f"Validated build URL: {build_url}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
