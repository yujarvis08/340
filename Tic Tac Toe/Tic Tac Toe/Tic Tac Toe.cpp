#include <iostream>
using namespace std;

int counter = 0;
bool isWon(char, char[][3]);
bool isDraw(char[][3]);
void displayBoard(char[][3]);
void makeAMove(char[][3], char);

int main() {
	//
	//	PLEASE DO NOT CHANGE function main
	//
	char board[3][3] = { { ' ', ' ', ' ' },{ ' ', ' ', ' ' },{ ' ', ' ', ' ' } };
	displayBoard(board);

	while (true) {
		// The first player makes a move
		makeAMove(board, 'X');
		displayBoard(board);
		if (isWon('X', board)) {
			cout << "X player won" << endl;
			exit(0);
		}
		else if (isDraw(board)) {
			cout << "No winner" << endl;
			exit(0);
		}

		// The second player makes a move
		makeAMove(board, 'O');
		displayBoard(board);

		if (isWon('O', board)) {
			cout << "O player won" << endl;
			exit(0);
		}
		else if (isDraw(board)) {
			cout << "No winner" << endl;
			exit(0);
		}
	}

	return 0;
}

void displayBoard(char board[][3]) {

	for (int rows = 0; rows < 3; rows++) {
		cout << "-------------" << endl;
		for (int columns = 0; columns < 3; columns++) {
			cout << "|" << board[rows][columns] << " ";
		}
		cout << "|" << endl;
	}
	cout << "-------------" << endl;
}

bool isWon(char player, char board[][3]) {
	bool win = false;
	if(counter >= 5) {
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player)
			win = true;
		else if (board[2][0] == player && board[2][1] == player && board[2][2] == player)
			win = true;
		else if (board[1][0] == player && board[1][1] == player && board[1][2] == player)
			win = true;
		else if (board[0][0] == player && board[1][0] == player && board[2][0] == player)
			win = true;
		else if (board[2][0] == player && board[1][1] == player && board[0][2] == player)
			win = true;
		else if (board[0][1] == player && board[1][1] == player && board[2][1] == player)
			win = true;
		else if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
			win = true;
		else if (board[0][2] == player && board[1][2] == player && board[2][2] == player)
			win = true;
		
	}

	return win;
}

bool isDraw(char board[][3]) {
	return counter == 9;
	return false;
}

void makeAMove(char board[][3], char player) {
	int row = 0;
	int column = 0;
	bool status = false;
	while (!status) {

		cout << "Enter a row (0, 1, 2) for player " << player << "    : ";
		cin >> row;
		cout << "Enter a column (0, 1, 2) for player " << player << " : "; ;
		cin >> column;

		if (board[row][column] == ' ' && (row >= 0 && row <= 2) && (column >= 0 && column <= 2)) {
			board[row][column] = player;
			status = true;
			counter++;
		}
		else if (row < 0 || row > 2 || column < 0 || column > 2)
			cout << "Invalid row/column input" << endl;
		else
			cout << "This cell is already occupied. Try a different cell" << endl;

	
	}
}