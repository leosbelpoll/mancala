import {
  GET_INITIAL_CONFIG_ERROR,
  GET_INITIAL_CONFIG_FETCH,
  GET_INITIAL_CONFIG_SUCCESS,
  SET_CONFIG,
} from "redux/actions/actionTypes";
import { getData } from "utils/api";

export const setInitialConfig = () => {
  return async (dispatch) => {
    dispatch(setInitialConfigFetch());
    try {
      const initialConfig = await getData("configs");
      dispatch(setInitialConfigSuccess(initialConfig));
    } catch (error) {
      dispatch(setInitialConfigError(error));
    }
  };
};

export const setInitialConfigFetch = () => ({
  type: GET_INITIAL_CONFIG_FETCH,
});

export const setInitialConfigSuccess = (initialConfig) => ({
  type: GET_INITIAL_CONFIG_SUCCESS,
  payload: initialConfig,
});

export const setInitialConfigError = (error) => ({
  type: GET_INITIAL_CONFIG_ERROR,
  payload: error,
});

export const setConfig = (config) => ({
  type: SET_CONFIG,
  payload: config,
});
