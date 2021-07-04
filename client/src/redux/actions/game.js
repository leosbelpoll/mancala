import {
  START_GAME_FETCH,
  START_GAME_SUCCESS,
  START_GAME_ERROR,
  PLAY_FETCH,
  PLAY_SUCCESS,
  PLAY_ERROR,
} from "redux/actions/actionTypes";
import { putData, postData } from "utils/api";

export const startGameFetch = () => ({
  type: START_GAME_FETCH,
});

export const startGameSuccess = (game) => ({
  type: START_GAME_SUCCESS,
  payload: game,
});

export const startGameError = (error) => ({
  type: START_GAME_ERROR,
  payload: error,
});

export const startGame = (gameOptions) => {
  return async (dispatch) => {
    dispatch(startGameFetch());
    try {
      const game = await postData("game", gameOptions);
      dispatch(startGameSuccess(game));
    } catch (error) {
      dispatch(startGameError(error));
    }
  };
};

export const playFetch = () => ({
  type: PLAY_FETCH,
});

export const playSuccess = (board) => ({
  type: PLAY_SUCCESS,
  payload: board,
});

export const playError = (error) => ({
  type: PLAY_ERROR,
  payload: error,
});

export const play = (id, options) => {
  return async (dispatch) => {
    dispatch(playFetch());
    try {
      const game = await putData(`game/${id}`, options);
      dispatch(playSuccess(game));
    } catch (error) {
      dispatch(playError(error));
    }
  };
};
