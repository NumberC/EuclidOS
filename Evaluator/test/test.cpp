#include <iostream>
#include <chrono>

using namespace std;

int main(){
    const auto now = chrono::system_clock::now().time_since_epoch(); 

    const auto after = chrono::system_clock::now().time_since_epoch(); 

    cout << chrono::duration_cast<chrono::milliseconds>(after - now).count() << endl;
}