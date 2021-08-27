cmake_minimum_required(VERSION 3.18)

project(EuclidOS)

include_directories(${PROJECT_SOURCE_DIR}/include)

add_subdirectory(Evaluator)

add_executable(${PROJECT_NAME} Evaluator/src/main.cpp Evaluator/test/test.cpp Evaluator/src/parser.cpp)
