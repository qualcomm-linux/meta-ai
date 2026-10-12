#!/bin/sh
# Copyright (c) 2025 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

set -eu

if [ "$#" -ne 1 ]; then
    echo "The script path argument is missing, please run it with:"
    echo " $0 /path/to/script"
    exit 1
fi

TOPDIR=$(realpath "$(dirname "$(readlink -f "$0")")/..")
SCRIPT=$(realpath "$1")

if [ ! -f "$SCRIPT" ]; then
    echo "The script '$SCRIPT' does not exist."
    exit 1
fi

SCRIPT=${SCRIPT#"$TOPDIR/"}
KAS_CONTAINER=${KAS_CONTAINER:-$(command -v kas-container)}

exec "$KAS_CONTAINER" shell "$TOPDIR/ci/base.yml" --command "/repo/$SCRIPT /repo /work"
