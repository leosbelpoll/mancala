import React, { useState } from "react";
import { connect } from "react-redux";
import { setConfig } from "redux/actions/config";
import "./Configuration.scss";

const Configuration = ({
  config: { pits, balls, maxPits, maxBalls, loading },
  setConfigurations,
}) => {
  const [pitsNumber, setPitsNumber] = useState(pits);
  const [ballsNumber, setBallsNumber] = useState(balls);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(false);

  const isPitsNumberValid = pitsNumber > 0 && pitsNumber <= maxPits;

  const isBallsNumberValid = ballsNumber > 0 && ballsNumber <= maxBalls;

  const onSetConfig = (e) => {
    e.preventDefault();
    if (!isPitsNumberValid || !isBallsNumberValid) {
      setError(true);
      setSuccess(false);
    } else {
      setError(false);
      setSuccess(true);
      setConfigurations({
        pits: Number(pitsNumber),
        balls: Number(ballsNumber),
      });
    }
  };

  return (
    <div className="configuration-component container">
      <div className="jumbotron">
        <h2 className="big-title big-title-primary">Game configuration</h2>
        <div className="row">
          <div className="col-xs-12">
            <form onSubmit={onSetConfig}>
              <div className="row">
                <div className="col-sm-3 col-md-4"></div>
                <div className="col-xs-12 col-sm-6 col-md-4">
                  <label htmlFor="pits">Enter pits number</label>
                  <input
                    type="number"
                    id="pitsNumber"
                    name="pitsNumber"
                    value={pitsNumber}
                    placeholder="Pits number"
                    className="form-control"
                    onChange={(e) => setPitsNumber(e.target.value)}
                  />
                </div>
                <div className="col-sm-3 col-md-4"></div>
              </div>
              <br />
              <div className="row">
                <div className="col-sm-3 col-md-4"></div>
                <div className="col-xs-12 col-sm-6 col-md-4">
                  <label htmlFor="ballsNumber">Enter balls number</label>
                  <input
                    type="number"
                    id="ballsNumber"
                    name="ballsNumber"
                    value={ballsNumber}
                    placeholder="Balls number"
                    className="form-control"
                    onChange={(e) => setBallsNumber(e.target.value)}
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
                    className="btn btn-primary btn-lg btn-full"
                    disabled={!pitsNumber || !ballsNumber}
                  >
                    <i className="glyphicon glyphicon-ok"></i> Config
                  </button>
                  {success && (
                    <>
                      <br />
                      <br />
                      <span className="text-success">
                        Configuration set successfully!
                      </span>
                    </>
                  )}
                  {error && (
                    <>
                      <br />
                      <br />
                      <ul className="text-danger">
                        {!isPitsNumberValid && (
                          <li>Pits number must be between 1 and {maxPits}</li>
                        )}
                        {!isBallsNumberValid && (
                          <li>Balls number must be between 1 and {maxBalls}</li>
                        )}
                      </ul>
                    </>
                  )}
                </div>
                <div className="col-sm-3 col-md-4"></div>
              </div>
            </form>
          </div>
        </div>
        <br />
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
    setConfigurations: (options) => dispatch(setConfig(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Configuration);
