import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Header from "components/Header";
import PageNotFound from "components/PageNotFound";

const Routes = () => {
  return (
    <div className="app-component">
      <Router>
        <Header />
        <Switch>
          <Route component={PageNotFound} />
        </Switch>
      </Router>
    </div>
  );
};

export default Routes;
