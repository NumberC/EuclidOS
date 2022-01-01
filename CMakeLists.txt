cmake_minimum_required(VERSION 3.18)
project(EuclidOS)

add_subdirectory(Math/libs/cparse)

# check_cxx_compiler_flag( "-std=c++11"   COMPILER_SUPPORTS_CXX11 )

add_executable(${PROJECT_NAME} Math/src/main.cpp)
target_link_libraries(EuclidOS cparse)