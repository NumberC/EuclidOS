#include <iostream>
#include "../libs/cparse/shunting-yard.h"

using namespace std;
using namespace cparse;

int main(){
    TokenMap vars = new TokenMap();
    cout << calculator::calculate("sin(30+4)*2+6^2+5", vars) << endl;
    return 0;
}