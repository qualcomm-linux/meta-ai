#!/bin/sh
# Copyright (c) 2024-2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

set -eu

if [ "$#" -ne 2 ]; then
    echo "The REPO_DIR or WORK_DIR is empty and it needs to point to the corresponding directories."
    echo "Please run it with:"
    echo " $0 REPO_DIR WORK_DIR"
    exit 1
fi

REPO_DIR="$1"
WORK_DIR="$2"

require_dir() {
    if [ ! -d "$1" ]; then
        echo "The '$1' is not a directory."
        exit 1
    fi
}

require_dir "$REPO_DIR"
require_dir "$WORK_DIR"

"$WORK_DIR/oe-core/scripts/contrib/patchreview.py" -v -b -j status.json "$REPO_DIR"

STATUS_FILE="$WORK_DIR/build/status.json"
test -s "$STATUS_FILE"

python3 - "$STATUS_FILE" <<'PY'
import json
import sys

with open(sys.argv[1], encoding="utf-8") as status_file:
    status = json.load(status_file)

malformed = [
    patch
    for patch in status
    if "malformed-sob" in patch or "malformed-upstream-status" in patch
]
if malformed:
    print(f"{len(malformed)} patch(es) have malformed metadata", file=sys.stderr)
    sys.exit(1)
PY
