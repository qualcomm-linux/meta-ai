SUMMARY = "TensorFlow Lite C API Library"
DESCRIPTION = "TensorFlow Lite C API Library for embedded systems"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

DEPENDS += " \
    flatbuffers-tflite \
    flatbuffers-tflite-native \
    jpeg \
    libeigen \
    protobuf \
    protobuf-native \
"

# The GPU delegate bundles OpenCL, OpenGL ES, and Vulkan backends into a single
# build-time flag; the runtime selects the backend based on hardware availability.
# opencl-headers, virtual/egl, virtual/libopencl1, and vulkan-headers are only
# needed when GPU is enabled.
# To enable GPU support, add PACKAGECONFIG:append = " gpu" in a hardware-specific
# layer bbappend.
PACKAGECONFIG ?= ""
PACKAGECONFIG[gpu] = " \
    -DTFLITE_ENABLE_GPU=ON, \
    -DTFLITE_ENABLE_GPU=OFF, \
    opencl-headers virtual/egl virtual/libopencl1 vulkan-headers, \
"

# TensorFlow Lite version components derived from PV
TF_LITE_VERSION = "${PV}"
TF_LITE_MAJOR = "${@d.getVar('PV').split('.')[0]}"
TF_LITE_MINOR = "${@d.getVar('PV').split('.')[1]}"
TF_LITE_PATCH = "0"

SRCREV_FORMAT = "tensorflow_cpuinfo_farmhash_fft2d_fp16_fxdiv_gemmlowp_kleidiai_mlDtypes_openclHeaders_pthreadpool_ruy_vulkanHeaders_xnnpack"
SRCREV_FORMAT:append:x86 = "_neon2sse"
SRCREV_FORMAT:append:x86-64 = "_neon2sse"

# Main TensorFlow repository revision
SRCREV_tensorflow = "a481b10260dfdf833a1b16007eead49c1d7febf3"

# Third‑party dependency revisions used by TensorFlow Lite
#
# How to update these SRCREV_* values:
#
# 1) Use the automated script 'extract_tflite_srcrevs_from_github.py' to generate
#    the latest SRCREV values for all dependencies.
#
# 2) Run the script with the target TensorFlow version:
#    python3 extract_tflite_srcrevs_from_github.py v2.21.0
#
# 3) Replace the SRCREV_* values below with the output from the script.
#
# 4) For fft2d specifically, the script automatically fetches from:
#    https://android.googlesource.com/platform/external/fft2d.git
#
# 5) Rebuild and validate.
#
# IMPORTANT: Always use the automated script to ensure SRCREV values match
#            the exact pinned versions in the TensorFlow repository.
# Pinned third‑party revisions (automatically generated)
SRCREV_cpuinfo = "8a9210069b5a37dd89ed118a783945502a30a4ae"
SRCREV_farmhash = "0d859a811870d10f53a594927d0d0b97573ad06d"
SRCREV_fft2d = "fa0ad63f8b666836f56a823de546390a6e4ff4b6"
SRCREV_fp16 = "4dfe081cf6bcd15db339cf2680b9281b8451eeb3"
SRCREV_fxdiv = "63058eff77e11aa15bf531df5dd34395ec3017c8"
SRCREV_gemmlowp = "16e8662c34917be0065110bfcd9cc27d30f52fdf"
SRCREV_kleidiai = "63205aa90afa6803d8f58bc3081b69288e9f1906"
SRCREV_mlDtypes = "00d98cd92ade342fef589c0470379abb27baebe9"
SRCREV_openclHeaders = "dcd5bede6859d26833cd85f0d6bbcee7382dc9b3"
SRCREV_pthreadpool = "0e6ca13779b57d397a5ba6bfdcaa8a275bc8ea2e"
SRCREV_ruy = "3286a34cc8de6149ac6844107dfdffac91531e72"
SRCREV_vulkanHeaders = "32c07c0c5334aea069e518206d75e002ccd85389"
SRCREV_xnnpack = "25b42dfddb0ee22170d73ff0d4b333ea1e6edfeb"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;name=tensorflow;nobranch=1;protocol=https;tag=v${TF_LITE_VERSION} \
    file://tflite/0002-cmake-lite-tools-benchmark-require-protobuf-through-.patch \
    file://tflite/0003-feat-tflite-Use-fixed-OpenCL-library-name-for-compat.patch \
    file://tflite/0004-cmake-Exclude-subdirectories-from-all-builds.patch \
    file://tflite/0005-c-Force-delegate-symbols-from-shared-library.patch \
    file://tflite/0006-c-Add-version-support-to-C-API.patch \
    file://tflite/0007-cmake-Fix-label-image-dependencies.patch \
    file://tflite/0008-cmake-Add-install-rule-for-c-interface-shared-librar.patch \
    file://tflite/0009-tflite-Add-absl-log-dependency-for-enhanced-logging-.patch \
    file://tflite/0013-tflite-examples-label_image-build-profile_buffer.patch \
    git://github.com/google/farmhash;name=farmhash;destsuffix=${S}/third_party/farmhash/;branch=master;protocol=https \
    git://github.com/google/gemmlowp.git;name=gemmlowp;destsuffix=${S}/third_party/gemmlowp/;branch=master;protocol=https \
    git://github.com/pytorch/cpuinfo.git;name=cpuinfo;destsuffix=${S}/third_party/cpuinfo/;branch=main;protocol=https \
    git://github.com/jax-ml/ml_dtypes.git;name=mlDtypes;destsuffix=${S}/third_party/ml_dtypes/;branch=main;protocol=https \
    git://github.com/google/ruy.git;name=ruy;destsuffix=${S}/third_party/ruy/;branch=master;protocol=https \
    git://github.com/KhronosGroup/OpenCL-Headers.git;name=openclHeaders;destsuffix=${S}/third_party/opencl_headers/;branch=main;protocol=https \
    git://github.com/KhronosGroup/Vulkan-Headers.git;name=vulkanHeaders;destsuffix=${S}/third_party/vulkan_headers/;branch=main;protocol=https \
    git://github.com/google/XNNPACK.git;name=xnnpack;destsuffix=${S}/third_party/xnnpack/;branch=master;protocol=https \
    git://android.googlesource.com/platform/external/fft2d.git;name=fft2d;destsuffix=${S}/fft2d;branch=main;protocol=https \
    git://github.com/Maratyszcza/FP16.git;name=fp16;destsuffix=${S}/FP16;branch=master;protocol=https \
    git://github.com/ARM-software/kleidiai.git;name=kleidiai;destsuffix=${S}/kleidiai;branch=main;protocol=https \
    git://github.com/google/pthreadpool.git;name=pthreadpool;destsuffix=${S}/pthreadpool;branch=main;protocol=https \
    git://github.com/Maratyszcza/FXdiv.git;name=fxdiv;destsuffix=${S}/FXdiv;branch=master;protocol=https \
"

SRC_URI:append:class-target:x86 = " \
    git://github.com/intel/ARM_NEON_2_x86_SSE.git;name=neon2sse;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/neon2sse/;protocol=https;nobranch=1 \
"
SRC_URI:append:class-target:x86-64 = " \
    git://github.com/intel/ARM_NEON_2_x86_SSE.git;name=neon2sse;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/neon2sse/;protocol=https;nobranch=1 \
"

SRC_URI:append:class-target:arm = " \
    file://tflite/0010-Revert-Fix-XNNPACK-build-failure-with-when-mcpu-comp.patch \
    file://cpuinfo/0001-Fix-syscall-undeclared-error-if-built-with-stricter-.patch;patchdir=third_party/cpuinfo \
    file://cpuinfo/0002-Support-OE-specific-arm-value-for-CMAKE_SYSTEM_PROCE.patch;patchdir=third_party/cpuinfo \
"

inherit cmake pkgconfig

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite/c"
OECMAKE_TARGET_COMPILE += "benchmark_model label_image"

EXTRA_OECMAKE += " \
    -DCMAKE_FIND_PACKAGE_PREFER_CONFIG=ON \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DCMAKE_SYSTEM_NAME=Linux \
    -DCPUINFO_BUILD_UNIT_TESTS=OFF \
    -DCPUINFO_SUPPORTED_PLATFORM=ON \
    -DFP16_SOURCE_DIR=${S}/FP16 \
    -DFXDIV_SOURCE_DIR=${S}/FXdiv \
    -DKLEIDIAI_SOURCE_DIR=${S}/kleidiai \
    -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
    -DTFLITE_C_BUILD_SHARED_LIBS=ON \
    -DTFLITE_ENABLE_BENCHMARK_MODEL=ON \
    -DTFLITE_ENABLE_INSTALL=ON \
    -DTFLITE_ENABLE_LABEL_IMAGE=ON \
    -DTFLITE_ENABLE_NNAPI=OFF \
    -DTFLITE_ENABLE_RUY=ON \
    -DTFLITE_ENABLE_XNNPACK=ON \
    -DTFLITE_HOST_TOOLS_DIR=${STAGING_BINDIR_NATIVE} \
    -DTF_MAJOR_VERSION=${TF_LITE_MAJOR} \
    -DTF_MINOR_VERSION=${TF_LITE_MINOR} \
    -DTF_PATCH_VERSION=${TF_LITE_PATCH} \
    -DTF_VERSION_SUFFIX= \
    -DPTHREADPOOL_SOURCE_DIR=${S}/pthreadpool \
    -DTENSORFLOW_SOURCE_DIR=${S} \
"

# Disable ARM BF16 support for clang toolchain builds
EXTRA_OECMAKE:append:toolchain-clang = " -DXNNPACK_ENABLE_ARM_BF16=OFF"

# Symlink third-party directories
do_configure:prepend() {
    mkdir -p ${WORKDIR}/build
    cd ${WORKDIR}/build
    
    # Create symbolic links for third-party dependencies
    ln -sf ${S}/third_party/cpuinfo cpuinfo
    ln -sf ${S}/third_party/farmhash farmhash
    ln -sf ${S}/third_party/gemmlowp gemmlowp
    ln -sf ${S}/third_party/ml_dtypes ml_dtypes
    ln -sf ${S}/third_party/opencl_headers opencl_headers
    ln -sf ${S}/third_party/ruy ruy
    ln -sf ${S}/third_party/vulkan_headers vulkan_headers
    ln -sf ${S}/third_party/xnnpack xnnpack
    ln -sf ${S}/fft2d/src/fft2d/fft2d fft2d
    if [ -d "${S}/third_party/neon2sse" ]; then
        ln -sf ${S}/third_party/neon2sse neon2sse
    fi

    mkdir -p opengl_headers
    cp ${COMMON_LICENSE_DIR}/Apache-2.0 opengl_headers/opengl_headers_LICENSE.txt

    mkdir -p egl_headers
    cp ${COMMON_LICENSE_DIR}/Apache-2.0 egl_headers/egl_headers_LICENSE.txt
}

# Header files to install
TFLITE_HEADERS = " \
    tensorflow/compiler/mlir/lite \
    tensorflow/core/lib \
    tensorflow/core/platform \
    tensorflow/core/public \
    tensorflow/lite \
"

do_install:append() {
    for HPATH in ${TFLITE_HEADERS}; do
        install -d ${D}${includedir}/$(dirname ${HPATH})
        cd ${S}
        find ${HPATH} \( ! -name "*hexagon*" -name "*.h*" \) -type f | while read header; do
            install -D -m 0644 ${S}/$header ${D}${includedir}/$header
        done
    done

    install -d ${D}${bindir}
    install -m 0755 ${B}/tensorflow-lite/examples/label_image/label_image ${D}${bindir}
    install -m 0755 ${B}/tensorflow-lite/tools/benchmark/benchmark_model ${D}${bindir}
}

PACKAGE_BEFORE_PN += "${PN}-tools"

# Keep ${PN}-tools package name fixed (disable Debian auto-renaming)
DEBIAN_NOAUTONAME:${PN}-tools = "1"

FILES:${PN}-tools = "${bindir}"

