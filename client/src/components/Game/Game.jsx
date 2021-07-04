import React from "react";
import { connect } from "react-redux";
import { useHistory } from "react-router-dom";
import { play } from "redux/actions/game";
import "./Game.scss";
import Pit from "./Pit";
import User from "./User";

const Game = ({
  game: { loading, player1, player2, turn, id, winner, status },
  playAction,
}) => {
  const history = useHistory();

  if (loading === null) {
    history.push("/");
  }

  if (!player1 || !player2) return null;

  const {
    pits: pitsPlayer1,
    hut: hutPlayer1,
    user: { email: emailUser1, id: userId1 },
  } = player1;
  const pitsPlayer1Reversed = [...pitsPlayer1].reverse();
  const {
    pits: pitsPlayer2,
    hut: hutPlayer2,
    user: { email: emailUser2, id: userId2 },
  } = player2;
  const { id: userPlayingId } = turn;

  const play = (pitPosition) => {
    playAction(id, {
      userId: userPlayingId,
      pitPosition,
    });
  };

  const isActive = (userId) => userId === userPlayingId && !winner;

  const isWinner = (userId) =>
    userId === winner?.id || (status === "FINISHED" && !winner);

  const isDisabled = (userId, ballsInPit) =>
    userId !== userPlayingId || ballsInPit === 0 || loading;

  return (
    <>
      <div className="game">
        <User
          email={emailUser1}
          winner={isWinner(userId1)}
          active={isActive(userId1)}
        />
        <div className="game-container">
          <div className="hut hole">{hutPlayer1}</div>
          <div>
            <div className="pits">
              {pitsPlayer1Reversed.map((ballsInPit, index) => (
                <Pit
                  key={index}
                  ballsInPit={ballsInPit}
                  disabled={isDisabled(userId1, ballsInPit)}
                  play={play}
                  position={pitsPlayer1Reversed.length - 1 - index}
                />
              ))}
            </div>
            <br />
            <br />
            <div className="pits">
              {pitsPlayer2.map((ballsInPit, index) => (
                <Pit
                  key={index}
                  ballsInPit={ballsInPit}
                  disabled={isDisabled(userId2, ballsInPit)}
                  play={play}
                  position={index}
                />
              ))}
            </div>
          </div>
          <div className="hut hole">{hutPlayer2}</div>
        </div>
        <User
          email={emailUser2}
          winner={isWinner(userId2)}
          active={isActive(userId2)}
          bottomUser
        />
      </div>
    </>
  );
};

const mapStateToProps = (state) => {
  return {
    game: state.game,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    playAction: (id, options) => dispatch(play(id, options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Game);
