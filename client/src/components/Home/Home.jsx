import { useState } from "react";
import { connect } from "react-redux";
import { useHistory } from "react-router-dom";
import { startGame } from "redux/actions/game";
import { isEmailValid } from "utils/email";

const Home = ({ config: { pits, balls }, startGame }) => {
  const [user1, setUser1] = useState("");
  const [user2, setUser2] = useState("");
  const [error, setError] = useState();
  const history = useHistory();

  const createGame = (e) => {
    e.preventDefault();
    if (!isEmailValid(user1) || !isEmailValid(user2)) {
      setError("Emails must be valid");
    } else {
      setError(null);
      startGame({ pitsNumber: pits, ballsNumber: balls, user1, user2 });
      history.push("/game");
    }
  };

  return (
    <div className="home-component container">
      <div className="jumbotron">
        <form onSubmit={createGame}>
          <div className="row">
            <div className="col-xs-12">
              <h2 className="big-title big-title-primary">
                Welcome to the Game
              </h2>
              <div className="row">
                <div className="col-sm-3 col-md-4"></div>
                <div className="col-xs-12 col-sm-6 col-md-4">
                  <label htmlFor="user1">Enter first user email</label>
                  <input
                    type="email"
                    id="user1"
                    name="user1"
                    value={user1}
                    placeholder="firstuser@test.com"
                    className="form-control"
                    onChange={(e) => setUser1(e.target.value)}
                  />
                </div>
                <div className="col-sm-3 col-md-4"></div>
              </div>
              <br />
              <div className="row">
                <div className="col-sm-3 col-md-4"></div>
                <div className="col-xs-12 col-sm-6 col-md-4">
                  <label htmlFor="user2">Enter second user email</label>
                  <input
                    type="email"
                    id="user2"
                    name="user2"
                    value={user2}
                    placeholder="seconduser@test.com"
                    className="form-control"
                    onChange={(e) => setUser2(e.target.value)}
                  />
                </div>
                <div className="col-sm-3 col-md-4"></div>
              </div>
              <br />
              <div className="row">
                <div className="col-sm-3 col-md-4"></div>
                <div className="col-xs-12 col-sm-6 col-md-4">
                  <button
                    type="submit"
                    className={`btn btn-primary btn-lg btn-full ${
                      (!user1 || !user2) && "btn-disabled"
                    }`}
                    disabled={!user1 || !user2}
                  >
                    <i className="glyphicon glyphicon-refresh"></i> New game
                  </button>
                  {error && (
                    <>
                      <br />
                      <br />
                      <ul className="text-danger">
                        <li>{error}</li>
                      </ul>
                    </>
                  )}
                </div>
                <div className="col-sm-3 col-md-4"></div>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
    config: state.config,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    startGame: (options) => dispatch(startGame(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Home);
