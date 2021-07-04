import React from "react";
import { connect } from "react-redux";
import { NavLink } from "react-router-dom";
import { getEnv } from "utils/env";
import "./Header.scss";

const Header = ({ game, config }) => {
  const loading = config.loading || game.loading;
  const error = config.error || game.error;

  return (
    <div className="header-component">
      <nav>
        <span className="title">
          <NavLink to="/">{getEnv("APP_NAME")}</NavLink>
        </span>
        <br className="visible-xs" />
        <NavLink to="/">
          <i className="glyphicon glyphicon-home"></i> <span>Home</span>
        </NavLink>
        <NavLink to="/configuration">
          <i className="glyphicon glyphicon-cog"></i> Configuration
        </NavLink>
        {loading && <span className="text-warning">Loading ....</span>}
        {error && (
          <span className="label label-danger">Oops, an error ocurred!</span>
        )}
      </nav>
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
    config: state.config,
    game: state.game,
  };
};

export default connect(mapStateToProps)(Header);
