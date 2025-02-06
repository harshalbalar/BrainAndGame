import React from "react";

const Leaderboard = ({ leaderboard }) => {
  return (
    <div className="leaderboard" style={{marginTop: "20px"}}>
      <table className="leaderboard-table">
        <thead>
          <tr>
            <th>Rank</th>
            <th>Participant</th>
            <th>Score</th>
          </tr>
        </thead>
        <tbody>
          {leaderboard.map((participation, index) => (
            <tr key={participation.id}>
              <td>{index + 1}</td>
              <td>{participation.userKey.username}</td>
              <td>{participation.points}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Leaderboard;