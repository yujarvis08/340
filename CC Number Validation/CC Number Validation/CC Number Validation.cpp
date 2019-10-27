#include <string>
#include <vector>
#include <iostream>
#include <iomanip>
using namespace std;

bool isvalidcc(const string&);

int main()
{
	//
	// PLEASE DO NOT CHANGE function main
	//
	vector<string> cardnumbers = {
		 "371449635398431", "4444444444444448", "4444424444444440", "4110144110144115",
		"4114360123456785", "4061724061724061", "5500005555555559", "5115915115915118",
		"5555555555555557", "6011016011016011", "372449635398431", "4444544444444448",
		"4444434444444440", "4110145110144115", "4124360123456785", "4062724061724061",
		"5501005555555559", "5125915115915118", "5556555555555557", "6011116011016011",
		 "372449635397431", "4444544444444448", "4444434444544440", "4110145110184115",
		"4124360123457785", "4062724061724061", "5541005555555559", "5125115115915118",
		"5556551555555557", "6011316011016011"
	};

	int i;
	vector<string>::iterator itr;

	for (i = 1, itr = cardnumbers.begin(); itr != cardnumbers.end(); ++itr, i++) {
		cout << setw(2)  << i << " " 
			 << setw(17) << *itr 
			 << ((isvalidcc(*itr)) ? " is valid" : " is not valid") << endl;
	}

	return 0;
}

bool isvalidcc(const string& ccNo) {
	int temp = 0;
	int oddNo = 0;
	int evenNo = 0;
	int total = 0;

	for (int i = (ccNo.size() - 2); i >= 0; i -= 2) {
			temp = 2 * stoi(ccNo.substr(i, 1));
			if (temp >= 10) {
				temp = 1 + (temp % 10);
				evenNo += temp;
			}
			else
				evenNo += temp;
	}

	for (int j = (ccNo.size() - 1); j >= 0; j -= 2) {
		oddNo += stoi(ccNo.substr(j, 1));
	}

	total = oddNo + evenNo;

	return (total % 10 == 0);

		return false;
}
