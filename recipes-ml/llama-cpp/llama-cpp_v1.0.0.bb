SUMMARY = "Fast LLM inference in C/C++"
DESCRIPTION = "llama.cpp provides fast, CPU-first inference for large language models in pure C/C++ with optional GPU acceleration backends."
HOMEPAGE = "https://github.com/ggml-org/llama.cpp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=223b26b3c1143120c87e2b13111d3e99"

SRCREV = "a6d1a74dbebce3a16f4f2ceddbf38c21a08d8674"
SRC_URI = "git://github.com/ggml-org/llama.cpp.git;protocol=https;tag=${PV};nobranch=1 \
    file://0001-sgemm-guard-ARM-NEON-fp16-load-on-FP16_VECTOR_ARITH.patch \
"

inherit cmake pkgconfig

# Optional HTTPS support in llama-server.
PACKAGECONFIG ?= ""
PACKAGECONFIG[openssl] = "-DLLAMA_OPENSSL=ON,-DLLAMA_OPENSSL=OFF,openssl,"

EXTRA_OECMAKE += " \
    -DBUILD_SHARED_LIBS=ON \
    -DGGML_NATIVE=OFF \
    -DLLAMA_BUILD_EXAMPLES=OFF \
    -DLLAMA_BUILD_TESTS=OFF \
    -DLLAMA_BUILD_WEBUI=OFF \
    -DLLAMA_TOOLS_INSTALL=ON \
"

PACKAGES = "${PN}-src ${PN}-dbg ${PN}-staticdev ${PACKAGE_BEFORE_PN} ${PN} ${PN}-dev ${PN}-doc ${PN}-locale"
FILES:${PN} += "${libdir}/libllama-*-impl.so"
