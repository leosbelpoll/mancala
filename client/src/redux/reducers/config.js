import {
  GET_INITIAL_CONFIG_FETCH,
  GET_INITIAL_CONFIG_SUCCESS,
  GET_INITIAL_CONFIG_ERROR,
  SET_CONFIG,
} from "redux/actions/actionTypes";

const initialState = {
  pits: null,
  balls: null,
  maxPits: null,
  maxBalls: null,
  error: null,
  loading: true,
};

const configReducer = (state = initialState, { type, payload }) => {
  switch (type) {
    case GET_INITIAL_CONFIG_FETCH: {
      return {
        ...state,
        loading: true,
      };
    }
    case GET_INITIAL_CONFIG_SUCCESS: {
      const { pitsNumber, ballsNumber, maxPitsNumber, maxBallsNumber } =
        payload;
      return {
        ...state,
        pits: pitsNumber,
        balls: ballsNumber,
        maxPits: maxPitsNumber,
        maxBalls: maxBallsNumber,
        loading: false,
      };
    }
    case GET_INITIAL_CONFIG_ERROR: {
      const { error } = payload;
      return {
        ...state,
        error,
      };
    }
    case SET_CONFIG: {
      const { pits, balls } = payload;
      return {
        ...state,
        pits,
        balls,
      };
    }
    default:
      return state;
  }
};

export default configReducer;
