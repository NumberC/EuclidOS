project(EuclidOS)

include_directories(/include/)
add_subdirectory(${PROJECT_NAME} Evaluator)

add_executable(${PROJECT_NAME} Evaluator/src/main.cpp)
