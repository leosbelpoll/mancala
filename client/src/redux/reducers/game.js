import {
  START_GAME_FETCH,
  START_GAME_SUCCESS,
  START_GAME_ERROR,
  PLAY_FETCH,
  PLAY_SUCCESS,
  PLAY_ERROR,
} from "redux/actions/actionTypes";

const initialState = {
  id: null,
  createAt: null,
  pitsNumber: null,
  ballsNumber: null,
  player1: null,
  player2: null,
  turn: null,
  status: null,
  winner: null,
  loading: null,
  error: null,
};

const gameReducer = (state = initialState, { type, payload }) => {
  switch (type) {
    case START_GAME_FETCH: {
      return {
        ...initialState,
        loading: true,
      };
    }
    case START_GAME_SUCCESS: {
      return {
        ...state,
        id: payload.id,
        createAt: payload.createAt,
        pitsNumber: payload.pitsNumber,
        ballsNumber: payload.ballsNumber,
        player1: payload.player1,
        player2: payload.player2,
        turn: payload.turn,
        status: payload.status,
        winner: payload.winner,
        loading: false,
        error: null,
      };
    }
    case START_GAME_ERROR: {
      return {
        ...state,
        loading: false,
        error: payload,
      };
    }
    case PLAY_FETCH: {
      return {
        ...state,
        loading: true,
        error: null,
      };
    }
    case PLAY_SUCCESS: {
      return {
        ...state,
        id: payload.id,
        createAt: payload.createAt,
        pitsNumber: payload.pitsNumber,
        ballsNumber: payload.ballsNumber,
        player1: payload.player1,
        player2: payload.player2,
        turn: payload.turn,
        status: payload.status,
        winner: payload.winner,
        loading: false,
        error: null,
      };
    }
    case PLAY_ERROR: {
      return {
        ...state,
        loading: false,
        error: payload,
      };
    }
    default:
      return state;
  }
};

export default gameReducer;
