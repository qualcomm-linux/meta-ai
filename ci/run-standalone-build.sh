#!/bin/sh
# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

set -eu

require_value() {
    name="$1"
    if ! value=$(printenv "$name") || [ -z "$value" ]; then
        echo "Required environment variable '$name' is empty."
        exit 1
    fi
}

for name in CACHE_DIR KAS_CONTAINER KAS_WORK_DIR MACHINE; do
    require_value "$name"
done

test -x "$KAS_CONTAINER"
mkdir -p "$KAS_WORK_DIR"

DL_DIR="$CACHE_DIR/downloads"
SSTATE_DIR="$CACHE_DIR/sstate-cache"
KAS_YAMLS="ci/ci.yml:ci/${MACHINE}.yml:ci/world.yml"
RESOLVED_CONFIG="kas-build-${MACHINE}.yml"

export DL_DIR KAS_WORK_DIR SSTATE_DIR

"$KAS_CONTAINER" dump --resolve-env --resolve-local --resolve-refs "$KAS_YAMLS" > "$RESOLVED_CONFIG"
test -s "$RESOLVED_CONFIG"

"$KAS_CONTAINER" build "$KAS_YAMLS"
ci/kas-container-shell-helper.sh ci/yocto-buildstats.sh

test -s buildstats.log
test -s buildstats.svg
