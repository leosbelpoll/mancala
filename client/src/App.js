import { connect } from "react-redux";
import { setInitialConfig } from "redux/actions/config";
import { useEffect } from "react";

function App({ setInitialConfig, config }) {
  const { maxPits, maxBalls } = config;
  useEffect(() => {
    // Load initial config
    if (!maxBalls || !maxPits) {
      setInitialConfig();
    }
  }, [setInitialConfig, maxPits, maxBalls]);

  return <h1>Mancala</h1>;
}

const mapStateToProps = (state) => {
  return {
    config: state.config,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    setInitialConfig: () => dispatch(setInitialConfig()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
