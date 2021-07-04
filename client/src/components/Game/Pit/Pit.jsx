import React from "react";
import classNames from "classnames";
import "./Pit.scss";

const Pit = ({ ballsInPit, disabled, play, position }) => {
  return (
    <div className={classNames("pit-wrapper", { disabled })}>
      <div className="pit hole" onClick={() => play(position)}>
        {ballsInPit}
      </div>
    </div>
  );
};

export default Pit;
