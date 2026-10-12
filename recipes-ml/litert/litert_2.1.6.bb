SUMMARY = "LiteRT (Google AI Edge runtime)"
DESCRIPTION = "LiteRT runtime, built from LiteRT's upstream CMake build for aarch64, \
with optional GPU/NPU/KleidiAI/Qualcomm acceleration backends selectable via \
PACKAGECONFIG. TFLite is statically bundled into the LiteRT libraries; the \
standalone TFLite C API is provided separately by the 'tensorflow-lite' recipe."
HOMEPAGE = "https://github.com/google-ai-edge/LiteRT"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

LITERT_MAJOR_VERSION := "${@'${PV}'.split('.')[0]}"
LITERT_MINOR_VERSION := "${@'${PV}'.split('.')[1]}"
LITERT_PATCH_VERSION := "${@'${PV}'.split('.')[2]}"

SRCREV_litert          = "1461b6b2def31713f5c71446eab844aae05d02e9"
SRCREV_tensorflow      = "a481b10260dfdf833a1b16007eead49c1d7febf3"
SRCREV_eigen           = "dcbaf2d608f306450f1e74949eb87e9a22a7ef4b"
SRCREV_cpuinfo         = "bc3c01e230c6974283e4b89421cfb0e232435589"
SRCREV_farmhash        = "0d859a811870d10f53a594927d0d0b97573ad06d"
SRCREV_fp16            = "0a92994d729ff76a58f692d3028ca1b64b145d91"
SRCREV_fxdiv           = "b408327ac2a15ec3e43352421954f5b1967701d1"
SRCREV_gemmlowp        = "16e8662c34917be0065110bfcd9cc27d30f52fdf"
SRCREV_kleidiai        = "51f7190558e51b7415d9ba24994fb477d7371446"
SRCREV_mlDtypes        = "00d98cd92ade342fef589c0470379abb27baebe9"
SRCREV_openclHeaders   = "dcd5bede6859d26833cd85f0d6bbcee7382dc9b3"
SRCREV_openglHeaders   = "0cb0880d91581d34f96899c86fc1bf35627b4b81"
SRCREV_eglHeaders      = "649981109e263b737e7735933c90626c29a306f2"
SRCREV_pthreadpool     = "9003ee6c137cea3b94161bd5c614fb43be523ee1"
SRCREV_ruy             = "3286a34cc8de6149ac6844107dfdffac91531e72"
SRCREV_vulkanHeaders   = "32c07c0c5334aea069e518206d75e002ccd85389"
SRCREV_xnnpack         = "c2e81f01b01fca3327d4b3aa070b56085f2603bd"
SRCREV_flatbuffers     = "edbe17738352418245d7228e7fd9f12c3ddc34c4"
SRCREV_nlohmannJson    = "9cca280a4d0ccf0c08f47a99aa71d1b0e52f8d03"
SRCREV_googletest      = "58d77fa8070e8cec2dc1ed015d66b454c8d78850"
SRCREV_googlebenchmark = "0d98dba29d66e93259db7daa53a9327df767a415"
SRCREV_fft2d           = "c6fd2dd6d21397baa6653139d31d84540d5449a2"

SRCREV_FORMAT = "litert_tensorflow_eigen_cpuinfo_farmhash_fp16_fxdiv_gemmlowp_kleidiai_mlDtypes_openclHeaders_openglHeaders_eglHeaders_pthreadpool_ruy_vulkanHeaders_xnnpack_flatbuffers_nlohmannJson_googletest_googlebenchmark_fft2d"

FETCHCONTENT_LOCAL_DIR = "${UNPACKDIR}/fetched"

SRC_URI = " \
    gitsm://github.com/google-ai-edge/LiteRT.git;name=litert;branch=main;protocol=https;lfs=0 \
    file://0001-litert-qualcomm-Add-QCS6490-Kodiak-SoC-support.patch \
    file://0002-litert-qualcomm-htp-log-auto-detected-SoC-and-fall-b.patch \
    file://0003-litert-honor-FP16_SOURCE_DIR-for-the-fp16-headers-in.patch \
    file://0004-tflite-add-option-to-disable-the-benchmark_model-too.patch \
    file://0005-litert-let-find_package-flatbuffers-use-the-system-p.patch \
    file://0006-litert-c-add-soversion-install-rules-and-pkg-config-.patch \
    file://0008-Make-CMake-vendor-builds-opt-in-and-offline-safe.patch \
    file://0009-litert-core-accept-soname-versioned-plugin-libs.patch \
    git://github.com/tensorflow/tensorflow.git;name=tensorflow;nobranch=1;protocol=https;destsuffix=fetched/tensorflow \
    git://gitlab.com/libeigen/eigen.git;name=eigen;protocol=https;nobranch=1;destsuffix=fetched/eigen \
    git://github.com/pytorch/cpuinfo.git;name=cpuinfo;branch=main;protocol=https;destsuffix=fetched/cpuinfo \
    git://github.com/google/farmhash.git;name=farmhash;branch=master;protocol=https;destsuffix=fetched/farmhash \
    git://github.com/Maratyszcza/FP16.git;name=fp16;branch=master;protocol=https;destsuffix=fetched/fp16_headers \
    git://github.com/Maratyszcza/FXdiv.git;name=fxdiv;branch=master;protocol=https;destsuffix=fetched/fxdiv \
    git://github.com/google/gemmlowp.git;name=gemmlowp;branch=master;protocol=https;destsuffix=fetched/gemmlowp \
    git://github.com/ARM-software/kleidiai.git;name=kleidiai;branch=main;protocol=https;destsuffix=fetched/kleidiai \
    git://github.com/jax-ml/ml_dtypes.git;name=mlDtypes;branch=main;protocol=https;destsuffix=fetched/ml_dtypes \
    git://github.com/KhronosGroup/OpenCL-Headers.git;name=openclHeaders;branch=main;protocol=https;destsuffix=fetched/opencl_headers \
    git://github.com/KhronosGroup/OpenGL-Registry.git;name=openglHeaders;nobranch=1;protocol=https;destsuffix=fetched/opengl_headers \
    git://github.com/KhronosGroup/EGL-Registry.git;name=eglHeaders;nobranch=1;protocol=https;destsuffix=fetched/egl_headers \
    git://github.com/google/pthreadpool.git;name=pthreadpool;branch=main;protocol=https;destsuffix=fetched/pthreadpool \
    git://github.com/google/ruy.git;name=ruy;branch=master;protocol=https;destsuffix=fetched/ruy \
    git://github.com/KhronosGroup/Vulkan-Headers.git;name=vulkanHeaders;branch=main;protocol=https;destsuffix=fetched/vulkan_headers \
    git://github.com/google/XNNPACK.git;name=xnnpack;branch=master;protocol=https;destsuffix=fetched/xnnpack \
    git://github.com/google/flatbuffers.git;name=flatbuffers;protocol=https;nobranch=1;destsuffix=fetched/flatbuffers \
    git://github.com/nlohmann/json.git;name=nlohmannJson;protocol=https;nobranch=1;destsuffix=fetched/nlohmann_json \
    git://github.com/google/googletest.git;name=googletest;protocol=https;branch=v1.12.x;destsuffix=fetched/googletest \
    git://github.com/google/benchmark.git;name=googlebenchmark;protocol=https;nobranch=1;destsuffix=fetched/googlebenchmark \
    git://github.com/petewarden/OouraFFT.git;name=fft2d;nobranch=1;protocol=https;destsuffix=fetched/fft2d \
    "

# Patch 0007 installs the Qualcomm plugins; only needed/valid when qualcomm is enabled.
SRC_URI:append = "${@bb.utils.contains('PACKAGECONFIG', 'qualcomm', ' file://0007-litert-qualcomm-add-soversion-install-rules-and-pkg-.patch', '', d)}"

OECMAKE_SOURCEPATH = "${S}/litert"

COMPATIBLE_HOST = "aarch64.*"

DEPENDS = " \
    abseil-cpp \
    cmake-native \
    flatbuffers-tflite \
    flatbuffers-tflite-native \
    libeigen \
    protobuf \
    protobuf-native \
    python3-native \
"

inherit cmake python3native features_check pkgconfig

REQUIRED_DISTRO_FEATURES = "${@bb.utils.contains('PACKAGECONFIG', 'gpu', 'opengl', '', d)}"

PACKAGECONFIG ?= "gpu npu kleidiai"

PACKAGECONFIG[gpu] = "-DLITERT_ENABLE_GPU=ON -DTFLITE_ENABLE_GPU=ON,-DLITERT_ENABLE_GPU=OFF -DTFLITE_ENABLE_GPU=OFF,opencl-headers"
PACKAGECONFIG[npu] = "-DLITERT_ENABLE_NPU=ON,-DLITERT_ENABLE_NPU=OFF"
PACKAGECONFIG[qualcomm] = "-DLITERT_ENABLE_QUALCOMM=ON -DQAIRT_HEADERS_DIR=${QIMSDK_QAIRT_HEADERS_DIR},-DLITERT_ENABLE_QUALCOMM=OFF,qairt-sdk,qairt-sdk"
PACKAGECONFIG[kleidiai] = "-DLITERT_DISABLE_KLEIDIAI=OFF,-DLITERT_DISABLE_KLEIDIAI=ON"

QIMSDK_QAIRT_HEADERS_DIR ?= "${STAGING_INCDIR}/QNN"
QIMSDK_QAIRT_SDK_DIR ?= "${STAGING_DIR_TARGET}${prefix}"

LITERT_HOST_TOOLS_DIR ?= "${STAGING_BINDIR_NATIVE}"

EXTRA_OECMAKE = " \
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_FIND_PACKAGE_PREFER_CONFIG=ON \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DLITERT_AUTO_BUILD_TFLITE=ON \
    -DLITERT_MAJOR_VERSION=${LITERT_MAJOR_VERSION} \
    -DLITERT_MINOR_VERSION=${LITERT_MINOR_VERSION} \
    -DLITERT_PATCH_VERSION=${LITERT_PATCH_VERSION} \
    -DLITERT_BUILD_TESTS=OFF \
    -DLITERT_ENABLE_SAMSUNG=OFF \
    -DLITERT_ENABLE_MEDIATEK=OFF \
    -DTFLITE_HOST_TOOLS_DIR=${LITERT_HOST_TOOLS_DIR} \
    -DBUILD_TESTING=OFF \
    -DBENCHMARK_ENABLE_TESTING=OFF \
    -DBENCHMARK_ENABLE_INSTALL=OFF \
    -DINSTALL_GTEST=OFF \
    ${LITERT_FETCHCONTENT_OFFLINE_OECMAKE} \
"

# Point CMake at the pre-fetched trees so nothing is downloaded at build time.
LITERT_FETCHCONTENT_OFFLINE_OECMAKE = " \
    -DFETCHCONTENT_FULLY_DISCONNECTED=ON \
    -DTENSORFLOW_SOURCE_DIR=${FETCHCONTENT_LOCAL_DIR}/tensorflow \
    -DFETCHCONTENT_SOURCE_DIR_TENSORFLOW=${FETCHCONTENT_LOCAL_DIR}/tensorflow \
    -DPTHREADPOOL_SOURCE_DIR=${FETCHCONTENT_LOCAL_DIR}/pthreadpool \
    -DFP16_SOURCE_DIR=${FETCHCONTENT_LOCAL_DIR}/fp16_headers \
    -DFETCHCONTENT_SOURCE_DIR_FP16_HEADERS=${FETCHCONTENT_LOCAL_DIR}/fp16_headers \
    -DFXDIV_SOURCE_DIR=${FETCHCONTENT_LOCAL_DIR}/fxdiv \
    -DXNNPACK_SOURCE_DIR=${FETCHCONTENT_LOCAL_DIR}/xnnpack \
    -DFETCHCONTENT_SOURCE_DIR_XNNPACK=${FETCHCONTENT_LOCAL_DIR}/xnnpack \
    -DKLEIDIAI_SOURCE_DIR=${FETCHCONTENT_LOCAL_DIR}/kleidiai \
    -DCPUINFO_SOURCE_DIR=${FETCHCONTENT_LOCAL_DIR}/cpuinfo \
    -DFETCHCONTENT_SOURCE_DIR_CPUINFO=${FETCHCONTENT_LOCAL_DIR}/cpuinfo \
    -DFETCHCONTENT_SOURCE_DIR_EIGEN=${FETCHCONTENT_LOCAL_DIR}/eigen \
    -DFETCHCONTENT_SOURCE_DIR_FARMHASH=${FETCHCONTENT_LOCAL_DIR}/farmhash \
    -DFETCHCONTENT_SOURCE_DIR_GEMMLOWP=${FETCHCONTENT_LOCAL_DIR}/gemmlowp \
    -DFETCHCONTENT_SOURCE_DIR_ML_DTYPES=${FETCHCONTENT_LOCAL_DIR}/ml_dtypes \
    -DFETCHCONTENT_SOURCE_DIR_RUY=${FETCHCONTENT_LOCAL_DIR}/ruy \
    -DFETCHCONTENT_SOURCE_DIR_OPENCL_HEADERS=${FETCHCONTENT_LOCAL_DIR}/opencl_headers \
    -DFETCHCONTENT_SOURCE_DIR_VULKAN_HEADERS=${FETCHCONTENT_LOCAL_DIR}/vulkan_headers \
    -DFETCHCONTENT_SOURCE_DIR_OPENGL_HEADERS=${FETCHCONTENT_LOCAL_DIR}/opengl_headers \
    -DFETCHCONTENT_SOURCE_DIR_EGL_HEADERS=${FETCHCONTENT_LOCAL_DIR}/egl_headers \
    -DFETCHCONTENT_SOURCE_DIR_FLATBUFFERS=${FETCHCONTENT_LOCAL_DIR}/flatbuffers \
    -DFETCHCONTENT_SOURCE_DIR_FFT2D=${FETCHCONTENT_LOCAL_DIR}/fft2d \
    -DFETCHCONTENT_SOURCE_DIR_NLOHMANN_JSON=${FETCHCONTENT_LOCAL_DIR}/nlohmann_json \
    -DFETCHCONTENT_SOURCE_DIR_GOOGLETEST=${FETCHCONTENT_LOCAL_DIR}/googletest \
    -DFETCHCONTENT_SOURCE_DIR_GOOGLEBENCHMARK=${FETCHCONTENT_LOCAL_DIR}/googlebenchmark \
"

OECMAKE_TARGET_COMPILE = "litert_runtime_c_api_shared_lib run_model apply_plugin_main analyze_model extract_bytecode"
OECMAKE_TARGET_COMPILE:append = "${@bb.utils.contains('PACKAGECONFIG', 'qualcomm', ' dispatch_api_qualcomm_so qnn_compiler_plugin', '', d)}"

EXTRA_OECMAKE:append = " -DTFLITE_BUILD_BENCHMARK_TOOL=OFF"
EXTRA_OECMAKE:append:toolchain-clang = " -DXNNPACK_ENABLE_ARM_BF16=OFF"

# cl_khr_command_buffer typedefs in opencl-headers require CL_ENABLE_BETA_EXTENSIONS.
CFLAGS:append = "${@bb.utils.contains('PACKAGECONFIG', 'gpu', ' -DCL_ENABLE_BETA_EXTENSIONS -DCL_TARGET_OPENCL_VERSION=300', '', d)}"
CXXFLAGS:append = "${@bb.utils.contains('PACKAGECONFIG', 'gpu', ' -DCL_ENABLE_BETA_EXTENSIONS -DCL_TARGET_OPENCL_VERSION=300', '', d)}"

export LITERT_QAIRT_SDK = "${@bb.utils.contains('PACKAGECONFIG', 'qualcomm', '${QIMSDK_QAIRT_SDK_DIR}/', '', d)}"
export TF_NEED_CUDA             = "0"
export TF_NEED_ROCM             = "0"
export TF_NEED_TENSORRT         = "0"
export TF_SET_ANDROID_WORKSPACE = "0"
export TF_ENABLE_XLA            = "0"

# The build embeds some build paths in debug info and binaries due to the complex
# CMake FetchContent setup and bundled TFLite. Skip the buildpaths QA check.
INSANE_SKIP:${PN} += "buildpaths"
INSANE_SKIP:${PN}-dbg += "buildpaths"

RDEPENDS:${PN} += "abseil-cpp"
