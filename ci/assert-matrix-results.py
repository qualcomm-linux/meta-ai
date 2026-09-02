#!/usr/bin/env python3
# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

import json
import os
import sys


def main() -> int:
    raw_results = os.environ.get("MATRIX_RESULTS", "")
    if not raw_results:
        print("MATRIX_RESULTS is empty.", file=sys.stderr)
        return 1

    try:
        results = json.loads(raw_results)
    except json.JSONDecodeError as error:
        print(f"MATRIX_RESULTS is not valid JSON: {error}", file=sys.stderr)
        return 1

    if not isinstance(results, dict) or not results:
        print("MATRIX_RESULTS must contain at least one job result.", file=sys.stderr)
        return 1

    failed = False
    for job, result in sorted(results.items()):
        if result != "success":
            print(f"{job} result was {result}", file=sys.stderr)
            failed = True
        else:
            print(f"{job} succeeded")

    return int(failed)


if __name__ == "__main__":
    sys.exit(main())
