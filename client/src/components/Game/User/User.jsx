import React from "react";
import classNames from "classnames";
import "./User.scss";

const User = ({ email, active, winner, bottomUser }) => {
  return (
    <h2
      className={classNames("user", {
        "bottom-user": bottomUser,
        active: active,
        winner: winner,
      })}
    >
      {email}
    </h2>
  );
};

export default User;
