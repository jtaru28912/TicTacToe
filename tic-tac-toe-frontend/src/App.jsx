import React, { useState } from 'react';
import './App.css'
import Cell from './Cell';

function App() {
  const [board, setBoard] = useState(Array(9).fill(null));
  const [xTurn, setXTurn] = useState(true);
  const [winner, setWinner] = useState(null);

  const handleClick = (index) => {
    if( board[index] !== null)
      return;
    

    const newBoard = [...board]
    newBoard[index] = xTurn ? 'X' : 'O';
    setBoard(newBoard);

    const gameResult = checkWinner(newBoard);
    if (gameResult) {
      setWinner(gameResult);
    } else if (!newBoard.includes(null)) {
      setWinner('Draw');
    } else {
      setXTurn(!xTurn);
    }
  };

    const checkWinner = (board) => {
    const winPatterns = [
      [0, 1, 2], [3, 4, 5], [6, 7, 8], // rows
      [0, 3, 6], [1, 4, 7], [2, 5, 8], // columns
      [0, 4, 8], [2, 4, 6]             // diagonals
    ];

    for (const [a, b, c] of winPatterns) {
      if (board[a] && board[a] === board[b] && board[b] === board[c]) {
        return board[a]; // 'X' or 'O'
      }
    }

    return null;
  };

  const resetGame = () => {
    setBoard(Array(9).fill(null));
    setXTurn(true);
    setWinner(null);
  };


 return (
    <div className="app">
      <h1>Tic Tac Toe</h1>

      <div className="board">
        {board.map((value, index) => (
          <Cell key={index} value={value} onClick={() => handleClick(index)} />
        ))}
      </div>

      {winner ? (
        <div className="status">
          <p>{winner === 'Draw' ? 'It\'s a Draw!' : `Player ${winner} Wins! 🎉`}</p>
          <button onClick={resetGame}>Play Again</button>
        </div>
      ) : (
        <p>Turn: {xTurn ? 'X' : 'O'}</p>
      )}
    </div>
  );

}
export default App
  
