#!/bin/sh
# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

set -eu

if [ "$#" -eq 0 ]; then
    echo "Usage: $0 job=result [job=result ...]"
    exit 1
fi

failed=0
for job_result in "$@"; do
    job=${job_result%%=*}
    result=${job_result#*=}
    if [ -z "$job" ] || [ "$result" = "$job_result" ]; then
        echo "Invalid job result '$job_result'; expected job=result."
        exit 1
    fi
    if [ "$result" != "success" ]; then
        echo "$job result was $result"
        failed=1
    else
        echo "$job succeeded"
    fi
done

exit "$failed"
