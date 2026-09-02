#!/bin/sh
# Copyright (c) 2024-2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

# This filename is part of the shared meta-qcom compile action interface.

set -eu

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 REPO_DIR WORK_DIR"
    exit 1
fi

WORK_DIR="$2"
if [ ! -d "$WORK_DIR" ]; then
    echo "The '$WORK_DIR' directory does not exist."
    exit 1
fi

BUILDSTATS_ROOT=$(bitbake-getvar --value TMPDIR)/buildstats
BUILDSTATS=$(find "$BUILDSTATS_ROOT" -mindepth 1 -maxdepth 1 -type d | sort | tail -1)
if [ -z "$BUILDSTATS" ]; then
    echo "No build statistics found below '$BUILDSTATS_ROOT'."
    exit 1
fi

"$WORK_DIR/oe-core/scripts/pybootchartgui/pybootchartgui.py" \
    --minutes \
    --full-time \
    --format=svg \
    --output=buildstats \
    "$BUILDSTATS"

buildstats-summary \
    --sort duration \
    --highlight 0 \
    "$BUILDSTATS" | tee buildstats.log

test -s buildstats.svg
test -s buildstats.log
