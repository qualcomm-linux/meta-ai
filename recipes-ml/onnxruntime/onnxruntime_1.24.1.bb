SUMMARY = "ONNX Runtime"
DESCRIPTION = "ONNX Runtime is a cross-platform, high-performance inference \
engine for machine learning models in the Open Neural Network Exchange (ONNX) \
format. Developed by Microsoft, it provides optimized execution of ONNX models \
across a variety of hardware targets including CPUs, GPUs, and dedicated AI accelerators."
HOMEPAGE = "https://github.com/microsoft/onnxruntime"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f7e3b1308cb5c00b372a6e78835732d"

DEPENDS = " \
    zlib \
    protobuf \
    protobuf-native \
    re2 \
    abseil-cpp \
    nlohmann-json \
    microsoft-gsl \
"

SRC_URI = "gitsm://github.com/microsoft/onnxruntime.git;protocol=https;branch=rel-${PV};name=ort \
    file://0001-cmake-fix-GCC-16-build.patch \
    git://github.com/HowardHinnant/date.git;protocol=https;nobranch=1;name=date;tag=v3.0.4;destsuffix=date \
    git://github.com/boostorg/mp11.git;protocol=https;nobranch=1;name=mp11;tag=boost-1.91.0;destsuffix=mp11 \
    git://github.com/pytorch/cpuinfo.git;protocol=https;nobranch=1;name=pytorch_cpuinfo;destsuffix=pytorch_cpuinfo \
    git://github.com/dcleblanc/SafeInt.git;protocol=https;nobranch=1;name=safeint;tag=3.0.28;destsuffix=safeint \
    git://github.com/google/flatbuffers.git;protocol=https;nobranch=1;name=flatbuffers;tag=v23.5.26;destsuffix=flatbuffers \
    git://github.com/onnx/onnx.git;protocol=https;nobranch=1;name=onnx;tag=v1.20.1;destsuffix=onnx \
    git://github.com/eigen-mirror/eigen.git;protocol=https;nobranch=1;name=eigen3;destsuffix=eigen3 \
"

SRCREV_FORMAT = "ort_date_mp11_pytorch_cpuinfo_safeint_flatbuffers_onnx_eigen3"
SRCREV_ort              = "470ae16099a74fe05e31f2530489332c0525edb5"
SRCREV_date             = "f94b8f36c6180be0021876c4a397a054fe50c6f2"
SRCREV_mp11             = "b94b089d4ec83cd397f20958f34edf25bc3e06f4"
SRCREV_pytorch_cpuinfo  = "ea6b9f1bb6e1001d8b21574d5bc78ddef62e499d"
SRCREV_safeint          = "4cafc9196c4da9c817992b20f5253ef967685bf8"
SRCREV_flatbuffers      = "c20d64b8de759423af61e072fcabf916c1f7bf9f"
SRCREV_onnx             = "d3f6b795aedb48eaecc881bf5e8f5dd6efbe25b3"
SRCREV_eigen3           = "549bf8c75b6aae071cde2f28aa48f16ee3ae60b0"

# Fix buildpaths QA issue: remap TMPDIR references in both debug info and
# string literals embedded in the compiled libraries.
CFLAGS:append   = " -ffile-prefix-map=${WORKDIR}=. -ffile-prefix-map=${S}=. -ffile-prefix-map=${B}=."
CXXFLAGS:append = " -ffile-prefix-map=${WORKDIR}=. -ffile-prefix-map=${S}=. -ffile-prefix-map=${B}=."

inherit cmake

OECMAKE_SOURCEPATH = "${S}/cmake"

EXTRA_OECMAKE = " \
    -DCMAKE_BUILD_TYPE=RelWithDebInfo \
    -DCMAKE_FIND_ROOT_PATH=${STAGING_DIR_TARGET} \
    -Donnxruntime_BUILD_SHARED_LIB=ON \
    -DFETCHCONTENT_FULLY_DISCONNECTED=ON \
    -DFETCHCONTENT_SOURCE_DIR_DATE=${WORKDIR}/sources/date \
    -DFETCHCONTENT_SOURCE_DIR_MP11=${WORKDIR}/sources/mp11 \
    -DFETCHCONTENT_SOURCE_DIR_PYTORCH_CPUINFO=${WORKDIR}/sources/pytorch_cpuinfo \
    -DFETCHCONTENT_SOURCE_DIR_SAFEINT=${WORKDIR}/sources/safeint \
    -DFETCHCONTENT_SOURCE_DIR_FLATBUFFERS=${WORKDIR}/sources/flatbuffers \
    -DFETCHCONTENT_SOURCE_DIR_ONNX=${WORKDIR}/sources/onnx \
    -DFETCHCONTENT_SOURCE_DIR_EIGEN3=${WORKDIR}/sources/eigen3 \
    -DONNX_CUSTOM_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
    -DONNX_BUILD_TESTS=OFF \
    -Donnxruntime_BUILD_UNIT_TESTS=OFF \
    -Donnxruntime_RUN_ONNX_TESTS=OFF \
"

# Onnxruntime builds optional objects using all instructionset
# extensions, which will get loaded at runtime after checking
# /proc/cpuinfo. OE forcing '-mcpu' makes that fail. This is a rare case
# of upstream doing a better job than OE, so disable forcing -mcpu in
# do_compile to work around this. Keep it in do_configure to ensure the
# default tuning is correct.
# This seems to only affect aarch64, regular arm, x86 and x86-64 just work.
TARGET_CC_ARCH:aarch64 = ""
do_compile:prepend:aarch64() {
    sed -E 's/-mcpu=[^ ]+//g' -i ${WORKDIR}/toolchain.cmake
}

# libonnxruntime_providers_shared.so is an execution-provider plugin, not a
# proper versioned shared library, so it installs as a non-symlink .so file.
# Skip the dev-elf QA check that would otherwise flag it in the -dev package.
INSANE_SKIP:${PN}-dev += "dev-elf"
