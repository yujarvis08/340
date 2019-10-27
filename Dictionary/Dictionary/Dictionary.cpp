#include <unordered_map>
#include <unordered_set>
#include <map>
#include <vector>
#include <algorithm>
#include <iostream>
#include <fstream>
#include <regex>
#include <locale>
#include <functional>

using namespace std;

enum pos : short {
	adjective,
	adverb,
	conjunction,
	interjection,
	noun,
	pronoun,
	proposition,
	verb
};

static unordered_map<string, map<pos, vector<string>>> defs;

static unordered_map<string, pos> positionMap;
void init() {
	positionMap["adjective"] = pos::adjective;
	positionMap["adverb"] = pos::adverb;
	positionMap["conjunction"] = pos::conjunction;
	positionMap["interjection"] = pos::interjection;
	positionMap["noun"] = pos::noun;
	positionMap["pronoun"] = pos::pronoun;
	positionMap["proposition"] = pos::proposition;
	positionMap["verb"] = pos::verb;
}
string namepos(pos pos) {
	switch (pos) {
	case pos::adjective:
		return "adjective";
	case pos::adverb:
		return "adverb";
	case pos::conjunction:
		return "conjunction";
	case pos::interjection:
		return "interjection";
	case pos::noun:
		return "noun";
	case pos::pronoun:
		return "pronoun";
	case pos::proposition:
		return "proposition";
	case pos::verb:
		return "verb";
	default:
		return "Error";
	}
}

static string ERROR_ONE("<Not found>");
static string ERROR_TWO("<Second argument must be part of speech or \'distinct\'>");
static function<bool(pos, string)> ACCEPT([](pos, string) {return true; });

int load(string filename) {
	regex regWords("([^\\|]+)(.+)");
	regex regDefinitions("\\|(.*?) -=>> ([^\\|]+)");
	smatch finder;
	string line;
	cout << "! Opening data file... " << filename << "\n";
	ifstream f(filename);
	if (f.is_open()) {
		cout << "! Loading data...\n";
		sregex_iterator end;
		while (getline(f, line, '\n')) {
			if (line[line.length() - 1] == '\r') line.resize(line.length() - 1);
			bool found = regex_match(line, finder, regWords);
			if (found) {
				map<pos, vector<string>> entry;
				string word = finder.str(1);
				string defn = finder.str(2);
				for (sregex_iterator findDefinition(defn.begin(), defn.end(), regDefinitions); findDefinition != end; ++findDefinition) {
					finder = *findDefinition;
					pos pos = positionMap[finder.str(1)];
					string definition = finder.str(2);
					entry[pos].push_back(definition);
				}
				for (auto name : positionMap) {
					auto pos = name.second;
					sort(entry[pos].begin(), entry[pos].end());
				}
				defs[word] = entry;
			}
			else {
				return 2;
			}
		}
		cout << "! Loading completed...\n";
		f.close();
		cout << "! Closing data file... " << filename << "\n";
		return 0;
	}
	else {
		return 1;
	}
}

string format(string word, pos pos, string def) {
	locale location;
	return toupper(word[0], location) + word.substr(1) + " [" + namepos(pos) + "] : " + def;
}

vector<string> search(string word, function<bool(pos, string)> tester) {
	transform(word.begin(), word.end(), word.begin(), ::tolower);
	auto def = defs.find(word);
	vector<string> result;
	if (def != defs.end()) {
		for (auto i : def->second)
			for (auto j : i.second) {
				if (tester(i.first, j))
					result.push_back(format(word, i.first, j));
			}
	}
	return result;
}

void printDefinitions(vector<string> result) {
	if (result.empty()) {
		cout << ERROR_ONE << "\n";
		return;
	}
	cout << "        " << "|\n";
	for (auto i : result) {
		cout << "        " << " " << i << "\n";
	}
	cout << "        " << "|\n";
}

void printOne(string word, function<bool(pos, string)> tester) {
	printDefinitions(search(word, tester));
}

bool loop() {
	string input;
	regex one("^\\s*([^\\s]+)\\s*$");
	regex two("^\\s*([^\\s]+)\\s+([^\\s]+)\\s*$");
	regex three("^\\s*([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s*$");
	smatch match;
	vector<unordered_set<string>> sameDefinitions(positionMap.size());
	function<bool(pos, string)> checkDistinct([&sameDefinitions](pos pos, string def) {
		if (sameDefinitions[pos].count(def) > 0) return false;
		sameDefinitions[pos].insert(def);
		return true;
		});
	cout << "Search: ";
	getline(cin, input);
	if (input == "!q" || input == "!Q") {
		cout << "-----THANK YOU-----" << endl;
		exit(0);
	}
	bool matched = regex_match(input, match, one);
	if (matched) {
		printOne(match.str(1), ACCEPT);
		return false;
	}
	matched = regex_match(input, match, two);
	if (matched) {
		auto func = positionMap.find(match.str(2));
		if (func == positionMap.end()) {
			if (match.str(2) == "distinct") {
				printOne(match.str(1), checkDistinct);
				return false;
			}
			else {
				cout << ERROR_TWO << "\n";
				return false;
			}
		}
		else {
			function<bool(pos, string)> checkPosition([func = func->second](pos pos, string) {
				return pos == func;
			});
			printOne(match.str(1), checkPosition);
			return false;
		}
	}
	matched = regex_match(input, match, three);
	if (matched) {
		auto func = positionMap.find(match.str(2));
		if (func == positionMap.end() || match.str(3) != "distinct") {
			cout << ERROR_TWO << "\n";
			return false;
		}
		function<bool(pos, string)> checkDefinitions([func = func->second, &checkDistinct](pos pos, string def) {
			return pos == func && checkDistinct(pos, def);
		});
		printOne(match.str(1), checkDefinitions);
		return false;
	}
	return true;
}

int main() {
	init();
	int file = load("./Data.CS.SFSU.txt");
	
	bool stopped = false;
	while (!stopped) {
		stopped = loop();
	}
	return 0;
}
