SUMMARY = "Fast LLM inference in C/C++"
DESCRIPTION = "llama.cpp provides fast, CPU-first inference for large language models in pure C/C++ with optional GPU acceleration backends."
HOMEPAGE = "https://github.com/ggml-org/llama.cpp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=223b26b3c1143120c87e2b13111d3e99"

SRCREV = "550d684bd13132d4af744cee64c1a3acb0ceaf09"
SRC_URI = "git://github.com/ggml-org/llama.cpp.git;protocol=https;tag=${PV};nobranch=1"

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
