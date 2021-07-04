import { combineReducers } from "redux";
import game from "redux/reducers/game";
import config from "redux/reducers/config";

export default combineReducers({ game, config });
