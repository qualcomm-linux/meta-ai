SUMMARY = "Open Neural Network Exchange (ONNX)"
DESCRIPTION = "ONNX is an open format built to represent machine learning models"
HOMEPAGE = "https://github.com/onnx/onnx.git"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = " \
    protobuf \
    protobuf-native \
"

SRC_URI = "git://github.com/onnx/onnx.git;protocol=https;branch=rel-${PV}"
SRCREV = "be2b5fde82d9c8874f3d19328bdfe3b6962dc67b"

inherit cmake

EXTRA_OECMAKE = " \
    -DCMAKE_BUILD_TYPE=RelWithDebInfo \
    -DONNX_USE_PROTOBUF_SHARED_LIBS=ON \
    -DBUILD_SHARED_LIBS=ON \
    -DONNX_USE_LITE_PROTO=OFF \
    -DONNX_GEN_PB_TYPE_STUBS=OFF \
    -DONNX_BUILD_TESTS=OFF \
    -DONNX_BUILD_BENCHMARKS=OFF \
    -DFETCHCONTENT_FULLY_DISCONNECTED=ON \
    -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
    -DProtobuf_INCLUDE_DIR=${STAGING_INCDIR} \
    -DProtobuf_LIBRARY=${STAGING_LIBDIR}/libprotobuf.so \
"

# ONNX does not set SOVERSION, so libonnx.so and libonnx_proto.so are not linker
# symlinks. Remove the bare .so glob from the -dev package and place them in the
# main package.
FILES:${PN}-dev:remove = "${FILES_SOLIBSDEV}"

FILES:${PN} += "${libdir}/libonnx.so ${libdir}/libonnx_proto.so"
