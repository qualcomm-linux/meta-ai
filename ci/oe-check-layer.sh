#!/bin/bash
# Copyright (c) 2024-2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

set -euo pipefail

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

# Use a fresh build directory so layer checks cannot inherit state from compilation.
BUILDDIR=$(mktemp -p "$WORK_DIR" -d -t build-oe-check-layer-XXXX)
source "$WORK_DIR/oe-core/oe-init-build-env" "$BUILDDIR"
git -c advice.detachedHead=false -c init.defaultBranch=main clone --quiet --shared "$REPO_DIR" meta-ai

exec yocto-check-layer \
    meta-ai \
    --no-auto \
    --dependency "$WORK_DIR/oe-core/meta" \
    --no-auto-dependency
